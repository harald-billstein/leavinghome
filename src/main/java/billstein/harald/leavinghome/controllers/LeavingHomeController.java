package billstein.harald.leavinghome.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
public class LeavingHomeController {

  @GetMapping("home/resources/all")
  public ResponseEntity<String> turnOff() {
    return ResponseEntity.ok("{\"content\":\"Hello, World!\"}");
  }

}
