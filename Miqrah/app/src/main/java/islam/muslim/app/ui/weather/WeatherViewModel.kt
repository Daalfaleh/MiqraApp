package islam.muslim.app.ui.weather

import android.app.Application
import islam.muslim.app.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(val appContext: Application) : BaseViewModel() {


}