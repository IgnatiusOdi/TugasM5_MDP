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

// adapter perlu mengextend class RecyclerView.Adapter<ViewHolder>
// viewholder digunakan sebagai penampung view dalam layout
// serta menggabungkan data dengan view
// view holder berada didalam class Adapter
class DompetAdapter(
    private val data: ArrayList<Dompet>,
    private val layout: Int,
    private val context: Context,
//    private val cb: ()->Unit
): RecyclerView.Adapter<DompetAdapter.CustomViewHolder>(){

    // custom class yang mengextend ViewHolder
    // digunakan untuk menambahkan property yang berisi view
    // yang didapatkan dari layout
    inner class CustomViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvTotal: TextView = itemView.findViewById(R.id.tvTotal)
        val tvTipe: TextView = itemView.findViewById(R.id.tvTipe)
        init {
            view.setOnClickListener {
                onClickListener?.invoke(it, position)
            }
        }
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
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = data[position]
        val locale = Locale("id", "ID")
        val number = NumberFormat.getCurrencyInstance(locale)

        holder.tvNama.text = item.nama
        holder.tvTotal.text = "${number.format(item.saldo)},00"
        holder.tvTipe.text = item.tipe
    }

    // digunakan untuk mengetahui ukuran dari list view yang akan di iterasikan
    override fun getItemCount(): Int {
        return data.size
    }

    var onClickListener:((view: View, position: Int)->Unit)? = null
}