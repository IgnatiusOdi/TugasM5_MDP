package com.example.m5

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class FragmentTopupWithdraw(
    private val indexUser: Int,
    private val indexDompet: Int,
    private val statusAction: String,
) : Fragment() {

    lateinit var tvWithdraw: TextView
    lateinit var tvNama: TextView
    lateinit var tvTotal: TextView
    lateinit var tvTitleStatus: TextView
    lateinit var etNominal: EditText
    lateinit var etKeterangan: EditText
    lateinit var spinnerJenis: Spinner
    lateinit var etPIN: EditText
    lateinit var btAction: Button
    lateinit var btBack: Button

    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_topup_withdraw, container, false)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvWithdraw = view.findViewById(R.id.tvWithdraw_topup_withdraw)
        tvNama = view.findViewById(R.id.tvNama_topup_withdraw)
        tvTotal = view.findViewById(R.id.tvTotal_topup_withdraw)
        tvTitleStatus = view.findViewById(R.id.tvTitleStatus_topup_withdraw)
        etNominal = view.findViewById(R.id.etNominal_topup_withdraw)
        etKeterangan = view.findViewById(R.id.etKeterangan_topup_withdraw)
        spinnerJenis = view.findViewById(R.id.spinnerJenis_topup_withdraw)
        etPIN = view.findViewById(R.id.etPIN_topup_withdraw)
        btAction = view.findViewById(R.id.btAction_topup_withdraw)
        btBack = view.findViewById(R.id.btBack_topup_withdraw)

        user = User.listUser[indexUser]
        val dompet = user.dompet[indexDompet]

        alterView(dompet)

        btAction.setOnClickListener {
            val nominal = etNominal.text.toString()
            val keterangan = etKeterangan.text.toString()
            val jenis = spinnerJenis.selectedItem.toString()
            val pin = etPIN.text.toString()

            if (nominal.isNotBlank() && keterangan.isNotBlank() && pin.isNotBlank()) {
                if (pin == user.pin) {
                    lateinit var history: History
                    if (statusAction == "topup") {
                        // ADD NOMINAL
                        dompet.saldo += nominal.toInt()
                        // ADD HISTORY
                        history = History(nominal.toInt(), keterangan, jenis, SimpleDateFormat("yyyy MMM dd").format(user.tanggal), 1)
                        Toast.makeText(view.context, "Topup Sukses!", Toast.LENGTH_SHORT).show()
                    } else {
                        // MIN NOMINAL
                        dompet.saldo -= nominal.toInt()
                        // ADD HISTORY
                        history = History(nominal.toInt(), keterangan, jenis, SimpleDateFormat("yyyy MMM dd").format(user.tanggal), -1)
                        Toast.makeText(view.context, "Withdraw Sukses!", Toast.LENGTH_SHORT).show()
                    }
                    user.history.add(history)
                    dompet.addHistory(history)
                    toDompet()
                } else {
                    Toast.makeText(view.context, "PIN salah!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(view.context, "Field kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        btBack.setOnClickListener {
            toDompet()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun alterView(dompet: Dompet) {
        val number = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        tvNama.text = dompet.nama
        tvTotal.text = "Total Asset:\n${number.format(dompet.saldo)},00"
        if (statusAction == "topup") {
            tvWithdraw.isVisible = false
            tvTitleStatus.text = "TAMBAH ASSET"
            btAction.text = "TOPUP"
        } else {
            tvWithdraw.isVisible = true
            tvTitleStatus.text = "WITHDRAW ASSET"
            btAction.text = "WITHDRAW"
        }
    }

    private fun toDompet() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.frame_home, FragmentDompet(indexUser))
            .commit()
    }
}