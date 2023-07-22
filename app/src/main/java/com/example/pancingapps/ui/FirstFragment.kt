package com.example.pancingapps.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pancingapps.R
import com.example.pancingapps.application.PancingApps
import com.example.pancingapps.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!;
    private lateinit var applicationContext: Context
    private val PancingViewModel: PancingViewModel by viewModels {
        pancingViewModelFactory((applicationContext as PancingApps).repository)
    }
    //private val args : SecondFragmentargs by navArgs()
    //private var Pancing: Pancing? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      _binding = FragmentFirstBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PancingListAdapter {pancing ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(pancing)
            findNavController().navigate(action)
        }
        binding.dataRecyclerView.adapter = adapter
        binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
        PancingViewModel.allPancings.observe(viewLifecycleOwner){pancing->
             pancing.let {
                if (pancing.isEmpty()) {
                    binding.emptyTextView.visibility = View.VISIBLE
                    binding.illustrationImageView.visibility = View.VISIBLE
                }else{
                    binding.emptyTextView.visibility = View.GONE
                    binding.illustrationImageView.visibility = View.GONE
                }
                adapter.submitList(pancing)
            }
        }


        binding.addFAB.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
         binding.aboutFAB.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_AboutFragment)
        }
        binding.katalogFAB.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_KatalogFragment)
        }
        binding.contactFAB.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ContactFragment)
        }

    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}