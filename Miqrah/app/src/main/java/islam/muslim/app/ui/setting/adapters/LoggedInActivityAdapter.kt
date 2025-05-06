package islam.muslim.app.ui.setting.adapters

import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import islam.muslim.app.databinding.LoggedinActivityItemBinding
import islam.muslim.app.models.sealedClasses.LoggedInActivityResponseSealed
import islam.muslim.app.utils.extensions.ViewExtensions.hide
import islam.muslim.app.utils.extensions.ViewExtensions.visible

class LoggedInActivityAdapter(val deviceList: List<LoggedInActivityResponseSealed.LoggedinActivityResponse.Data>) :
    RecyclerView.Adapter<LoggedInActivityAdapter.LoggedInActivityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoggedInActivityViewHolder {
        val binding = LoggedinActivityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoggedInActivityViewHolder(deviceList, binding)
    }

    override fun onBindViewHolder(holder: LoggedInActivityViewHolder, position: Int) {

        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }


    class LoggedInActivityViewHolder(
        val deviceList: List<LoggedInActivityResponseSealed.LoggedinActivityResponse.Data>,
        val binding: LoggedinActivityItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {





        fun bind(position: Int) {
            binding.txtMobileName.text = deviceList[position].deviceName
            val deviceNumber =       Settings.Secure.getString(binding.root.context.contentResolver,
                Settings.Secure.ANDROID_ID)
            if(deviceList[position].deviceIMEI == deviceNumber){
                binding.deviceInfo.visible()
            }else{
                binding.deviceInfo.hide()
            }

        }


    }
}


