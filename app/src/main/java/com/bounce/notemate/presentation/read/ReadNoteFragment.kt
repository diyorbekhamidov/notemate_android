package com.bounce.notemate.presentation.read

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bounce.notemate.R
import com.bounce.notemate.databinding.FragmentReadNoteBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class ReadNoteFragment : Fragment() {
    private var param1: Int = 0
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(getString(R.string.noteID))
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentReadNoteBinding
    private val viewModel by viewModels<ReadViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReadNoteBinding.inflate(inflater, container, false)
        viewModel.fetchNote(param1)

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.getNoteLiveData().observe(viewLifecycleOwner) {

            binding.apply {
                tvDateTime.text = it.dateTime
                tvTitle.text = it.title
                tvDesc.text = it.noteText

                when {
                    it.imgPath?.isNotEmpty() == true -> {
                        imgNote.isVisible = true
                        Glide.with(requireContext()).load(it.imgPath).into(imgNote)

                        if (it.storeWebLink?.isNotEmpty() == true) {
                            tvWebLink.isVisible = true
                            tvWebLink.text = it.storeWebLink
                        }
                    }

                    it.storeWebLink?.isNotEmpty() == true -> {
                        tvWebLink.isVisible = true
                        tvWebLink.text = it.storeWebLink

                        if (it.imgPath?.isNotEmpty() == true) {
                            imgNote.isVisible = true
                            Glide.with(requireContext()).load(it.imgPath).into(imgNote)
                        }

                    }

                }

            }
        }



        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReadNoteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}