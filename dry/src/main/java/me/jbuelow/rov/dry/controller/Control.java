package me.jbuelow.rov.dry.controller;

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
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.dry.exception.JinputNativesNotFoundException;
import me.jbuelow.rov.dry.ui.setup.JoystickSelecter;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.EventQueue;
import net.java.games.input.Rumbler;

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

  public enum Controllers {
    PRIMARY(0), SECONDARY(1);

    private int id;

    Controllers(int id) {
      this.id = id;
    }
  }

  public Control() throws JinputNativesNotFoundException {
    ensureAccessToNatives();
    refreshList();
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

  private File createTempDirectory(String prefix) throws IOException {
    String tempDir = System.getProperty("java.io.tmpdir");
    File generatedDir = new File(tempDir, prefix + System.nanoTime());

    if (!generatedDir.mkdir())
      throw new IOException("Failed to create temp directory " + generatedDir.getName());

    return generatedDir;
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
    if (selectableControllers.size() <= 0) {
      if (Objects.equals(System.getenv("ROV_DEVMODE"), "true")) {
        selectableControllers.add(new FalseController());
        selectableControllers.add(new BadController());
      } else {
        JOptionPane.showMessageDialog(null,
            "No compatible joysticks connected.\nProgram will exit.", "Error",
            JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }

    for (int position = 0; position < 2; position++) {
      if (position > (selectableControllers.size() - 1)) {
        def[position] = selectableControllers.get(selectableControllers.size() - 1);
      } else {
        def[position] = selectableControllers.get(position);
      }
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

  private class FalseController implements Controller {

    @Override
    public Controller[] getControllers() {
      return new Controller[0];
    }

    @Override
    public Type getType() {
      return null;
    }

    @Override
    public Component[] getComponents() {
      return new Component[0];
    }

    @Override
    public Component getComponent(Identifier identifier) {
      return null;
    }

    @Override
    public Rumbler[] getRumblers() {
      return new Rumbler[0];
    }

    @Override
    public boolean poll() {
      return true;
    }

    @Override
    public void setEventQueueSize(int i) {

    }

    @Override
    public EventQueue getEventQueue() {
      return null;
    }

    @Override
    public PortType getPortType() {
      return null;
    }

    @Override
    public int getPortNumber() {
      return 0;
    }

    @Override
    public String getName() {
      return "False Controller";
    }

    @Override
    public String toString() {
      return getName();
    }
  }

  private class BadController implements Controller {

    @Override
    public Controller[] getControllers() {
      return new Controller[0];
    }

    @Override
    public Type getType() {
      return null;
    }

    @Override
    public Component[] getComponents() {
      return new Component[0];
    }

    @Override
    public Component getComponent(Identifier identifier) {
      return null;
    }

    @Override
    public Rumbler[] getRumblers() {
      return new Rumbler[0];
    }

    @Override
    public boolean poll() {
      return false;
    }

    @Override
    public void setEventQueueSize(int i) {

    }

    @Override
    public EventQueue getEventQueue() {
      return null;
    }

    @Override
    public PortType getPortType() {
      return null;
    }

    @Override
    public int getPortNumber() {
      return 0;
    }

    @Override
    public String getName() {
      return "Bad Controller";
    }

    @Override
    public String toString() {
      return getName();
    }
  }

  @Override
  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

}
