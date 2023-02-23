package com.example.pruebadeingreso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebadeingreso.adapter.PublishAdapter
import com.example.pruebadeingreso.databinding.ActivityUserDetailBinding
import com.example.pruebadeingreso.model.Publish
import com.example.pruebadeingreso.service.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    var userId: Int? = null

    lateinit var pubs: ArrayList<Publish>
    private var mPubsRecycler: RecyclerView? = null
    private var mAdapter: PublishAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPubsRecycler = binding.recyclerViewPubs
        getData()
        getPublish()
    }
    private fun getData() {
        val bundle: Bundle? = this.intent.extras
        userId = bundle!!.getInt("userId")
        val name = bundle.getString("userName")
        val phone = bundle.getString("userPhone")
        val email = bundle.getString("userEmail")

        val nameView = findViewById<TextView>(R.id.name_detail)
        val phoneView = findViewById<TextView>(R.id.phone_detail)
        val emailView = findViewById<TextView>(R.id.email_detail)
        //pubList = findViewById<ListView>(R.id.list_detail)

        nameView.text = name
        phoneView.text = phone
        emailView.text = email
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getPublish(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getUserPublish("/posts?userId=$userId")
            val data = call.body()
            runOnUiThread {
                if(call.isSuccessful){

                    val mPubs = mutableListOf<Publish>()
                    if (data != null) {
                        for (pub in data) {
                            mPubs.add(pub)
                        }
                    }
                    pubs = arrayListOf()
                    pubs.addAll(mPubs)

                    initRecyclerView()
                }
            }
        }
    }

    private fun initRecyclerView() {
        mAdapter = PublishAdapter(pubs)
        mPubsRecycler!!.layoutManager = LinearLayoutManager(this)   //Vista en forma de grilla
        mPubsRecycler!!.adapter = mAdapter
    }
}