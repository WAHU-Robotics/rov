package me.jbuelow.rov.common.capabilities;

import lombok.Data;

@Data
public abstract class AbstractCapability implements Capability {

  private static final long serialVersionUID = 5483228059601905629L;

  private int id;
  private String name;

}
