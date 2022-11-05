package com.example.m5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class FragmentAddContact(
    private val indexUser: Int
) : Fragment() {
    
    lateinit var etNomorRekening: EditText
    lateinit var etPIN: EditText
    lateinit var btAdd: Button
    lateinit var btBack: Button

    lateinit var user: User
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etNomorRekening = view.findViewById(R.id.etNomorRekening_add_contact)
        etPIN = view.findViewById(R.id.etPIN_add_contact)
        btAdd = view.findViewById(R.id.btAdd_add_contact)
        btBack = view.findViewById(R.id.btBack_add_contact)

        user = User.listUser[indexUser]
        
        btAdd.setOnClickListener {
            val norek = etNomorRekening.text.toString()
            val pin = etPIN.text.toString()

            if (norek.isNotBlank() && pin.isNotBlank()) {
                if (pin == user.pin) {
                    val idxTarget = findNomorRekening(norek)
                    if (idxTarget != -1) {
                        user.contact.add(User.listUser[idxTarget])
                        Toast.makeText(view.context, "Berhasil menambahkan kontak!", Toast.LENGTH_SHORT).show()
                        toContact()
                    } else {
                        Toast.makeText(view.context, "Nomor rekening tidak ditemukan!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(view.context, "PIN salah!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(view.context, "Field kosong!", Toast.LENGTH_SHORT).show()
            }
        }
        
        btBack.setOnClickListener {
            toContact()
        }
    }

    private fun findNomorRekening(norek: String): Int {
        return User.listUser.indexOf(User.listUser.find { it.norek == norek })
    }

    private fun toContact() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.frame_home, FragmentContact(indexUser))
            .commit()
    }
}