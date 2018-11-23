package billstein.harald.leavinghome.controllers;

import billstein.harald.leavinghome.services.ServiceHandler;
import billstein.harald.leavinghome.services.SonosRequestBody;
import billstein.harald.leavinghome.services.Response;
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

  private ServiceHandler serviceHandler;

  public LeavingHomeController(ServiceHandler serviceHandler) {
    this.serviceHandler = serviceHandler;
  }

  @PostMapping(value = "/home/resources/all/")
  public ResponseEntity<Response> toggleOnOff(@RequestBody SonosRequestBody request) {
    LOGGER.info("LeavingHomeController/toggleOnOff - accessed");
    return serviceHandler.setHomeResourcesState(request.isResources());
  }
}
