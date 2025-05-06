package islam.muslim.app.ui.duas.adapter

import islam.muslim.app.databinding.HisnulMuslimContextBinding
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import islam.muslim.app.ui.duas.AllDuasDirections
import islam.muslim.app.utils.extensions.FragmentExtension.setSafeOnClickListener
import java.util.*

class HisnulMuslimAdapter(var activity: Context, var nameList: MutableList<String>) :
    RecyclerView.Adapter<HisnulMuslimAdapter.Holder>() {

    private val originalData: MutableList<String> = ArrayList()

    init {
        originalData.addAll(nameList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = HisnulMuslimContextBinding.inflate(LayoutInflater.from(activity), parent, false)
        return Holder(view)
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val duaText = nameList[position].split("###")[0]
        holder.view.textView20.text = duaText
      //  holder.view.countText.text = (holder.adapterPosition + 1).toString()


        holder.view.mainCOns.setSafeOnClickListener {
            val action = AllDuasDirections.actionAllDuasFragmentToHisnulmuslim(duaText)
            it.findNavController().navigate(action)
        }
    }

    fun searchIt(text: String) {
        nameList.clear()
        if (text.isEmpty()) {
            nameList.addAll(originalData)
        } else {
            nameList.addAll(
                originalData.filter {
                    it.lowercase().contains(text.lowercase())
                }

            )
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    inner class Holder(var view: HisnulMuslimContextBinding) : RecyclerView.ViewHolder(view.root)

}