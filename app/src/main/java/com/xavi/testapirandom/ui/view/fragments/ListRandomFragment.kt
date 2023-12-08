package com.xavi.testapirandom.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.xavi.testapirandom.databinding.FragmentListRandomBinding

class ListRandomFragment : Fragment() {
    private var _binding: FragmentListRandomBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListRandomBinding.inflate(inflater, container, false)
        testClick(binding)
        return binding.root
    }

    private fun testClick(binding: FragmentListRandomBinding) {
        binding.test1.setOnClickListener {
            val passArgs = ListRandomFragmentDirections.actionListRandomFragmentToDetailRandomFragment("Hola")
            findNavController().navigate(passArgs)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}