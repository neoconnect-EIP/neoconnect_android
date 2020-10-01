package c.eip.neoconnect


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import c.eip.neoconnect.data.model.profil.InfluenceurResponseModel
import c.eip.neoconnect.ui.view.account.MyAccountInf
import c.eip.neoconnect.ui.view.chat.Chat
import c.eip.neoconnect.ui.view.feed.FeedInf
import c.eip.neoconnect.ui.view.search.Search
import c.eip.neoconnect.ui.viewModel.InfViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainViewInf : Fragment() {
    private lateinit var viewModel: InfViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_main_view_inf, container, false)
        val token = DataGetter.INSTANCE.getToken(requireContext())
        viewModel = ViewModelProvider(this).get(InfViewModel::class.java)
        viewModel.getProfilInf(token = token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        influenceurData = it.data
                    }
                    Status.ERROR -> {
                    }
                }
            }
        })
        return inflate
    }

    /**
     * Mise en place des interaction possible
     * Déplacement entre les vues
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.beginTransaction().replace(R.id.container, FeedInf())
            .addToBackStack(null).commit()
        view.findViewById<BottomNavigationView>(R.id.nav_view)
            .setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_home -> {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, FeedInf())
                            .addToBackStack(null).commit()
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.navigation_search -> {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, Search())
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
                            .replace(R.id.container, MyAccountInf())
                            .addToBackStack(null).commit()
                        return@setOnNavigationItemSelectedListener true
                    }
                    else -> return@setOnNavigationItemSelectedListener true
                }
            }
    }

    companion object {
        var influenceurData: InfluenceurResponseModel? = InfluenceurResponseModel()
    }
}
