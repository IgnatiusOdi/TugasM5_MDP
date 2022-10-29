package com.example.m5

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// adapter perlu mengextend class RecyclerView.Adapter<ViewHolder>
// viewholder digunakan sebagai penampung view dalam layout
// serta menggabungkan data dengan view
// view holder berada didalam class Adapter
class HistoryAdapter(
    private val data: ArrayList<History>,
    private val layout: Int,
    private val context: Context,
): RecyclerView.Adapter<HistoryAdapter.CustomViewHolder>(){

    // custom class yang mengextend ViewHolder
    // digunakan untuk menambahkan property yang berisi view
    // yang didapatkan dari layout
    inner class CustomViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val icon: ImageView = itemView.findViewById(R.id.iconJenis_history)
        val tvNominal: TextView = itemView.findViewById(R.id.tvNominal_history)
        val tvKeterangan: TextView = itemView.findViewById(R.id.tvKeterangan_history)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal_history)
    }

    // on create view holder dipanggil setiap kali ada list item bary ditambahkan
    // atau ada data terupdate dengan notifyDataSetChanged
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            layout, parent ,false
        ))
    }

    // saat viewholder sudah dibuat, pada bagian ini kita gabungkan view
    // dengan data yang kita miliki
    // position berdasarkan index pada data
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = data[position]
        val locale = Locale("id", "ID")
        val number = NumberFormat.getCurrencyInstance(locale)

        when (item.jenis) {
            "Umum" -> {
                holder.icon.setImageResource(R.drawable.ic_baseline_attach_money_24)
            }
            "Belanja" -> {
                holder.icon.setImageResource(R.drawable.ic_baseline_shopping_cart_24)
            }
            "Sumbangan" -> {
                holder.icon.setImageResource(R.drawable.ic_baseline_volunteer_activism_24)
            }
            else -> {
                holder.icon.setImageResource(R.drawable.ic_baseline_school_24)
            }
        }
        holder.tvNominal.text = "${number.format(item.nominal)},00"
        when (item.tipe) {
            -1 -> {
                holder.tvNominal.setTextColor(Color.parseColor("#FF0000"))
            }
            else -> {
                holder.tvNominal.setTextColor(Color.parseColor("#00FF00"))
            }
        }
        holder.tvKeterangan.text = item.keterangan
        holder.tvTanggal.text = "Tanggal : ${item.tanggal}"
    }

    // digunakan untuk mengetahui ukuran dari list view yang akan di iterasikan
    override fun getItemCount(): Int {
        return data.size
    }
}