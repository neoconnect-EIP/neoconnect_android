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
import c.eip.neoconnect.MainViewShop
import c.eip.neoconnect.R
import c.eip.neoconnect.utils.DataGetter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MyAccountShop : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_my_account_shop, container, false)
        if (MainViewShop.shopData?.userPicture?.size!! <= 0) {
            inflate.findViewById<ImageView>(R.id.myAccountPicture)
                .setImageResource(R.drawable.ic_picture_shop)
        } else {
            Glide.with(requireContext()).load(MainViewShop.shopData!!.userPicture[0]?.imageData)
                .circleCrop().error(R.drawable.ic_picture_shop)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(inflate.findViewById(R.id.myAccountPicture))
        }
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            DataGetter.INSTANCE.clearData(requireContext())
            findNavController().navigate(R.id.navigation_login_shop)
        }
        view.findViewById<TextView>(R.id.goToProfil).setOnClickListener {
            findNavController().navigate(R.id.navigation_profil_shop)
        }
        view.findViewById<TextView>(R.id.goToMyOffers).setOnClickListener {
            findNavController().navigate(R.id.navigation_offer_shop)
        }
        view.findViewById<TextView>(R.id.goToContact).setOnClickListener {
            findNavController().navigate(R.id.navigation_contact)
        }
        view.findViewById<TextView>(R.id.goToMyStats).setOnClickListener {
            findNavController().navigate(R.id.navigation_stats)
        }
    }
}
