package com.dicoding.suitmedia.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.suitmedia.R
import com.dicoding.suitmedia.databinding.ActivitySecondBinding
import com.dicoding.suitmedia.ui.third_screen.ThirdActivity

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private var selectedName: String? = null
    private var nameLauncher  = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedName = result.data?.getStringExtra(EXTRA_SELECTED_NAME)
            if (selectedName != null) {
                binding.tvSelectedName.text = selectedName
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name = intent.getStringExtra(EXTRA_NAME)
        selectedName = savedInstanceState?.getString(EXTRA_SELECTED_NAME)
        binding.apply {
            tvName.text = name
            if (selectedName!= null) {
                tvSelectedName.text = selectedName
            }
            btnChooseUser.setOnClickListener {
                val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
                nameLauncher.launch(intent)
            }
            appToolbar.setNavigationOnClickListener {
                finish()
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_SELECTED_NAME, selectedName)
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_SELECTED_NAME = "extra_selected_name"
    }
}