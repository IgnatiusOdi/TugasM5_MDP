package com.example.m5

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryAdapter(
    private val data: ArrayList<History>,
): RecyclerView.Adapter<HistoryAdapter.CustomViewHolder>(){

    inner class CustomViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val icon: ImageView = itemView.findViewById(R.id.iconJenis_history)
        val tvNominal: TextView = itemView.findViewById(R.id.tvNominal_history)
        val tvKeterangan: TextView = itemView.findViewById(R.id.tvKeterangan_history)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal_history)
        val tvOrang: TextView = itemView.findViewById(R.id.tvOrang_history)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            R.layout.layout_history, parent ,false
        ))
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = data[position]
        val locale = Locale("id", "ID")
        val number = NumberFormat.getCurrencyInstance(locale)

        when (item.jenis) {
            "Umum" -> {
                holder.icon.setImageResource(R.drawable.ic_baseline_attach_money_black_24)
            }
            "Belanja" -> {
                holder.icon.setImageResource(R.drawable.ic_baseline_shopping_cart_24)
            }
            "Sumbangan" -> {
                holder.icon.setImageResource(R.drawable.ic_baseline_volunteer_activism_24)
            }
            "Pendidikan" -> {
                holder.icon.setImageResource(R.drawable.ic_baseline_school_24)
            }
        }
        holder.tvNominal.text = "${number.format(item.nominal)},00"
        holder.tvOrang.text = ""
        when (item.tipe) {
            -1 -> {
                holder.tvNominal.setTextColor(Color.parseColor("#FF0000"))
                if (item.orang != "") holder.tvOrang.text = "Ke: ${item.orang}"
            }
            else -> {
                holder.tvNominal.setTextColor(Color.parseColor("#00FF00"))
                if (item.orang != "") holder.tvOrang.text = "Dari: ${item.orang}"
            }
        }
        holder.tvKeterangan.text = item.keterangan
        holder.tvTanggal.text = "Tanggal : ${item.tanggal}"
    }

    override fun getItemCount(): Int {
        return data.size
    }
}