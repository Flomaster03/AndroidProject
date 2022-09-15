package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.util.showKeyboard


class NewPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding.edit.text?.toString())
        }

    }

    // смотрим, ввел ли что-то пользователь и если да, то кладем этот текст в интент
    private fun onOkButtonClicked(postContent: String?) {
        val intent = Intent()           //это результирующий интент
        if (postContent.isNullOrBlank()) {
            setResult(Activity.RESULT_CANCELED, intent)
        } else {
            intent.putExtra(RESULT_KEY, postContent)
            setResult(Activity.RESULT_OK, intent) // передаем данные
        }
        finish()
    }

    companion object {
        const val RESULT_KEY = "postContent"
    }

    object ResultContract : ActivityResultContract<Unit, String?>() {

        //когда создается интент: context - это 1 часть полного квалиф имени(название пакета),
        //2 часть - название класса (это та активити, которую мы хотим запустить)
        override fun createIntent(context: Context, input: Unit) =
            Intent(context, NewPostActivity::class.java)

        // проверяем интент
        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode != Activity.RESULT_OK) return null
            intent ?: return null
            return intent.getStringExtra(RESULT_KEY)
        }

    }
}
