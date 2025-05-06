package islam.muslim.app.ui.prayer

import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.text.TextUtilsCompat
import androidx.core.view.ViewCompat
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import islam.muslim.app.Constants
import islam.muslim.app.R
import islam.muslim.app.base.BaseFragment
import islam.muslim.app.databinding.ActivityPrayerBinding
import islam.muslim.app.settings.AppSettings
import islam.muslim.app.utils.DateUtils
import islam.muslim.app.utils.DialogUtils
import islam.muslim.app.utils.PrayTime
import islam.muslim.app.utils.PrefsUtils
import islam.muslim.app.utils.extensions.ViewExtensions.visible
import java.util.*


@AndroidEntryPoint
class PrayerFragment : BaseFragment<ActivityPrayerBinding, PrayerViewModel>(),
    DialogUtils.OnSettingsSavedListener {

    lateinit var currentlocale: Locale
    var prayers: PrayTime = PrayTime()
    var timezone: Double = 0.0
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    private lateinit var utils: PrefsUtils
    private lateinit var dialog: DialogUtils

    override val viewModel: PrayerViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override val layoutId: Int = R.layout.activity_prayer
    override fun onReady(savedInstanceState: Bundle?) {

        observe()

 /*       binding.toolbar.btnMore.visible()
        binding.toolbar.btnMore.setImageResource(R.drawable.settings)
        binding.toolbar.tvTitle.text = resources.getString(R.string.prayer_time)*/

        currentlocale = resources.configuration.locale
        utils = PrefsUtils(requireActivity())
        dialog = DialogUtils(requireActivity(), layoutInflater, this)

        val isLeftToRight = TextUtilsCompat.getLayoutDirectionFromLocale(currentlocale) == ViewCompat.LAYOUT_DIRECTION_LTR
        if(isLeftToRight) {
            binding.ivCardTop.scaleX = -1f
        } else {
            binding.ivCardTop.scaleX = 1f
        }

        if (!utils.getFromPrefsWithDefault(AppSettings.Key.IS_INIT, false)) {
            for (key in Constants.KEYS) {
                utils.saveToPrefs(Constants.ALARM_FOR + key, 0)
            }
            utils.saveToPrefs(AppSettings.Key.IS_INIT, true)
        }

        // get prayer time
        latitude = utils.getFromPrefsWithDefault(Constants.LATITUDE, 0.0)
        longitude = utils.getFromPrefsWithDefault(Constants.LONGITUDE, 0.0)
        val tz = TimeZone.getDefault()
        val nowt = Date()
        timezone = tz.getOffset(nowt.time) / 3600000.0

        getPrayerTimes(prayers, latitude, longitude, timezone)

        binding.settings.setOnClickListener {
            dialog.showSettingDialog()
        }
       val azansound= utils.getFromPrefsWithDefault(PrefsUtils.AZAN, 0)
        if(azansound.equals("Madni azan")){
            val soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context?.packageName + "/"+R.raw.azan)
            var longArray = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

            val mNotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                viewModel.makeNotificationChannel(soundUri, requireActivity(), mNotificationManager, true, longArray)
            }
        }
        else if (azansound.equals("makkah azan")){

            val soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context?.packageName + "/"+R.raw.azan_fajr)
            var longArray = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

            val mNotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                viewModel.makeNotificationChannel(soundUri, requireActivity(), mNotificationManager, true, longArray)
            }
        }
        else if (azansound.equals("None")){

            val soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context?.packageName + "/"+R.raw.tasbeehsound)
            var longArray = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

            val mNotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                viewModel.makeNotificationChannel(soundUri, requireActivity(), mNotificationManager, true, longArray)
            }
        }

    }

    fun observe() {
        /*viewModel.dashboardResponse.observe(viewLifecycleOwner) {
            Log.d("sizeiz", it.size.toString())
            dashboardAdapter.submitList(it)
        }*/
    }

    private fun getPrayerTimes(
        prayers: PrayTime,
        latitude: Double,
        longitude: Double,
        timezone: Double
    ) {
        prayers.timeFormat = /*utils.getFromPrefsWithDefault(PrefsUtils.timeFormat, 0)*/1
        prayers.calcMethod = utils.getFromPrefsWithDefault(PrefsUtils.calcMethod, 1)
        prayers.asrJuristic = utils.getFromPrefsWithDefault(PrefsUtils.juristic, 0)
        prayers.adjustHighLats = utils.getFromPrefsWithDefault(PrefsUtils.highLats, 0)

        Log.d("timeFormatiz", "${prayers.timeNames} and ${prayers.calcMethod} and ${prayers.asrJuristic} and ${prayers.adjustHighLats}")

        prayers.lat = latitude
        prayers.lng = longitude

        val offsets =
            intArrayOf(0, 0, 0, 0, 0, 0, 0) // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}

        prayers.tune(offsets)

        // Today's date
        val calToday = Calendar.getInstance()
        calToday.time = Date()

        val todayPrayerTimes = prayers.getPrayerTimes(
            calToday,
            latitude, longitude, timezone
        )

        Log.d("checktime1", viewModel.checktimingsafter(viewModel.getCurrentTime(), todayPrayerTimes[6]).toString())

        if(viewModel.checktimingsafter(viewModel.getCurrentTime(), todayPrayerTimes[0]) && viewModel.checktimingsbefore(viewModel.getCurrentTime(), todayPrayerTimes[1])) {
          /*  binding.cardSunrise.setCardBackgroundColor(resources.getColor(R.color.highlight_color))
            Log.d("checktime", "sunrise")*/
        } else if(viewModel.checktimingsafter(viewModel.getCurrentTime(), todayPrayerTimes[1]) && viewModel.checktimingsbefore(viewModel.getCurrentTime(), todayPrayerTimes[2])) {
          //  binding.cardZuhr.setCardBackgroundColor(resources.getColor(R.color.highlight_color))
          //  binding.loctv.text=
            binding.loctv.text= "Zohr"
            binding.loctvtime.text=todayPrayerTimes[1]
            Log.d("checktime", "zohr")
        } else if(viewModel.checktimingsafter(viewModel.getCurrentTime(), todayPrayerTimes[2]) && viewModel.checktimingsbefore(viewModel.getCurrentTime(), todayPrayerTimes[3])) {
            //binding.cardAsr.setCardBackgroundColor(resources.getColor(R.color.highlight_color))
            Log.e("checktime1", viewModel.checktimingsbefore(viewModel.getCurrentTime(), todayPrayerTimes[2]).toString())

            binding.loctv.text= "ASR"
            binding.loctvtime.text=todayPrayerTimes[3]
            Log.d("checktime", "asr")
        } else if(viewModel.checktimingsafter(viewModel.getCurrentTime(), todayPrayerTimes[3]) && viewModel.checktimingsbefore(viewModel.getCurrentTime(), todayPrayerTimes[4])) {
            //binding.cardMaghrib.setCardBackgroundColor(resources.getColor(R.color.highlight_color))
            binding.loctv.text= "Maghrib"
            binding.loctvtime.text=todayPrayerTimes[4]
            Log.d("checktime", "maghrib")
        } else if(viewModel.checktimingsafter(viewModel.getCurrentTime(), todayPrayerTimes[4]) && viewModel.checktimingsbefore(viewModel.getCurrentTime(), todayPrayerTimes[5])) {
           // binding.cardMaghrib.setCardBackgroundColor(resources.getColor(R.color.highlight_color))
            binding.loctv.text= "Maghrib"
            binding.loctvtime.text=todayPrayerTimes[5]
            Log.d("checktime", "maghrib")
        } else if(viewModel.checktimingsafter(viewModel.getCurrentTime(), todayPrayerTimes[5]) && viewModel.checktimingsbefore(viewModel.getCurrentTime(), todayPrayerTimes[6])) {
            //binding.cardIsha.setCardBackgroundColor(resources.getColor(R.color.highlight_color))
            binding.loctv.text= "Isha"
            binding.loctvtime.text=todayPrayerTimes[6]
            Log.d("checktime", "isha")
        } else if(viewModel.checktimingsafter(viewModel.getCurrentTime(), todayPrayerTimes[6])) {
            binding.loctv.text= "Fajr"
            binding.loctvtime.text=todayPrayerTimes[0]
            //binding.cardFajr.setCardBackgroundColor(resources.getColor(R.color.highlight_color))
            Log.d("checktime", "fajr")
        }

        binding.tvFajr.text = todayPrayerTimes[0]
        binding.tvSunrise.text = todayPrayerTimes[1]
        binding.tvDuhr.text = todayPrayerTimes[2]
        binding.tvAsar.text = todayPrayerTimes[3]
        binding.tvSunset.text = todayPrayerTimes[4]
        binding.tvMaghrib.text = todayPrayerTimes[5]
        binding.tvIsha.text = todayPrayerTimes[6]
        binding.tvGregorianDate.text = DateUtils.getGregorianDate()
        binding.tvIslamicDate.text = DateUtils.writeIslamicDate()
        viewModel.updateAlarmStatus()

        /*binding.toolbar.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }*/

    }

    override fun onSettingsSaved(isSaved: Boolean) {
        getPrayerTimes(prayers, latitude, longitude, timezone)
    }
 /*public fun getloc() {
     val geocoder: Geocoder
     val addresses: List<Address>
     geocoder = Geocoder(activity, Locale.getDefault())

     addresses = geocoder.getFromLocation(
         latitude,
         longitude,
         1
     ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


     val address: String =
         addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

     val city: String = addresses[0].getLocality()
     val state: String = addresses[0].getAdminArea()
     val country: String = addresses[0].getCountryName()
     val postalCode: String = addresses[0].getPostalCode()
     val knownName: String = addresses[0].getFeatureName()
     binding.loctv.text=city.toString() + " " +country.toString()

 }*/
}