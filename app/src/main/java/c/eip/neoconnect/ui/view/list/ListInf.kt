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
import c.eip.neoconnect.ui.adapter.ListInfAdapter
import c.eip.neoconnect.ui.viewModel.ListViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class ListInf : Fragment() {
    private lateinit var viewModel: ListViewModel

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_list_inf, container, false)
        getListInf(view = inflate)
        return inflate
    }

    /**
     * Récupération de la liste des Influenceurs
     */
    private fun getListInf(view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        val recyclerListView = view.findViewById<RecyclerView>(R.id.recyclerListInf)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getListInf(token = token!!).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data!!.isEmpty()) {
                            view.findViewById<TextView>(R.id.pb_list_inf).visibility =
                                View.VISIBLE
                        } else {
                            view.findViewById<TextView>(R.id.pb_list_inf).visibility =
                                View.GONE
                            Log.i(
                                "List Inf",
                                "Récupération des influenceurs réussie"
                            )
                            recyclerListView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            val adapter = ListInfAdapter(it.data)
                            adapter.notifyDataSetChanged()
                            recyclerListView.adapter = adapter
                        }
                    }
                    Status.ERROR -> {
                        view.findViewById<TextView>(R.id.pb_list_inf).visibility =
                            View.VISIBLE
                        recyclerListView.visibility = View.GONE
                        Log.e("List Inf", it.message!!)
                    }
                }
            }
        })
    }
}
