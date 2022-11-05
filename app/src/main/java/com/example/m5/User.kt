package com.example.m5

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class User (
    val name: String,
    val username: String,
    val password: String,
    var pin: String,
    val norek: String,
    var tanggal: Date,
    var dompet: ArrayList<Dompet> = arrayListOf(Dompet("Main Pocket", 0, "Spending Pocket")),
    var contact: ArrayList<User> = arrayListOf(),
    var history: ArrayList<History> = arrayListOf(),
) {
    companion object {
        var listUser = ArrayList<User>()
    }

    override fun toString(): String {
        return "$name - $norek"
    }

    fun updateTanggal(cheatDate: Date) {
        while (tanggal < cheatDate) {
            // ADD 1 DAY
            val calendar = Calendar.getInstance()
            calendar.time = tanggal
            calendar.add(Calendar.DATE, 1)
            tanggal = calendar.time

            checkTanggal()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun checkTanggal() {
        val calendar = Calendar.getInstance()
        calendar.time = tanggal
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            dompet.forEach {
                // BUNGA
                val bunga = when (it.tipe) {
                    "Spending Pocket" -> {
                        0.015
                    }
                    else -> {
                        0.045
                    }
                }
                val bungaDompet: Int = (it.saldo * bunga).toInt()

                // BIAYA ADMIN
                val biayaAdmin: Int = (it.saldo * 0.0015).toInt()

                it.saldo += bungaDompet - biayaAdmin

                val historyBunga = History(bungaDompet, "Bunga bulan ini", "Umum", SimpleDateFormat("yyyy MMM dd").format(tanggal), 1)
                val historyBiayaAdmin = History(biayaAdmin, "Biaya admin", "Umum", SimpleDateFormat("yyyy MMM dd").format(tanggal), -1)
                it.addHistory(historyBunga)
                it.addHistory(historyBiayaAdmin)
            }
        }
    }

    fun hitungAsset(): Int {
        var total = 0
        for (tabungan in dompet) {
            total += tabungan.saldo
        }
        return total
    }
}