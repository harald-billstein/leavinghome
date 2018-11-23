package billstein.harald.leavinghome.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"prod", "dev"})
public class SonosService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SonosService.class);
  private String pausePath = "http://192.168.0.183:5005/pauseall/";
  private String resumePath = "http://192.168.0.183:5005/resumeall/";

  public String playPauseToggle(boolean state) {
    LOGGER.info("SonosService/playPauseToggle - accessed");

    Integer responseCode = null;

    try {
      URL url;

      if (state) {
        System.out.println("play");
        url = new URL(resumePath);
      } else {
        System.out.println("stop");
        url = new URL(pausePath);
      }

      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.setRequestMethod("GET");
      responseCode = httpURLConnection.getResponseCode();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String response;
    if (responseCode == 200) {
      response = "Success";
    } else {
      response = "Failed INTERNAL ERROR";
      LOGGER.error("COULD_NOT_ACCESS_RESOURCE EXCEPTION - " + responseCode);
    }

    return response;
  }
}
