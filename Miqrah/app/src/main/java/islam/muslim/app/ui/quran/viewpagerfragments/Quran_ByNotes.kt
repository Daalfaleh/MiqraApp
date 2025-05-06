package islam.muslim.app.ui.quran.viewpagerfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import islam.muslim.app.R
import islam.muslim.app.databinding.FragmentQuranByNotesBinding
import islam.muslim.app.Helper.LastSurahAndAyahHelper
import islam.muslim.app.ui.quran.QuranFragment
import islam.muslim.app.ui.quran.adapters.QuranNotesAdapter
import islam.muslim.app.utils.DataBaseFile
import islam.muslim.app.utils.extensions.ViewExtensions.hide
import islam.muslim.app.utils.extensions.ViewExtensions.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Quran_ByNotes : Fragment() {
    val quranViewModel: QuranViewModel by hiltNavGraphViewModels(R.id.quranMainGraph)
    lateinit var binding: FragmentQuranByNotesBinding
    lateinit var databaseFile:DataBaseFile
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuranByNotesBinding.inflate(layoutInflater, container, false)

        activity?.let {
            databaseFile = DataBaseFile(it)
            observe(it)
        }
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
            quranViewModel.getNotes()
        }
        return binding.root
    }


    fun observe(context: Context) {
        quranViewModel.notesData.observe(viewLifecycleOwner, {
            if(it.isEmpty()){
                binding.qurannotesNodatafound.visible()
            }
            else{
                binding.qurannotesNodatafound.hide()
            }
            val quranNotesAdapter =
                QuranNotesAdapter(
                    it,
                    { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                        LastSurahAndAyahHelper.storeSelectedSurah(
                            context,
                            (it[position]?.surahNumber ?: 0) - QuranFragment.DIFFERENCE_OF_AYAH
                        )
                        LastSurahAndAyahHelper.storeSelectedAyah(
                            context,
                            (it[position]?.verseNumber ?: 0) - QuranFragment.DIFFERENCE_OF_AYAH
                        )
                        if(databaseFile.getBooleanData(DataBaseFile.isBookViewEnabled , false)){
                            findNavController().navigate("https://quranBookView.com/${(it[position]?.verseNumber ?: 0) - QuranFragment.DIFFERENCE_OF_AYAH}".toUri())
                        }
                        else{

                            findNavController().navigate("https://qurandetail.com/${(it[position]?.verseNumber ?: 0) - QuranFragment.DIFFERENCE_OF_AYAH}".toUri())
                        }
                    },
                    context
                )
            binding.quranBYNotesRec.adapter = quranNotesAdapter
        })
    }
}