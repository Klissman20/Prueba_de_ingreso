package com.example.pruebadeingreso.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebadeingreso.R
import com.example.pruebadeingreso.adapter.UserAdapter
import com.example.pruebadeingreso.model.User
import java.util.*

class RecyclerFragment(private var mShopsRecycler: RecyclerView? = null,
                       private var mSearchView: SearchView? = null,
                       private var mSearchTerm: String? = null,
                       private var mAdapter: UserAdapter? = null
): Fragment(), View.OnClickListener {

    lateinit var users: ArrayList<User>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.recycler_fragment, container, false)

        mShopsRecycler = v.findViewById(R.id.recycler_view_fragment)

        mSearchView = v.findViewById(R.id.searchView)
        mSearchView!!.isActivated

        //initFirestore()
        //getFirestoreQuery()

        initRecyclerView()

        return v

    }

    private fun filter(){
        mSearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                mSearchTerm = newText
                if (mSearchTerm != "") {
                    Search(mSearchTerm)
                }else{
                    //traer a todos
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                mSearchTerm = query
                if (mSearchTerm != "") {
                    Search(mSearchTerm)

                }else{
                    //traer a todos
                }
                return false
            }
        })
    }

    private fun Search(search: String?): String{

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

        mAdapter = object : UserAdapter(users) {

        }

        mShopsRecycler!!.adapter = mAdapter
        mShopsRecycler!!.layoutManager = LinearLayoutManager(activity)   //Vista en forma de grilla

    }

    companion object {
        fun newInstance() = RecyclerFragment()
    }

    override fun onClick(p0: View?) {
    }
}