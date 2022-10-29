package com.example.m5

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat

class SettingsActivity : AppCompatActivity() {
    lateinit var tvTanggal: TextView
    lateinit var etTanggal: EditText
    lateinit var etPIN: EditText
    lateinit var btSave: Button
    lateinit var btBack: Button

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        tvTanggal = findViewById(R.id.tvTanggal_settings)
        etTanggal = findViewById(R.id.etTanggal_settings)
        etPIN = findViewById(R.id.etPIN_settings)
        btSave = findViewById(R.id.btSave_settings)
        btBack = findViewById(R.id.btBack_settings)

        val indexUser = intent.getIntExtra("indexUser", -1)
        val user = User.listUser[indexUser]

        tvTanggal.text = "Tanggal Saat Ini :\n${SimpleDateFormat("yyyy MMM dd").format(user.tanggal)}"

        btSave.setOnClickListener {
            if (etTanggal.text.isNotBlank() && etPIN.text.isNotBlank()) {
                if (etPIN.text.toString() == user.pin) {
                    val tanggal = SimpleDateFormat("yyyy/MM/dd").parse(etTanggal.text.toString())
                    if (tanggal!! > user.tanggal) {
                        user.updateTanggal(tanggal)
                        val intent = Intent()
                        setResult(RESULT_OK, intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Tanggal invalid!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "PIN salah!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Field kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        btBack.setOnClickListener {
            finish()
        }
    }
}