package me.jbuelow.rov.common.capabilities;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public abstract class AbstractCapability implements Capability {

  private static final long serialVersionUID = 5483228059601905629L;

  @Getter()
  private final UUID id;
  
  @Getter
  @Setter
  private String name;

  AbstractCapability() {
    this(UUID.randomUUID());
  }

  AbstractCapability(UUID id) {
    this.id = id;
  }
}
