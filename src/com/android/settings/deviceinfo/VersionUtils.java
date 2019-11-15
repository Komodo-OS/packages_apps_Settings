
package com.android.settings.deviceinfo;

import android.os.SystemProperties;

public class VersionUtils {
    public static String getKomodoVersion(){
        return SystemProperties.get("org.komodo.version.display","");
    }
}
