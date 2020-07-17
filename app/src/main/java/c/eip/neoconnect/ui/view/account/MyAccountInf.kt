package c.eip.neoconnect.ui.view.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.MainViewInf
import c.eip.neoconnect.R
import c.eip.neoconnect.utils.DataGetter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MyAccountInf : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_my_account_inf, container, false)
        if (MainViewInf.influenceurData?.userPicture?.size!! <= 0) {
            inflate.findViewById<ImageView>(R.id.myAccountPicture)
                .setImageResource(R.drawable.ic_picture_inf)
        } else {
            Glide.with(context!!).load(MainViewInf.influenceurData!!.userPicture[0]?.imageData)
                .circleCrop().error(R.drawable.ic_picture_inf)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(inflate.findViewById(R.id.myAccountPicture))
        }
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            DataGetter.INSTANCE.clearData(context!!)
            findNavController().navigate(R.id.navigation_login_inf)
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
    }
}
