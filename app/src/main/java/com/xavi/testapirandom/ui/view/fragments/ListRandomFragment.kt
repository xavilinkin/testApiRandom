package com.xavi.testapirandom.ui.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xavi.testapirandom.R
import com.xavi.testapirandom.data.model.ResultUser
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

    private var handler: Handler = Handler(Looper.getMainLooper())
    private var isScrollEnabled = true

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
        configureScroll()
        configureError()
        configureToolbar()
        return binding.root
    }

    private var adapterList: ListAdapter? = null
    private var isLoading = true

    private fun loadRandomSearch() {
        statusProgressbar(true)
        lisRandomViewModel.getListRandomUsers().observe(
            viewLifecycleOwner
        ) { result ->
            result?.let { usersResult ->
                if (usersResult.isSuccess) {
                    getRandomList()
                } else {
                    statusProgressbar(false)
                    statusError(true)
                }
            }
        }
    }

    private fun getRandomList() {
        lisRandomViewModel.getListSearchLiveData().observe(
            viewLifecycleOwner
        ) { listResult ->
            initRecyclerView(listResult)
        }
    }

    private fun configureScroll() {
        binding.listScroll.viewTreeObserver.addOnScrollChangedListener {
            if (isScrollEnabled) {
                val scrollView = binding.listScroll
                val bottomReached =
                    scrollView.scrollY + scrollView.height >= scrollView.getChildAt(0).height

                if (bottomReached && isLoading && binding.loadingListProgressBar.isGone) {
                    statusError(false)
                    statusProgressbar(true)
                    isLoading = false
                    page++

                    isScrollEnabled = false
                    handler.postDelayed({ isScrollEnabled = true }, 500)

                    lisRandomViewModel.onCreate(page.toString())
                }
            }
        }
    }

    private fun initRecyclerView(list: List<ResultUser>?) {
        list as MutableList
        configureRecyclerView(list)
        statusProgressbar(false)
    }

    private fun configureRecyclerView(list: MutableList<ResultUser>) {
        adapterList = ListAdapter(list, listener)
        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.listRecyclerView.adapter = adapterList
        isLoading = true
    }

    private fun setListener() {
        listener = object : OnClickListUserListener {
            override fun goToUser(user: ResultUser) {
                val passArgs =
                    ListRandomFragmentDirections.actionListRandomFragmentToDetailRandomFragment(user)
                findNavController().navigate(passArgs)
            }
        }
    }

    private fun statusProgressbar(show: Boolean) {
        binding.loadingListProgressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun statusError(show: Boolean){
        binding.errorListTextView.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun configureError() {
        binding.errorListTextView.text = context?.getText(R.string.error_list_fragment)
    }

    private fun configureToolbar() {
        binding.listBackButtonTextView.text = "<"
        binding.listBackButtonTextView.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}