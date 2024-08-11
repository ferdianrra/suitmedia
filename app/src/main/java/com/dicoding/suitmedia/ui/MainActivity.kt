package com.dicoding.suitmedia.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.suitmedia.R
import com.dicoding.suitmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnCheck.setOnClickListener {
                if (edPalindrome.text.toString().isEmpty()) {
                    showToast(getString(R.string.palindrome_error_empty))
                } else {
                    val result = isPalindrome(edPalindrome.text.toString())
                    if (result) {
                        showToast(getString(R.string.is_palindrome))
                    } else {
                        showToast(getString(R.string.not_palindrome))
                    }
                }
            }
            btnNext.setOnClickListener {
                if (edName.text.toString().isEmpty()) {
                    showToast(getString(R.string.name_error_empty))
                } else {
                    moveActivity(edName.text.toString())
                }
            }
        }
    }

    private fun isPalindrome(sentences: String): Boolean {
        var left = 0
        var right = sentences.length-1
        while (left<right) {
            if(sentences[left] == ' ') {
                left++
                continue
            } else if (sentences[right] == ' ') {
                right--
                continue
            }
            if (sentences[left] != sentences[right]) {
                return false
            }
            left++
            right--
        }
        return true
    }

    private fun showToast (message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun moveActivity(name: String) {
        intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(EXTRA_NAME, name)
        startActivity(intent)
    }

    companion object {
      const val EXTRA_NAME = "extra_name"
    }
}