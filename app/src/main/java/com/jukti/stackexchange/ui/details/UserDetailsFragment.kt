package com.jukti.stackexchange.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jukti.stackexchange.R
import com.jukti.stackexchange.data.model.StackExchangeUser
import com.jukti.stackexchange.data.model.Status
import com.jukti.stackexchange.databinding.FragmentUserDetailsBinding
import com.jukti.stackexchange.ui.search.SearchFragment
import com.jukti.unrd.utilities.USER_ITEM
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    lateinit var userDetailsViewModel: UserDetailsViewModel

    private var _binding: FragmentUserDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userDetailsViewModel = ViewModelProvider(this).get(UserDetailsViewModel::class.java)
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        arguments?.getParcelable<StackExchangeUser>(USER_ITEM)?.let {
            userDetailsViewModel.user.value = it
        }
    }

    private fun initObservers() {
        userDetailsViewModel.user.observe(viewLifecycleOwner,{
            binding.user = it
        })

        userDetailsViewModel.taglist.observe(viewLifecycleOwner,{ response ->
            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let { tags ->
                        binding.tags = tags
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
                Status.NO_INTERNET -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val TAG = "UserDetailsFragment"
    }
}