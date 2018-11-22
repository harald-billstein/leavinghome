package billstein.harald.leavinghome.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SonosService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SonosService.class);
  private String pausePath = "http://192.168.0.183:5005/pauseall/";
  private String resumePath = "http://192.168.0.183:5005/resumeall/";

  public ResponseEntity<SonosResponse> playPauseToggle(boolean toggle) {
    LOGGER.info("SonosService/playPauseToggle - accessed");

    Integer responseCode = null;

    try {
      URL url;

      if (toggle) {
         url = new URL(pausePath);
      } else {
        url = new URL(resumePath);
      }

      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.setRequestMethod("GET");
      responseCode = httpURLConnection.getResponseCode();
    } catch (IOException e) {
      e.printStackTrace();
    }

    SonosResponse sonosResponse = new SonosResponse();
    if (responseCode == 200) {
      sonosResponse.setSuccess(true);
      sonosResponse.setMessage("Affirmative, Dave. I read you.");

    } else {
      sonosResponse.setSuccess(false);
      sonosResponse.setMessage("It can only be attributable to human error.");
      LOGGER.error("COULD_NOT_ACCESS_RESOURCE EXCEPTION - " + responseCode);
    }

    return ResponseEntity.ok(sonosResponse);
  }
}
