package ir.awlrhm.modules.log.investigation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ir.awlrhm.modules.log.AWLRHMLogger;


public class Crash {
  private int id;
  private DeviceInfo deviceInfo;
  private AppInfo appInfo;
  private String placeOfCrash;
  private String reasonOfCrash;
  private String stackTrace;
  private Date date;
  public static final String DATE_FORMAT = "EEE MMM dd kk:mm:ss z yyyy";

  public Crash(String place, String reasonOfCrash, String stackTrace) {
    this.placeOfCrash = place;
    this.stackTrace = stackTrace;
    this.reasonOfCrash = reasonOfCrash;
    this.date = new Date();
    this.deviceInfo = DeviceInfoProvider.getDeviceInfo();
    if (AWLRHMLogger.isInitialized()) {
      this.appInfo = AWLRHMLogger.getInstance().getAppInfoProvider().getAppInfo();
    }
  }

  public Crash(int id, String placeOfCrash, String reasonOfCrash, String stacktrace, String date) {
    this(placeOfCrash, reasonOfCrash, stacktrace);
    this.id = id;
    DateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
    try {
      this.date = df.parse(date);
    } catch (ParseException e) {
      this.date = new Date();
    }
  }

  public DeviceInfo getDeviceInfo() {
    return deviceInfo;
  }

  public AppInfo getAppInfo() {
    return appInfo;
  }

  public Class<Crash> getType() {
    return Crash.class;
  }

  public String getReasonOfCrash() {
    return reasonOfCrash;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public String getPlaceOfCrash() {
    return placeOfCrash;
  }

  public int getId() {
    return id;
  }

  public Date getDate() {
    return date;
  }

  public void setId(int id) {
    this.id = id;
  }
}
