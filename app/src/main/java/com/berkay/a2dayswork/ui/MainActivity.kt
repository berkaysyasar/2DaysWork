package com.berkay.a2dayswork.ui

import RoutineWorker
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.berkay.a2dayswork.R
import com.berkay.a2dayswork.databinding.ActivityMainBinding
import com.berkay.a2dayswork.ui.fragment.menuItems.AboutFragment
import com.berkay.a2dayswork.ui.fragment.MainFragment
import com.berkay.a2dayswork.ui.fragment.ReminderFragment
import com.berkay.a2dayswork.ui.fragment.RoutineFragment
import com.berkay.a2dayswork.ui.fragment.menuItems.DevFragment
import com.berkay.a2dayswork.ui.fragment.menuItems.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startRoutineWorker()

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

        val headerView = navView.getHeaderView(0)
        val menuCloseImageView = headerView.findViewById<ImageView>(R.id.menucloseImageView)

        menuCloseImageView.setOnClickListener{
            drawerLayout.closeDrawer(GravityCompat.START)
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
                R.id.nav_settings -> {
                    replaceFragment(SettingsFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    binding.bottomNavigationView.selectedItemId = 0
                }
                R.id.nav_about ->{
                    replaceFragment(AboutFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    binding.bottomNavigationView.selectedItemId = 0
                }
                R.id.nav_aboutdeveloper ->{
                    replaceFragment(DevFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    binding.bottomNavigationView.selectedItemId = 0
                }
            }
            true
        }

        val menuItem = navView.menu.findItem(R.id.nav_contact)
        val menuItem2 = navView.menu.findItem(R.id.nav_menu)
        // MenuItem'i bir SpannableString'e dönüştürün
        val spannableString = SpannableString(menuItem.title)
        val spannableString2 = SpannableString(menuItem2.title)
        spannableString.setSpan(AbsoluteSizeSpan(60), 0, spannableString.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        spannableString2.setSpan(AbsoluteSizeSpan(60), 0, spannableString2.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        // Rengi değiştirmek için bir ForegroundColorSpan ekleyin
        spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, spannableString.length, 0)
        spannableString2.setSpan(StyleSpan(Typeface.BOLD), 0, spannableString2.length, 0)
        // MenuItem'in başlığını güncelleyin
        menuItem.title = spannableString
        menuItem2.title = spannableString2

        replaceFragment(MainFragment())

        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.routineItem -> replaceFragment(RoutineFragment())
                R.id.homeItem -> replaceFragment(MainFragment())
                R.id.reminderItem -> replaceFragment(ReminderFragment())
                else -> {

                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        checkDailyRoutine()
        println("onResume")
    }

    private fun checkDailyRoutine() {
        val workRequest = OneTimeWorkRequestBuilder<RoutineWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }

    private fun startRoutineWorker() {
        val workRequest = PeriodicWorkRequestBuilder<RoutineWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        ).build()

        // Eğer bir önceki iş zaten varsa, bu işi durdur ve tekrar ekle
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "routineWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
}