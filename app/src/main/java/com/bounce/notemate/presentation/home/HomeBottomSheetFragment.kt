package com.bounce.notemate.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bounce.notemate.R
import com.bounce.notemate.data.local.entities.NoteEntity
import com.bounce.notemate.databinding.FragmentHomeBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class HomeBottomSheetFragment : BottomSheetDialogFragment() {
    private var param1: Int? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBottomSheetBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBottomSheetBinding.inflate(inflater, container, false)


        //READ
        binding.readNoteLinear.setOnClickListener {
            dismiss()
            val bundle = Bundle()
            bundle.putInt(getString(R.string.noteID), param1 ?: 0)
            findNavController().navigate(R.id.readNoteFragment, bundle)

        }

        //UPDATE
        binding.updateNoteLinear.setOnClickListener {
            dismiss()
            val bundle = Bundle()
            bundle.putInt(getString(R.string.noteID), param1 ?: 0)
            findNavController().navigate(R.id.createNoteFragment, bundle)

        }


        //DELETE
        binding.deleteNoteLinear.setOnClickListener {
            viewModel.deleteNoteById(param1 ?: 0)
            dismiss()
            Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
        }


        //CLOSE
        binding.closeLinear.setOnClickListener {
            dismiss()
        }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
            HomeBottomSheetFragment().apply {
                param1 = id
            }
    }
}