package com.android.settings.komodo.about

import android.content.Context
import android.os.SystemProperties
import android.widget.TextView
import androidx.preference.PreferenceScreen
import com.android.settings.R
import com.android.settings.komodo.about.utils.SpecUtils
import com.android.settingslib.core.AbstractPreferenceController
import com.android.settingslib.widget.LayoutPreference

class AboutPreferenceController(context: Context) : AbstractPreferenceController(context) {

    companion object {
        private const val KEY_KOMODO_ABOUT_INFO = "about_phone_info_header"
        private const val PROP_KOMODO_DEVICE = "ro.product.model"
    }

    override fun displayPreference(screen: PreferenceScreen) {
        super.displayPreference(screen)
        val komodoInfoPreference = screen.findPreference<LayoutPreference>(KEY_KOMODO_ABOUT_INFO)
        komodoInfoPreference?.let { layoutPreference ->
            val device = layoutPreference.findViewById<TextView>(R.id.device_summary)
            val storage = layoutPreference.findViewById<TextView>(R.id.memory_storage_summary)
            val battery = layoutPreference.findViewById<TextView>(R.id.battery_size_summary)
            val infoScreen = layoutPreference.findViewById<TextView>(R.id.screen_res_summary)
            val komodoDevice = SystemProperties.get(
                PROP_KOMODO_DEVICE,
                mContext.getString(R.string.device_info_default)
            )
            device.text = komodoDevice
            storage.text = "${SpecUtils.getTotalInternalMemorySize()}GB ROM + ${SpecUtils.getTotalRAM()}GB RAM"
            battery.text = "${SpecUtils.getBatteryCapacity(mContext)} mAh"
            infoScreen.text = SpecUtils.getScreenRes(mContext)
        }
    }

    override fun isAvailable(): Boolean = true

    override fun getPreferenceKey(): String = KEY_KOMODO_ABOUT_INFO
}
