package com.example.m5

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class TopupWithdrawActivity : AppCompatActivity() {
    lateinit var tvTitleWithdraw: TextView
    lateinit var tvNama: TextView
    lateinit var tvTotal: TextView
    lateinit var tvTitleStatus: TextView
    lateinit var etNominal: EditText
    lateinit var etKeterangan: EditText
    lateinit var spinnerJenis: Spinner
    lateinit var etPIN: EditText
    lateinit var btAction: Button
    lateinit var btBack: Button

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topup_withdraw)

        tvTitleWithdraw = findViewById(R.id.tvTitleWithdraw)
        tvNama = findViewById(R.id.tvNamaDompet)
        tvTotal = findViewById(R.id.tvTotalDompet)
        tvTitleStatus = findViewById(R.id.tvTitleStatusDompet)
        etNominal = findViewById(R.id.etNominalDompet)
        etKeterangan = findViewById(R.id.etKeteranganDompet)
        spinnerJenis = findViewById(R.id.spinnerStatusDompet)
        etPIN = findViewById(R.id.etPINDompet)
        btAction = findViewById(R.id.btActionDompet)
        btBack = findViewById(R.id.btBackDompet)

        val indexUser = intent.getIntExtra("indexUser", -1)
        val indexDompet = intent.getIntExtra("indexDompet", -1)
        val status = intent.getStringExtra("status")
        val user = User.listUser[indexUser]
        val dompet = user.dompet[indexDompet]
        val locale = Locale("id", "ID")
        val number = NumberFormat.getCurrencyInstance(locale)

        tvNama.text = dompet.nama
        tvTotal.text = "Total Asset:\n${number.format(dompet.saldo)},00"
        if (status == "topup") TopupView()
        else WithdrawView()

        btAction.setOnClickListener {
            if (etNominal.text.isNotBlank() && etKeterangan.text.isNotBlank() && etPIN.text.isNotBlank()) {
                if (etPIN.text.toString() == user.pin) {
                    val nominal = etNominal.text.toString().toInt()
                    val keterangan = etKeterangan.text.toString()
                    val jenis = spinnerJenis.selectedItem.toString()

                    if (status == "topup") {
                        // ADD NOMINAL
                        dompet.saldo += nominal
                        // ADD HISTORY
                        dompet.addHistory(nominal, keterangan, jenis, SimpleDateFormat("yyyy MMM dd").format(user.tanggal), 1)
                        Toast.makeText(this, "Topup Sukses!", Toast.LENGTH_SHORT).show()
                    } else {
                        // MIN NOMINAL
                        dompet.saldo -= nominal
                        // ADD HISTORY
                        dompet.addHistory(nominal, keterangan, jenis, SimpleDateFormat("yyyy MMM dd").format(user.tanggal), -1)
                        Toast.makeText(this, "Withdraw Sukses!", Toast.LENGTH_SHORT).show()
                    }

                    val intent = Intent()
                    intent.putExtra("indexDompet", indexDompet)
                    setResult(RESULT_OK, intent)
                    finish()
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

    @SuppressLint("SetTextI18n")
    private fun TopupView() {
        tvTitleWithdraw.isVisible = false
        tvTitleStatus.text = "TAMBAH ASSET"
        btAction.text = "TOPUP"
    }

    @SuppressLint("SetTextI18n")
    private fun WithdrawView() {
        tvTitleWithdraw.isVisible = true
        tvTitleStatus.text = "WITHDRAW ASSET"
        btAction.text = "WITHDRAW"
    }
}