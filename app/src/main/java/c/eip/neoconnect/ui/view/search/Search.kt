package c.eip.neoconnect.ui.view.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
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
import c.eip.neoconnect.ui.viewModel.ProfilViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText

class Search : Fragment() {
    private var PAGES: Int = 0
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var viewModel: ProfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_search, container, false)
        tabLayout = inflate.findViewById(R.id.searchTab)
        viewPager = inflate.findViewById(R.id.searchPager)
        if (DataGetter.INSTANCE.getUserType(context!!) == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.searchLayout)
                .setBackgroundResource(R.drawable.background_shop)
            PAGES = 2
        } else if (DataGetter.INSTANCE.getUserType(context!!) == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.searchLayout)
                .setBackgroundResource(R.drawable.background_influencer)
            PAGES = 3
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
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextInputEditText>(R.id.searchKeyword)
            .setOnEditorActionListener { v, actionId, event ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        val token = DataGetter.INSTANCE.getToken(context!!)
                        val search = SearchModel()
                        search.pseudo =
                            view.findViewById<TextInputEditText>(R.id.searchKeyword).text.toString()
                        viewModel = ViewModelProvider(this).get(ProfilViewModel::class.java)
                        viewModel.searchUser(token!!, search).observe(this, Observer {
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
            return PAGES
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
