package islam.muslim.app.ui.duas.rabbanadua

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import islam.muslim.app.R
import islam.muslim.app.databinding.FragmentRabbanaDuaListBinding
import islam.muslim.app.databinding.RabbanadualistAdaptercusotmBinding
import islam.muslim.app.ui.duas.AllDuasDirections
import islam.muslim.app.ui.duas.viewmodels.AllDuasSearchViewModel
import islam.muslim.app.utils.DataBaseFile
import islam.muslim.app.utils.extensions.FragmentExtension.setSafeOnClickListener
import kotlinx.coroutines.launch

class RabbanaDuaListFragment : Fragment() {

    private var dataBaseFile: DataBaseFile? = null
    private val allDuasSearchViewModel: AllDuasSearchViewModel by navGraphViewModels(R.id.graph_allduas)

    lateinit var binding: FragmentRabbanaDuaListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRabbanaDuaListBinding.inflate(layoutInflater, container, false)
        binding.rabbaDuaListRec.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rabbaDuaListRec.adapter = VideoShowAdapder()
        dataBaseFile = DataBaseFile(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            allDuasSearchViewModel.searchText.observe(viewLifecycleOwner, {
                Log.d("SearcText", it)
            })
        }
    }

    inner class VideoShowAdapder :
        RecyclerView.Adapter<VideoShowAdapder.MyHolder>() {

        inner class MyHolder(var view: RabbanadualistAdaptercusotmBinding) :
            RecyclerView.ViewHolder(view.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val binding =
                RabbanadualistAdaptercusotmBinding.inflate(layoutInflater, parent, false)
            return MyHolder(binding)
        }


        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            holder.view.textView20.text =
                String.format(getString(R.string.text_rabbana_duas2), (position + 1).toString())
            holder.view.textView20.setSafeOnClickListener {
                val action =
                    AllDuasDirections.actionAllDuasFragmentToRabbanaDuaFragment(
                        position
                    )
                findNavController().navigate(action)
            }
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }


        override fun getItemCount(): Int {
            return 40
        }

    }
}
