package billstein.harald.leavinghome.controllers;

import billstein.harald.leavinghome.services.SonosRequestBody;
import billstein.harald.leavinghome.services.SonosResponse;
import billstein.harald.leavinghome.services.SonosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class LeavingHomeController {

  private static final Logger LOGGER = LoggerFactory.getLogger(LeavingHomeController.class);

  private SonosService sonosService;

  public LeavingHomeController(SonosService sonosService) {
    this.sonosService = sonosService;
  }

  @PostMapping(value = "/home/resources/all/")
  public ResponseEntity<SonosResponse> toggleOnOff(@RequestBody SonosRequestBody request) {
    LOGGER.info("LeavingHomeController/toggleOnOff - accessed");


    return sonosService.playPauseToggle();
  }
}
