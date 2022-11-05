package com.example.m5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentHistory(
    private val indexUser: Int
) : Fragment() {

    lateinit var spinnerDompet: Spinner
    lateinit var rvHistory: RecyclerView

    var dompet: ArrayList<Dompet> = ArrayList()
    var arraySpinner: ArrayList<String> = ArrayList()
    var arrayHistory: ArrayList<History> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerDompet = view.findViewById(R.id.spinnerDompet_history)
        rvHistory = view.findViewById(R.id.rv_history)

        dompet = User.listUser[indexUser].dompet

        dompet.forEach {
            arraySpinner.add(it.nama)
            it.history.forEach { history ->
                arrayHistory.add(history)
            }
        }

        spinnerDompet.adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, arraySpinner)

        val adapterHistory = HistoryAdapter(arrayHistory)
        rvHistory.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        rvHistory.adapter = adapterHistory
        rvHistory.layoutManager = LinearLayoutManager(view.context)

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
    }
}