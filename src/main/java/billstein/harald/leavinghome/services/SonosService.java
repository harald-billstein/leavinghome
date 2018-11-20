package billstein.harald.leavinghome.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SonosService {


  public ResponseEntity<SonosResponse> pauseAll() {

    Integer responseCode = null;

    try {
      URL url = new URL("http://192.168.0.183:5005/pauseall/");
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.setRequestMethod("GET");
      responseCode = httpURLConnection.getResponseCode();
    } catch (IOException e) {
      e.printStackTrace();
    }

    SonosResponse sonosResponse = new SonosResponse();
    if (responseCode == 200) {
      sonosResponse.setSuccess(true);
      sonosResponse.setMessage("And all is quiet");

    } else {
      sonosResponse.setSuccess(false);
      sonosResponse.setMessage("Something went wrong!");
      // TODO LOG
    }

    return ResponseEntity.ok(sonosResponse);
  }
}
