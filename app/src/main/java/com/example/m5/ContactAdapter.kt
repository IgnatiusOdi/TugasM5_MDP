package com.example.m5

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class ContactAdapter(
    private val data: ArrayList<String>,
): RecyclerView.Adapter<ContactAdapter.CustomViewHolder>(){

    inner class CustomViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama_contact)
        val btDelete: Button = itemView.findViewById(R.id.btDelete_contact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            R.layout.layout_contact, parent ,false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = data[position]
        holder.tvNama.text = item
        holder.btDelete.setOnClickListener {
            onClickListener?.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    var onClickListener:((position: Int)->Unit)? = null
}