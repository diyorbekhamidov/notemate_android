package com.bounce.notemate.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bounce.notemate.presentation.create_notes.CreateNoteFragment
import com.bounce.notemate.R
import com.bounce.notemate.data.local.entities.NoteEntity
import com.bounce.notemate.databinding.FragmentHomeBinding
import com.bounce.notemate.util.MyResult
import com.bounce.notemate.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private lateinit var notesAdapter: NotesAdapter

    private val viewModel by viewModels<HomeViewModel>()

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRv()
        collectNotes()

        // FAB CREATE NOTE FRAGMENT
        binding.fabCreateNoteBtn.setOnClickListener {
            findNavController().navigate(R.id.createNoteFragment)
        }

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    private val onClicked = object : NotesAdapter.OnItemClickListener {
        override fun onClicked(notesId: Int) {
            val bundle = Bundle()
            bundle.putInt(getString(R.string.noteID), notesId)
            findNavController().navigate(R.id.readNoteFragment, bundle)
        }
    }

    private fun collectNotes() = binding.apply {
        viewModel.getNotesLiveData().observe(viewLifecycleOwner) {
            when (it) {
                MyResult.Loading -> {
                    progressBar.isVisible = true
                    recyclerView.isVisible = false
                }

                is MyResult.Success -> {
                    notesAdapter.submitList(it.value)
                    progressBar.isVisible = false
                    recyclerView.isVisible = true
                }

                is MyResult.Error -> {

                }
            }
        }
    }

    private fun setUpRv() = binding.apply {
        notesAdapter = NotesAdapter(object : NotesAdapter.MoreClickListener {
            override fun OnClickMore(note: NoteEntity) {
                val homeBottomSheetFragment = HomeBottomSheetFragment.newInstance(note.id)
                homeBottomSheetFragment.show(requireActivity().supportFragmentManager, "")
            }

        }).apply { setOnClickListener(onClicked) }
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = notesAdapter
    }

}
