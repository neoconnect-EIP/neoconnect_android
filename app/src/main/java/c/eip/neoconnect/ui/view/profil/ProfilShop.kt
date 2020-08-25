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
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import c.eip.neoconnect.ui.viewModel.ShopViewModel
import c.eip.neoconnect.ui.viewModel.UserViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ProfilShop : Fragment() {
    private lateinit var shopViewModel: ShopViewModel
    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_profil_shop, container, false)
        val token = DataGetter.INSTANCE.getToken(requireContext())
        shopViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        shopViewModel.getProfilShop(token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        shopData = it.data
                        if (it.data?.userPicture.isNullOrEmpty()) {
                            inflate.findViewById<ImageView>(R.id.myProfilPicture)
                                .setImageResource(R.drawable.ic_picture_shop)
                        } else {
                            Glide.with(requireContext())
                                .load(it.data?.userPicture?.get(0)?.imageData)
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .error(R.drawable.ic_picture_shop)
                                .into(inflate.findViewById(R.id.myProfilPicture))
                        }
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
                        Log.e("Profil", it.message.toString())
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
                        val token = DataGetter.INSTANCE.getToken(requireContext())
                        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
                        userViewModel.deleteAccount(token!!)
                            .observe(viewLifecycleOwner, Observer { deleteResponse ->
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
                                                "${DataGetter.INSTANCE.getUserId(requireContext())}"
                                            )
                                            DataGetter.INSTANCE.clearData(requireContext())
                                            if (DataGetter.INSTANCE.getToken(requireContext())
                                                    .isNullOrEmpty()
                                            ) {
                                                findNavController().navigate(R.id.navigation_login_shop)
                                            }
                                        }
                                        Status.ERROR -> {
                                            Log.e(
                                                "Suppression de compte",
                                                deleteResponse.message.toString()
                                            )
                                            Toast.makeText(
                                                context,
                                                deleteResponse.message,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            if (DataGetter.INSTANCE.getToken(requireContext())
                                                    .isNullOrEmpty()
                                            ) {
                                                findNavController().navigate(R.id.navigation_login_shop)
                                            }
                                        }
                                    }
                                }
                            })
                    }
                    R.id.editProfil -> findNavController().navigate(R.id.navigation_edit_profil_shop)
                }
                true
            }
            popupMenu.show()
        }
    }

    companion object {
        var shopData: ShopResponseModel? = ShopResponseModel()
    }
}
