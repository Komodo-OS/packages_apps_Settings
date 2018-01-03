
package com.android.settings.deviceinfo;

import android.os.SystemProperties;

public class VersionUtils {
    public static String getKomodoBuildFingerprint(){
        return SystemProperties.get("org.komodo.fingerprint","");
    }
}
