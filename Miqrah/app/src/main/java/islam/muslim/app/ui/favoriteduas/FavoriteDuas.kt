package islam.muslim.app.ui.favoriteduas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.AdRequest
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import islam.muslim.app.R
import islam.muslim.app.databinding.FragmentFavoriteDuasBinding
import islam.muslim.app.ui.favoriteduas.adapters.FavoriteDuasViewPagerAdapter
import islam.muslim.app.utils.DataBaseFile
import islam.muslim.app.utils.extensions.FragmentExtension.setSafeOnClickListener

class FavoriteDuas : Fragment() {

    private var dataBaseFile: DataBaseFile? = null
    lateinit var allDuasViewPager: ViewPager2
    lateinit var tabLayout: TabLayout
    lateinit var allDuasViewPagerAdapter: FavoriteDuasViewPagerAdapter
    lateinit var binding: FragmentFavoriteDuasBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteDuasBinding.inflate(layoutInflater, container, false)
        setUpToolbar()
        allDuasViewPager = binding.viewPagerAllDuas
        tabLayout = binding.tableLayoutAllDuas
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBaseFile = DataBaseFile(context)
        onClickListener()
        setUpViewPager()
        //       loadAds()
    }

    private fun setUpToolbar() {
        binding.include23.tvTitle.setText(getString(R.string.favorite_duas))
    }

    private fun loadAds() {
        val mAdView = binding.adView
        if (dataBaseFile?.getBooleanData(DataBaseFile.purchaseKey, false) == true) {
            mAdView.visibility = View.GONE
        } else {
            mAdView.visibility = View.VISIBLE
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
        }
    }

    private fun setUpViewPager() {
        val titles =
            listOf(
                resources.getString(R.string.text_rabbana_duas),
                resources.getString(R.string.text_hisnul_muslim),
                resources.getString(R.string.ramdhan_dua)
            )
        allDuasViewPagerAdapter = FavoriteDuasViewPagerAdapter(this, titles)
        allDuasViewPager.adapter = allDuasViewPagerAdapter
        TabLayoutMediator(
            tabLayout, allDuasViewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()
    }

    private fun onClickListener() {
        binding.include23.backBtn.setSafeOnClickListener {
            findNavController().navigateUp()
        }
    }
}