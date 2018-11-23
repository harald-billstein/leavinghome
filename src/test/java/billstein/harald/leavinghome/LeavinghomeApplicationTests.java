package billstein.harald.leavinghome;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeavinghomeApplicationTests {


  private static final String VALID_USER = "user";
  private static final String VALID_PASSWORD = "password";
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;
  private String path = "http://nasareth.synology.me:8080/v1/home/resources/all/";

  @Before
  public void setUpMockMvc() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void test() throws Exception {
    mvc.perform(RestDocumentationRequestBuilders
        .get(path)
        .secure(true)
        .with(httpBasic(VALID_USER, VALID_PASSWORD)));


  }
}
