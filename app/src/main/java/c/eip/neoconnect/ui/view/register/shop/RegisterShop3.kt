package c.eip.neoconnect.ui.view.register.shop


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import com.google.android.material.textfield.TextInputEditText

class RegisterShop3 : Fragment() {

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_register_shop_3, container, false)
        if (arguments?.get("society") != null) {
            inflate.findViewById<TextInputEditText>(R.id.registerSociety)
                ?.setText(arguments?.get("society") as String)
        }
        if (arguments?.get("fonction") != null) {
            inflate.findViewById<TextInputEditText>(R.id.registerFonction)
                ?.setText(arguments?.get("fonction") as String)
        }
        if (arguments?.get("website") != null) {
            inflate.findViewById<TextInputEditText>(R.id.registerWebsite)
                ?.setText(arguments?.get("website") as String)
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
            findNavController().navigate(R.id.navigation_register_shop_2, arguments)
        }
        view.findViewById<Button>(R.id.nextPageRegister).setOnClickListener {
            val bundle = bundleOf()
            bundle.putAll(arguments)
            when (view.findViewById<TextInputEditText>(R.id.registerSociety).text.toString().trim()
                .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerSociety).text.toString()
                .trim().isNotEmpty()) {
                true -> bundle.putString(
                    "society",
                    view.findViewById<TextInputEditText>(R.id.registerSociety).text.toString()
                )
                false -> bundle.putString("society", null)
            }
            when (view.findViewById<TextInputEditText>(R.id.registerFonction).text.toString().trim()
                .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerFonction).text.toString()
                .trim().isNotEmpty()) {
                true -> bundle.putString(
                    "fonction",
                    view.findViewById<TextInputEditText>(R.id.registerFonction).text.toString()
                )
                false -> bundle.putString("fonction", null)
            }
            when (view.findViewById<TextInputEditText>(R.id.registerWebsite).text.toString().trim()
                .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerWebsite).text.toString()
                .trim().isNotEmpty()) {
                true -> bundle.putString(
                    "website",
                    view.findViewById<TextInputEditText>(R.id.registerWebsite).text.toString()
                )
                false -> bundle.putString("website", null)
            }
            findNavController().navigate(R.id.navigation_register_shop_4, bundle)
        }
    }
}
