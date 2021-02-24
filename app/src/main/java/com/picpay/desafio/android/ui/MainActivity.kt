package com.picpay.desafio.android.ui

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.ui.adapter.UserListAdapter
import com.picpay.desafio.android.ui.viewModel.MainViewModel
import com.picpay.desafio.android.util.LoadingState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter
    private lateinit var viewFlipperMain: ViewFlipper
    private lateinit var textViewMainError: TextView

    private val viewModel by viewModel<MainViewModel>()

    override fun onResume() {
        super.onResume()

        findView()

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.loadingState.observe(this, Observer {
            when (it.status) {
                LoadingState.Status.RUNNING -> {
                    viewFlipperMain.displayedChild = 0
                }
                LoadingState.Status.SUCCESS -> {
                    viewFlipperMain.displayedChild = 1
                }
                LoadingState.Status.FAILED -> {
                    viewFlipperMain.displayedChild = 2
                    textViewMainError.text = it.msg
                }
            }
        })

        viewModel.data.observe(this, Observer {
            progressBar.visibility = View.GONE
            adapter.users = it
        })
    }

    private fun findView() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)
        viewFlipperMain = findViewById(R.id.viewFlipperMain)
        textViewMainError = findViewById(R.id.textViewMainError)
    }
}
