package c.eip.neoconnect.ui.view.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.main.MainActivity
import c.eip.neoconnect.ui.view.feed.FeedInf
import c.eip.neoconnect.utils.DataGetter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MyAccountInf : Fragment() {
    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Affichage de l'image de profil ou d'une image par défaut
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_my_account_inf, container, false)
        if (FeedInf.influenceurData?.userPicture?.size!! <= 0) {
            inflate.findViewById<ImageView>(R.id.myAccountPicture)
                .setImageResource(R.drawable.ic_picture_inf)
        } else {
            Glide.with(requireContext())
                .load(FeedInf.influenceurData!!.userPicture[0]?.imageData)
                .circleCrop().error(R.drawable.ic_picture_inf)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(inflate.findViewById(R.id.myAccountPicture))
        }
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
        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            DataGetter.INSTANCE.clearData(requireContext())
            val intent = Intent(context, MainActivity::class.java).apply {}
            this.activity?.finish()
            startActivity(intent)
        }
        view.findViewById<TextView>(R.id.goToProfil).setOnClickListener {
            findNavController().navigate(R.id.navigation_profil_inf)
        }
        view.findViewById<TextView>(R.id.goToMyOffers).setOnClickListener {
            findNavController().navigate(R.id.navigation_offer_inf)
        }
        view.findViewById<TextView>(R.id.goToContact).setOnClickListener {
            findNavController().navigate(R.id.navigation_contact)
        }
        view.findViewById<TextView>(R.id.goToMyStats).setOnClickListener {
            findNavController().navigate(R.id.navigation_stats, bundleOf("profil" to "me"))
        }
        view.findViewById<TextView>(R.id.goToFAQ).setOnClickListener {
            findNavController().navigate(R.id.navigation_faq)
        }
        view.findViewById<TextView>(R.id.goToParrainage).setOnClickListener {
            findNavController().navigate(R.id.navigation_parrainage)
        }
    }

}
