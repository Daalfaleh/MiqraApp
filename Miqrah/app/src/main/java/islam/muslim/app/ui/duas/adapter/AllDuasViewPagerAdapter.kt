package islam.muslim.app.ui.duas.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import islam.muslim.app.ui.duas.hisnulmuslim.HisnulMuslimFragment
import islam.muslim.app.ui.duas.rabbanadua.RabbanaDuaListFragment
import islam.muslim.app.ui.duas.ramadhanduas.RamdanDuaListFragment

class AllDuasViewPagerAdapter(var fragment: Fragment, val viewPagerItems: List<String>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return viewPagerItems.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                RabbanaDuaListFragment()
            }
            1 -> {
                HisnulMuslimFragment()
            }
            2 -> {
                RamdanDuaListFragment()
            }
            else -> {
                RabbanaDuaListFragment()
            }

        }
    }

}
