package islam.muslim.app.ui.shirk

import islam.muslim.app.R
import islam.muslim.app.base.BaseFragment
import islam.muslim.app.databinding.FragmentShirkBinding
import islam.muslim.app.ui.shirk.adapter.ShirkListFragmentViewPagerAdapter
import islam.muslim.app.utils.extensions.FragmentExtension.hideKeyboard
import islam.muslim.app.utils.extensions.setSafeOnClickListener
import android.content.Intent
import android.os.Bundle
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.AdRequest
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShirkFragment : BaseFragment<FragmentShirkBinding, ShirkViewModel>() {

    private var tabName: String? = null
    private lateinit var shirkAndTawhidViewPagerAdapter: ShirkListFragmentViewPagerAdapter

    override val viewModel: ShirkViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override val layoutId: Int = R.layout.fragment_shirk

    override fun onReady(savedInstanceState: Bundle?) {

        setOnClickListener()
        setUpViewPager()

    }

    private fun setOnClickListener() {
        binding.backBtn.setSafeOnClickListener {
            hideKeyboard()
            findNavController().navigateUp()
        }

        binding.shareBtn.setSafeOnClickListener {
            shareData()
        }
    }

    private fun shareData() {
        if (tabName.equals(resources.getString(R.string.shirk))) {
            shareShirk()
        } else {
            shareTawhid()
        }

    }

    private fun shareShirk() {
        val intent = Intent(Intent.ACTION_SEND)

        val shareBody =
            resources.getString(R.string.definition) + "\n" + resources.getString(R.string.shirk_definition) + "\n\n" +
                    resources.getString(R.string.verses) + "\n" + resources.getString(R.string.shirk_verse_1) + "\n\n" + resources.getString(
                R.string.shirk_verse_2
            ) + "\n\n" +
                    resources.getString(R.string.ahadees) + "\n" + resources.getString(R.string.shirk_hadees_1) + "\n\n" + resources.getString(
                R.string.shirk_hadees_2
            ) + "\n\n" +
                    resources.getString(R.string.types) + "\n" + resources.getString(R.string.shirk_type_1) + "\n\n" + resources.getString(
                R.string.shirk_type_2
            ) + "\n\n" + resources.getString(R.string.shirk_type_3) + "\n\n" +
                    resources.getString(R.string.differences) + "\n" + resources.getString(R.string.shirk_difference_1) + "\n" + resources.getString(
                R.string.shirk_difference_1
            ) + "\n\n" + resources.getString(R.string.shirk_difference_2) + "\n\n" + resources.getString(
                R.string.shirk_difference_3
            ) + "\n\n" + resources.getString(R.string.shirk_difference_4)
//            binding.txtHadeesArbi.text.toString() + "\n\n" + binding.txtHadeesEnglish.text.toString()
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        context?.startActivity(Intent.createChooser(intent, "share"))
    }

    private fun shareTawhid() {
        val intent = Intent(Intent.ACTION_SEND)
        val shareBody =
            resources.getString(R.string.definition) + "\n" + resources.getString(R.string.tawhid_defination) + "\n\n" +
                    resources.getString(R.string.verses) + "\n" + resources.getString(R.string.tawhid_verse_1) + "\n\n" + resources.getString(
                R.string.tawhid_verse_2
            ) + "\n\n" +
                    resources.getString(R.string.ahadees) + "\n" + resources.getString(R.string.tawhid_hadees_1) + "\n\n" + resources.getString(
                R.string.tawhid_hadees_2
            )
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        context?.startActivity(Intent.createChooser(intent, "share"))
    }

    private fun setUpViewPager() {
        val titles =
            listOf(resources.getString(R.string.shirk), resources.getString(R.string.tawhid))
        shirkAndTawhidViewPagerAdapter = ShirkListFragmentViewPagerAdapter(this, titles)
        binding.viewPagerLayoutShirkListFragment.adapter = shirkAndTawhidViewPagerAdapter
        binding.viewPagerLayoutShirkListFragment.offscreenPageLimit = 1
        TabLayoutMediator(
            binding.tableLayoutShirkListFragment, binding.viewPagerLayoutShirkListFragment
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()


        binding.viewPagerLayoutShirkListFragment.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                when (position) {
                    0 -> {
                        tabName =
                            activity?.resources?.getString(R.string.shirk)

                    }
                    1 -> {
                        tabName =
                            activity?.resources?.getString(R.string.tawhid)
                    }
                }
            }
        })
    }

    private fun loadAds() {
        val mAdView = binding.adView7
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

}