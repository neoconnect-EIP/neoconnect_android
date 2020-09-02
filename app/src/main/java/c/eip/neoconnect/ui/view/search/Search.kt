package c.eip.neoconnect.ui.view.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.search.SearchModel
import c.eip.neoconnect.data.model.search.SearchResponseModel
import c.eip.neoconnect.ui.view.list.ListInf
import c.eip.neoconnect.ui.view.list.ListOffer
import c.eip.neoconnect.ui.view.list.ListShop
import c.eip.neoconnect.ui.viewModel.InfViewModel
import c.eip.neoconnect.ui.viewModel.ShopViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Resource
import c.eip.neoconnect.utils.Status
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText

class Search : Fragment() {
    private var pages: Int = 0
    private var searchState: Int = 0
    private var searchOffreState: Int = 0
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var infViewModel: InfViewModel
    private lateinit var shopViewModel: ShopViewModel

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
            inflate.findViewById<TextInputEditText>(R.id.searchKeyword).setHint(R.string.searchInf)
            inflate.findViewById<TabLayout>(R.id.searchTab).visibility = View.GONE
            inflate.findViewById<ViewPager2>(R.id.searchPager).visibility = View.GONE
            inflate.findViewById<FrameLayout>(R.id.shopListInSearch).visibility = View.VISIBLE
            parentFragmentManager.beginTransaction().replace(R.id.shopListInSearch, ListInf())
                .commit()
        } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
            inflate.findViewById<TabLayout>(R.id.searchTab).visibility = View.VISIBLE
            inflate.findViewById<ViewPager2>(R.id.searchPager).visibility = View.VISIBLE
            inflate.findViewById<FrameLayout>(R.id.shopListInSearch).visibility = View.GONE
            inflate.findViewById<ConstraintLayout>(R.id.searchLayout)
                .setBackgroundResource(R.drawable.background_influencer)
            inflate.findViewById<TextInputEditText>(R.id.searchKeyword).setHint(R.string.searchShop)
            pages = 2
            tabLayout.tabGravity = TabLayout.GRAVITY_FILL
            val adapter = SearchAdapter(this)
            viewPager.adapter = adapter
            tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white, null))
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.setIcon(R.drawable.icon_shop_white)
                    1 -> tab.setIcon(R.drawable.icon_offre_white)
                }
            }.attach()
        }
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextInputEditText>(R.id.searchKeyword)
            .setOnEditorActionListener { _, actionId, _ ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        val token = DataGetter.INSTANCE.getToken(requireContext())
                        val search = SearchModel()
                        search.pseudo =
                            view.findViewById<TextInputEditText>(R.id.searchKeyword).text.toString()
                        val viewModel: LiveData<Resource<SearchResponseModel>>
                        if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop") {
                            infViewModel = ViewModelProvider(this).get(InfViewModel::class.java)
                            viewModel = infViewModel.searchInf(token!!, search)
                        } else {
                            shopViewModel =
                                ViewModelProvider(this).get(ShopViewModel::class.java)
                            viewModel = shopViewModel.searchShop(token!!, search)
                        }
                        viewModel.observe(viewLifecycleOwner, Observer {
                            it?.let { resource ->
                                when (resource.status) {
                                    Status.SUCCESS -> {
                                        val bundle = bundleOf("mode" to 1)
                                        searchResponse = it.data
                                        when (it.data?.userType) {
                                            "shop" -> {
                                                findNavController().navigate(
                                                    R.id.navigation_other_profil_shop,
                                                    bundle
                                                )
                                            }
                                            "influencer" -> {
                                                findNavController().navigate(
                                                    R.id.navigation_other_profil_inf,
                                                    bundle
                                                )
                                            }
                                            else -> {
                                                Toast.makeText(
                                                    context,
                                                    "Imposible de récupérer cet utilisateur",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                Log.e("Search User", it.message)
                                            }
                                        }
                                    }
                                    Status.ERROR -> {
                                        Toast.makeText(context, it.message, Toast.LENGTH_LONG)
                                            .show()
                                        Log.e("Search User", it.message)
                                    }
                                }
                            }
                        })
                        true
                    }
                    else -> false
                }
            }
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
