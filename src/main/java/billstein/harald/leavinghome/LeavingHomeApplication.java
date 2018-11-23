package billstein.harald.leavinghome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class LeavingHomeApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(LeavingHomeApplication.class);
  private static String profile;

  public LeavingHomeApplication(Environment environment) {
    profile = environment.getActiveProfiles()[0];
  }

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(LeavingHomeApplication.class, args);

    if (profile.equalsIgnoreCase("dev") || profile.equalsIgnoreCase("prod")) {
      LOGGER.debug("PROFILE - ACCEPTED");
    } else {
      SpringApplication.exit(context, (ExitCodeGenerator) () -> {
        LOGGER.error("WRONG PROFILE");
        return 0;
      });
    }
  }
}
