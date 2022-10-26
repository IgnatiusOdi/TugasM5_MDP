package com.example.m5

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView

// adapter perlu mengextend class RecyclerView.Adapter<ViewHolder>
// viewholder digunakan sebagai penampung view dalam layout
// serta menggabungkan data dengan view
// view holder berada didalam class Adapter
class RVAdapter(
    private val data: ArrayList<Data>,
    private val layout: Int,
    private val context: Context,
//    private val cb: ()->Unit
): RecyclerView.Adapter<RVAdapter.CustomViewHolder>(){

    // on create view holder dipanggil setiap kali ada list item bary ditambahkan
    // atau ada data terupdate dengan notifyDataSetChanged
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            layout, parent ,false
        ))
    }

    // saat viewholder sudah dibuat, pada bagian ini kita gabungkan view
    // dengan data yang kita miliki
    // position berdasarkan index pada data
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = data[position]
        holder.tvNama.text = item.nama
        holder.tvTotal.text = item.total.toString()
        holder.tvTipe.text = item.tipe
        holder.tvNama.setOnClickListener {
            val popUp = PopupMenu(context, holder.tvNama)
            popUp.menuInflater.inflate(R.menu.context_menu, popUp.menu)
            popUp.setOnMenuItemClickListener {
                return@setOnMenuItemClickListener when(it.itemId){
                    R.id.topup->{
                        context.startActivity(Intent(context, TopupActivity::class.java))
                        true
                    }
                    R.id.delete->{
                        Data.listData.removeAt(position)
                        Toast.makeText(context, "Terhapus!", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else->{
                        false
                    }
                }
            }
            popUp.show()
        }
    }

    // digunakan untuk mengetahui ukuran dari list view yang akan di iterasikan
    override fun getItemCount(): Int {
        return data.size
    }

    // custom class yang mengextend ViewHolder
    // digunakan untuk menambahkan property yang berisi view
    // yang didapatkan dari layout
    class CustomViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvTotal: TextView = itemView.findViewById(R.id.tvTotal)
        val tvTipe: TextView = itemView.findViewById(R.id.tvTipe)
    }
}