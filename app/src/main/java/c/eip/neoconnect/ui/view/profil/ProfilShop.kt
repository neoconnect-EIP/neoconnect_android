package c.eip.neoconnect.ui.view.profil


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.viewModel.ProfilViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class ProfilShop : Fragment() {
    private lateinit var viewModel: ProfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_profil_shop, container, false)
        val token = DataGetter.INSTANCE.getToken(context!!)
        viewModel = ViewModelProvider(this).get(ProfilViewModel::class.java)
        viewModel.getProfilShop(token!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        inflate.findViewById<TextView>(R.id.profilPseudo).text = it.data?.pseudo
                        inflate.findViewById<TextView>(R.id.profilEmail).text = it.data?.email
                        inflate.findViewById<TextView>(R.id.profilNom).text = it.data?.fullName
                        inflate.findViewById<TextView>(R.id.profilPhone).text = it.data?.phone
                        inflate.findViewById<TextView>(R.id.profilVille).text = it.data?.city
                        inflate.findViewById<TextView>(R.id.profilPostal).text = it.data?.postal
                        inflate.findViewById<TextView>(R.id.profilSociety).text = it.data?.society
                        inflate.findViewById<TextView>(R.id.profilFunction).text = it.data?.fonction
                        inflate.findViewById<TextView>(R.id.profilWebsite).text = it.data?.website
                        inflate.findViewById<TextView>(R.id.profilFacebook).text = it.data?.facebook
                        inflate.findViewById<TextView>(R.id.profilTwitter).text = it.data?.twitter
                        inflate.findViewById<TextView>(R.id.profilInstagram).text =
                            it.data?.instagram
                        inflate.findViewById<TextView>(R.id.profilSnapchat).text = it.data?.snapchat
                        inflate.findViewById<TextView>(R.id.profilSubject).text = it.data?.theme
                        Log.i("Profil", "${it.data?.userType} ${it.data?.id} récupéré")
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        Log.e("Profil", it.message)
                    }
                }
            }
        })
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<ImageView>(R.id.settingsButton).setOnClickListener {
            val popupMenu = PopupMenu(context, view.findViewById(R.id.settingsButton))
            popupMenu.menuInflater.inflate(R.menu.profil_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.deleteMyAccount -> {
                        val token = DataGetter.INSTANCE.getToken(context!!)
                        viewModel = ViewModelProvider(this).get(ProfilViewModel::class.java)
                        viewModel.deleteAccount(token!!).observe(this, Observer { deleteResponse ->
                            deleteResponse?.let { resources ->
                                when (resources.status) {
                                    Status.SUCCESS -> {
                                        Toast.makeText(
                                            context,
                                            deleteResponse.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.i(
                                            "Suppression de compte",
                                            "${DataGetter.INSTANCE.getUserId(context!!)}"
                                        )
                                        DataGetter.INSTANCE.clearData(context!!)
                                        if (DataGetter.INSTANCE.getToken(context!!)
                                                .isNullOrEmpty()
                                        ) {
                                            findNavController().navigate(R.id.navigation_login_shop)
                                        }
                                    }
                                    Status.ERROR -> {
                                        Log.e(
                                            "Suppression de compte",
                                            deleteResponse.message
                                        )
                                        Toast.makeText(
                                            context,
                                            deleteResponse.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        if (DataGetter.INSTANCE.getToken(context!!)
                                                .isNullOrEmpty()
                                        ) {
                                            findNavController().navigate(R.id.navigation_login_shop)
                                        }
                                    }
                                }
                            }
                        })
                    }
                    R.id.editProfil -> Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show()
                }
                true
            }
            popupMenu.show()
        }
    }
}
