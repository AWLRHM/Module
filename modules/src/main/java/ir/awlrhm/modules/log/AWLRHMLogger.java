package ir.awlrhm.modules.log;

import android.content.Context;
import android.util.Log;

import java.util.List;

import ir.awlrhm.modules.log.database.CrashRecord;
import ir.awlrhm.modules.log.database.AWLRHMDatabaseHelper;
import ir.awlrhm.modules.log.investigation.AppInfoProvider;
import ir.awlrhm.modules.log.investigation.Crash;
import ir.awlrhm.modules.log.investigation.CrashAnalyzer;
import ir.awlrhm.modules.log.investigation.DefaultAppInfoProvider;

public class AWLRHMLogger {
  private static final String TAG = AWLRHMLogger.class.getSimpleName();
  private static AWLRHMLogger instance;
  private final AWLRHMDatabaseHelper database;
//  private final CrashReporter crashReporter;
  private AppInfoProvider appInfoProvider;

  private AWLRHMLogger(Context context) {
    database = new AWLRHMDatabaseHelper(context);
//    crashReporter = new CrashReporter(context);
    appInfoProvider = new DefaultAppInfoProvider(context);
  }

  public static void init(final Context context) {
    Log.d(TAG, "Initializing Sherlock...");
    instance = new AWLRHMLogger(context);

    final Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();

    Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
      analyzeAndReportCrash(throwable);
      handler.uncaughtException(thread, throwable);
    });
  }

  public static boolean isInitialized() {
    return instance != null;
  }

  public static AWLRHMLogger getInstance() {
    if (!isInitialized()) {
      throw new AWLRHMNotInitializedException();
    }
    Log.d(TAG, "Returning existing instance...");
    return instance;
  }

  public List<Crash> getAllCrashes() {
    return getInstance().database.getCrashes();
  }

  private static void analyzeAndReportCrash(Throwable throwable) {
    Log.d(TAG, "Analyzing Crash...");
    CrashAnalyzer crashAnalyzer = new CrashAnalyzer(throwable);
    Crash crash = crashAnalyzer.getAnalysis();
    int crashId = instance.database.insertCrash(CrashRecord.createFrom(crash));
    crash.setId(crashId);
//    instance.crashReporter.report(new CrashViewModel(crash));
    Log.d(TAG, "Crash analysis completed!");
  }

  public static void setAppInfoProvider(AppInfoProvider appInfoProvider) {
    getInstance().appInfoProvider = appInfoProvider;
  }

  public AppInfoProvider getAppInfoProvider() {
    return getInstance().appInfoProvider;
  }
}
