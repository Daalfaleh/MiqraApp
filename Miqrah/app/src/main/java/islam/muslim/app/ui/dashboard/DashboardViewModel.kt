package islam.muslim.app.ui.dashboard

import islam.muslim.app.R
import islam.muslim.app.base.BaseViewModel
import islam.muslim.app.models.DashboardModel
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(val app: Application) : BaseViewModel() {

    private val _dashboardResponse = MutableLiveData<ArrayList<DashboardModel>>()
    val dashboardResponse: LiveData<ArrayList<DashboardModel>> = _dashboardResponse

    fun getData() {
        val dashboardList = arrayListOf<DashboardModel>()

        val options = arrayOf(
            "Prayer Timings",
            "Tasbeeh Counter",
            "Shirk",
            "Qibla Compass",
            "Learn Quran",
            "6 kalma",
            "Azkar",
            "Zakat Calculator",
            "Weather",
            "Allah 99 Names",
            "Islamic Calendar",
            "Prayer Guide"
        )
        val icons = arrayOf<Int>(
            R.drawable.aqeda,
            R.drawable.arabia,
            R.drawable.falsfa,
            R.drawable.hadis,
            R.drawable.mnatk,
            R.drawable.quranic,
            R.drawable.wasola,
            R.drawable.tarikh,
        )

        for (i in icons.indices) {
            dashboardList.add(DashboardModel(options[i], icons[i]))
        }

        _dashboardResponse.postValue(dashboardList)
    }

}