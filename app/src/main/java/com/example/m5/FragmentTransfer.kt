package com.example.m5

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.text.SimpleDateFormat

class FragmentTransfer(
    private val indexUser: Int
) : Fragment() {

    lateinit var spinnerContact: Spinner
    lateinit var spinnerJenis: Spinner
    lateinit var spinnerDompet: Spinner
    lateinit var etKeterangan: EditText
    lateinit var etJumlah: EditText
    lateinit var etPIN: EditText
    lateinit var btTransfer: Button

    lateinit var user: User
    var arrayContact: ArrayList<String> = ArrayList()
    var arrayDompet: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transfer, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerContact = view.findViewById(R.id.spinnerContact_transfer)
        spinnerJenis = view.findViewById(R.id.spinnerJenis_transfer)
        spinnerDompet = view.findViewById(R.id.spinnerDompet_transfer)
        etKeterangan = view.findViewById(R.id.etKeterangan_transfer)
        etJumlah = view.findViewById(R.id.etJumlah_transfer)
        etPIN = view.findViewById(R.id.etPIN_transfer)
        btTransfer = view.findViewById(R.id.btTransfer)

        user = User.listUser[indexUser]

        user.contact.forEach {
            arrayContact.add(it.toString())
        }

        user.dompet.forEach {
            arrayDompet.add(it.nama)
        }

        spinnerContact.adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, arrayContact)
        spinnerDompet.adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, arrayDompet)

        btTransfer.setOnClickListener {
            if (spinnerContact.selectedItemPosition != -1) {
                if (etKeterangan.text.isNotBlank() && etJumlah.text.isNotBlank() && etPIN.text.isNotBlank()) {
                    val kontak = user.contact[spinnerContact.selectedItemPosition]
                    val jenis = spinnerJenis.selectedItem.toString()
                    val dompet = user.dompet[spinnerDompet.selectedItemPosition]
                    val keterangan = etKeterangan.text.toString()
                    val jumlah = etJumlah.text.toString().toInt()
                    val pin = etPIN.text.toString()

                    if (pin == user.pin) {
                        if (jumlah <= dompet.saldo) {
                            // MIN SALDO USER
                            dompet.saldo -= jumlah

                            // ADD HISTORY AND HISTORY DOMPET
                            val history = History(jumlah, keterangan, jenis, SimpleDateFormat("yyyy MM dd").format(user.tanggal), -1, kontak.name)
                            user.history.add(history)
                            dompet.addHistory(history)

                            // ADD HISTORY AND HISTORY DOMPET PENERIMA
                            val historyPenerima = History(jumlah, keterangan, jenis, SimpleDateFormat("yyyy MM dd").format(user.tanggal), 1, user.name)
                            kontak.history.add(historyPenerima)
                            kontak.dompet[0].addHistory(historyPenerima)
                            // ADD SALDO PENERIMA
                            kontak.dompet[0].saldo += jumlah

                            Toast.makeText(view.context, "Transfer berhasil!", Toast.LENGTH_SHORT).show()
                            clearField()
                        } else {
                            Toast.makeText(view.context, "Saldo dompet tidak cukup!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(view.context, "PIN salah!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(view.context, "Field kosong!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(view.context, "Contact kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearField() {
        spinnerContact.setSelection(0)
        spinnerJenis.setSelection(0)
        spinnerDompet.setSelection(0)
        etKeterangan.setText("")
        etJumlah.setText("")
        etPIN.setText("")
    }
}