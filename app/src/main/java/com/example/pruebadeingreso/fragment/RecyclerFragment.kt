package com.example.pruebadeingreso.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebadeingreso.UserDetailActivity
import com.example.pruebadeingreso.adapter.UserAdapter
import com.example.pruebadeingreso.databinding.FragmentRecyclerBinding
import com.example.pruebadeingreso.model.User
import com.example.pruebadeingreso.database.UserDao
import com.example.pruebadeingreso.database.UserDatabase
import com.example.pruebadeingreso.service.APIService
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class RecyclerFragment(): Fragment(), UserAdapter.OnItemClickListener {

    private var _binding:FragmentRecyclerBinding? = null
    private val binding get() = _binding!!

    lateinit var users: ArrayList<User>
    private var mUsersRecycler: RecyclerView? = null
    private var mSearchView: androidx.appcompat.widget.SearchView? = null
    private var mAdapter: UserAdapter? = null
    private var mEmptyText: TextView? = null
    private lateinit var db: UserDao
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)

        mUsersRecycler = binding.recyclerViewFragment
        mSearchView = binding.searchView
        mEmptyText = binding.tvEmpty
        mEmptyText!!.visibility = View.INVISIBLE
        mSearchView!!.isActivated = false
        mSearchView!!.isEnabled = false

        db = context?.let { UserDatabase.getInstance(it)?.getUserDao()}!!

        getUsers()
        filter()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        filter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filter()
    }

    private fun getUsers(){
        if(db.getAll().isEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                val call = getRetrofit().create(APIService::class.java).getUserInformation("/users")
                val data = call.body()
                activity?.runOnUiThread {
                    while (!call.isSuccessful) {
                        binding.progressBar.visibility = View.VISIBLE
                        mUsersRecycler!!.visibility = View.INVISIBLE
                        mSearchView?.visibility = View.INVISIBLE
                    }

                    val mUsers = mutableListOf<User>()
                    if (data != null) {
                        for (user in data) {
                            mUsers.add(user)
                        }
                    }
                    users = arrayListOf()
                    users.addAll(mUsers)

                    binding.progressBar.visibility = View.INVISIBLE
                    mUsersRecycler!!.visibility = View.VISIBLE
                    mSearchView?.visibility = View.VISIBLE

                    initRecyclerView()
                }
                withContext(Dispatchers.IO) {
                    for (user in users) {
                        db.insertAll(user)
                    }
                }
            }

        }else{
            users = arrayListOf()
            users.addAll(db.getAll())
            binding.progressBar.visibility = View.INVISIBLE
            initRecyclerView()
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun filter(){
        mSearchView!!.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText != "") {
                    users.clear()
                    users.addAll(db.findByName(search(newText)))
                    if (users.isEmpty()) {
                        mEmptyText!!.visibility = View.VISIBLE
                        mUsersRecycler!!.visibility = View.INVISIBLE
                    }else{
                        mEmptyText!!.visibility = View.INVISIBLE
                        mUsersRecycler!!.visibility = View.VISIBLE
                    }
                    mAdapter!!.notifyDataSetChanged()
                } else {
                    mEmptyText!!.visibility = View.INVISIBLE
                    mUsersRecycler!!.visibility = View.VISIBLE
                    users.clear()
                    users.addAll(db.getAll())
                    mAdapter!!.notifyDataSetChanged()
                }
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query != "") {
                    users.clear()
                    users.addAll(db.findByName(search(query)))
                    if (users.isEmpty()) {
                        mEmptyText!!.visibility = View.VISIBLE
                        mUsersRecycler!!.visibility = View.INVISIBLE
                    }else{
                        mEmptyText!!.visibility = View.INVISIBLE
                        mUsersRecycler!!.visibility = View.VISIBLE
                    }
                    mAdapter!!.notifyDataSetChanged()
                } else {
                    mEmptyText!!.visibility = View.INVISIBLE
                    mUsersRecycler!!.visibility = View.VISIBLE
                    users.clear()
                    users.addAll(db.getAll())
                    mAdapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
    }

    private fun search(search: String?): String{
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

    private fun initRecyclerView() {
        mAdapter = UserAdapter(users, this)
        mUsersRecycler!!.layoutManager = LinearLayoutManager(activity)   //Vista en forma de grilla
        mUsersRecycler!!.adapter = mAdapter
    }

    override fun onItemClick(user: User) {
        val intent = Intent(activity, UserDetailActivity::class.java).apply {
            putExtra("userId", user.id)
            putExtra("userName", user.name)
            putExtra("userPhone", user.phone)
            putExtra("userEmail",user.email)
        }
        startActivity(intent)
    }
}