package com.inovedia.sidemenu_bottombar

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)

        // Set up the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Set up AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.dashboardFragment,
                R.id.notificationsFragment
            ), drawerLayout
        )

        // Set up ActionBar with NavController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)
        NavigationUI.setupWithNavController(bottomNavView, navController)

        // Set up the side menu button to open the drawer
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Set up the side menu and bottom navigation item selection with toast messages
        navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    navigateToFragment(navController, R.id.homeFragment)
                    showToast("Home selected")
                }
                R.id.nav_dashboard -> {
                    navigateToFragment(navController, R.id.dashboardFragment)
                    showToast("Dashboard selected")
                }
                R.id.nav_notifications -> {
                    navigateToFragment(navController, R.id.notificationsFragment)
                    showToast("Notifications selected")
                }
            }
            true
        }

        // Fix for bottom navigation view item click listener
        bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    navigateToFragment(navController, R.id.homeFragment)
                    showToast("Home selected")
                }
                R.id.nav_dashboard -> {
                    navigateToFragment(navController, R.id.dashboardFragment)
                    showToast("Dashboard selected")
                }
                R.id.nav_notifications -> {
                    navigateToFragment(navController, R.id.notificationsFragment)
                    showToast("Notifications selected")
                }
            }
            true
        }
    
    }


    private fun navigateToFragment(navController: NavController, fragmentId: Int) {
        try {
            // Avoid navigating if the current destination is the same as the target
            if (navController.currentDestination?.id != fragmentId) {
                navController.navigate(fragmentId)
            }
        } catch (e: IllegalArgumentException) {
            // Handle the error gracefully, e.g., log the error or show a toast
            showToast("Navigation error: ${e.message}")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }
}
