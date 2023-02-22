package com.example.pruebadeingreso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.pruebadeingreso.adapter.PageAdapter
import com.example.pruebadeingreso.databinding.ActivityMainBinding
import com.example.pruebadeingreso.fragment.RecyclerFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = findViewById(R.id.viewPager)

        setupViewPager()
    }

    private fun addFragment(): ArrayList<Fragment> {
        return arrayListOf(
            RecyclerFragment.newInstance()
        )
    }

    private fun setupViewPager(){
        viewPager!!.adapter = PageAdapter(supportFragmentManager, addFragment())
    }

}