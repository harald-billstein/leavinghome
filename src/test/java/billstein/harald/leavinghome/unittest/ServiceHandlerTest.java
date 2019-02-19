package billstein.harald.leavinghome.unittest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import billstein.harald.leavinghome.services.PhilipHueService;
import billstein.harald.leavinghome.services.ResponseBuilder.Response;
import billstein.harald.leavinghome.services.ServiceHandler;
import billstein.harald.leavinghome.services.SonosService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class ServiceHandlerTest {

  private static final String SUCCESS_MESSAGE = "Success";
  private String pausePath = "http://192.168.0.183:5005/pauseall/";
  private String resumePath = "http://192.168.0.183:5005/resumeall/";

  private SonosService sonosService;
  private PhilipHueService philipHueService;
  private ServiceHandler serviceHandler;

  @Before
  public void init() {
     sonosService = Mockito.mock(SonosService.class);
     philipHueService = Mockito.mock(PhilipHueService.class);
     serviceHandler = new ServiceHandler(sonosService, philipHueService);
  }

  @After
  public void cleanup() {
    reset(sonosService, philipHueService);
  }


  @Test
  public void resourcesOff() {
    when(sonosService.playPauseToggle(pausePath)).thenReturn(SUCCESS_MESSAGE);
    when(philipHueService.lightsOn(false)).thenReturn(SUCCESS_MESSAGE);

    ResponseEntity<Response> response = serviceHandler.setHomeResourcesState(false);

    assertEquals(SUCCESS_MESSAGE, response.getBody().getSonosMessage());
    assertEquals(SUCCESS_MESSAGE, response.getBody().getPhilipsHueMessage());

    verify(sonosService, times(1)).playPauseToggle(pausePath);
    verify(philipHueService, times(1)).lightsOn(false);

  }

  @Test
  public void resourcesOn() {
    when(sonosService.playPauseToggle(resumePath)).thenReturn(SUCCESS_MESSAGE);
    when(philipHueService.lightsOn(true)).thenReturn(SUCCESS_MESSAGE);

    ResponseEntity<Response> response = serviceHandler.setHomeResourcesState(true);

    assertEquals(SUCCESS_MESSAGE, response.getBody().getSonosMessage());
    assertEquals(SUCCESS_MESSAGE, response.getBody().getPhilipsHueMessage());

    verify(sonosService, times(1)).playPauseToggle(resumePath);
    verify(philipHueService, times(1)).lightsOn(true);
  }

  @Test
  @Ignore("code refactor needed")
  public void resourceThrowsException(){
    when(sonosService.playPauseToggle(resumePath)).thenThrow(new RuntimeException());
    when(philipHueService.lightsOn(true)).thenReturn(SUCCESS_MESSAGE);

    ResponseEntity<Response> response = serviceHandler.setHomeResourcesState(true);

  }

}
