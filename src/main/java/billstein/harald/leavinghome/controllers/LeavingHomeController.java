package billstein.harald.leavinghome.controllers;

import billstein.harald.leavinghome.services.SonosRequestBody;
import billstein.harald.leavinghome.services.SonosResponse;
import billstein.harald.leavinghome.services.SonosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class LeavingHomeController {

  @PostMapping(value = "/home/resources/all/")
  public ResponseEntity<SonosResponse> turnOff(@RequestBody SonosRequestBody request) {
    return new SonosService().pauseAll();
  }
}
