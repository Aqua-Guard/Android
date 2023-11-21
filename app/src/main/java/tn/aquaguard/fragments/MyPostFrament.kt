package tn.aquaguard.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import tn.aquaguard.R
import tn.aquaguard.adapters.MyPostAdapter


import tn.aquaguard.databinding.FragmentMyPostFramentBinding
import tn.aquaguard.ui.DetailPostActivity
import tn.aquaguard.viewmodel.PostViewModel


class MyPostFrament : Fragment() {

    private lateinit var binding: FragmentMyPostFramentBinding
    private val viewModel: PostViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyPostFramentBinding.inflate(layoutInflater)

        viewModel.deletePostResult.observe(viewLifecycleOwner, Observer { result ->
            if (isAdded) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MyPostFrament())
                    .commitAllowingStateLoss()
            }
        })

        binding.myPostsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.myPostsRV.adapter = MyPostAdapter(emptyList(),viewModel, this)
        viewModel.myposts.observe(viewLifecycleOwner) { myposts ->
            binding.myPostsRV.adapter = MyPostAdapter(myposts,viewModel, this)
        }

        viewModel.deleteCommentResult.observe(viewLifecycleOwner, Observer { result ->
            if (isAdded) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MyPostFrament())
                    .commitAllowingStateLoss()
            }
        })


        binding.swipeRefreshLayout.setOnRefreshListener {
            if (isAdded) { // Check if the fragment is currently added to its activity
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MyPostFrament())
                    .commitAllowingStateLoss()
            }
            binding.swipeRefreshLayout.post { binding.swipeRefreshLayout.isRefreshing = false }
        }
        return binding.root
    }



}