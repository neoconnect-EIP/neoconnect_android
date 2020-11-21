package c.eip.neoconnect.ui.view.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R

class FAQ : Fragment() {
    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_faq, container, false)
        if (arguments?.get("type") != null) {
            when (arguments?.get("type")) {
                "inf" -> inflate.findViewById<ConstraintLayout>(R.id.faqLayout)
                    .setBackgroundResource(R.drawable.background_influencer)
                "shop" -> inflate.findViewById<ConstraintLayout>(R.id.faqLayout)
                    .setBackgroundResource(R.drawable.background_shop)
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
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<Button>(R.id.moreQuestions).setOnClickListener {
            findNavController().navigate(R.id.navigation_contact)
        }
        view.findViewById<Button>(R.id.question1).setOnClickListener {
            view.findViewById<TextView>(R.id.reponse1).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.reponse2).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse3).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse4).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse5).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse6).visibility = View.GONE
        }
        view.findViewById<Button>(R.id.question2).setOnClickListener {
            view.findViewById<TextView>(R.id.reponse2).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.reponse1).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse3).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse4).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse5).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse6).visibility = View.GONE
        }
        view.findViewById<Button>(R.id.question3).setOnClickListener {
            view.findViewById<TextView>(R.id.reponse3).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.reponse1).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse2).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse4).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse5).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse6).visibility = View.GONE
        }
        view.findViewById<Button>(R.id.question4).setOnClickListener {
            view.findViewById<TextView>(R.id.reponse4).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.reponse1).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse2).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse3).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse5).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse6).visibility = View.GONE
        }
        view.findViewById<Button>(R.id.question5).setOnClickListener {
            view.findViewById<TextView>(R.id.reponse5).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.reponse1).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse2).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse3).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse4).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse6).visibility = View.GONE
        }
        view.findViewById<Button>(R.id.question6).setOnClickListener {
            view.findViewById<TextView>(R.id.reponse6).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.reponse1).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse2).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse3).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse4).visibility = View.GONE
            view.findViewById<TextView>(R.id.reponse5).visibility = View.GONE
        }
    }
}