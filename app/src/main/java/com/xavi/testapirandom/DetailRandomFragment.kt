package com.xavi.testapirandom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.xavi.testapirandom.databinding.FragmentDetailRandomBinding

class DetailRandomFragment : Fragment() {
    private var _binding: FragmentDetailRandomBinding? = null
    private val binding get() = _binding!!

    private val args: DetailRandomFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailRandomBinding.inflate(inflater, container, false)
        binding.test2.text = args.infoUser
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}