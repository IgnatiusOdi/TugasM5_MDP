package com.example.m5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast

class FragmentTambahDompet(
    private val indexUser: Int
) : Fragment() {

    lateinit var etNama: EditText
    lateinit var etSaldo: EditText
    lateinit var rgTipe: RadioGroup
    lateinit var etPIN: EditText
    lateinit var btTambah: Button
    lateinit var btBack: Button

    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tambah_dompet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etNama = view.findViewById(R.id.etNama_dompet)
        etSaldo = view.findViewById(R.id.etSaldo_dompet)
        rgTipe = view.findViewById(R.id.rgTipe_dompet)
        etPIN = view.findViewById(R.id.etPIN_dompet)
        btTambah = view.findViewById(R.id.btTambah_dompet)
        btBack = view.findViewById(R.id.btBack_dompet)

        user = User.listUser[indexUser]

        btTambah.setOnClickListener {
            val nama = etNama.text.toString()
            val saldo = etSaldo.text.toString()
            val tipe = when (rgTipe.checkedRadioButtonId) {
                R.id.rbSpending_dompet -> {
                    "Spending Dompet"
                }
                else -> {
                    "Saving Pocket"
                }
            }
            val pin = etPIN.text.toString()

            if (nama.isNotBlank() && saldo.isNotBlank() && pin.isNotBlank()) {
                if (pin == user.pin) {
                    user.dompet.add(Dompet(etNama.text.toString(), etSaldo.text.toString().toInt(), tipe))
                    Toast.makeText(view.context, "Berhasil menambahkan dompet", Toast.LENGTH_SHORT).show()
                    toDompet()
                } else {
                    Toast.makeText(view.context, "PIN salah!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(view.context, "Tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        btBack.setOnClickListener {
            toDompet()
        }
    }

    private fun toDompet() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.frame_home, FragmentDompet(indexUser))
            .commit()
    }
}