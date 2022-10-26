package com.example.m5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DompetActivity : AppCompatActivity() {

    lateinit var btLogout: Button
    lateinit var tvTabungan: TextView
    lateinit var btTambah: Button
    lateinit var rv: RecyclerView
    lateinit var adapter: RVAdapter
    lateinit var goToTambah: ActivityResultLauncher<Intent>

    var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dompet)

        btLogout = findViewById(R.id.button2)
        tvTabungan = findViewById(R.id.textView4)
        rv = findViewById(R.id.rv)
        btTambah = findViewById(R.id.button3)

        adapter = RVAdapter(Data.listData, R.layout.layout_rv, this)
        rv.adapter = adapter
        rv.layoutManager = GridLayoutManager(this, 2)

        goToTambah = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                res: ActivityResult ->
            if (res.resultCode == RESULT_OK) {
                val data = res.data
                if (data != null) {
                    adapter.notifyDataSetChanged()
                    total = 0
                    for (i in 0 until Data.listData.size) {
                        total += Data.listData[i].total
                    }
                    tvTabungan.text = "Total Tabungan : $total"
                }
            }
        }

        btLogout.setOnClickListener {
            finish()
        }

        btTambah.setOnClickListener {
            goToTambah.launch(Intent(this, AddDompetActivity::class.java))
        }
    }
}