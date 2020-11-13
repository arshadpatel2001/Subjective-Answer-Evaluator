package com.example.answer

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_navigation.*

class Navigation : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_one -> {
                Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TeacherActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_item_two -> {
                Toast.makeText(this, "Question Section", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TeacherActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_item_three -> {
                Toast.makeText(this, "Answer Section", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TeacherActivityAnswer::class.java)
                startActivity(intent)
            }
            R.id.nav_item_four -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
            R.id.nav_item_five -> {
                nav_view.menu.findItem(R.id.nav_item_one).isVisible = false
                nav_view.menu.findItem(R.id.nav_item_two).isVisible = false
                nav_view.menu.findItem(R.id.nav_item_three).isVisible = false
                nav_view.menu.findItem(R.id.nav_item_four).isVisible = false
                nav_view.menu.findItem(R.id.nav_item_six).isVisible = false
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginStudent::class.java)
                startActivity(intent)
            }
            R.id.nav_item_six -> {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                nav_view.menu.findItem(R.id.nav_item_five).isVisible = false
                val intent = Intent(this, LoginStudent::class.java)
                startActivity(intent)
            }
            R.id.nav_item_seven -> Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show()
            R.id.nav_item_eight -> Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
        override fun onBackPressed() {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }
}