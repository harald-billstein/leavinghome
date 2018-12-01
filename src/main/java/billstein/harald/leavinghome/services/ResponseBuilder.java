package billstein.harald.leavinghome.services;

public final class ResponseBuilder {

  private String sonosMessage;
  private String philipsHueMessage;

  public Resopnse build() {
    return new Resopnse(sonosMessage, philipsHueMessage);
  }

  private String getSonosMessage() {
    return sonosMessage;
  }

  public ResponseBuilder setSonosMessage(String sonosMessage) {
    this.sonosMessage = sonosMessage;
    return this;
  }

  private String getPhilipsHueMessage() {
    return philipsHueMessage;
  }

  public ResponseBuilder setPhilipsHueMessage(String philipsHueMessage) {
    this.philipsHueMessage = philipsHueMessage;
    return this;
  }

  public class Resopnse {

    private String sonosMessage;
    private String philipsHueMessage;

    public Resopnse(String sonosMessage, String philipsHueMessage) {
      this.sonosMessage = sonosMessage;
      this.philipsHueMessage = philipsHueMessage;
    }

    public String getSonosMessage() {
      return sonosMessage;
    }

    public String getPhilipsHueMessage() {
      return philipsHueMessage;
    }
  }
}



