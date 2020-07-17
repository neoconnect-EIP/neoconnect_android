package c.eip.neoconnect.ui.view.profil

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.view.search.Search
import c.eip.neoconnect.ui.viewModel.ProfilViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ProfilOtherInf : Fragment() {
    private lateinit var viewModel: ProfilViewModel
    val bundle = bundleOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_profil_other_inf, container, false)
        when {
            arguments?.get("mode") == 0 -> {
                val id = arguments?.get("id") as Int
                val token = DataGetter.INSTANCE.getToken(context!!)
                viewModel = ViewModelProvider(this).get(ProfilViewModel::class.java)
                viewModel.getOtherInf(token!!, id).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (it.data?.userPicture?.size!! <= 0) {
                                    inflate.findViewById<ImageView>(R.id.otherProfilPicture)
                                        .setImageResource(R.drawable.ic_picture_inf)
                                } else {
                                    Glide.with(context!!).load(it.data.userPicture[0]?.imageData)
                                        .circleCrop().error(R.drawable.ic_picture_inf)
                                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                        .into(inflate.findViewById(R.id.otherProfilPicture))
                                }
                                inflate.findViewById<TextView>(R.id.otherProfilPseudo).text =
                                    it.data.pseudo
                                inflate.findViewById<TextView>(R.id.otherProfilSubject).text =
                                    it.data.theme
                                if (it.data.average.isNullOrBlank()) {
                                    inflate.findViewById<TextView>(R.id.otherProfilAverage).text = "0"
                                } else {
                                    inflate.findViewById<TextView>(R.id.otherProfilAverage).text =
                                        it.data.average
                                }
                                bundle.putString("dest", it.data.email)
                            }
                            Status.ERROR -> {
                                Log.e("Get Inf", "$id introuvable")
                                Toast.makeText(context, "Utilisateur introuvable", Toast.LENGTH_LONG)
                                    .show()
                                findNavController().popBackStack()
                            }
                        }
                    }
                })
            }
            arguments?.get("mode") == 1 -> {
                if (Search.searchResponse?.userPicture?.size!! <= 0) {
                    inflate.findViewById<ImageView>(R.id.otherProfilPicture)
                        .setImageResource(R.drawable.ic_picture_inf)
                } else {
                    Glide.with(context!!).load(Search.searchResponse?.userPicture?.get(0)?.imageData)
                        .circleCrop().error(R.drawable.ic_picture_inf)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(inflate.findViewById(R.id.otherProfilPicture))
                }
                inflate.findViewById<TextView>(R.id.otherProfilPseudo).text =
                    Search.searchResponse?.pseudo
                inflate.findViewById<TextView>(R.id.otherProfilSubject).text =
                    Search.searchResponse?.theme
                inflate.findViewById<TextView>(R.id.otherProfilAverage).text =
                    Search.searchResponse?.average.toString()
                bundle.putString("dest", Search.searchResponse?.email)
            }
            else -> {
                findNavController().popBackStack()
            }
        }
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<Button>(R.id.contactProfilButton).setOnClickListener {
            findNavController().navigate(R.id.navigation_contact, bundle)
        }
    }
}
