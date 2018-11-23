package billstein.harald.leavinghome.controllers;

import billstein.harald.leavinghome.services.PhilipHueService;
import billstein.harald.leavinghome.services.SonosRequestBody;
import billstein.harald.leavinghome.services.SonosResponse;
import billstein.harald.leavinghome.services.SonosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
@Profile({"prod","dev"})
public class LeavingHomeController {

  private static final Logger LOGGER = LoggerFactory.getLogger(LeavingHomeController.class);

  private SonosService sonosService;
  private PhilipHueService philipHueService;
  private boolean toggle;

  public LeavingHomeController(SonosService sonosService, PhilipHueService philipHueService) {
    this.sonosService = sonosService;
    this.philipHueService = philipHueService;
  }

  @PostMapping(value = "/home/resources/all/")
  public ResponseEntity<SonosResponse> toggleOnOff(@RequestBody SonosRequestBody request) {
    LOGGER.info("LeavingHomeController/toggleOnOff - accessed");

    philipHueService.toggleAllLights(toggle);
    sonosService.playPauseToggle(toggle);

    toggle = !toggle;
    // TODO MAKE BETTER RESPONSE
    // TODO Toggle should first shutoff but if all is off turn on
    return ResponseEntity.ok(new SonosResponse());
  }
}
