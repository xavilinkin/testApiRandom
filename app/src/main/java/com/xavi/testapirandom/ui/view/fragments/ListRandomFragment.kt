package com.xavi.testapirandom.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xavi.testapirandom.data.model.Result
import com.xavi.testapirandom.databinding.FragmentListRandomBinding
import com.xavi.testapirandom.ui.view.adapter.ListAdapter
import com.xavi.testapirandom.ui.viewmodel.ListRandomViewModel

class ListRandomFragment : Fragment() {
    private var _binding: FragmentListRandomBinding? = null
    private val binding get() = _binding!!
    private val lisRandomViewModel: ListRandomViewModel by viewModels()
    private val page = "1"
    private var adapterList: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lisRandomViewModel.onCreate(page)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListRandomBinding.inflate(inflater, container, false)
        loadRandomSearch()
        return binding.root
    }
    private fun loadRandomSearch() {
        lisRandomViewModel.getListRandomUsers().observe(
            viewLifecycleOwner
        ) { result ->
            result?.let { usersResult ->
                if (usersResult.isSuccess) {
                    // Esta lista pasará al recycler
                    val listResult = usersResult.getOrNull()?.results
                    initRecyclerView(listResult)
                    adapterList?.notifyDataSetChanged()
                } else {
                    // "Ha ocurrido un error"
                }
            }
        }
    }

    private fun initRecyclerView(list: List<Result>?) {
        adapterList = list?.let { ListAdapter(it) }
        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.listRecyclerView.adapter = adapterList
    }
/**
    private fun testClick(result: Result) {
        binding.test1.setOnClickListener {
            val passArgs =
                ListRandomFragmentDirections.actionListRandomFragmentToDetailRandomFragment(result)
            findNavController().navigate(passArgs)
        }
    }

    private fun testGetRandomUsers() {
        lisRandomViewModel.getListRandomUsers().observe(
            viewLifecycleOwner
        ) { result ->
            var result1: Result? = null
            result?.let { usersResult ->
                if (usersResult.isSuccess) {
                    val names = buildString {
                        usersResult.getOrNull()?.results?.forEach { user ->
                            append(user.name.first)
                            result1 = user
                        }
                    }
                    binding.test1.text = names
                    testClick(result1!!)
                } else {
                    binding.test1.text = "Ha ocurrido un error"
                }
            }
        }
    } */

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}