package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentSingleBinding
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewModel.PostViewModel

class SingleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSingleBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val viewHolder = PostsAdapter.ViewHolder(binding.singlePostLayout, viewModel)

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val singlePost = posts.find { it.id == arguments?.longArg } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(singlePost)
        }

        viewModel.editPostEvent.observe(viewLifecycleOwner) { post ->
            findNavController().navigate(
                R.id.action_singleFragment_to_newPostFragment,
                Bundle().apply { textArg = post.content })
        }

        viewModel.videoPlayEvent.observe(viewLifecycleOwner) { videoLink ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoLink))
            startActivity(intent)
        }

        viewModel.shareEvent.observe(viewLifecycleOwner) { post ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(
                intent,
                getString(R.string.description_post_share)
            )
            startActivity(shareIntent)
        }

        return binding.root
    }

    companion object {
        var Bundle.longArg: Long? by LongArg
    }
}