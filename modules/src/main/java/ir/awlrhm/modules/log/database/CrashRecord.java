package ir.awlrhm.modules.log.database;

import java.text.SimpleDateFormat;

import ir.awlrhm.modules.log.investigation.Crash;

public class CrashRecord {
  private String place;
  private String reason;
  private String date;
  private String stackTrace;

  public CrashRecord(String place, String reason, String date, String stackTrace) {
    this.place = place;
    this.reason = reason;
    this.date = date;
    this.stackTrace = stackTrace;
  }

  public String getPlace() {
    return place;
  }

  public String getReason() {
    return reason;
  }

  public String getDate() {
    return date;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public static CrashRecord createFrom(Crash crash) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(Crash.DATE_FORMAT);
    return new CrashRecord(crash.getPlaceOfCrash(), crash.getReasonOfCrash(), dateFormat.format(crash.getDate()), crash.getStackTrace());
  }
}
