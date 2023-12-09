package com.xavi.testapirandom.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xavi.testapirandom.data.model.Result
import com.xavi.testapirandom.databinding.FragmentListRandomBinding
import com.xavi.testapirandom.ui.view.adapter.ListAdapter
import com.xavi.testapirandom.ui.view.fragments.listener.OnClickListUserListener
import com.xavi.testapirandom.ui.viewmodel.ListRandomViewModel

class ListRandomFragment : Fragment() {
    private var _binding: FragmentListRandomBinding? = null
    private val binding get() = _binding!!
    private val lisRandomViewModel: ListRandomViewModel by viewModels()
    private val page = "1"
    private var adapterList: ListAdapter? = null
    private lateinit var listener: OnClickListUserListener

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
        setListener()
        return binding.root
    }
    private fun loadRandomSearch() {
        lisRandomViewModel.getListRandomUsers().observe(
            viewLifecycleOwner
        ) { result ->
            result?.let { usersResult ->
                if (usersResult.isSuccess) {
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
        adapterList = list?.let { ListAdapter(it, listener) }
        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.listRecyclerView.adapter = adapterList
    }

    private fun setListener() {
        listener = object : OnClickListUserListener {
            override fun goToUser(user: Result) {
                val passArgs =
                    ListRandomFragmentDirections.actionListRandomFragmentToDetailRandomFragment(user)
                findNavController().navigate(passArgs)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}