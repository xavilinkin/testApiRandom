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
    private var page = 1
    private lateinit var listener: OnClickListUserListener
    private var saveList: MutableList<Result>? = mutableListOf()
    private var getList: MutableList<Result> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lisRandomViewModel.onCreate(page.toString())
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

    private var adapterList: ListAdapter? = null
    private var isLoading = true

    private fun loadRandomSearch() {
        lisRandomViewModel.getListRandomUsers().observe(
            viewLifecycleOwner
        ) { result ->
            result?.let { usersResult ->
                if (usersResult.isSuccess) {
                    val listResult = usersResult.getOrNull()?.results
                    initRecyclerView(listResult)

                    binding.listScroll.viewTreeObserver.addOnScrollChangedListener {
                        val scrollView = binding.listScroll
                        val bottomReached =
                            scrollView.scrollY + scrollView.height >= scrollView.getChildAt(0).height

                        if (bottomReached && isLoading) {
                            binding.loadingListProgressBar.visibility = View.VISIBLE
                            isLoading = false
                            page++
                            lisRandomViewModel.onCreate(page.toString())
                        }
                    }
                } else {
                    // "Ha ocurrido un error"
                }
            }
        }
    }

    private fun initRecyclerView(list: List<Result>?) {
        if (adapterList == null) {
            list as MutableList
            configureRecyclerView(list)
        } else {
            if (saveList?.isEmpty() == true) {
                configureRecyclerView(getList)
            } else {
                adapterList?.addAll(list)
            }
        }
        saveList?.addAll(list as MutableList<Result>)
        isLoading = true
        binding.loadingListProgressBar.visibility = View.GONE
    }

    private fun configureRecyclerView(list: MutableList<Result>) {
        adapterList = ListAdapter(list, listener)
        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.listRecyclerView.adapter = adapterList
    }

    private fun setListener() {
        listener = object : OnClickListUserListener {
            override fun goToUser(user: Result) {
                val passArgs =
                    ListRandomFragmentDirections.actionListRandomFragmentToDetailRandomFragment(user)
                findNavController().navigate(passArgs)
                // TODO David aplicar en otro lugar
                // saveList = saveList?.distinctBy { it.picture.thumbnail } as MutableList<Result>?
                saveList?.let { getList.addAll(it) }
                saveList = mutableListOf()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}