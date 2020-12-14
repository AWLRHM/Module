package ir.awlrhm.modules.log.viewmodel;

import java.text.SimpleDateFormat;
import ir.awlrhm.modules.log.investigation.Crash;

public class CrashViewModel {
  private Crash crash;
  private AppInfoViewModel appInfoViewModel;

  public CrashViewModel(Crash crash) {
    populate(crash);
  }

  public String getPlace() {
    String[] placeTrail = crash.getPlaceOfCrash().split("\\.");
    return placeTrail[placeTrail.length - 1];
  }

  public String getExactLocationOfCrash() {
    return crash.getPlaceOfCrash();
  }

  public String getReasonOfCrash() {
    return crash.getReasonOfCrash();
  }

  public String getStackTrace() {
    return crash.getStackTrace();
  }

  public String getCrashInfo() {
    StringBuilder crashInfo = new StringBuilder();
    crashInfo.append("Device Info:\n");

    crashInfo.append("Name: ");
    crashInfo.append(getDeviceName() + "\n");

    crashInfo.append("Brand: ");
    crashInfo.append(getDeviceBrand() + "\n");

    crashInfo.append("Android API: ");
    crashInfo.append(getDeviceAndroidApiVersion() + "\n\n");

    crashInfo.append("App Info:\n");
    crashInfo.append(getAppInfoViewModel().getDetails());
    crashInfo.append("\n");

    crashInfo.append("StackTrace:\n");
    crashInfo.append(getStackTrace() + "\n");

    return crashInfo.toString();
  }

  public String getDeviceManufacturer() {
    return crash.getDeviceInfo().getManufacturer();
  }

  public String getDeviceName() {
    return crash.getDeviceInfo().getName();
  }

  public String getDeviceAndroidApiVersion() {
    return crash.getDeviceInfo().getSdk();
  }

  public String getDeviceBrand() {
    return crash.getDeviceInfo().getBrand();
  }

  public AppInfoViewModel getAppInfoViewModel() {
    return appInfoViewModel;
  }

  public int getIdentifier() {
    return crash.getId();
  }

  public String getDate() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a EEE, MMM d, yyyy");
    return simpleDateFormat.format(crash.getDate());
  }

  public void populate(Crash crash) {
    this.crash = crash;
    this.appInfoViewModel = new AppInfoViewModel(crash.getAppInfo());
  }
}
