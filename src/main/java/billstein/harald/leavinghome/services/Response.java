package billstein.harald.leavinghome.services;

import org.springframework.stereotype.Component;

@Component
public class Response {

  private String sonosMessage;
  private String philipsHueMessage;

  public String getSonosMessage() {
    return sonosMessage;
  }

  public void setSonosMessage(String sonosMessage) {
    this.sonosMessage = sonosMessage;
  }

  public String getPhilipsHueMessage() {
    return philipsHueMessage;
  }

  public void setPhilipsHueMessage(String philipsHueMessage) {
    this.philipsHueMessage = philipsHueMessage;
  }
}
