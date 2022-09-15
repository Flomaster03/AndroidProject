package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)

        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        val newPostActivityLauncher = registerForActivityResult(
            NewPostActivity.ResultContract
        ) { postContent: String? ->
            postContent?.let(viewModel::onCreateNewPost)
        }

        binding.fab.setOnClickListener {
            newPostActivityLauncher.launch(Unit)
        }


    }

}



//      binding.saveButton.setOnClickListener {
//          with(binding.inputContent) {
//              val content = text.toString()
//              viewModel.onSaveButtonClick(content)
//              hideKeyboard()
//          }
//      }


 //       viewModel.currentPost.observe(this) { currentPost ->
 //           with(binding.inputContent) {
 //               val content = currentPost?.content
 //               setText(content)
 //               if (content != null) {
 //                   requestFocus()
 //                   showKeyboard()
 //               } else {
 //                   clearFocus()
 //                   hideKeyboard()
 //               }
 //           }
 //       }
