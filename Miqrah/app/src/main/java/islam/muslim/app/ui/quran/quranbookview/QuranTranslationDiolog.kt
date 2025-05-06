package islam.muslim.app.ui.quran.quranbookview

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import islam.muslim.app.R
import islam.muslim.app.databinding.FragmentQuranTranslationDiologBinding
import islam.muslim.app.utils.extensions.FragmentExtension.setSafeOnClickListener

class QuranTranslationDiolog : BottomSheetDialogFragment() {

    val homeViewModel: QuranBookViewViewModel by navGraphViewModels(R.id.QuranBookViewNavigation)
    lateinit var binding: FragmentQuranTranslationDiologBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentQuranTranslationDiologBinding.inflate(layoutInflater  , container ,false)
        binding.textView3.text = homeViewModel.selectedQuranVerse.value
        val font: Typeface = Typeface.createFromAsset(context?.assets, "Font/arabic.ttf")
    binding.textView3.typeface=font

           // binding.textView3.typeface=homeViewModel.selectedVerseFont

        binding.quranBottomSheetTransilation.setText(homeViewModel.selectedQuranVerseTransilation.value)

        binding.textView2.text = homeViewModel.selectedQuranVerseTranslation.value
        binding.close.setSafeOnClickListener {
            dismiss()
        }
        return binding.root
    }

}