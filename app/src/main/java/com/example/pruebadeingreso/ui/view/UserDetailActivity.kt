package com.example.pruebadeingreso.ui.view

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebadeingreso.ui.adapter.PublishAdapter
import com.example.pruebadeingreso.databinding.ActivityUserDetailBinding
import com.example.pruebadeingreso.data.model.PublishModel
import com.example.pruebadeingreso.ui.viewmodel.PublishViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    var userId: Int? = null

    private val publishViewModel: PublishViewModel by viewModels()

    private var mPubsRecycler: RecyclerView? = null
    private var mAdapter: PublishAdapter? = null
    private var mToolbar: androidx.appcompat.widget.Toolbar? = null

    var mPubs = mutableListOf<PublishModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mToolbar = binding.toolbar
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mPubsRecycler = binding.recyclerViewPubs
        getData()

        publishViewModel.onCreate("/posts?=$userId")
        publishViewModel.pubsModel.observe(this, Observer {
            mPubs.addAll(it)
            mAdapter?.notifyDataSetChanged()
        })
        publishViewModel.isLoading.observe(this, Observer {
            binding.progressBar.isVisible = it
        })

        initRecyclerView()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        val bundle: Bundle? = this.intent.extras
        userId = bundle!!.getInt("userId")
        val name = bundle.getString("userName")
        val phone = bundle.getString("userPhone")
        val email = bundle.getString("userEmail")

        binding.nameDetail.text = name
        binding.phoneDetail.text = phone
        binding.emailDetail.text = email
    }

    private fun initRecyclerView() {
        mAdapter = PublishAdapter(mPubs)
        mPubsRecycler!!.layoutManager = LinearLayoutManager(this)   //Vista en forma de grilla
        mPubsRecycler!!.adapter = mAdapter
    }
}