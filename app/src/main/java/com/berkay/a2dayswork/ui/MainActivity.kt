package com.berkay.a2dayswork.ui

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.databinding.ActivityMainBinding
import com.berkay.a2dayswork.ui.fragment.MainFragment
import com.berkay.a2dayswork.ui.fragment.RoutineFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout : DrawerLayout = binding.drawerLayout
        val navView : NavigationView = binding.navView

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.homeItem

        binding.menuImageView.setOnClickListener{
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    replaceFragment(MainFragment())
                    val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    bottomNavigationView.selectedItemId = R.id.homeItem
                    true
                }
                R.id.menucloseImageView -> {
                    val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

            }
            true
        }

        val menuItem = navView.menu.findItem(R.id.nav_contact)
        // MenuItem'i bir SpannableString'e dönüştürün
        val spannableString = SpannableString(menuItem.title)
        spannableString.setSpan(AbsoluteSizeSpan(50), 0, spannableString.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        // Rengi değiştirmek için bir ForegroundColorSpan ekleyin
        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.maincolor)), 0, spannableString.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, spannableString.length, 0)
        // MenuItem'in başlığını güncelleyin
        menuItem.title = spannableString

        replaceFragment(MainFragment())

        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.routineItem -> replaceFragment(RoutineFragment())
                R.id.homeItem -> replaceFragment(MainFragment())

                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
}