package com.example.m5

class Data(
    val nama: String,
    val total: Int,
    val tipe: String,
) {
    companion object {
        var listData = ArrayList<Data>()
    }

    override fun toString(): String {
        return "$nama - $total - $tipe"
    }
}