package c.eip.neoconnect.ui.view.profil


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.profil.ShopResponseModel
import c.eip.neoconnect.main.MainActivity
import c.eip.neoconnect.ui.viewModel.ShopViewModel
import c.eip.neoconnect.ui.viewModel.UserViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ProfilShop : Fragment() {
    private lateinit var shopViewModel: ShopViewModel
    private lateinit var userViewModel: UserViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_profil_shop, container, false)
        getProfilShop(inflate = inflate)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
        return inflate
    }

    /**
     * Mise en place des interaction possible
     * Déplacement entre les vues
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<ImageView>(R.id.settingsButton).setOnClickListener {
            settingsOption(view = view)
        }
    }

    /**
     * Récupération du profil Influenceur
     */
    private fun getProfilShop(inflate: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        shopViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        shopViewModel.getProfilShop(token = token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        shopData = it.data
                        if (it.data?.userPicture.isNullOrEmpty()) {
                            inflate.findViewById<ImageView>(R.id.myProfilPicture)
                                .setImageResource(R.drawable.ic_picture_shop)
                        } else {
                            Glide.with(requireContext())
                                .load(it.data?.userPicture?.get(0)?.imageData).circleCrop()
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .error(R.drawable.ic_picture_shop)
                                .into(inflate.findViewById(R.id.myProfilPicture))
                        }
                        inflate.findViewById<TextView>(R.id.profilDescription).text = it.data?.userDescription
                        inflate.findViewById<TextView>(R.id.profilPseudo).text = it.data?.pseudo
                        inflate.findViewById<TextView>(R.id.profilEmail).text = it.data?.email
                        inflate.findViewById<TextView>(R.id.profilNom).text = it.data?.fullName
                        inflate.findViewById<TextView>(R.id.profilPhone).text = it.data?.phone
                        inflate.findViewById<TextView>(R.id.profilVille).text = it.data?.city
                        inflate.findViewById<TextView>(R.id.profilPostal).text = it.data?.postal
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
    }

    /**
     * Option pour son profil (Supprimer, Modifier)
     */
    private fun settingsOption(view: View) {
        val popupMenu = PopupMenu(context, view.findViewById(R.id.settingsButton))
        popupMenu.menuInflater.inflate(R.menu.profil_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteMyAccount -> {
                    val mDialogView =
                        LayoutInflater.from(requireContext())
                            .inflate(R.layout.dialog_delete_account, null)
                    val mAlertDialogBuilder =
                        AlertDialog.Builder(requireContext()).setView(mDialogView)
                            .setTitle("Êtes-vous sûr de vouloir supprimer votre compte ?")
                    val mAlertDialog = mAlertDialogBuilder.show()
                    mDialogView.findViewById<TextView>(R.id.yesDeleteAccount).setOnClickListener {
                        deleteAccount()
                        mAlertDialog.dismiss()
                    }
                    mDialogView.findViewById<TextView>(R.id.noDeleteAccount).setOnClickListener {
                        Toast.makeText(
                            context,
                            "Suppression de compte annulé",
                            Toast.LENGTH_SHORT
                        ).show()
                        mAlertDialog.dismiss()
                    }
                }
                R.id.editProfil -> findNavController().navigate(R.id.navigation_edit_profil_shop)
            }
            true
        }
        popupMenu.show()
    }

    /**
     * Supprimer son compte
     */
    private fun deleteAccount() {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.deleteAccount(token = token!!)
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
                                val intent = Intent(context, MainActivity::class.java).apply {}
                                this.activity?.finish()
                                startActivity(intent)
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

    companion object {
        var shopData: ShopResponseModel? = ShopResponseModel()
    }
}
