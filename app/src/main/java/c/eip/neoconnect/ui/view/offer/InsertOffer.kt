package c.eip.neoconnect.ui.view.offer


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import c.eip.neoconnect.MainViewShop
import c.eip.neoconnect.R
import c.eip.neoconnect.data.model.offres.OffreModel
import c.eip.neoconnect.ui.viewModel.OffresViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.google.android.material.textfield.TextInputEditText

class InsertOffer : Fragment() {
    private lateinit var viewModel: OffresViewModel
    private var themeOffreState: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_insert_offer, container, false)
        val offreThemeList = resources.getStringArray(R.array.themeSpinner)
        val offreThemeSpinner = inflate.findViewById<Spinner>(R.id.themeOffreSpinner)
        if (offreThemeSpinner != null) {
            val themeAdapter = ArrayAdapter(context!!, R.layout.layout_spinner_item, offreThemeList)
            offreThemeSpinner.adapter = themeAdapter
        }
        offreThemeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                themeOffreState = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val offreThemeList = resources.getStringArray(R.array.themeSpinner)
        view.findViewById<TextView>(R.id.resetForm).setOnClickListener {
            view.findViewById<ImageView>(R.id.insertOfferPicture1).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture2).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture3).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture4).setImageURI(null)
            view.findViewById<ImageView>(R.id.insertOfferPicture5).setImageURI(null)
            view.findViewById<TextInputEditText>(R.id.insertOfferName).text = null
            view.findViewById<TextInputEditText>(R.id.insertOfferDescription).text = null
            view.findViewById<RadioButton>(R.id.insertOfferSexFemme).isChecked = false
            view.findViewById<RadioButton>(R.id.insertOfferSexHomme).isChecked = false
            view.findViewById<Spinner>(R.id.themeOffreSpinner).setSelection(0)
            view.findViewById<TextInputEditText>(R.id.insertOfferColor).text = null
        }
        view.findViewById<Button>(R.id.insertOfferButton).setOnClickListener {
            val productName =
                view.findViewById<TextInputEditText>(R.id.insertOfferName).text.toString()
            val productDesc =
                view.findViewById<TextInputEditText>(R.id.insertOfferDescription).text.toString()
            val productSex =
                when (view.findViewById<RadioGroup>(R.id.insertOfferSex).checkedRadioButtonId) {
                    R.id.insertOfferSexFemme -> "Femme"
                    R.id.insertOfferSexHomme -> "Homme"
                    else -> null
                }
            val productSubject = offreThemeList[themeOffreState]
            val productColor =
                view.findViewById<TextInputEditText>(R.id.insertOfferColor).text.toString()
            if (productName.isNotEmpty() && productName.isNotBlank() && productDesc.isNotEmpty() && productDesc.isNotBlank() && productSubject.isNotBlank() && productSubject.isNotEmpty() && productColor.isNotEmpty() && productColor.isNotBlank() && !productSex.isNullOrBlank()) {
                val offer = OffreModel()
                val token = DataGetter.INSTANCE.getToken(context!!)
                offer.productName = productName
                offer.productSex = productSex
                offer.productDesc = productDesc
                offer.productSubject = productSubject
                offer.brand = MainViewShop.shopData?.pseudo
                offer.color = productColor
                viewModel = ViewModelProvider(this).get(OffresViewModel::class.java)
                viewModel.insertOffer(token!!, offer).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                Log.i(
                                    "Offre",
                                    "${DataGetter.INSTANCE.getUserId(context!!)} a ajouté l'offre ${it.data?.id}"
                                )
                                view.findViewById<ImageView>(R.id.insertOfferPicture1)
                                    .setImageURI(null)
                                view.findViewById<ImageView>(R.id.insertOfferPicture2)
                                    .setImageURI(null)
                                view.findViewById<ImageView>(R.id.insertOfferPicture3)
                                    .setImageURI(null)
                                view.findViewById<ImageView>(R.id.insertOfferPicture4)
                                    .setImageURI(null)
                                view.findViewById<ImageView>(R.id.insertOfferPicture5)
                                    .setImageURI(null)
                                view.findViewById<TextInputEditText>(R.id.insertOfferName).text =
                                    null
                                view.findViewById<TextInputEditText>(R.id.insertOfferDescription).text =
                                    null
                                view.findViewById<RadioButton>(R.id.insertOfferSexFemme).isChecked =
                                    false
                                view.findViewById<RadioButton>(R.id.insertOfferSexHomme).isChecked =
                                    false
                                view.findViewById<Spinner>(R.id.themeOffreSpinner).setSelection(0)
                                view.findViewById<TextInputEditText>(R.id.insertOfferColor).text =
                                    null
                            }
                            Status.ERROR -> {
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                                Log.e("Offre", it.message)
                            }
                        }
                    }
                })
            }
        }
    }
}
