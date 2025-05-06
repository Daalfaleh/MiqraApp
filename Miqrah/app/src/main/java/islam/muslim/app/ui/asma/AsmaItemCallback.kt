package islam.muslim.app.ui.asma

import islam.muslim.app.models.AsmaModel

interface AsmaItemCallback {
    fun onItemClicked(folder: AsmaModel, adapterPosition: Int)
    fun onCopyClicked(folder: AsmaModel, adapterPosition: Int)
}