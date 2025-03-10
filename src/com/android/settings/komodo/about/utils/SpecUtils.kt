package com.android.settings.komodo.about.utils

import android.content.Context
import android.os.Environment
import android.os.StatFs
import android.view.Display
import android.view.WindowManager
import android.util.DisplayMetrics
import android.graphics.Point
import com.android.internal.os.PowerProfile
import com.android.internal.util.MemInfoReader

object SpecUtils {
    private const val POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile"
    private var aproxStorage: String? = null

    @JvmStatic
    fun getTotalInternalMemorySize(): String {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val total = (totalBlocks * blockSize).toDouble() / 1073741824
        val lastval = Math.round(total).toInt()
        aproxStorage = when {
            lastval in 1..16 -> "16"
            lastval in 17..32 -> "32"
            lastval in 33..64 -> "64"
            lastval in 65..128 -> "128"
            lastval in 129..256 -> "256"
            lastval in 257..512 -> "512"
            lastval > 512 -> "512+"
            else -> "null"
        }
        return aproxStorage!!
    }

    @JvmStatic
    fun getTotalRAM(): Int {
        val memReader = MemInfoReader()
        memReader.readMemInfo()
        val totalMem = memReader.totalSize.toDouble()
        // Penyesuaian untuk perangkat 4GB yang mungkin menunjukkan 3.48GB
        val gb = (totalMem / 1073741824) + 0.3
        return Math.round(gb).toInt()
    }

    @JvmStatic
    fun getScreenRes(context: Context): String {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y + getNavigationBarHeight(windowManager)
        return "$width x $height"
    }

    private fun getNavigationBarHeight(wm: WindowManager): Int {
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        val usableHeight = metrics.heightPixels
        wm.defaultDisplay.getRealMetrics(metrics)
        val realHeight = metrics.heightPixels
        return if (realHeight > usableHeight) realHeight - usableHeight else 0
    }

    @JvmStatic
    fun getBatteryCapacity(context: Context): Int {
        var powerProfile: Any? = null
        var batteryCapacity = 0.0
        try {
            powerProfile = Class.forName(POWER_PROFILE_CLASS)
                .getConstructor(Context::class.java)
                .newInstance(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            batteryCapacity = Class.forName(POWER_PROFILE_CLASS)
                .getMethod("getAveragePower", String::class.java)
                .invoke(powerProfile, "battery.capacity") as Double
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val capacityStr = batteryCapacity.toString().split(".")
        return capacityStr[0].toInt()
    }
}
