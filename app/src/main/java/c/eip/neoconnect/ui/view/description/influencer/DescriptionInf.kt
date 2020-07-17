package c.eip.neoconnect.ui.view.description.influencer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import c.eip.neoconnect.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DescriptionInf : Fragment() {
    private val PAGES = 3
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_description_inf, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.descInfPager)!!
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout_desc)
        val pagerAdapter = DescriptionInfAdapter(this)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
        }.attach()
        view.findViewById<Button>(R.id.descLogin).setOnClickListener {
            findNavController().navigate(R.id.navigation_login_inf)
        }
    }

    private inner class DescriptionInfAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return PAGES
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    DescriptionInf1()
                }
                1 -> {
                    DescriptionInf2()
                }
                2 -> {
                    DescriptionInf3()
                }
                else -> {
                    DescriptionInf()
                }
            }
        }
    }
}
