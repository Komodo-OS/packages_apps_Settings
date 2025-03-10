package com.android.settings.komodo.about

import android.app.settings.SettingsEnums
import android.content.Context
import android.provider.SearchIndexableResource
import com.android.settings.R
import com.android.settings.dashboard.DashboardFragment
import com.android.settings.deviceinfo.firmwareversion.FirmwareVersionScreen
import com.android.settings.search.BaseSearchIndexProvider
import com.android.settingslib.search.Indexable
import com.android.settingslib.search.SearchIndexable
import com.android.settingslib.core.AbstractPreferenceController
import java.util.ArrayList

@SearchIndexable
class FirmwareVersionSettingsKomodo : DashboardFragment() {

    override fun getPreferenceScreenBindingKey(context: Context): String? {
        return FirmwareVersionScreen.KEY
    }

    override fun getPreferenceScreenResId(): Int {
        return R.xml.komodo_about_device
    }

    override fun getLogTag(): String {
        return "FirmwareVersionSettingsKomodo"
    }

    override fun getMetricsCategory(): Int {
        return SettingsEnums.DIALOG_FIRMWARE_VERSION
    }

    override fun createPreferenceControllers(context: Context): List<AbstractPreferenceController> {
        val controllers: MutableList<AbstractPreferenceController> = ArrayList()
        controllers.add(AboutPreferenceController(context))
        controllers.add(AboutFooterPreferenceController(context))
        return controllers
    }

    companion object {
        @JvmField
        val SEARCH_INDEX_DATA_PROVIDER: Indexable.SearchIndexProvider = object : BaseSearchIndexProvider() {
            override fun getXmlResourcesToIndex(context: Context, enabled: Boolean): List<SearchIndexableResource> {
                val result = ArrayList<SearchIndexableResource>()

                val sir = SearchIndexableResource(context)
                sir.xmlResId = R.xml.komodo_about_device
                result.add(sir)
                return result
            }
        }
    }
}
