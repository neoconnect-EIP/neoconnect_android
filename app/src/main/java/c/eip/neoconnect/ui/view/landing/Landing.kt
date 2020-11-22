package c.eip.neoconnect.ui.view.landing


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.main.MainViewInf
import c.eip.neoconnect.main.MainViewShop
import c.eip.neoconnect.utils.DataGetter

class Landing : Fragment() {

    /**
     * Creation de la vue. Déclaration du layout à afficher
     * Redirection si déjà connecté 1 fois
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_landing, container, false)
        if (!DataGetter.INSTANCE.getToken(requireContext()).isNullOrBlank()) {
            if (DataGetter.INSTANCE.getUserType(requireContext()) == "influencer") {
                val intent = Intent(context, MainViewInf::class.java).apply {}
                this.activity?.finish()
                startActivity(intent)
            } else if (DataGetter.INSTANCE.getUserType(requireContext()) == "shop") {
                val intent = Intent(context, MainViewShop::class.java).apply {}
                this.activity?.finish()
                startActivity(intent)
            }
        }
        return inflate
    }

    /**
     * Mise en place des interaction possible
     * Déplacement entre les vues
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.landingToShop).setOnClickListener {
            findNavController().navigate(R.id.navigation_description_shop, null)
        }
        view.findViewById<Button>(R.id.landingToInf).setOnClickListener {
            findNavController().navigate(R.id.navigation_description_inf, null)
        }
    }
}
