package com.example.m5

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class FragmentHome(
    private val indexUser: Int
) : Fragment() {

    lateinit var tvNama: TextView
    lateinit var tvNomorRekening: TextView
    lateinit var tvAsset: TextView
    lateinit var navbar: BottomNavigationView
    lateinit var rvNotifikasi: RecyclerView
    var notifikasi: ArrayList<History> = arrayListOf()
    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity.let {

        }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvNama = view.findViewById(R.id.tvNama_home)
        tvNomorRekening = view.findViewById(R.id.tvNomorRekening_home)
        tvAsset = view.findViewById(R.id.tvAsset_home)
        navbar = view.findViewById(R.id.navbar_home)
        rvNotifikasi = view.findViewById(R.id.rvNotifikasi_home)
        
        navbar.setOnItemSelectedListener { 
            when(it.itemId) {
                R.id.transfer -> {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_home, FragmentTransfer(indexUser))
                        .commit()
                }
                R.id.history -> {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_home, FragmentContact(indexUser))
                        .commit()
                }
                else -> {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_home, FragmentAddContact(indexUser))
                        .commit()
                }
            }
            true
        }

        user = User.listUser[indexUser]
        tvNama.text = "Hi, ${user.name} !"
        tvNomorRekening.text = "No Rekening : ${user.norek}"
        val number = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        tvAsset.text = "${number.format(user.hitungAsset())},00"

        // NOTIFIKASI
        filterNewestHistory()

        val adapter = HistoryAdapter(notifikasi)
        rvNotifikasi.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        rvNotifikasi.layoutManager = LinearLayoutManager(view.context)
        rvNotifikasi.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun filterNewestHistory() {
        var counter = 0
        for (history in user.history.reversed()) {
            notifikasi.add(history)
            counter++
            if (counter == 5) {
                break
            }
        }
    }

}
