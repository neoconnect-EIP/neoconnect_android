package c.eip.neoconnect


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import c.eip.neoconnect.ui.view.account.MyAccountShop
import c.eip.neoconnect.ui.view.chat.Chat
import c.eip.neoconnect.ui.view.feed.FeedShop
import c.eip.neoconnect.ui.view.offer.InsertOffer
import c.eip.neoconnect.ui.view.search.Search
import c.eip.neoconnect.ui.viewModel.ProfilViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainViewShop : Fragment() {
    private lateinit var viewModel: ProfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_main_view_shop, container, false)
        val token = DataGetter.INSTANCE.getToken(context!!)
        viewModel = ViewModelProvider(this).get(ProfilViewModel::class.java)
        viewModel.getProfilShop(token!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        shopData = it.data
                    }
                    Status.ERROR -> {
                    }
                }
            }
        })
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.beginTransaction().replace(R.id.container, FeedShop())
            .addToBackStack(null).commit()
        view.findViewById<BottomNavigationView>(R.id.nav_view)
            .setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_home -> {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, FeedShop())
                            .addToBackStack(null).commit()
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.navigation_search -> {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, Search())
                            .addToBackStack(null).commit()
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.navigation_insert_offer -> {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, InsertOffer())
                            .addToBackStack(null).commit()
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.navigation_chat -> {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, Chat())
                            .addToBackStack(null).commit()
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.navigation_profil -> {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, MyAccountShop())
                            .addToBackStack(null).commit()
                        return@setOnNavigationItemSelectedListener true
                    }
                    else -> return@setOnNavigationItemSelectedListener true
                }
            }
    }

    companion object {
        var shopData: ShopResponseModel? = ShopResponseModel()
    }
}
