package com.example.m5

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*

class FragmentDompet(
    private val indexUser: Int,
) : Fragment() {

    lateinit var tvNama: TextView
    lateinit var tvTabungan: TextView
    lateinit var rvDompet: RecyclerView
    lateinit var btTambah: Button

    lateinit var user: User
    lateinit var adapter: DompetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dompet, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvNama = view.findViewById(R.id.tvNama_dompet)
        tvTabungan = view.findViewById(R.id.tvTabungan_dompet)
        rvDompet = view.findViewById(R.id.rv_dompet)
        btTambah = view.findViewById(R.id.btTambahDompet_dompet)

        user = User.listUser[indexUser]

        tvNama.text = "Hi, ${user.name} !"
        val number = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        tvTabungan.text = "Total Tabungan :\n${number.format(user.hitungAsset())},00"

        adapter = DompetAdapter(user.dompet)
        rvDompet.adapter = adapter
        rvDompet.layoutManager = GridLayoutManager(view.context, 2)

        adapter.onClickListener = fun (it: View, position: Int) {
            val popUp = PopupMenu(view.context, it)
            popUp.menuInflater.inflate(R.menu.dompet_menu, popUp.menu)
            popUp.setOnMenuItemClickListener {
                return@setOnMenuItemClickListener when(it.itemId){
                    R.id.topup->{
                        parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_home, FragmentTopupWithdraw(indexUser, position, "topup"))
                            .commit()
                        true
                    }
                    R.id.withdraw->{
                        parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_home, FragmentTopupWithdraw(indexUser, position, "withdraw"))
                            .commit()
                        true
                    }
                    R.id.delete -> {
                        if (position != 0) {
                            user.dompet[0].saldo += user.dompet[position].saldo
                            adapter.notifyItemChanged(0)
                            user.dompet.removeAt(position)
                            adapter.notifyItemRemoved(position)
                            Toast.makeText(view.context, "Berhasil menghapus dompet!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(view.context, "Main Pocket tidak dapat dihapus!", Toast.LENGTH_SHORT).show()
                        }
                        true
                    }
                    else->{
                        false
                    }
                }
            }
            popUp.show()
        }

        btTambah.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.frame_home, FragmentTambahDompet(indexUser))
                .commit()
        }
    }

    var onTopupListener:((indexDompet: Int)->Unit)? = null
    var onWithdrawListener:((indexDompet: Int)->Unit)? = null
}