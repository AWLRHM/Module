package ir.awlrhm.modules.security;

import android.app.ActivityManager;
import android.content.Context;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;

import static android.content.Context.ACTIVITY_SERVICE;

public class CheckReverseEngineeringToolsUtil {

    public Boolean isExistTools(Context context){
        return getXposedVersion(context) != null
                || isExistFrida(context);
    }

    private Integer getXposedVersion(Context context) {
        try {
            File xposedBridge = new File("/system/framework/XposedBridge.jar");
            if (xposedBridge.exists()) {
                File optimizedDir = context.getDir("dex", Context.MODE_PRIVATE);
                DexClassLoader dexClassLoader = new DexClassLoader(xposedBridge.getPath(),
                        optimizedDir.getPath(), null, ClassLoader.getSystemClassLoader());
                Class<?> XposedBridge = dexClassLoader.loadClass("de.robv.android.xposed.XposedBridge");
                Method getXposedVersion = XposedBridge.getDeclaredMethod("getXposedVersion");
                if (!getXposedVersion.isAccessible()) getXposedVersion.setAccessible(true);
                return (Integer) getXposedVersion.invoke(null);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public boolean isExistFrida(Context context){

        boolean returnValue = false;
//  Get currently running application processes

        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = null;
        if (manager != null) {
            list = manager.getRunningServices (300);
        }

        if(list != null){
            String tempName;
            for(int i=0;i<list.size();++i){
                tempName = list.get(i).process;
                if(tempName.contains("fridaserver"))   {
                    returnValue = true ;
                }
            }
        }return returnValue ;
    }
}
