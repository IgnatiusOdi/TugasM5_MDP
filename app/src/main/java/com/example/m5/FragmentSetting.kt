package com.example.m5

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat

class FragmentSetting(
    private val indexUser: Int
) : Fragment() {

    lateinit var tvTanggal: TextView
    lateinit var etTanggal: EditText
    lateinit var etPINBaru: EditText
    lateinit var etPINSekarang: EditText
    lateinit var btSave: Button

    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTanggal = view.findViewById(R.id.tvTanggal_setting)
        etTanggal = view.findViewById(R.id.etTanggal_setting)
        etPINBaru = view.findViewById(R.id.etPINBaru_setting)
        etPINSekarang = view.findViewById(R.id.etPINSekarang_setting)
        btSave = view.findViewById(R.id.btSave_setting)

        user = User.listUser[indexUser]

        tvTanggal.text = "Tanggal Saat Ini :\n${SimpleDateFormat("yyyy MMM dd").format(user.tanggal)}"

        btSave.setOnClickListener {
            if (etTanggal.text.isNotBlank() && etPINBaru.text.isNotBlank() && etPINSekarang.text.isNotBlank()) {
                if (etPINSekarang.text.toString() == user.pin) {
                    val tanggal = SimpleDateFormat("yyyy/MM/dd").parse(etTanggal.text.toString())
                    if (tanggal!! > user.tanggal) {
                        user.updateTanggal(tanggal)
                        user.pin = etPINBaru.text.toString()
                        Toast.makeText(view.context, "Berhasil memperbarui tanggal dan pin!", Toast.LENGTH_SHORT).show()
                        clearField()
                    } else {
                        Toast.makeText(view.context, "Tanggal invalid!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(view.context, "PIN salah!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(view.context, "Field kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearField() {
        etTanggal.setText("")
        etPINBaru.setText("")
        etPINSekarang.setText("")
    }
}