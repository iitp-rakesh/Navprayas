package com.example.navprayas.activity.mainactivity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.navprayas.R
import com.example.navprayas.activity.loginActivity.LoginActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    lateinit var navController: NavController

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_mainactivity) as NavHostFragment
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.mainActivityHomeFragment)
                    drawerLayout.close()
                    true
                }
                R.id.nav_Logout -> {
                    val auth = Firebase.auth
                    val builder=AlertDialog.Builder(this)
                    builder.setTitle("Logout")
                    builder.setMessage("Are you sure you want to logout?")
                    builder.setPositiveButton("Yes"){dialog, which ->
                        auth.signOut()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    builder.setNegativeButton("No"){dialog, which ->
                        dialog.dismiss()
                    }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    true
                }
                R.id.nav_profile -> {
                    navController.navigate(R.id.profileFragment)
                    drawerLayout.close()
                    true
                }
                R.id.nav_AboutUs -> {
                    navController.navigate(R.id.aboutUsFragment)
                    drawerLayout.close()
                    true
                }
                R.id.nav_ContactUs -> {
                    navController.navigate(R.id.contactUsFragment)
                    drawerLayout.close()
                    true
                }
                else -> false
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START)
                else
                    drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {

        if (!navController.popBackStack()) {
            super.onBackPressed()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Exit")
            builder.setMessage("Are you sure you want to exit?")
            builder.setPositiveButton("Yes") { dialog, which ->
                finishAffinity()
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notification, menu)

        // Get the notification menu item and set a listener
        val notificationMenuItem = menu?.findItem(R.id.notification_item)
        notificationMenuItem?.setOnMenuItemClickListener {
            navController.navigate(R.id.notificationFragment)
            true
        }
        return true
    }
}
