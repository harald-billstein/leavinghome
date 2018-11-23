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

  public ResponseEntity<Response> setHomeResourcesState(boolean state) {
    Response response = new Response();
    response.setSonosMessage(sonosService.playPauseToggle(state));
    response.setPhilipsHueMessage(philipHueService.lightsOn(state));
    return ResponseEntity.ok(response);
  }
}
