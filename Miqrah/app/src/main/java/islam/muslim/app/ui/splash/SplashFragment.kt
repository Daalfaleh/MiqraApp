package islam.muslim.app.ui.splash

import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import islam.muslim.app.LoginActivity
import islam.muslim.app.R
import islam.muslim.app.base.BaseFragment
import islam.muslim.app.databinding.ActivitySplashBinding
import islam.muslim.app.utils.DialogUtils
import islam.muslim.app.utils.GeneralUtils
import islam.muslim.app.utils.PrefsUtils

@AndroidEntryPoint
class SplashFragment : BaseFragment<ActivitySplashBinding, SplashViewModel>() {

    private lateinit var dialog: DialogUtils
    private var isGPSEnabled = false
    private var isNetworkEnabled = false
    private var locationManager: LocationManager? = null
    lateinit var prefs: PrefsUtils

    override val viewModel: SplashViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override val layoutId: Int = R.layout.activity_splash

    override fun onReady(savedInstanceState: Bundle?) {

        observe()
             if(LoginActivity.Checklogin){

                 findNavController().navigate(R.id.dashboardFragment)
             }
        prefs = PrefsUtils(requireActivity())

        dialog = DialogUtils(requireActivity(), layoutInflater)
        dialog.showProgressDialog()
        locationManager = requireActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager?

        binding.btnProceed.setOnClickListener {
            viewModel.checkPermissions()
        }

    }

    fun observe() {
        viewModel.grantedStatusObservable.observe(viewLifecycleOwner) {
            if (it == viewModel.GRANTED) {
/*
                startActivity(Intent(this, DestinationActivity::class.java))
*/          dialog.hideProgressDialog()
            findNavController().navigate(R.id.navigateToLogin)
            }
            if (it == viewModel.NOT_GRANTED) {
                findNavController().navigate(R.id.navigateToPermissionDetails)
            }
            viewModel._grantedStatusObservable.postValue(0)
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Log.d(TAG, "Fragment back pressed invoked")
                    // Do custom work here
                    //  findNavController().navigate(R.id.splashFragment)
                    // if you want onBackPressed() to be called as normal afterwards
                    if (isEnabled) {
                        isEnabled = false
                       // findNavController().popBackStack()
                       // requireActivity().onBackPressed()
                        requireActivity().finishAffinity()
                    }
                }
            }
            )
    }

    override fun onPause() {
        super.onPause()
        dialog.hideProgressDialog()
    }

    override fun onResume() {
        super.onResume()
        // Getting GPS status
        isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isGPSEnabled && isNetworkEnabled) {
            dialog.hideProgressDialog()
        } else {
            dialog.hideProgressDialog()
            GeneralUtils.showSettingsAlert(requireActivity())
        }
    }

}