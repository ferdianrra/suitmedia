package com.dicoding.suitmedia.ui.third_screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.suitmedia.R
import com.dicoding.suitmedia.ViewModelFactory
import com.dicoding.suitmedia.databinding.ActivityThirdBinding
import com.dicoding.suitmedia.network.response.UserItem

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private var isLoading = true
    private var isError = false
    private val viewModel by viewModels<ThirdViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        isLoading = savedInstanceState?.getBoolean(EXTRA_LOADING) ?: true
        isError = savedInstanceState?.getBoolean(EXTRA_ERROR) ?: false
        Log.e(TAG, "Saving instance state: isLoading=$isLoading, isError=$isError")
        showLoading(isLoading)
        showError(isError)
        
        viewModel.listUser.observe(this@ThirdActivity) {
            showUser(it)
        }


        binding.apply {
            refreshPages.setOnRefreshListener {
                refreshPages.isRefreshing = false
                viewModel.refreshPages()
            }
            appToolbar.setNavigationOnClickListener {
                finish()
            }
            btnTryAgain.setOnClickListener {
                viewModel.refreshPages()
            }
        }

    }

    private fun showError(error: Boolean) {
        if (error) {
            binding.errorServer.visibility = View.VISIBLE
        } else {
            binding.errorServer.visibility = View.GONE
        }
    }

    private fun showUser(it: PagingData<UserItem>) {
        val adapter =ReviewUsersAdapter()
        binding.rvUser.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        adapter.submitData(lifecycle, it)
        adapter.setOnItemClickCallback(object : ReviewUsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                val name = "${data.firstName} ${data.lastName}"
                val resultIntent = Intent().apply {
                    putExtra(EXTRA_SELECTED_NAME, name)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        })

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                isLoading = true
            } else {
                isLoading = false
                if (loadState.refresh is LoadState.Error) {
                    isError = true

                } else {
                    isError = false
                }
                showError(isError)
                val isListEmpty = adapter.itemCount == 0
                isAdapterEmpty(isListEmpty)
            }
            showLoading(isLoading)
        }
    }

    private fun isAdapterEmpty(adapter: Boolean) {
        Log.d(TAG, "Adapter: $adapter")
        if (adapter) {
            binding.tvErrorEmpty.visibility = View.VISIBLE
        } else {
            binding.tvErrorEmpty.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(EXTRA_LOADING, isLoading)
        outState.putBoolean(EXTRA_ERROR, isError)
    }


    companion object {
        const val EXTRA_SELECTED_NAME = "extra_selected_name"
        const val TAG = "ThirdActivity"
        const val EXTRA_LOADING = "extra_loading"
        const val EXTRA_ERROR = "extra_error"
    }
}
