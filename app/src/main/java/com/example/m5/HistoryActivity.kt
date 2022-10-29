package com.example.m5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {
    lateinit var spinnerDompet: Spinner
    lateinit var rvHistory: RecyclerView
    lateinit var btBack: Button

    var dompet: ArrayList<Dompet> = ArrayList()
    var arraySpinner: ArrayList<String> = ArrayList()
    var arrayHistory: ArrayList<History> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        spinnerDompet = findViewById(R.id.spinnerDompet_history)
        rvHistory = findViewById(R.id.rv_history)
        btBack = findViewById(R.id.btBack_history)

        val indexUser = intent.getIntExtra("indexUser", -1)
        dompet = User.listUser[indexUser].dompet
        dompet.forEach {
            arraySpinner.add(it.nama)
        }

        dompet[0].history.forEach {
            arrayHistory.add(it)
        }

        val adapterDompet = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arraySpinner)
        spinnerDompet.adapter = adapterDompet

        val adapterHistory = HistoryAdapter(arrayHistory, R.layout.layout_history,this)
        rvHistory.adapter = adapterHistory
        rvHistory.layoutManager = LinearLayoutManager(this)

        spinnerDompet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                arrayHistory.clear()
                dompet[p2].history.forEach {
                    arrayHistory.add(it)
                }
                adapterHistory.notifyDataSetChanged()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        btBack.setOnClickListener {
            finish()
        }
    }
}