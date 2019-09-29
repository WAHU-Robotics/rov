package me.jbuelow.rov.dry.controller;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.dry.controller.mock.ErrorMockController;
import me.jbuelow.rov.dry.controller.mock.MockController;
import me.jbuelow.rov.dry.exception.JinputNativesNotFoundException;
import me.jbuelow.rov.dry.ui.setup.JoystickSelecter;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.EventQueue;
import net.java.games.input.Rumbler;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

/**
 * Handles assigning joysticks and retrieving values
 */
@Slf4j
@org.springframework.stereotype.Component // Let Spring load this on startup!
public class Control implements ResourceLoaderAware {

  public static final String NATIVE_FOLDER_PATH_PREFIX = "nativeutils";

  /**
   * Temporary directory which will contain the DLLs.
   */
  private File temporaryDir;

  private ResourceLoader resourceLoader;

  @Getter
  private Controller[] foundControllers;

  @Getter
  private Controller primaryController;

  @Getter
  private Controller secondaryController;

  @Getter
  private List<Controller> selectedControllers = new ArrayList<>(Collections.nCopies(2, null));

  private List<Controller> selectableControllers = new ArrayList<>(20);

  public Control() throws JinputNativesNotFoundException {
    ensureAccessToNatives();
    refreshList();
  }

  /**
   * Loads library from current JAR archive to a temporary directory
   */
  public void loadLibraryFromJar(Resource resource) throws IOException {

    // Prepare temporary file
    if (temporaryDir == null) {
      temporaryDir = createTempDirectory(NATIVE_FOLDER_PATH_PREFIX);
      temporaryDir.deleteOnExit();
      log.debug("Setting net.java.games.input.librarypath: " + temporaryDir);
      System.setProperty("net.java.games.input.librarypath", temporaryDir.getAbsolutePath());
    }

    String outputFileName = resource.getFilename();
    if (OSType.MAC.equals(OSUtils.getOsType())) {
      //OSX uses different file extensions depending on the version
      //Let the System determine the correct extension
      String testLibName = System.mapLibraryName("test");

      if (testLibName.endsWith(".dylib")) {
        outputFileName = outputFileName.replace(".jnilib", ".dylib");
      }
    }

    File temp = new File(temporaryDir, outputFileName);

    try (InputStream is = resource.getInputStream()) {
      Files.copy(is, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
      temp.deleteOnExit();
    } catch (IOException e) {
      temp.delete();
      throw e;
    } catch (NullPointerException e) {
      temp.delete();
      throw new FileNotFoundException("File " + resource.getURL() + " was not found inside JAR.");
    }
  }

  private void ensureAccessToNatives() {
    switch (OSUtils.getOsType()) {
      case MAC:
        loadOSNatives("/osx/*");
        break;
      case LINUX:
        loadOSNatives("/linux/*");
        break;
      case WINDOWS:
        loadOSNatives("/windows/*");
        break;
      default:
        throw new RuntimeException("Unsupported OS has been detected");
    }
    // String path = System.getProperty("java.library.path");
    // log.debug("Found existing java.library.path var: " + path);
    // File[] directories = getDirectoryCandidates(".");
    // log.debug("Found these candidate directories for dll files: " + directories);
    // StringBuilder addPath = new StringBuilder();
    // for (File directory:directories) {
    // addPath.append(directory.getPath());
    // }
    // String newpath = path + addPath;
    // log.debug("Setting new java.library.path var: " + newpath);
    // System.setProperty("java.library.path", newpath);
  }

  private void loadOSNatives(String libPathPattern) {
    try {
      Resource[] libraries = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
          .getResources(libPathPattern);

      for (Resource nativeLibrary : libraries) {
        loadLibraryFromJar(nativeLibrary);
      }
    } catch (IOException e) {
      throw new RuntimeException("Error loading native libraries.", e);
    }
  }

  private File createTempDirectory(String prefix) throws IOException {
    String tempDir = System.getProperty("java.io.tmpdir");
    File generatedDir = new File(tempDir, prefix + System.nanoTime());

    if (!generatedDir.mkdir())
      throw new IOException("Failed to create temp directory " + generatedDir.getName());

    return generatedDir;
  }

  public enum Controllers {
    PRIMARY(0), SECONDARY(1);

    private int id;

    Controllers(int id) {
      this.id = id;
    }
  }

  private void refreshList() throws JinputNativesNotFoundException {
    foundControllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

    if (foundControllers.length == 0) {
      // We should pretty much always find at least a keyboard or mouse,
      // so if nothing shows up its safe to assume that the natives are missing
      // because for some reason jinput doesnt throw any exceptions for this
      throw new JinputNativesNotFoundException();
    }

    for (Controller c : foundControllers) {
      if (c.getType() == Controller.Type.STICK) {
        selectableControllers.add(c);
      }
    }
  }

  private void updateShortcuts() {
    this.primaryController = selectedControllers.get(0);
    this.secondaryController = selectedControllers.get(1);
  }

  public void promptForControllers() throws JinputNativesNotFoundException {
    boolean unset = true;
    Object[] def = new Object[2];
    if (Objects.equals(System.getenv("ROV_DEVMODE"), "true")) {
      selectableControllers.add(new MockController());
      selectableControllers.add(new ErrorMockController());
    }

    while (unset) {
      JoystickSelecter sel = new JoystickSelecter(selectableControllers, def);
      Controller[] controllers = sel.getSelection();

      int i = 0;
      for (Controller c : controllers) {
        if (c == null) {
          refreshList();
          continue;
        }

        if (c.poll()) {
          selectedControllers.set(i, c);
          updateShortcuts();
          unset = false;
        } else {
          couldNotPollErrorMessage(c);
        }
        i++;
      }
    }
  }

  private void couldNotPollErrorMessage(Controller c) throws JinputNativesNotFoundException {
    JOptionPane.showMessageDialog(null,
        "Could not poll '" + c.getName() + "'.\nPlease select a different controller.", "Error",
        JOptionPane.ERROR_MESSAGE);
    refreshList();
  }

  public PolledValues getPolledValues(int controller) throws JinputNativesNotFoundException {
    Controller c = selectedControllers.get(controller);

    if (!c.poll()) {
      couldNotPollErrorMessage(c);
      promptForControllers();
    }

    return new PolledValues(c);
  }

  @Override
  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

}
