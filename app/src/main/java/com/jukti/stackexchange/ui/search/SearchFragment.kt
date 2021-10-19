package com.jukti.stackexchange.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jukti.stackexchange.R
import com.jukti.stackexchange.data.model.StackExchangeUser
import com.jukti.stackexchange.data.model.Status
import com.jukti.stackexchange.databinding.FragmentSearchBinding
import com.jukti.unrd.utilities.USER_ITEM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    @Inject
    lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.userRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }

        searchAdapter.clickListener = { user ->
            val bundle = bundleOf(USER_ITEM to user)
            view?.findNavController()?.navigate(R.id.action_UserSearchFragment_to_UserDetailsFragment,bundle)
        }

        binding.searchButton.setOnClickListener {
            if(!binding.searchInputEdittxt.text.isNullOrEmpty()) {
                searchViewModel.setSearchQuery(binding.searchInputEdittxt.text.toString())
                hideSoftKeyBoard()
            }

        }


        return binding.root

    }

    fun hideSoftKeyBoard() {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        searchViewModel.searchUserList.observe(
            viewLifecycleOwner,
            { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        response.data?.users?.let { users ->
                            searchAdapter.collection = users as MutableList<StackExchangeUser>

                        }
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.NO_INTERNET -> {
                        binding.progressBar.visibility = View.GONE
                        activity?.let {
                            Snackbar.make(it.findViewById(android.R.id.content), getString(R.string.no_internet_connection_message), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()
                        }
                    }
                }
            })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val TAG = "SearchFragment"
    }

}