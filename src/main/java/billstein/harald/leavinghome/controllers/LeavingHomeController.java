package billstein.harald.leavinghome.controllers;

import billstein.harald.leavinghome.services.SonosResponse;
import billstein.harald.leavinghome.services.SonosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
public class LeavingHomeController {

  @GetMapping("home/resources/all")
  public ResponseEntity<SonosResponse> turnOff() {
    SonosService sonosService = new SonosService();
    return sonosService.pauseAll();
  }
}
