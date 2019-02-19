package billstein.harald.leavinghome.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServiceHandler {

  private SonosService sonosService;
  private PhilipHueService philipHueService;

  private String pausePath = "http://192.168.0.183:5005/pauseall/";
  private String resumePath = "http://192.168.0.183:5005/resumeall/";

  public ServiceHandler(SonosService sonosService, PhilipHueService philipHueService) {
    this.sonosService = sonosService;
    this.philipHueService = philipHueService;
  }

  public ResponseEntity<ResponseBuilder.Response> setHomeResourcesState(boolean state) {

    String lightsOn;
    String playPauseToggle;

    lightsOn = philipHueService.lightsOn(state);

    if (state) {
      playPauseToggle = sonosService.playPauseToggle(resumePath);

    } else {
      playPauseToggle = sonosService.playPauseToggle(pausePath);
    }

    return ResponseEntity.ok(new ResponseBuilder()
        .setPhilipsHueMessage(lightsOn)
        .setSonosMessage(playPauseToggle)
        .build());
  }
}
