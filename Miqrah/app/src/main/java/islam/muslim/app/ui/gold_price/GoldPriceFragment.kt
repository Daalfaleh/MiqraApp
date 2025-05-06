package islam.muslim.app.ui.gold_price

import islam.muslim.app.R
import islam.muslim.app.base.BaseFragment
import islam.muslim.app.databinding.ActivityGoldPriceBinding
import islam.muslim.app.utils.extensions.isNetworkStatusAvailable
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoldPriceFragment : BaseFragment<ActivityGoldPriceBinding, GoldPriceViewModel>() {

    override val viewModel: GoldPriceViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override val layoutId: Int = R.layout.activity_gold_price

    override fun onReady(savedInstanceState: Bundle?) {

        observe()

        if (requireActivity().isNetworkStatusAvailable(requireActivity())) {
            binding.webview.setWebViewClient(WebViewClient())
            binding.webview.loadUrl("http://goldpricez.com/calculator/gold-rates")
            val webSettings: WebSettings = binding.webview.settings
            webSettings.javaScriptEnabled = true
        } else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.internetfirst),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun observe() {

    }

}