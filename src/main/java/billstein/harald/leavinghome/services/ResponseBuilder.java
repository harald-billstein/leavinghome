package billstein.harald.leavinghome.services;

public final class ResponseBuilder {

  private String sonosMessage;
  private String philipsHueMessage;

  Response build() {
    return new Response(sonosMessage, philipsHueMessage);
  }

  ResponseBuilder setSonosMessage(String sonosMessage) {
    this.sonosMessage = sonosMessage;
    return this;
  }

  ResponseBuilder setPhilipsHueMessage(String philipsHueMessage) {
    this.philipsHueMessage = philipsHueMessage;
    return this;
  }

  private class Response {

    private String sonosMessage;
    private String philipsHueMessage;

    private Response(String sonosMessage, String philipsHueMessage) {
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



