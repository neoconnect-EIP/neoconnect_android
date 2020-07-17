package c.eip.neoconnect.ui.view.landing


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import c.eip.neoconnect.R
import c.eip.neoconnect.utils.DataGetter

class Landing : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_landing, container, false)
        if (!DataGetter.INSTANCE.getToken(context!!).isNullOrBlank()) {
            Log.i("Data", DataGetter.INSTANCE.getToken(context!!))
            Log.i("Data", DataGetter.INSTANCE.getUserId(context!!).toString())
            if (DataGetter.INSTANCE.getUserType(context!!) == "influencer") {
                Log.i("Data", DataGetter.INSTANCE.getUserType(context!!))
                findNavController().navigate(R.id.navigation_main_view_inf)
            } else if (DataGetter.INSTANCE.getUserType(context!!) == "shop") {
                Log.i("Data", DataGetter.INSTANCE.getUserType(context!!))
                findNavController().navigate(R.id.navigation_main_view_shop)
            }
        }
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.landingToShop).setOnClickListener {
            findNavController().navigate(R.id.navigation_description_shop, null)
//            findNavController().navigate(R.id.navigation_profil_shop, null)
        }
        view.findViewById<Button>(R.id.landingToInf).setOnClickListener {
            findNavController().navigate(R.id.navigation_description_inf, null)
        }
    }
}
