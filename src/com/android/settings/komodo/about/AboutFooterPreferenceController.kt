package com.android.settings.komodo.about

import android.content.Context
import android.os.SELinux
import android.widget.TextView
import androidx.preference.PreferenceScreen
import com.android.settings.R
import com.android.settingslib.DeviceInfoUtils
import com.android.settingslib.core.AbstractPreferenceController
import com.android.settingslib.widget.LayoutPreference

class AboutFooterPreferenceController(context: Context) : AbstractPreferenceController(context) {

    companion object {
        private const val KEY_KOMODO_ABOUT_FOOTER_INFO = "about_phone_info_footer"
    }

    override fun displayPreference(screen: PreferenceScreen) {
        super.displayPreference(screen)
        val komodoFooterInfoPreference = screen.findPreference<LayoutPreference>(KEY_KOMODO_ABOUT_FOOTER_INFO)
        komodoFooterInfoPreference?.let { layoutPreference ->
            val selinux = layoutPreference.findViewById<TextView>(R.id.selinux_summary)
            val secpatch = layoutPreference.findViewById<TextView>(R.id.secpatch_summary)

            selinux.text = if (SELinux.isSELinuxEnforced()) {
                mContext.getString(R.string.selinux_status_enforcing)
            } else {
                mContext.getString(R.string.selinux_status_permissive)
            }

            secpatch.text = DeviceInfoUtils.getSecurityPatch()
        }
    }

    override fun isAvailable(): Boolean = true

    override fun getPreferenceKey(): String = KEY_KOMODO_ABOUT_FOOTER_INFO

}