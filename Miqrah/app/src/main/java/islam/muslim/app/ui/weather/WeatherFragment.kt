package islam.muslim.app.ui.weather

import islam.muslim.app.Adapters.NextWeatherAdapter
import islam.muslim.app.Adapters.TodayWeatherAdapter
import islam.muslim.app.R
import islam.muslim.app.SharedData.SharedClass
import islam.muslim.app.base.BaseFragment
import islam.muslim.app.databinding.ActivityWeatherWidgetBinding
import islam.muslim.app.models.WeatherModel
import islam.muslim.app.utils.extensions.getCurrentTime
import islam.muslim.app.utils.extensions.getDayValue
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherFragment : BaseFragment<ActivityWeatherWidgetBinding, WeatherViewModel>() {

    var url = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + SharedClass.lat + "&lon=" + SharedClass.lng + "&cnt=17&appid=ac6f2688dbfdc24772be777529947e27"
    var dailyurl = "http://api.openweathermap.org/data/2.5/forecast?lat=" + SharedClass.lat + "&lon=" + SharedClass.lng + "&lang=en&appid=ac6f2688dbfdc24772be777529947e27"
    private val firstEntry = true
    private val weatherList = arrayListOf<WeatherModel>()
    var weathers = arrayListOf<WeatherModel>()
    var nextWeatherAdapter: NextWeatherAdapter? = null
    var todayWeatherAdapter: TodayWeatherAdapter? = null

    override val viewModel: WeatherViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override val layoutId: Int = R.layout.activity_weather_widget

    override fun onReady(savedInstanceState: Bundle?) {

        observe()

        binding.toolbar.tvTitle.text = resources.getString(R.string.weather)
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        getSingleTodayWeather()
        getNextWeather()
        getTodayWeather()
        binding.timeTxt.text = requireActivity().getCurrentTime("time")
        binding.ampmTxt.text = requireActivity().getCurrentTime("ampm")?.uppercase(Locale.getDefault())
        binding.dayTxt.text = requireActivity().getDayValue()

        binding.btnRefresh.setOnClickListener {
            binding.timeTxt.text = requireActivity().getCurrentTime("time")
            binding.ampmTxt.text = requireActivity().getCurrentTime("ampm")?.uppercase(Locale.getDefault())
            binding.dayTxt.text = requireActivity().getDayValue()
            getSingleTodayWeather()
            getNextWeather()
            getTodayWeather()
            Toast.makeText(requireActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getSingleTodayWeather() {
        val requestQueue = Volley.newRequestQueue(requireActivity())
        val request = StringRequest(
            Request.Method.GET,
            "http://api.openweathermap.org/data/2.5/weather?lat=" + SharedClass.lat + "&lon=" + SharedClass.lng + "&appid=ac6f2688dbfdc24772be777529947e27",
            { response ->
                try {
                    if (response != null) {
                        val Jobject = JSONObject(response)
                        val main = Jobject.getJSONObject("main")
                        val temp =
                            (java.lang.Float.valueOf(main.getString("temp")) - 272.15f).roundToInt()
                                .toFloat()
                        val temp_min =
                            (java.lang.Float.valueOf(main.getString("temp_min")) - 272.15f).roundToInt()
                                .toFloat()
                        val temp_max =
                            (java.lang.Float.valueOf(main.getString("temp_max")) - 272.15f).roundToInt()
                                .toFloat()
                        val humidity =
                            (java.lang.Float.valueOf(main.getString("humidity")) - 272.15f).roundToInt()
                                .toFloat()
                        // String sea_level = main.getString("sea_level");
                        val feels_like =
                            (java.lang.Float.valueOf(main.getString("feels_like")) - 272.15f).roundToInt()
                                .toFloat()
                        val weather = Jobject.getJSONArray("weather")
                        val desc = weather.getJSONObject(0).getString("description")
                        val icon = weather.getJSONObject(0).getString("icon")
                        binding.txtTemp.text = temp.roundToInt().toString() + "°C"
                        binding.feelsText.text = "Feels like: " + feels_like.roundToInt().toString()
                        binding.minmaxTxt.text = "Min " + temp_min.roundToInt() + "° | Max " + temp_max.roundToInt() + "°"
                        binding.descTxt.text = desc
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        ) { }
        requestQueue.add(request)
    }

    private fun getNextWeather() {
        weathers.clear()
        val requestQueue = Volley.newRequestQueue(requireActivity())
        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    if (response != null) {
                        val Jobject = JSONObject(response)
                        val Jarray = Jobject.getJSONArray("list")
                        for (i in 0 until Jarray.length()) {
                            val `object` = Jarray.getJSONObject(i)
                            val main = `object`.getJSONObject("temp")
                            val weather = `object`.getJSONArray("weather")
                            val desc = weather.getJSONObject(0).getString("description")
                            Log.i("URL_WITHER", "desc : $desc")
                            val icon = weather.getJSONObject(0).getString("icon")
                            val date = `object`.getString("dt")
                            val temp = main.getString("min")
                            val temp_min = main.getString("min")
                            val temp_max = main.getString("max")
                            val humidity = `object`.getString("humidity")
                            val windSpeed = `object`.getString("speed")
                            weathers.add(
                                WeatherModel(
                                    date,
                                    Math.round(java.lang.Float.valueOf(temp) - 272.15f)
                                        .toString() + "",
                                    Math.round(java.lang.Float.valueOf(temp_min) - 272.15f)
                                        .toString() + "",
                                    Math.round(java.lang.Float.valueOf(temp_max) - 272.15f)
                                        .toString() + "",
                                    icon,
                                    desc,
                                    humidity,
                                    windSpeed
                                )
                            )
                        }
                        Log.d("size", weathers.size.toString() + "")
                        if (weathers.size > 0) {
                            nextWeatherAdapter = NextWeatherAdapter(requireActivity(), weathers)
                            binding.nextWeatherRV.adapter = nextWeatherAdapter
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }) { }
        requestQueue.add(request)
    }

    private fun getTodayWeather() {
        weatherList.clear()
        val requestQueue = Volley.newRequestQueue(requireActivity())
        val request = StringRequest(
            Request.Method.GET, dailyurl,
            { response ->
                try {
                    if (response != null) {
                        val Jobject = JSONObject(response)
                        val Jarray = Jobject.getJSONArray("list")
                        for (i in 0 until Jarray.length()) {
                            val `object` = Jarray.getJSONObject(i)
                            val main = `object`.getJSONObject("main")
                            val weather = `object`.getJSONArray("weather")
                            val desc = weather.getJSONObject(0).getString("description")
                            Log.i("URL_WITHER", "desc : $desc")
                            val icon = weather.getJSONObject(0).getString("icon")
                            val date = `object`.getString("dt_txt")
                            val temp = main.getString("temp")
                            val temp_min = main.getString("temp_min")
                            val temp_max = main.getString("temp_max")
                            val humidity = main.getString("humidity")
                            val wind = `object`.getJSONObject("wind")
                            val windSpeed = wind.getString("speed")
                            weatherList.add(
                                WeatherModel(
                                    date,
                                    Math.round(java.lang.Float.valueOf(temp) - 272.15f)
                                        .toString() + "",
                                    Math.round(java.lang.Float.valueOf(temp_min) - 272.15f)
                                        .toString() + "",
                                    Math.round(java.lang.Float.valueOf(temp_max) - 272.15f)
                                        .toString() + "",
                                    icon,
                                    desc,
                                    humidity,
                                    windSpeed
                                )
                            )
                        }
                        if (weatherList!!.size > 0) {
                            todayWeatherAdapter =
                                TodayWeatherAdapter(weatherList, requireActivity())
                            binding.dailyRV.adapter = todayWeatherAdapter
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }) { }
        requestQueue.add(request)
    }

    fun observe() {

    }

}