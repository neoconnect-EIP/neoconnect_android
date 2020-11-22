package c.eip.neoconnect.ui.view.list


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.eip.neoconnect.R
import c.eip.neoconnect.ui.adapter.OfferAdapter
import c.eip.neoconnect.ui.viewModel.ListViewModel
import c.eip.neoconnect.utils.DataGetter
import c.eip.neoconnect.utils.Status

class ListSortedOffer : Fragment() {
    private lateinit var viewModel: ListViewModel
    private var viewState: Int = 0

    /**
     * Creation de la vue. Déclaration du layout à afficher
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_list_sorted_offer, container, false)
        inflate.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.GONE
        inflate.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility = View.GONE
        inflate.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.VISIBLE
        inflate.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.GONE
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
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
            if (viewState == 0 || viewState == 2) {
                findNavController().popBackStack()
            } else if (viewState == 1) {
                view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.GONE
                view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                    View.GONE
                view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.VISIBLE
                view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.GONE
            }
        }
        view.findViewById<TextView>(R.id.sortByAscendingOrder).setOnClickListener {
            view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.VISIBLE
            sortOffer("order", "ascending", view)
        }

        view.findViewById<TextView>(R.id.sortByDescendingOrder).setOnClickListener {
            view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.VISIBLE
            sortOffer("order", "descending", view)
        }

        view.findViewById<TextView>(R.id.sortBySubject).setOnClickListener {
            viewState = 1
            view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                View.VISIBLE
            view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.GONE
        }

        view.findViewById<TextView>(R.id.sortByFemme).setOnClickListener {
            viewState = 2
            view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.VISIBLE
            sortOffer("productSex", "Femme", view)
        }

        view.findViewById<TextView>(R.id.sortByHomme).setOnClickListener {
            viewState = 2
            view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.VISIBLE
            sortOffer("productSex", "Homme", view)
        }

        view.findViewById<TextView>(R.id.sortByUnisexe).setOnClickListener {
            viewState = 2
            view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.VISIBLE
            sortOffer("productSubject", "Mode", view)
        }

        view.findViewById<TextView>(R.id.sortByMode).setOnClickListener {
            viewState = 2
            view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.VISIBLE
            view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.GONE
        }

        view.findViewById<TextView>(R.id.sortByHighTech).setOnClickListener {
            viewState = 2
            view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.VISIBLE
            sortOffer("productSubject", "High Tech", view)
        }

        view.findViewById<TextView>(R.id.sortBySport).setOnClickListener {
            viewState = 2
            view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.VISIBLE
            sortOffer("productSubject", "Sport", view)
        }

        view.findViewById<TextView>(R.id.sortByNourriture).setOnClickListener {
            viewState = 2
            view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.VISIBLE
            sortOffer("productSubject", "Nourriture", view)
        }

        view.findViewById<TextView>(R.id.sortByJeux).setOnClickListener {
            viewState = 2
            view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.VISIBLE
            sortOffer("productSubject", "Jeux Vidéo", view)
        }

        view.findViewById<TextView>(R.id.sortByCosmétique).setOnClickListener {
            viewState = 2
            view.findViewById<LinearLayout>(R.id.choiceSortingSexLayout).visibility = View.VISIBLE
            view.findViewById<LinearLayout>(R.id.choiceSortingSujetLayout).visibility =
                View.GONE
            view.findViewById<LinearLayout>(R.id.choiceSortingLayout).visibility = View.GONE
            view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer).visibility = View.GONE
        }
    }

    /**
     * Récupération de la liste des Offres filtrés
     */
    private fun sortOffer(sortingType: String, sortingValue: String, view: View) {
        val token = DataGetter.INSTANCE.getToken(requireContext())
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        when (sortingType) {
            "productSex" -> {
                viewModel.getOffers(
                    token = token!!, sex = sortingValue, color = null,
                    brand = null, subject = null
                ).observe(
                    viewLifecycleOwner,
                    Observer {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    val recyclerSortedListView =
                                        view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer)
                                    recyclerSortedListView.layoutManager =
                                        LinearLayoutManager(
                                            context,
                                            LinearLayoutManager.VERTICAL,
                                            false
                                        )
                                    val adapter = OfferAdapter(it.data!!, "list", "inf")
                                    adapter.notifyDataSetChanged()
                                    recyclerSortedListView.adapter = adapter
                                }
                                Status.ERROR -> {
                                    Toast.makeText(
                                        context,
                                        "Impossible de trouver une offre avec ce filtre",
                                        Toast.LENGTH_LONG
                                    )
                                    Log.e("Filter offer", it.message!!)
                                }
                            }
                        }
                    })
            }
            "productSubject" -> {
                viewModel.getOffers(
                    token = token!!, sex = null, color = null,
                    brand = null, subject = sortingValue
                ).observe(
                    viewLifecycleOwner,
                    Observer {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    val recyclerSortedListView =
                                        view.findViewById<RecyclerView>(R.id.recyclerSortedListOffer)
                                    recyclerSortedListView.layoutManager =
                                        LinearLayoutManager(
                                            context,
                                            LinearLayoutManager.VERTICAL,
                                            false
                                        )
                                    val adapter = OfferAdapter(it.data!!, "list", "inf")
                                    adapter.notifyDataSetChanged()
                                    recyclerSortedListView.adapter = adapter
                                }
                                Status.ERROR -> {
                                    Toast.makeText(
                                        context,
                                        "Impossible de trouver une offre avec ce filtre",
                                        Toast.LENGTH_LONG
                                    )
                                    Log.e("Filter offer", it.message!!)
                                }
                            }
                        }
                    })
            }
        }
    }
}
