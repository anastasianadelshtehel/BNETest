package com.example.bnetest.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.bnetest.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide
import com.example.bnetest.utils.Utils.Companion.BASE_URL


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var drugsId: Int? = null
    private val args: DetailsFragmentArgs by navArgs()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            drugsId = it.getInt("drugs")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drugs = args.drugs
        drugs.let {
            binding.title.text = drugs.name
            binding.text.text = drugs.description
            Glide.with(binding.image.context)
                .load(BASE_URL + it.image)
                .into(binding.image)

            Glide.with(binding.imageIcon.context)
                .load(BASE_URL + it.categories!!.icon)
                .into(binding.imageIcon)
        }


    }
}

