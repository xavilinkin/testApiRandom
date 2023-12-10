package com.xavi.testapirandom.ui.view.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import com.xavi.testapirandom.R
import com.xavi.testapirandom.databinding.FragmentDetailRandomBinding
import com.xavi.testapirandom.utils.CircleTransform
import com.xavi.testapirandom.utils.TransformDate

class DetailRandomFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentDetailRandomBinding? = null
    private val binding get() = _binding!!
    private val args: DetailRandomFragmentArgs by navArgs()
    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailRandomBinding.inflate(inflater, container, false)
        setUserImage()
        configureToolbar()
        configureText()
        controlToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapView = binding.addressMapDetailTextName
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        mapView.onResume()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val location = LatLng(
            args.RandomModel.location.coordinates.latitude.toDouble(),
            args.RandomModel.location.coordinates.longitude.toDouble()
        )
        googleMap?.addMarker(
            MarkerOptions().position(location)
                .title(context?.getString(R.string.address_map_detail_fragment))
        )
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    private fun setUserImage() {
        Picasso.get().load(args.RandomModel.picture.medium).transform(CircleTransform())
            .into(binding.userDetailImageView)
    }

    private fun configureToolbar() {
        binding.backButtonTextView.text = "<"
        binding.backButtonTextView.setOnClickListener {
            parentFragment?.findNavController()?.popBackStack()
        }
        val name = args.RandomModel.name.first
        val last = args.RandomModel.name.last
        binding.nameDetailToolbar.text = "$name $last"
    }

    private fun configureText() {
        // Name
        binding.nameDetailTextView.text = context?.getText(R.string.name_detail_fragment)
        val name = args.RandomModel.name.first
        val last = args.RandomModel.name.last
        binding.editNameDetailTextName.text = addEditable("$name $last")

        // Mail
        binding.mailDetailTextView.text = context?.getText(R.string.email_detail_fragment)
        binding.editMailDetailTextName.text = addEditable(args.RandomModel.email)

        // Gender
        binding.genderDetailTextView.text = context?.getText(R.string.gender_detail_fragment)
        binding.editGenderDetailTextName.text = configureGender()

        // Register Date
        binding.dateDetailTextView.text = context?.getText(R.string.date_detail_fragment)
        val inputDateString = args.RandomModel.registered.date
        val outputDateFormat = TransformDate.FORMAT_DDMMYYYY
        val transformedDate = TransformDate.transformDate(inputDateString, outputDateFormat)
        binding.editDateDetailTextName.text = addEditable(transformedDate)

        // Phone
        binding.phoneDetailTextView.text = context?.getText(R.string.phone_detail_fragment)
        binding.editPhoneDetailTextName.text = addEditable(args.RandomModel.phone)

        // Address
        binding.addressDetailTextView.text = context?.getText(R.string.address_detail_fragment)
    }

    private fun controlToolbar() {
        val toolbar = binding.detailToolbar
        val nestedScrollView = binding.detailScroll

        nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY != 0) {
                toolbar.setBackgroundColor(resources.getColor(R.color.white, null))
            } else {
                toolbar.setBackgroundColor(resources.getColor(R.color.transparent, null))
            }
        }
    }

    private fun configureGender(): Editable {
        val gender: String = when (args.RandomModel.gender) {
            context?.getText(R.string.gender_female_detail_fragment) -> {
                context?.getText(R.string.genero_femenino_detail_fragment) as String
            }

            context?.getText(R.string.gender_male_detail_fragment) -> {
                context?.getText(R.string.genero_masculino_detail_fragment) as String
            }

            else -> {
                context?.getText(R.string.gender_other_detail_fragment) as String
            }
        }
        return addEditable(gender)
    }

    private fun addEditable(text: String): Editable {
        return Editable.Factory.getInstance().newEditable(text)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}