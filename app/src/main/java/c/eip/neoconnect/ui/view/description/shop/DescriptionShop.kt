package c.eip.neoconnect.ui.view.description.shop


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

class DescriptionShop : Fragment() {
    private val pages = 3
    private lateinit var viewPager: ViewPager2
    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_description_shop, container, false)
    }

    /**
     * Mise en place des interaction possible
     * Déplacement entre les vues
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.descShopPager)!!
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout_desc)
        val pagerAdapter = DescriptionShopAdapter(this)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
        }.attach()
        view.findViewById<Button>(R.id.descLogin).setOnClickListener {
            findNavController().navigate(R.id.navigation_login_shop)
        }
    }

    private inner class DescriptionShopAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return pages
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    DescriptionShop1()
                }
                1 -> {
                    DescriptionShop2()
                }
                2 -> {
                    DescriptionShop3()
                }
                else -> {
                    DescriptionShop()
                }
            }
        }
    }
}
