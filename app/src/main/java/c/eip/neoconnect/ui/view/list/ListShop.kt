package c.eip.neoconnect.ui.view.list


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.ListShopAdapter
import c.eip.neoconnect.ui.viewModel.ListViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class ListShop : Fragment() {
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_list_shop, container, false)
        val token = DataGetter.INSTANCE.getToken(context!!)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getListShop(token!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data!!.isEmpty()) {
                            inflate.findViewById<TextView>(R.id.pb_list_shop).visibility =
                                View.VISIBLE
                        } else {
                            inflate.findViewById<TextView>(R.id.pb_list_shop).visibility =
                                View.GONE
                            Log.i(
                                "List Shop",
                                "Récupération des boutiques réussie"
                            )
                            val recyclerListView =
                                inflate.findViewById<RecyclerView>(R.id.recyclerListShop)
                            recyclerListView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            val adapter = ListShopAdapter(it.data)
                            adapter.notifyDataSetChanged()
                            recyclerListView.adapter = adapter
                        }
                    }
                    Status.ERROR -> {
                        inflate.findViewById<TextView>(R.id.pb_list_shop).visibility =
                            View.VISIBLE
                        inflate.findViewById<RecyclerView>(R.id.recyclerListShop).visibility =
                            View.GONE
                        Log.e("List Shop", it.message)
                    }
                }
            }
        })
        return inflate
    }


}
