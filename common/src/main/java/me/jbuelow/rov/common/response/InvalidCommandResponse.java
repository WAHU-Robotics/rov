package me.jbuelow.rov.common.response;

public class InvalidCommandResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = -553494138497703879L;

  public InvalidCommandResponse(Exception e) {
    super(false, e);
  }
}
