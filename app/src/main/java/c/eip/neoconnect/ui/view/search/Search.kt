package c.eip.neoconnect.ui.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.search.SearchResponseModel
import c.eip.neoconnect.ui.view.list.ListInf
import c.eip.neoconnect.ui.view.list.ListOffer
import c.eip.neoconnect.ui.view.list.ListShop
import c.eip.neoconnect.ui.viewModel.InfViewModel
import c.eip.neoconnect.ui.viewModel.ShopViewModel
import c.eip.neoconnect.utils.DataGetter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Search : Fragment() {
    private var pages: Int = 0
    private var searchState: Int = 0
    private var searchOffreState: Int = 0
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var infViewModel: InfViewModel
    private lateinit var shopViewModel: ShopViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Mise en place des onglets si Influenceur connecté
     * Mise en place du fond selon Influenceur ou Marque
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_search, container, false)
        tabLayout = inflate.findViewById(R.id.searchTab)
        viewPager = inflate.findViewById(R.id.searchPager)
        if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.searchLayout)
                .setBackgroundResource(R.drawable.background_shop)
            tabLayout.visibility = View.GONE
            viewPager.visibility = View.GONE
            inflate.findViewById<FrameLayout>(R.id.shopListInSearch).visibility = View.VISIBLE
            parentFragmentManager.beginTransaction().replace(R.id.shopListInSearch, ListInf())
                .commit()
        } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
            tabLayout.visibility = View.VISIBLE
            viewPager.visibility = View.VISIBLE
            inflate.findViewById<FrameLayout>(R.id.shopListInSearch).visibility = View.GONE
            inflate.findViewById<ConstraintLayout>(R.id.searchLayout)
                .setBackgroundResource(R.drawable.background_influencer)
            pages = 2
            tabLayout.tabGravity = TabLayout.GRAVITY_FILL
            val adapter = SearchAdapter(this)
            viewPager.adapter = adapter
            viewPager.isUserInputEnabled = false
            tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white, null))
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.setText(R.string.shop)
                    1 -> tab.setText(R.string.offre)
                }
            }.attach()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
        return inflate
    }

    private inner class SearchAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return pages
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ListShop()
                1 -> ListOffer()
                else -> ListInf()
            }
        }
    }

    companion object {
        var searchResponse: SearchResponseModel? =
            SearchResponseModel()

    }
}
