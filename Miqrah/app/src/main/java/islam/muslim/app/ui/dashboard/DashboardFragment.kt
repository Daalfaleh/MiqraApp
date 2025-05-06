package islam.muslim.app.ui.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.adapters.AdapterViewBindingAdapter.setOnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import islam.muslim.app.LoginActivity
import islam.muslim.app.R
import islam.muslim.app.base.BaseFragment
import islam.muslim.app.databinding.ActivityDashboardBinding
import islam.muslim.app.models.DashboardModel


@AndroidEntryPoint
class DashboardFragment : BaseFragment<ActivityDashboardBinding, DashboardViewModel>() {
    val MY_PERMISSIONS_IGNORE_BATTERY_OPTIMIZATIONS = 1;

    private val dashboardItemCallback by lazy {
        object : DashboardItemCallback {
            override fun onItemClicked(audio: DashboardModel, adapterPosition: Int) {
                when (adapterPosition) {
                    0 -> findNavController().navigate(R.id.navigateToShirkFragment)
                    1 -> findNavController().navigate(R.id.navigateToTasbeeh)
                    2 -> findNavController().navigate(R.id.navigateToAsma)
                    3 -> findNavController().navigate(R.id.navigateToAzkar)
                    4 -> findNavController().navigate(R.id.navigateToPrayerGuide)
                    5 -> findNavController().navigate(R.id.action_navigation_home_to_graph_quran)
                    6 -> findNavController().navigate(R.id.navigateToZakat)
                    7 -> findNavController().navigate(R.id.navigateToCalendar)
                }
            }
        }
    }
    private val dashboardAdapter by lazy { DashboardAdapter(dashboardItemCallback) }

    override val viewModel: DashboardViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override val layoutId: Int = R.layout.activity_dashboard

    override fun onReady(savedInstanceState: Bundle?) {

        binding.dashboardRV.adapter = dashboardAdapter
        observe()
        viewModel.getData()
           LoginActivity.Checklogin=false
        binding.navigation1.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    binding.moreitemsid.isVisible=true
                    binding.prayid.isVisible=true
                    binding.dashboardRV.isVisible=false
                    binding.rellast.isVisible=false
                    true
                }
                R.id.navigation_dashboard -> {
                    findNavController().navigate(R.id.action_navigation_home_to_graph_quran)
                    true
                }
                R.id.navigation_madiation -> {
                    findNavController().navigate(R.id.navigateToWeather)
                    true
                }
                R.id. navigation_pro->{
                    findNavController().navigate(R.id.navigateToKalma)
                    true}
                else -> {

                    false
                }
            }
        }
      /*  MobileAds.initialize(requireActivity(), resources.getString(R.string.admob__app_id))
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.dayTxt.text = requireActivity().getDayOfWeek()*/
        binding.quran.setOnClickListener(View.OnClickListener {

            findNavController().navigate(islam.muslim.app.R.id.action_navigation_home_to_graph_quran)
        })
        binding.mores.setOnClickListener(View.OnClickListener {

            binding.moreitemsid.isVisible=false
            binding.prayid.isVisible=false
            binding.dashboardRV.isVisible=true
            binding.rellast.isVisible=true
            true
        })
        binding.tasbehh.setOnClickListener(View.OnClickListener {

            findNavController().navigate(islam.muslim.app.R.id.navigateToTasbeeh)
        })
        binding.azkr.setOnClickListener(View.OnClickListener {

            findNavController().navigate(islam.muslim.app.R.id.navigateToShirkFragment)
        })
        binding.calculater.setOnClickListener(View.OnClickListener {

            findNavController().navigate(R.id.navigateToCalendar)
        })

        binding.qurantext.setOnClickListener(View.OnClickListener {

            findNavController().navigate(R.id.action_navigation_home_to_graph_quran)
        })
        binding.salah.setOnClickListener(View.OnClickListener {

            findNavController().navigate(R.id.navigateToPrayer)
        })

       /* requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                   // Log.d(TAG, "Fragment back pressed invoked")
                    // Do custom work here
                  //  findNavController().navigate(R.id.splashFragment)
                    // if you want onBackPressed() to be called as normal afterwards
                    if (isEnabled) {
                        isEnabled = false
                        findNavController().popBackStack()
                        requireActivity().onBackPressed()
                    }
                }
            }
            )*/
    }

    fun observe() {
        viewModel.dashboardResponse.observe(viewLifecycleOwner) {
            dashboardAdapter.submitList(it)
        }
    }

}





