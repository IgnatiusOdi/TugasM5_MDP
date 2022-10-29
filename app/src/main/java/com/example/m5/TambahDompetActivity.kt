package com.example.m5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class TambahDompetActivity : AppCompatActivity() {
    lateinit var etNama: EditText
    lateinit var etSaldo: EditText
    lateinit var rgTipe: RadioGroup
    lateinit var etPIN: EditText
    lateinit var btTambah: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_dompet)

        etNama = findViewById(R.id.etNama_dompet)
        etSaldo = findViewById(R.id.etSaldo_dompet)
        rgTipe = findViewById(R.id.rgTipe_dompet)
        etPIN = findViewById(R.id.etPIN_dompet)
        btTambah = findViewById(R.id.btTambah_dompet)

        val indexUser = intent.getIntExtra("indexUser", -1)
        val user = User.listUser[indexUser]

        btTambah.setOnClickListener {
            val tipe = when (rgTipe.checkedRadioButtonId) {
                R.id.rbSpending_dompet -> {
                    "Spending Dompet"
                }
                else -> {
                    "Saving Pocket"
                }
            }
            if (etNama.text.isNotBlank() && etSaldo.text.isNotBlank() && etPIN.text.isNotBlank()) {
                if (etPIN.text.toString() == user.pin) {
                    user.dompet.add(Dompet(etNama.text.toString(), etSaldo.text.toString().toInt(), tipe))
                    Toast.makeText(this, "Berhasil menambahkan dompet", Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    Toast.makeText(this, "PIN salah!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}