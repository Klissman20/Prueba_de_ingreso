package com.example.pruebadeingreso.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebadeingreso.ui.adapter.UserAdapter
import com.example.pruebadeingreso.databinding.ActivityMainBinding
import com.example.pruebadeingreso.domain.model.User
import com.example.pruebadeingreso.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()


    private var mUsersRecycler: RecyclerView? = null
    private lateinit var mAdapter: UserAdapter
    private var mSearchView: androidx.appcompat.widget.SearchView? = null

    var mUsers = mutableListOf<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUsersRecycler = binding.recyclerViewFragment
        mSearchView = binding.searchView

        userViewModel.onCreate()
        userViewModel.userModel.observe(this, Observer {
            mUsers.addAll(it)
            mAdapter.notifyDataSetChanged()
        })
        userViewModel.isLoading.observe(this, Observer {
            binding.progressBar.isVisible = it
        })

        userViewModel.isEmpty.observe(this, Observer {
            binding.tvEmpty.isVisible = it
        })

        initRecyclerView()
        filter()
    }

    private fun initRecyclerView() {
        mAdapter = UserAdapter(mUsers, this)
        mUsersRecycler!!.layoutManager = LinearLayoutManager(this)   //Vista en forma de grilla
        mUsersRecycler!!.adapter = mAdapter
    }

    private fun filter(){
        mSearchView!!.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText != "") {
                    mUsers.clear()
                    userViewModel.findByName(searchFit(newText))
                } else {
                    mUsers.clear()
                    userViewModel.onCreate()
                }
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query != "") {
                    mUsers.clear()
                    userViewModel.findByName(searchFit(query))
                } else {
                    mUsers.clear()
                    userViewModel.onCreate()
                }
                return false
            }
        })
    }

    private fun searchFit(search: String?): String{
        val query : String
        if (search!!.length > 1) {
            val text0 = search.substring(0, 1).uppercase(Locale.ROOT)
            val text1 = search.substring(1).lowercase(Locale.ROOT)
            query = "$text0$text1%"
        }else {
            val text0 = search.substring(0, 1).uppercase(Locale.ROOT)
            query = "$text0%"
        }
        return query
    }

    override fun onItemClick(userModel: User) {
        val intent = Intent(this, UserDetailActivity::class.java).apply {
            putExtra("userId", userModel.id)
            putExtra("userName", userModel.name)
            putExtra("userPhone", userModel.phone)
            putExtra("userEmail",userModel.email)
        }
        startActivity(intent)
    }

}