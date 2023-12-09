package com.xavi.testapirandom.ui.view.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.xavi.testapirandom.databinding.FragmentDetailRandomBinding
import com.xavi.testapirandom.utils.CircleTransform
import com.xavi.testapirandom.utils.TransformDate

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
        setUserImage()
        configureToolbar()
        configureText()
        return binding.root
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
        binding.nameDetailTextView.text = "Nombre y apellidos"
        val name = args.RandomModel.name.first
        val last = args.RandomModel.name.last
        binding.editNameDetailTextName.text = addEditable("$name $last")

        // Mail
        binding.mailDetailTextView.text = "Email"
        binding.editMailDetailTextName.text = addEditable(args.RandomModel.email)

        // Gender
        binding.genderDetailTextView.text = "Género"
        binding.editGenderDetailTextName.text = configureGender()

        // Register Date
        binding.dateDetailTextView.text = "Fecha de Registro"
        val inputDateString = args.RandomModel.registered.date
        val outputDateFormat = "dd/MM/yyyy"
        val transformedDate = TransformDate.transformDate(inputDateString, outputDateFormat)
        binding.editDateDetailTextName.text = addEditable(transformedDate)

        // Phone
        binding.phoneDetailTextView.text = "Teléfono"
        binding.editPhoneDetailTextName.text = addEditable(args.RandomModel.phone)
    }

    private fun configureGender(): Editable {
        val gender: String = when (args.RandomModel.gender) {
            "female" -> {
                "Femenino"
            }

            "male" -> {
                "Masculino"
            }
            else -> {
                "Otro"
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