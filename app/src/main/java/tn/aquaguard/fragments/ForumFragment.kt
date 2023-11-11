package tn.aquaguard.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import tn.aquaguard.R
import tn.aquaguard.adapters.PostAdapter
import tn.aquaguard.databinding.FragmentForumBinding
import tn.aquaguard.models.Comment
import tn.aquaguard.models.Post

class ForumFragment : Fragment() {

    private lateinit var binding: FragmentForumBinding
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentForumBinding.inflate(layoutInflater)
        binding.rvPost.adapter = PostAdapter(getListPosts(requireContext()))
        binding.rvPost.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        return binding.root
    }
    private fun getListPosts(context: Context) : MutableList<Post> {
        return mutableListOf(
                Post(
                    userName = "Malek Labidi",
                    userRole = "Aquarist",
                    description = "Love the new coral additions to my tank!",
                    userImage = R.drawable.user, // Replace with your drawable resource
                    postImage = R.drawable.post1, // Replace with your drawable resource
                    nbLike = 123,
                    nbComments = 10,
                    nbShare = 5,
                    comments = listOf(
                        Comment(
                            commentAvatar = R.drawable.yousseff, // Replace with your drawable resource
                            commentUsername = "Youssef Farhat",
                            comment = "This looks amazing!"
                        ),
                        Comment(
                            commentAvatar = R.drawable.yousseff, // Replace with your drawable resource
                            commentUsername = "Youssef Farhat",
                            comment = "This looks amazing!"
                        ),
                        Comment(
                            commentAvatar = R.drawable.yousseff, // Replace with your drawable resource
                            commentUsername = "Youssef Farhat",
                            comment = "This looks amazing!"
                        )
                        // Add more comments here
                    )
                ),
            Post(
                userName = "Malek Labidi",
                userRole = "Aquarist",
                description = "Love the new coral additions to my tank!",
                userImage = R.drawable.user, // Replace with your drawable resource
                postImage = R.drawable.post1, // Replace with your drawable resource
                nbLike = 123,
                nbComments = 10,
                nbShare = 5,
                comments = listOf(
                    Comment(
                        commentAvatar = R.drawable.yousseff, // Replace with your drawable resource
                        commentUsername = "Youssef Farhat",
                        comment = "This looks amazing!"
                    ),

                    Comment(
                        commentAvatar = R.drawable.yousseff, // Replace with your drawable resource
                        commentUsername = "Youssef Farhat",
                        comment = "This looks amazing!"
                    )
                    // Add more comments here
                )
            ),
            Post(
                userName = "Malek Labidi",
                userRole = "Aquarist",
                description = "Love the new coral additions to my tank!",
                userImage = R.drawable.user, // Replace with your drawable resource
                postImage = R.drawable.post1, // Replace with your drawable resource
                nbLike = 123,
                nbComments = 10,
                nbShare = 5,
                comments = listOf(


                )
            )

        )





    }
}


