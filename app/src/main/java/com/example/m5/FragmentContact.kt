package com.example.m5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentContact(
    private val indexUser: Int
) : Fragment() {

    lateinit var rvContact: RecyclerView
    lateinit var btAdd: Button

    lateinit var user: User
    lateinit var adapter: ContactAdapter
    var arrayKontak: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvContact = view.findViewById(R.id.rv_contact)
        btAdd = view.findViewById(R.id.btAdd_contact)

        user = User.listUser[indexUser]

        user.contact.forEach {
            arrayKontak.add(it.name)
        }
        adapter = ContactAdapter(arrayKontak)
        rvContact.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        rvContact.adapter = adapter
        rvContact.layoutManager = LinearLayoutManager(view.context)
        adapter.onClickListener = fun (position: Int) {
            user.contact.removeAt(position)
            adapter.notifyItemRemoved(position)
            Toast.makeText(view.context, "Berhasil menghapus kontak!", Toast.LENGTH_SHORT).show()
        }
        
        btAdd.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.frame_home, FragmentAddContact(indexUser))
                .commit()
        }
    }
}