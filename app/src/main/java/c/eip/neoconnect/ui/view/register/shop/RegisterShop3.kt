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
        if (arguments?.get("name") != null) {
            inflate.findViewById<TextInputEditText>(R.id.registerName)
                ?.setText(arguments?.get("name") as String)
        }
        if (arguments?.get("ville") != null) {
            inflate.findViewById<TextInputEditText>(R.id.registerVille)
                ?.setText(arguments?.get("ville") as String)
        }
        if (arguments?.get("postal") != null) {
            inflate.findViewById<TextInputEditText>(R.id.registerPostal)
                ?.setText(arguments?.get("postal") as String)
        }
        if (arguments?.get("phone") != null) {
            inflate.findViewById<TextInputEditText>(R.id.registerPhone)
                ?.setText(arguments?.get("phone") as String)
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
            when (view.findViewById<TextInputEditText>(R.id.registerName).text.toString().trim()
                .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerName).text.toString()
                .trim().isNotEmpty()) {
                true -> bundle.putString(
                    "name",
                    view.findViewById<TextInputEditText>(R.id.registerName).text.toString()
                )
                false -> bundle.putString("name", null)
            }
            when (view.findViewById<TextInputEditText>(R.id.registerVille).text.toString().trim()
                .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerVille).text.toString()
                .trim().isNotEmpty()) {
                true -> bundle.putString(
                    "ville",
                    view.findViewById<TextInputEditText>(R.id.registerVille).text.toString()
                )
                false -> bundle.putString("ville", null)
            }
            when (view.findViewById<TextInputEditText>(R.id.registerPostal).text.toString().trim()
                .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerPostal).text.toString()
                .trim().isNotEmpty()) {
                true -> bundle.putString(
                    "postal",
                    view.findViewById<TextInputEditText>(R.id.registerPostal).text.toString()
                )
                false -> bundle.putString("postal", null)
            }
            when (view.findViewById<TextInputEditText>(R.id.registerPhone).text.toString().trim()
                .isNotBlank() && view.findViewById<TextInputEditText>(R.id.registerPhone).text.toString()
                .trim().isNotEmpty()) {
                true -> bundle.putString(
                    "phone",
                    view.findViewById<TextInputEditText>(R.id.registerPhone).text.toString()
                )
                false -> bundle.putString("phone", null)
            }
            findNavController().navigate(R.id.navigation_register_shop_4, bundle)
        }
    }
}
