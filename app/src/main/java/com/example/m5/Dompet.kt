package com.example.m5

class Dompet(
    val nama: String,
    var saldo: Int,
    var tipe: String,
    var history: ArrayList<History> = arrayListOf()
) {
    fun addHistory(hist: History) {
        history.add(hist)
    }
}