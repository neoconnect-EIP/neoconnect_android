package c.eip.neoconnect.ui.view.chat


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import c.eip.neoconnect.R
import c.eip.neoconnect.utils.DataGetter

class Chat : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_chat, container, false)
        if (DataGetter.INSTANCE.getUserType(context!!) == "shop") {
            inflate.findViewById<ConstraintLayout>(R.id.chatLayout)
                .setBackgroundResource(R.drawable.background_shop)
        } else if (DataGetter.INSTANCE.getUserType(context!!) == "influencer") {
            inflate.findViewById<ConstraintLayout>(R.id.chatLayout)
                .setBackgroundResource(R.drawable.background_influencer)
        }
        return inflate
    }
}
