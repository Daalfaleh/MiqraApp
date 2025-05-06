package islam.muslim.app.ui.splash

import islam.muslim.app.Helper.GpsTracker
import islam.muslim.app.base.BaseViewModel
import islam.muslim.app.utils.GeneralUtils.isMyServiceRunning
import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val appContext: Application) : BaseViewModel() {

    val GRANTED = 1
    val NOT_GRANTED = 2

    val _grantedStatusObservable = MutableLiveData<Int>(0)
    val grantedStatusObservable: LiveData<Int> = _grantedStatusObservable

    fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                _grantedStatusObservable.postValue(NOT_GRANTED)
            } else {
                val gpsService = GpsTracker(appContext)
                val intent = Intent(appContext, gpsService.javaClass)
                if (!isMyServiceRunning(appContext, gpsService.javaClass)) {
                    appContext.startService(intent)
                }
                _grantedStatusObservable.postValue(GRANTED)
            }
        } else {
            _grantedStatusObservable.postValue(GRANTED)
        }
    }

}