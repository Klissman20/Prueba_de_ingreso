package com.example.pruebadeingreso.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebadeingreso.adapter.UserAdapter
import com.example.pruebadeingreso.databinding.RecyclerFragmentBinding
import com.example.pruebadeingreso.model.User
import com.example.pruebadeingreso.service.APIService
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class RecyclerFragment(): Fragment(), View.OnClickListener {

    private var _binding:RecyclerFragmentBinding? = null
    private val binding get() = _binding!!

    lateinit var users: ArrayList<User>
    private var mUsersRecycler: RecyclerView? = null
    private var mSearchView: androidx.appcompat.widget.SearchView? = null
    private var mSearchTerm: String? = null
    private var mAdapter: UserAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = RecyclerFragmentBinding.inflate(inflater, container, false)

        mUsersRecycler = binding.recyclerViewFragment
        mSearchView = binding.searchView
        mSearchView!!.isActivated = false

        getUsers()

        return binding.root
    }

    private fun getUsers(){

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getUserInformation("/users")
            val data = call.body()
            activity?.runOnUiThread {
                while (!call.isSuccessful) {
                    binding.progressBar.visibility = View.VISIBLE
                    mUsersRecycler!!.visibility = View.INVISIBLE
                }

                val mUsers = mutableListOf<User>()
                if (data != null) {
                    for (user in data){
                        mUsers.add(user)
                    }
                }
                users = arrayListOf()
                users.addAll(mUsers)
                binding.progressBar.visibility = View.INVISIBLE
                mUsersRecycler!!.visibility = View.VISIBLE

                initRecyclerView()

            }
            //mAdapter!!.notifyDataSetChanged()
            /*if(call.isSuccessful){
                binding.progressBar.visibility = View.INVISIBLE
                mUsersRecycler!!.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.VISIBLE
                mUsersRecycler!!.visibility = View.INVISIBLE
            }*/
        }
    }

    override fun onClick(p0: View?) {
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
                mSearchTerm = newText
                if (mSearchTerm != "") {
                    search(mSearchTerm)
                    users.clear()
                    mAdapter!!.notifyDataSetChanged()
                }else{
                    //traer a todos
                    mAdapter!!.notifyDataSetChanged()
                }
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                mSearchTerm = query
                if (mSearchTerm != "") {
                    search(mSearchTerm)

                }else{
                    //traer a todos
                }
                return false
            }
        })
    }

    private fun search(search: String?): String{
        var query : String
        if (search!!.length > 1) {
            val text0 = search.substring(0, 1).uppercase(Locale.ROOT)
            val text1 = search.substring(1).lowercase(Locale.ROOT)
            query = text0 + text1
        }else {
            val text0 = search.substring(0, 1).uppercase(Locale.ROOT)
            query = text0
        }
        return query
    }

    private fun initRecyclerView() {
        if (mSearchView == null) {
            Log.w("MAIN", "No query, not initializing RecyclerView")
        }

        mAdapter = UserAdapter(users)
        mUsersRecycler!!.layoutManager = LinearLayoutManager(activity)   //Vista en forma de grilla
        mUsersRecycler!!.adapter = mAdapter

    }
    companion object {
        fun newInstance() = RecyclerFragment()
    }
}