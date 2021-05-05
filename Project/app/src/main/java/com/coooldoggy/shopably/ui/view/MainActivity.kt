package com.coooldoggy.shopably.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.coooldoggy.shopably.R
import com.coooldoggy.shopably.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainViewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainViewBinding.root)
        setResources()
    }

    private fun setResources(){
        mainViewBinding.vp.apply {
            offscreenPageLimit = 2
            adapter = FragmentPageAdapter(this@MainActivity)
            currentItem = 0
            isUserInputEnabled = false
        }
        mainViewBinding.bottomNav.setOnNavigationItemSelectedListener(bottomNavClickListener)
        supportActionBar?.title = baseContext.getString(R.string.bottom_nav_home)
    }

    private val bottomNavClickListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when(item.itemId){
            R.id.fragment_home -> {
                mainViewBinding.vp.setCurrentItem(0, false)
                supportActionBar?.title = baseContext.getString(R.string.bottom_nav_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.fragment_favorite -> {
                mainViewBinding.vp.setCurrentItem(1, false)
                supportActionBar?.title = baseContext.getString(R.string.bottom_nav_zzim)
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                return@OnNavigationItemSelectedListener true
            }
        }
    }

    inner class FragmentPageAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm){

        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> HomeFragment()
                1 -> FavoriteFragment()
                else -> Fragment()
            }
        }
    }

}