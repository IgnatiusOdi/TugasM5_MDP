package com.example.m5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class AddDompetActivity : AppCompatActivity() {

    lateinit var etNama: EditText
    lateinit var etSaldo: EditText
    lateinit var rg: RadioGroup
    lateinit var btTambah: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dompet)

        etNama = findViewById(R.id.etNama)
        etSaldo = findViewById(R.id.etSaldo)
        rg = findViewById(R.id.rg)
        btTambah = findViewById(R.id.button5)
        findViewById<RadioButton>(R.id.radioButton).isChecked = true

        btTambah.setOnClickListener {
            val tipe = findViewById<RadioButton>(rg.checkedRadioButtonId).text.toString()
            if (etNama.text.isNotBlank() && etSaldo.text.isNotBlank() && tipe != "") {
                Data.listData.add(Data(etNama.text.toString(), etSaldo.text.toString().toInt(), tipe))
                Toast.makeText(this, "Berhasil menambahkan", Toast.LENGTH_SHORT).show()
                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}