package com.example.pruebadeingreso.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pruebadeingreso.fragment.RecyclerFragment

class PageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return RecyclerFragment()
    }

    override fun getCount(): Int {
        return 1
    }

}