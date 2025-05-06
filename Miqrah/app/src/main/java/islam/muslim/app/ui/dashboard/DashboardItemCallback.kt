package islam.muslim.app.ui.dashboard

import islam.muslim.app.models.DashboardModel

interface DashboardItemCallback {
    fun onItemClicked(folder: DashboardModel, adapterPosition: Int)
}