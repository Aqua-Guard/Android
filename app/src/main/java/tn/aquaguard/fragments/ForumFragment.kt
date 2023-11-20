package tn.aquaguard.fragments

import android.content.Context
import android.content.Intent
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
import tn.aquaguard.ui.DetailPostActivity

class ForumFragment : Fragment() {

    private lateinit var binding: FragmentForumBinding

    private val viewModel: PostViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentForumBinding.inflate(inflater, container, false)

        viewModel.deleteCommentResult.observe(viewLifecycleOwner, Observer { result ->
            if (isAdded) { // Check if the fragment is currently added to its activity
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ForumFragment())
                    .commitAllowingStateLoss()
            }
        })
        viewModel.addCommentResult.observe(viewLifecycleOwner, Observer { result ->
            if (isAdded) { // Check if the fragment is currently added to its activity
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ForumFragment())
                    .commitAllowingStateLoss()
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            if (isAdded) { // Check if the fragment is currently added to its activity
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ForumFragment())
                    .commitAllowingStateLoss()
            }


            // Defer setting isRefreshing to false to the end of the current message queue
            binding.swipeRefreshLayout.post { binding.swipeRefreshLayout.isRefreshing = false }
        }
        // Set up RecyclerView with an empty adapter initially
        binding.rvPost.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPost.adapter = PostAdapter(emptyList(),viewModel,this)
        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            binding.rvPost.adapter = PostAdapter(posts, viewModel,this)
        }

        return binding.root
    }
}





