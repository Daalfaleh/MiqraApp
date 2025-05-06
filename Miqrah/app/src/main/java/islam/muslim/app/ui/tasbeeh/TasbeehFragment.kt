package islam.muslim.app.ui.tasbeeh

import islam.muslim.app.R
import islam.muslim.app.SharedData.SharedClass
import islam.muslim.app.base.BaseFragment
import islam.muslim.app.databinding.ActivityCounterBinding
import android.media.MediaPlayer
import android.os.Bundle
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasbeehFragment : BaseFragment<ActivityCounterBinding, TasbeehViewModel>() {

    var counter = 0
    lateinit var mp: MediaPlayer

    override val viewModel: TasbeehViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override val layoutId: Int = R.layout.activity_counter

    override fun onReady(savedInstanceState: Bundle?) {

        observe()

        binding.toolbar.tvTitle.text = resources.getString(R.string.tasbeeh_counter)
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        mp = MediaPlayer.create(requireActivity(), R.raw.tasbeehsound)
        counter = SharedClass.getCounter(requireActivity())
        binding.counterTxt.text = SharedClass.getCounter(requireActivity()).toString()

        binding.btnCount.setOnClickListener {
            counter += 1
            binding.counterTxt.text = counter.toString()
            if (SharedClass.getTasbeenSound(requireActivity()) == 1) {
                if (!mp.isPlaying) {
                    mp.start()
                }
            }
        }

    }

    fun observe() {

    }

}