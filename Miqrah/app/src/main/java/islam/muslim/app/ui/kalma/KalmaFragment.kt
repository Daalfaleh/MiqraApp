package islam.muslim.app.ui.kalma

import islam.muslim.app.R
import islam.muslim.app.base.BaseFragment
import islam.muslim.app.databinding.ActivitySixKalmasBinding
import islam.muslim.app.download.DownloadKalmaAudio
import islam.muslim.app.ui.kalma.adapters.KalmasRecyclerViewAdapter
import islam.muslim.app.ui.kalma.clicklisteners.KalmasRecyclerViewClickListeners
import islam.muslim.app.utils.DataBaseFile
import islam.muslim.app.utils.extensions.setSafeOnClickListener
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class KalmaFragment : BaseFragment<ActivitySixKalmasBinding, KalmaViewModel>(),
    KalmasRecyclerViewClickListeners {

    var dataBaseFile: DataBaseFile? = null
    lateinit var adapter: KalmasRecyclerViewAdapter
    var kalmaIndex = 0
    var player: MediaPlayer? = null
    var kalmaNames: Array<String>? = null
    lateinit var dataList: Array<String>
    var previousPosition: Int = -1

    @SuppressLint("HandlerLeak")
    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(message: Message) {
            if (message.what == 0) {
                val path: String
                val f = context?.let { getFile("kalma_" + (kalmaIndex + 1) + ".mp3", it) }
                if (f?.exists() == true) {
                    path = f.absolutePath
                    playAudioCode(path, kalmaIndex)
                }
            }
        }
    }

    override val viewModel: KalmaViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override val layoutId: Int = R.layout.activity_six_kalmas

    override fun onReady(savedInstanceState: Bundle?) {

        activity?.let {
            initUtils(it)
            getDataFromFile(it)
            onClickEvent()
            //   loadAds()
            activity?.let {
                setUpRecyclerView()
            }
        }

    }

    private fun setUpRecyclerView() {
        adapter = KalmasRecyclerViewAdapter(requireActivity(), dataList, this)
        binding.kalmasRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.kalmasRecyclerView.adapter = adapter
    }

    private fun loadAds() {
        val mAdView = binding.adView6
        if (dataBaseFile?.getBooleanData(DataBaseFile.purchaseKey, false) == true) {
            mAdView.visibility = View.GONE
        } else {
            mAdView.visibility = View.VISIBLE
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
        }
    }

    private fun onClickEvent() {
        binding.backBtn.setSafeOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun getDataFromFile(context: Context) {
        val data = removeCharacter(LoadData("06_kalmas_data.txt", context).trim { it <= ' ' })
        dataList = data.split("\n".toRegex()).toTypedArray()
    }

    private fun shareKalma(position: Int) {
        val kalmaData = dataList[position].split("@@@".toRegex()).toTypedArray()
        kalmaData.let {
            val shareBody = """
                ${it[0]}
                ${it[2]}
                ${it[1]}
                ${it[3]}
                ${it[4]}
                ${it[5]}
                """.trimIndent()
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                resources.getString(R.string.app_name)
            )
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(
                Intent.createChooser(
                    sharingIntent,
                    resources.getString(R.string.share_kalma)
                )
            )
        }

    }

    private fun playAudioCode(path: String, position: Int) {
        try {
            player = MediaPlayer()
            player?.setDataSource(path)
            player?.prepare()
            player?.start()
            adapter.setisPlaying('p', position)
            adapter.notifyItemChanged(position)
            player?.setOnCompletionListener { mp: MediaPlayer? ->
                adapter.setisPlaying('s', position)
                adapter.notifyItemChanged(position)
            }
        } catch (ex: Exception) {
            adapter.setisPlaying('s', position)
            adapter.notifyItemChanged(position)
        }
    }

    fun playAudio(context: Context, position: Int) {
        if (player == null) {
            val f = getFile("kalma_" + (kalmaIndex + 1) + ".mp3", context)
            if (f.exists()) {
                playAudioCode(f.absolutePath, position)
            } else {
                val dAudio = DownloadKalmaAudio(activity, kalmaIndex + 1, mHandler)
                dAudio.download()
            }
        } else {
            if (player?.isPlaying == true) {
                player?.pause()
                adapter.setisPlaying('s', position)
                adapter.notifyItemChanged(position)
            } else {
                player?.start()
                adapter.setisPlaying('p', position)
                adapter.notifyItemChanged(position)
            }
        }
    }

    private fun initUtils(context: Context) {

        dataBaseFile = DataBaseFile(context)

        kalmaNames =
            arrayOf("1st Kalma", "2nd Kalma", "3rd Kalma", "4th Kalma", "5th Kalma", "6th Kalma")
        binding.txtTitle.text = getString(R.string.kalam_title)
    }

    private fun stopBtn(position: Int) {
        if (player == null) return
        if (player?.isPlaying == true) {
            adapter.setisPlaying('s', position)
            adapter.notifyItemChanged(position)
        }
        resetPlayer()
    }

    override fun onDetach() {
        resetPlayer()
        super.onDetach()
    }

    private fun resetPlayer() {
        if (player != null) {
            player?.stop()
            player?.reset()
            player = null
        }
    }

    private fun getFile(filename: String, context: Context): File {
        val mydir = context.getDir("IslamicGuideProKalma", Context.MODE_PRIVATE)
        return File(mydir, filename)
    }

    companion object {
        fun LoadData(inFile: String?, con: Context?): String {
            var tContents = ""
            try {
                val stream = inFile?.let { con?.assets?.open(it) }
                val size = stream?.available()
                val buffer = ByteArray(size ?: 0)
                stream?.read(buffer)
                stream?.close()
                tContents = String(buffer)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return tContents
        }

        fun removeCharacter(str11: String): String {
            var str = str11
            str = str.replace("\r", "")
            return str
        }
    }

    override fun onPlayClick(position: Int) {
        if (previousPosition == -1) {
            previousPosition = position
            kalmaIndex = position
            context?.let {
                resetPlayer()
                playAudio(it, position)
            }
        } else if (position == previousPosition) {
            context?.let {
                playAudio(it, position)
            }
        } else {
            kalmaIndex = position
            adapter.setisPlaying('s', previousPosition)
            adapter.notifyItemChanged(previousPosition)
            previousPosition = position
            context?.let {
                resetPlayer()
                playAudio(it, position)
            }
        }
    }

    override fun onStopClick(position: Int) {
        if (kalmaIndex == position) {
            stopBtn(position)
        }
    }

    override fun onShareClick(position: Int) {
        shareKalma(position)
    }

    fun observe() {

    }

}