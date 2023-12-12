package com.example.foodiepal.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.foodiepal.R
import com.example.foodiepal.databinding.ActivityMainBinding
import com.example.foodiepal.adapter.ViewPager2Adapter
import com.example.foodiepal.ui.aboutme.AboutMeFragment
import com.example.foodiepal.ui.blogs.BlogFragment
import com.example.foodiepal.ui.contacts.ContactsFragment
import com.example.foodiepal.ui.planner.MealPlannerFragment
import com.example.foodiepal.ui.recipe.RecipeFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val bottomNavView: BottomNavigationView = binding.bottomNavView
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        val fragmentList = listOf(
            RecipeFragment(),
            MealPlannerFragment(),
            BlogFragment(),
            ContactsFragment(),
            AboutMeFragment()
        )

        val tabList = listOf("Recipe", "Meal Planner", "Blogs", "Contact", "About Me")

        val viewPager2Adapter = ViewPager2Adapter(fragmentList, this)


        viewPager.adapter = viewPager2Adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabList[position]
        }.attach()

        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_recipe -> viewPager.currentItem = 0
                R.id.navigation_meal_planner -> viewPager.currentItem = 1
                R.id.navigation_blogs -> viewPager.currentItem = 2
            }
            true
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if(tab.position<3){
                    viewPager.currentItem = tab.position
                    bottomNavView.selectedItemId = bottomNavView.menu.getItem(tab.position).itemId
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.viewPager, fragment)
        transaction.commit()
    }
}