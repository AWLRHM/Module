package ir.awlrhm.modules.log.investigation;

import android.content.Context;

import ir.awlrhm.modules.log.util.AppInfoUtil;

public class DefaultAppInfoProvider implements AppInfoProvider {
  private final Context context;

  public DefaultAppInfoProvider(Context context) {
    this.context = context;
  }

  @Override
  public AppInfo getAppInfo() {
    return new AppInfo.Builder()
        .with("Version", AppInfoUtil.getAppVersion(context))
        .build();
  }
}
