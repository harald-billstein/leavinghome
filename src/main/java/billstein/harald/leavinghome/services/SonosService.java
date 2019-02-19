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

  public String playPauseToggle(String url) {
    LOGGER.info("SonosService/playPauseToggle - accessed");

    Integer responseCode = null;

    try {
      URL urlToUse = new URL(url);

      HttpURLConnection httpURLConnection = (HttpURLConnection) urlToUse.openConnection();
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
