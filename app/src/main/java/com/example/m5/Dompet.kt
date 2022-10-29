package com.example.m5

class Dompet(
    val nama: String,
    var saldo: Int,
    var tipe: String,
    var history: ArrayList<History> = arrayListOf()
) {
    fun addHistory(nominal: Int, keterangan: String, jenis: String, tanggal: String, tipe: Int) {
        history.add(History(nominal, keterangan, jenis, tanggal, tipe))
    }
}