package c.eip.neoconnect.ui.view.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
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
            pages = 2
            inflate.findViewById<TextInputEditText>(R.id.searchKeyword).textAlignment =
                TEXT_ALIGNMENT_CENTER
            inflate.findViewById<Spinner>(R.id.searchSpinner).visibility = View.GONE
            inflate.findViewById<Spinner>(R.id.searchOffreSpinner).visibility = View.GONE
        } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.searchLayout)
                .setBackgroundResource(R.drawable.background_influencer)
            pages = 3
        }
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = SearchAdapter(this)
        viewPager.adapter = adapter
        tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white, null))
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.icon_inf_white)
                1 -> tab.setIcon(R.drawable.icon_shop_white)
                2 -> tab.setIcon(R.drawable.icon_offre_white)
            }
        }.attach()

        val searchType = resources.getStringArray(R.array.searchSpinner)
        val searchSpinner = inflate.findViewById<Spinner>(R.id.searchSpinner)
        if (searchSpinner != null) {
            val searchAdapter =
                ArrayAdapter(requireContext(), R.layout.layout_spinner_item, searchType)
            searchSpinner.adapter = searchAdapter
        }
        searchSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                searchState = position
                when (position) {
                    0 -> inflate.findViewById<Spinner>(R.id.searchOffreSpinner).visibility =
                        View.GONE
                    1 -> inflate.findViewById<Spinner>(R.id.searchOffreSpinner).visibility =
                        View.VISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val searchOffreType = resources.getStringArray(R.array.searchOffreSpinner)
        val searchOffreSpinner = inflate.findViewById<Spinner>(R.id.searchOffreSpinner)
        if (searchOffreSpinner != null) {
            val searchOffreAdapter =
                ArrayAdapter(requireContext(), R.layout.layout_spinner_item, searchOffreType)
            searchOffreSpinner.adapter = searchOffreAdapter
        }

        searchOffreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                searchOffreState = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
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
                        if (searchState == 0) {
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
                        } else if (searchState == 1) {
                            viewPager.setCurrentItem(2, true)
                            val bundle = bundleOf()
                            val searchKeyword =
                                view.findViewById<TextInputEditText>(R.id.searchKeyword).text.toString()
                            when (searchOffreState) {
                                0 -> bundle.putString("color", searchKeyword)
                                1 -> bundle.putString("brand", searchKeyword)
                                2 -> bundle.putString("sex", searchKeyword)
                            }
                            view.findNavController()
                                .navigate(R.id.navigation_list_sorted_offer, bundle)
                        }

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
                0 -> ListInf()
                1 -> ListShop()
                2 -> ListOffer()
                else -> ListInf()
            }
        }
    }

    companion object {
        var searchResponse: SearchResponseModel? =
            SearchResponseModel()

    }
}
