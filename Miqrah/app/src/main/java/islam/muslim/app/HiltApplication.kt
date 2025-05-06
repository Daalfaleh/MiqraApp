package islam.muslim.app

import android.Manifest
import islam.muslim.app.utils.DataBaseFile
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.MobileAds
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication : Application(){

    lateinit var dbFile: DataBaseFile

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this)
            .build()
        MobileAds.initialize(this, resources.getString(R.string.admob__app_id))
        dbFile = DataBaseFile(this)


    }

    companion object {
        private var instance: HiltApplication? = null
        fun getInstance(): HiltApplication? {
            if (instance == null) {
                synchronized(HiltApplication::class.java) {
                    if (instance == null) instance = HiltApplication()
                }
            }
            // Return the instance
            return instance
        }
    }

}