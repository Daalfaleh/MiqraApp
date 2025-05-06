package islam.muslim.app.ui.favoriteduas.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import islam.muslim.app.R
import islam.muslim.app.databinding.FragmentHisnulMuslimListFavoriteBinding
import islam.muslim.app.ui.favoriteduas.adapters.HisnulFavoriteRVAdapter
import islam.muslim.app.ui.favoriteduas.viewmodel.DuasViewModel
import islam.muslim.app.utils.DataBaseFile
import islam.muslim.app.utils.extensions.ViewExtensions.hide
import islam.muslim.app.utils.extensions.ViewExtensions.visible
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class HisnulMuslimFavoriteListFragment : Fragment() {
    lateinit var binding: FragmentHisnulMuslimListFavoriteBinding
    var dataBaseFile: DataBaseFile? = null
    val hisnulMuslimDuasViewModel: DuasViewModel by hiltNavGraphViewModels(R.id.graph_allduas)
    var mCategory: MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHisnulMuslimListFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { act ->
            initUtils(act)
            getDataFromFile(act)
        }
    }


    private fun initUtils(context: Context) {
        dataBaseFile = DataBaseFile(context)
    }


    var adapter: HisnulFavoriteRVAdapter? = null
    fun getDataFromFile(context: Context) {
        viewLifecycleOwner.lifecycleScope.launch(IO) {
            mCategory.clear()
            var savedHisnulMuslimsDuas = hisnulMuslimDuasViewModel.getAllHisnulMuslimDuas()
            if (savedHisnulMuslimsDuas.isNullOrEmpty()) {
                withContext(Main) {
                    binding.hisnulMuslimFavTxt.visible()
                    binding.hisnulmuslimSavedDuas.hide()
                }
            } else {
                withContext(Main) {
                    binding.hisnulMuslimFavTxt.hide()
                    binding.hisnulmuslimSavedDuas.visible()
                    for (i in 0..savedHisnulMuslimsDuas.size - 1) {
                        mCategory.add(savedHisnulMuslimsDuas[i].title)
                    }
                    adapter = HisnulFavoriteRVAdapter(context, mCategory)
                    binding.hisnulmuslimSavedDuas.addItemDecoration(
                        DividerItemDecoration(
                            activity,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    binding.hisnulmuslimSavedDuas.adapter = adapter
                }
            }
        }
    }
}