package com.xavi.testapirandom.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.xavi.testapirandom.databinding.FragmentListRandomBinding
import com.xavi.testapirandom.ui.viewmodel.ListRandomViewModel

class ListRandomFragment : Fragment() {
    private var _binding: FragmentListRandomBinding? = null
    private val binding get() = _binding!!

    private val lisRandomViewModel: ListRandomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lisRandomViewModel.onCreate("1")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListRandomBinding.inflate(inflater, container, false)
        testClick(binding)
        testGetRandomUsers()
        return binding.root
    }

    private fun testClick(binding: FragmentListRandomBinding) {
        binding.test1.setOnClickListener {
            val passArgs = ListRandomFragmentDirections.actionListRandomFragmentToDetailRandomFragment("Hola")
            findNavController().navigate(passArgs)
        }
    }

    private fun testGetRandomUsers() {
        lisRandomViewModel.getListRandomUsers().observe(
            viewLifecycleOwner
        ) { result ->
            result?.let { usersResult ->
                val names = buildString {
                    usersResult.results?.forEach { user ->
                        append(user.name.first)
                    }
                }
                binding.test1.text = names
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}