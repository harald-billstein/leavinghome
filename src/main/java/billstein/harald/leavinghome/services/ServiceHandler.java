package billstein.harald.leavinghome.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServiceHandler {

  private SonosService sonosService;
  private PhilipHueService philipHueService;

  public ServiceHandler(SonosService sonosService, PhilipHueService philipHueService) {
    this.sonosService = sonosService;
    this.philipHueService = philipHueService;
  }

  public ResponseEntity<ResponseBuilder.Resopnse> setHomeResourcesState(boolean state) {
    ResponseBuilder.Resopnse response = new ResponseBuilder()
        .setPhilipsHueMessage(philipHueService.lightsOn(state))
        .setSonosMessage(sonosService.playPauseToggle(state))
        .build();

    return ResponseEntity.ok(response);
  }
}
