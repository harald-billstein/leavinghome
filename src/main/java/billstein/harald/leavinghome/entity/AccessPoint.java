package billstein.harald.leavinghome.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccessPoint {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String ipAddress;
  private String username;
  private String bridgeID;
  private String macAddress;


  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getBridgeID() {
    return bridgeID;
  }

  public void setBridgeID(String bridgeID) {
    this.bridgeID = bridgeID;
  }

  public String getMacAddress() {
    return macAddress;
  }

  public void setMacAddress(String macAddress) {
    this.macAddress = macAddress;
  }
}
