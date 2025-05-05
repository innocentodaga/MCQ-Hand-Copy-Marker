package com.example.mcqhardcopymarker

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mcqhardcopymarker.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView

class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager:FragmentManager
    private lateinit var binding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

//         Set full-screen mode with no limits
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


        setSupportActionBar(binding.toolbar)


        val toogle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_closed)
        binding.drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        // Recieving the full name from the previous screen
        val userName = intent.getStringExtra("UserName")

        // Get the navigation header
        val headerView =binding.navigationDrawer.getHeaderView(0)

        //Finding the textview to replace with the user name
        val navName = headerView.findViewById<TextView>(R.id.drawer_username)

        // set the name
        navName.text = userName

        // Get the fragment name from intent extras
        val fragmentToLoad = intent.getStringExtra("fragment_to_load")

        if (savedInstanceState == null) {
            when (fragmentToLoad) {
                "ScanAnswerSheetFragment" -> {
                    // Load your ScanAnswerSheetFragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ScanAnswerSheet())
                        .commit()
                }
                // Add other fragments here as needed
            }
        }


        // Bottom navigation
        binding.bottonNav.background = null
        binding.bottonNav.setOnItemSelectedListener{ item ->
            when(item.itemId){
                R.id.home -> {openFragment(HomeFragment())}
                R.id.profile -> openFragment(AccountFragment())
                R.id.report -> openFragment(ReportFragment())
                R.id.students -> openFragment(StudentsFragment())
            }
            true
        }
        fragmentManager = supportFragmentManager
        openFragment(HomeFragment())

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {


        when(item.itemId){
            R.id.home -> { openFragment(HomeFragment())}
            R.id.students -> openFragment(StudentsFragment())
            R.id.performance -> openFragment(PerformanceFragment())
            R.id.answerKeys -> openFragment(AnswerKeys())
            R.id.settings -> openFragment(SettingsFragment())
            R.id.about -> openFragment(AboutUsFragment())
            R.id.rate -> {Toast.makeText(this, "This is a rate us dialog box", Toast.LENGTH_SHORT).show()}

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}