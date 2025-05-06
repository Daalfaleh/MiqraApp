package islam.muslim.app.ui.prayer_guide

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import islam.muslim.app.R
import islam.muslim.app.download.FileDownloadManager
import islam.muslim.app.utils.DataBaseFile
import islam.muslim.app.utils.common.constants.DirectoryConstants
import islam.muslim.app.utils.common.constants.FilesNameConstants
import islam.muslim.app.utils.common.constants.ServerLinksConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import islam.muslim.app.databinding.FragmentPrayerGuideMainBinding
import islam.muslim.app.utils.extensions.ContextExtensions.getFilePath
import islam.muslim.app.utils.extensions.ViewExtensions.visible
import islam.muslim.app.utils.extensions.setSafeOnClickListener

class PrayerGuideMainFragment : Fragment() {

    lateinit var binding: FragmentPrayerGuideMainBinding
    private var fragmentPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrayerGuideMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {

            setUpViewPager()
            onClickEvent(it)
        }
    }

    fun onClickEvent(context: Context) {
        binding.txtToolbar.text = getString(R.string.prayer_guide)
        binding.backBtn.setSafeOnClickListener {
            findNavController().navigateUp()
        }

        with(binding.shareBtn) {
            visible()
            setSafeOnClickListener {

                if (fragmentPosition == 0) {
                    sharePdf(context)
                } else if (fragmentPosition == 1) {
                    sharePdf(context, false)
                }
            }
        }
    }

    private fun sharePdf(context: Context, male: Boolean = true) {
        val fileName = FilesNameConstants.PrayerIllustrationZipFileName
        val myDir = context.getFilePath(subDir = DirectoryConstants.PrayerIllustrationPdfDirectory)
        val file =
            File("$myDir/$fileName")
        Log.d("path", file.toString())
        if (file.exists()) {

            val pathToDownloadFile =
                if (male) File("$myDir/${FilesNameConstants.PrayerIllustrationMalePdf}") else File("$myDir/${FilesNameConstants.PrayerIllustrationFemalePdf}")


            val photoURI = FileProvider.getUriForFile(
                context, context.applicationContext.packageName + ".fileprovider",
                pathToDownloadFile
            )

            Log.d("pdfpath", photoURI.toString())
            val share = Intent()
            share.action = Intent.ACTION_SEND
            share.type = "application/pdf"
            share.putExtra(Intent.EXTRA_STREAM, photoURI)

            context.startActivity(Intent.createChooser(share, "Share it"))
        } else {
            val myServerLink = ServerLinksConstants.PrayerIllustrationZipFileServerLink
            val fileDownlaodManager = FileDownloadManager(
                context = context,
                link = myServerLink,
                lifeCycleScope = viewLifecycleOwner.lifecycleScope,
                filePath = file.path,
                fileNameDisplayable = getString(R.string.prayer_guide),
                downloadCancel = { mess, file ->
                    file.delete()
                    Toast.makeText(context, getString(R.string.downloading_failed), Toast.LENGTH_SHORT).show()
                },
                downloadComplete = {
                    CoroutineScope(Dispatchers.IO).launch {

                        DataBaseFile.ExtractZip(File(it), File(myDir).path)
                        withContext(Dispatchers.Main){
                            if (fragmentPosition == 0) {
                                sharePdf(context)
                            } else if (fragmentPosition == 1) {
                                sharePdf(context, false)
                            }
                        }
                    }
                    //    File(it).Unzip(File(myDir))
                }

            )

            viewLifecycleOwner.lifecycleScope.launch {
                fileDownlaodManager.download()
            }
        }
    }

    private fun setUpViewPager() {
        val titles =
            listOf(
                resources.getString(R.string.prayer_formale),
                resources.getString(R.string.prayer_forfemale)
            )
        val allDuasViewPagerAdapter = PrayerGuideAdapter(this, titles)
        binding.prayerGuideViewPager.adapter = allDuasViewPagerAdapter
        TabLayoutMediator(
            binding.prayerGuideTabLayout, binding.prayerGuideViewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()


        binding.prayerGuideViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                fragmentPosition = position
                Log.e("position", fragmentPosition.toString())
            }
        })
    }

    class PrayerGuideAdapter(var fragment: Fragment, val viewPagerItems: List<String>) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return viewPagerItems.size
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    PrayerGuideFragment()
                }
                1 -> {
                    PrayerGuideFragFemale()
                }
                else -> {
                    PrayerGuideFragFemale()
                }
            }
        }
    }
}