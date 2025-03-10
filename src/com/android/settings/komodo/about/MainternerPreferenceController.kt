package com.android.settings.komodo.about

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.SystemProperties
import android.text.TextUtils
import android.util.Log
import androidx.preference.Preference
import com.android.settings.R
import com.android.settings.core.BasePreferenceController

class MainternerPreferenceController(context: Context, key: String) :
    BasePreferenceController(context, key) {

    companion object {
        private const val TAG = "MaintainerKomodoCtrl"
        private val INTENT_URI_DATA: Uri =
            Uri.parse("https://komodo-os.my.id/team")
        private const val PROP_KOMODO_MAINTAINER = "org.komodo.maintainer"
    }

    private val mPackageManager: PackageManager = mContext.packageManager
    private val mMaintainer: String = SystemProperties.get(
        PROP_KOMODO_MAINTAINER,
        mContext.getString(R.string.maintainer_info_default)
    )

    override fun getAvailabilityStatus(): Int =
        if (!TextUtils.isEmpty(mMaintainer)) AVAILABLE else CONDITIONALLY_UNAVAILABLE

    override fun getSummary(): CharSequence = mMaintainer

    override fun handlePreferenceTreeClick(preference: Preference): Boolean {
        if (!TextUtils.equals(preference.key, preferenceKey)) {
            return false
        }
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = INTENT_URI_DATA
        }
        if (mPackageManager.queryIntentActivities(intent, 0).isEmpty()) {
            // Jangan mengirimkan intent untuk mencegah crash
            Log.w(TAG, "queryIntentActivities() returns empty")
            return true
        }
        mContext.startActivity(intent)
        return true
    }
}