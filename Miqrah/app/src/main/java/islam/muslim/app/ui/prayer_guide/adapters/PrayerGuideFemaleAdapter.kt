package islam.muslim.app.ui.prayer_guide.adapters

import islam.muslim.app.databinding.ItemPrayerGuideBinding
import islam.muslim.app.models.PrayerGuideModel
import islam.muslim.app.utils.data.PrayerGuideProvider
import islam.muslim.app.utils.extensions.ViewExtensions.hide
import islam.muslim.app.utils.extensions.ViewExtensions.visible
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PrayerGuideFemaleAdapter  : RecyclerView.Adapter<PrayerGuideViewHolder2>() {

    val prayerGuideList = PrayerGuideProvider.getPrayerGuideFemaleData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayerGuideViewHolder2 {
        val binding = ItemPrayerGuideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrayerGuideViewHolder2(prayerGuideList, binding)
    }

    override fun onBindViewHolder(holder: PrayerGuideViewHolder2, position: Int) {
        holder.bind(holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return prayerGuideList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}

class PrayerGuideViewHolder2(val prayerGuideList: ArrayList<PrayerGuideModel>, val binding: ItemPrayerGuideBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(guideNumber: Int) {

        if (prayerGuideList[guideNumber].layout == 1) {
            binding.itemTop.visible()
            binding.itemBottom.hide()
            binding.txtItemHeadingTop.text = prayerGuideList[guideNumber].heading
            binding.txtDescriptionTop.text = prayerGuideList[guideNumber].description
        } else {
            binding.itemBottom.visible()
            binding.itemTop.hide()
            binding.txtItemHeadingBottom.text = prayerGuideList[guideNumber].heading
            binding.txtPrayerStepDescriptionBottom.text = prayerGuideList[guideNumber].description
            binding.imgPrayerStep.setImageResource(prayerGuideList[guideNumber].imagePath)
            Picasso.get().load(prayerGuideList[guideNumber].imagePath).placeholder(prayerGuideList[guideNumber].imagePath).into(binding.imgPrayerStep);
        }

    }

}

