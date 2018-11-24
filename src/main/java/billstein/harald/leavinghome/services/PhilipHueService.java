package billstein.harald.leavinghome.services;

import billstein.harald.leavinghome.entity.AccessPoint;
import billstein.harald.leavinghome.repository.PHAccessPointRepository;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"prod", "dev"})
public class PhilipHueService implements PHSDKListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhilipHueService.class);
  private PHAccessPointRepository phAccessPointRepository;
  private PHHueSDK phHueSDK;
  private AccessPoint accessPoint;
  private PHBridge phBridge;

  public PhilipHueService(PHAccessPointRepository phAccessPointRepository) {
    this.phAccessPointRepository = phAccessPointRepository;
    phHueSDK = PHHueSDK.getInstance();
    init();
    quickConnect();
  }

  private void init() {
    accessPoint = new AccessPoint();
    phHueSDK.setAppName("LeavingHome");
    phHueSDK.setDeviceName("Raspberry Pie");
    phHueSDK.getNotificationManager().registerSDKListener(this);
  }

  private void searchForNewBridge() {
    LOGGER.info("searchForNewBridge");
    PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK
        .getSDKService(PHHueSDK.SEARCH_BRIDGE);
    sm.search(true, true);
  }

  private void quickConnect() {
    LOGGER.info("quickConnect");

    List<AccessPoint> accessPointList = phAccessPointRepository.findAll();

    if (accessPointList.size() > 0) {
      PHAccessPoint phAccessPoint = new PHAccessPoint();
      phAccessPoint.setIpAddress(accessPointList.get((accessPointList.size() - 1)).getIpAddress());
      phAccessPoint.setUsername(accessPointList.get((accessPointList.size() - 1)).getUsername());
      phAccessPoint.setBridgeId(accessPointList.get((accessPointList.size() - 1)).getBridgeID());
      phAccessPoint.setMacAddress(accessPointList.get((accessPointList.size() - 1)).getMacAddress());
      this.accessPoint = accessPointList.get(0);

      phHueSDK.connect(phAccessPoint);
    } else {
      searchForNewBridge();
    }
  }

  public String lightsOn(boolean state) {
    LOGGER.info("lightsOn - accessed");

    String message;
    PHLightState lightState = new PHLightState();
    lightState.setOn(state);

    List<PHLight> lights = phBridge.getResourceCache().getAllLights();

    if (lights.size() > 0) {
      for (PHLight phLight : lights) {
        phBridge.updateLightState(phLight, lightState);
      }
      message = "Success";
    } else {
      message = "Failed, didn't find any lights";
    }
    return message;
  }

  @Override
  public void onCacheUpdated(List<Integer> list, PHBridge phBridge) {
    // Here you receive notifications that the BridgeResource Cache was updated. Use the
    // PHMessageType to check which cache was updated, e.g.
    LOGGER.debug("onCacheUpdated");
    if (list.contains(PHMessageType.LIGHTS_CACHE_UPDATED)) {
      this.phBridge = phBridge;
    }

  }

  @Override
  public void onBridgeConnected(PHBridge phBridge, String s) {
    // Here it is recommended to set your connected bridge in your sdk object (as above) and start
    // the heartbeat. At this point you are connected to a bridge so you should pass control to your
    // main program/activity. The username is generated randomly by the bridge. Also it is
    // recommended you store the connected IP Address/ Username in your app here.  This will allow
    // easy automatic connection on subsequent use.
    LOGGER.info("onBridgeConnected");

    // TODO save just newly created users
    if (accessPoint.getUsername() == null) {
      System.out.println("Createing new user!!");
      accessPoint.setUsername(s);
      phAccessPointRepository.save(accessPoint);
    }

    this.phBridge = phBridge;

    phHueSDK.setSelectedBridge(phBridge);
    phHueSDK.enableHeartbeat(phBridge, PHHueSDK.HB_INTERVAL);

  }

  @Override
  public void onAuthenticationRequired(PHAccessPoint phAccessPoint) {
    // Arriving here indicates that Push linking is required (to prove the User has physical access
    // to the bridge).  Typically here you will display a push link image (with a timer) indicating
    // to to the user they need to push the button on their bridge within 30 seconds.
    LOGGER.info("onAuthenticationRequired");
    phHueSDK.startPushlinkAuthentication(phAccessPoint);
  }

  @Override
  public void onAccessPointsFound(List<PHAccessPoint> list) {
    // Handle your bridge search results here.  Typically if multiple results are returned you will
    // want to display them in a list and let the user select their bridge.   If one is found you
    // may opt to connect automatically to that bridge.
    LOGGER.info("onAccessPointsFound - bridges found: " + list.size());
    PHAccessPoint phAccessPoint = list.get(0);

    this.accessPoint = new AccessPoint();
    accessPoint.setIpAddress(phAccessPoint.getIpAddress());
    accessPoint.setBridgeID(phAccessPoint.getBridgeId());
    accessPoint.setMacAddress(phAccessPoint.getMacAddress());

    phHueSDK.connect(phAccessPoint);
  }

  @Override
  public void onError(int i, String s) {
    // Here you can handle events such as Bridge Not Responding, Authentication Failed and Bridge
    // Not Found
    if (i == 46) {
      LOGGER.info("NO BRIDGE FOUND - SEARCH STARTING");
      searchForNewBridge();
    } else if (i == 101) {
      LOGGER.info("PRESS BRIDGE BUTTON TO APPROVE ACCESS");
    } else if (i == 1158) {
      LOGGER.info("AUTHENTICATION FAILED - SEARCH STARTING");
      searchForNewBridge();
    } else {
      LOGGER.info("onError " + i + " " + s);
    }
  }

  @Override
  public void onConnectionResumed(PHBridge phBridge) {
    LOGGER.debug("onConnectionResumed");
  }

  @Override
  public void onConnectionLost(PHAccessPoint phAccessPoint) {
    // Here you would handle the loss of connection to your bridge.
    LOGGER.info("onConnectionLost");
    quickConnect();
  }

  @Override
  public void onParsingErrors(List<PHHueParsingError> list) {
    // Any JSON parsing errors are returned here.  Typically your program should never return these.
    LOGGER.info("onParsingErrors");
  }
}
