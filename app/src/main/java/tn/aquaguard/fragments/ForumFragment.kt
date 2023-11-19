package tn.aquaguard.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import tn.aquaguard.R
import tn.aquaguard.adapters.PostAdapter
import tn.aquaguard.databinding.FragmentForumBinding
import tn.aquaguard.models.Comment
import tn.aquaguard.models.Post
import tn.aquaguard.viewmodel.PostViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

class ForumFragment : Fragment() {

    private lateinit var binding: FragmentForumBinding

    private val viewModel: PostViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentForumBinding.inflate(inflater, container, false)

        // Set up RecyclerView with an empty adapter initially
        binding.rvPost.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPost.adapter = PostAdapter(emptyList(),viewModel)

        // Observe the LiveData from ViewModel
        viewModel.posts.observe(viewLifecycleOwner) { posts ->

            binding.rvPost.adapter = PostAdapter(posts, viewModel)
        }


        return binding.root
    }
}





