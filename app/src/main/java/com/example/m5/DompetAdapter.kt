package com.example.m5

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DompetAdapter(
    private val data: ArrayList<Dompet>,
): RecyclerView.Adapter<DompetAdapter.CustomViewHolder>(){

    inner class CustomViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvTotal: TextView = itemView.findViewById(R.id.tvTotal)
        val tvTipe: TextView = itemView.findViewById(R.id.tvTipe)
        init {
            view.setOnClickListener {
                onClickListener?.invoke(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            R.layout.layout_dompet, parent ,false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = data[position]
        val locale = Locale("id", "ID")
        val number = NumberFormat.getCurrencyInstance(locale)

        holder.tvNama.text = item.nama
        holder.tvTotal.text = "${number.format(item.saldo)},00"
        holder.tvTipe.text = item.tipe
    }

    override fun getItemCount(): Int {
        return data.size
    }

    var onClickListener:((view: View, position: Int)->Unit)? = null
}