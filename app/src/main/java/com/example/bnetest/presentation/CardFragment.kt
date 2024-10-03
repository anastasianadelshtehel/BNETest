package com.example.bnetest.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.lifecycle.lifecycleScope
import com.example.bnetest.databinding.FragmentCardBinding
import com.example.bnetest.presentation.adapter.CardDrugsAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bnetest.R
import com.example.bnetest.data.Drugs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardFragment : Fragment() {
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    private val cardViewModel: CardViewModel by viewModels()
    private val cardDrugsAdapter = CardDrugsAdapter { drugs -> onItemIdDetails(drugs) }
    private var searchQuery: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)


        binding.recyclerView.adapter = cardDrugsAdapter

        lifecycleScope.launch {
            cardViewModel.getDrugsList(searchQuery = null)
                .collectLatest { pagingData -> cardDrugsAdapter.submitData(pagingData) }

        }





        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_search, menu)

                    val searchItem: MenuItem? = menu.findItem(R.id.action_search)
                    val searchView = searchItem?.actionView as SearchView
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            searchQuery = query
                            cardViewModel.getDrugsList(query)
                            getSearchResult()
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            searchQuery = newText
                            cardViewModel.getDrugsList(newText)
                            getSearchResult()
                            return false
                        }
                    })
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return false
                }

            }, viewLifecycleOwner, Lifecycle.State.RESUMED
        )
    }

    private fun getSearchResult() {
        if (view != null) {
            lifecycleScope.launch {
                cardViewModel.getDrugsList(searchQuery)
                    .collectLatest { pagingData -> cardDrugsAdapter.submitData(pagingData) }
            }

        }
    }


    private fun onItemIdDetails(item: Drugs) {
        val action = CardFragmentDirections.actionCardFragmentToDetailsFragment(item)
        findNavController().navigate(action)
    }


}