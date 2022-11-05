package com.example.m5

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomepageActivity : AppCompatActivity() {
    lateinit var frame: FrameLayout
    lateinit var navbar: BottomNavigationView

    lateinit var user: User
    var indexUser = -1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        frame = findViewById(R.id.frame_home)
        navbar = findViewById(R.id.navbar_main)

        indexUser = intent.getIntExtra("indexUser", - 1)
        user = User.listUser[indexUser]

        toHome()

        navbar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.homepage -> {
                    toHome()
                }
                R.id.dompet -> {
                    toDompet()
                }
                R.id.transfer -> {
                    toTransfer()
                }
                R.id.history -> {
                    toHistory()
                }
                else -> {
                    toSetting()
                }
            }
            true
        }
    }

    private fun toHome() {
        changeFrame(FragmentHome(indexUser))
    }

    private fun toDompet() {
        changeFrame(FragmentDompet(indexUser))
    }

    private fun toTransfer() {
        changeFrame(FragmentTransfer(indexUser))
    }

    private fun toHistory() {
        changeFrame(FragmentHistory(indexUser))
    }

    private fun toSetting() {
        changeFrame(FragmentSetting(indexUser))
    }

    private fun changeFrame(fragment: Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.frame_home, fragment)
        fragmentManager.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}