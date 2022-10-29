package com.example.m5

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class HomepageActivity : AppCompatActivity() {
    lateinit var tvNamaUser: TextView
    lateinit var tvNomorRekening: TextView
    lateinit var tvTanggal: TextView
    lateinit var tvTotalTabungan: TextView
    lateinit var rvDompet: RecyclerView
    lateinit var adapter: DompetAdapter
    lateinit var goToTambah: ActivityResultLauncher<Intent>
    lateinit var goToActionDompet: ActivityResultLauncher<Intent>
    lateinit var goToSettingsActivity: ActivityResultLauncher<Intent>

    lateinit var user: User
    var indexUser = -1
    var total = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        tvNamaUser = findViewById(R.id.tvNamaUser)
        tvNomorRekening = findViewById(R.id.tvNomorRekening)
        tvTanggal = findViewById(R.id.tvTanggal)
        tvTotalTabungan = findViewById(R.id.tvTotalTabungan)
        rvDompet = findViewById(R.id.rv_dompet)

        indexUser = intent.getIntExtra("indexUser", -1)
        user = User.listUser[indexUser]

        tvNamaUser.text = "Hi, ${user.name} !"
        tvNomorRekening.text = "Nomor Rekening\n${user.norek}"
        updateTanggal()
        updateTotalTabungan()

        adapter = DompetAdapter(user.dompet, R.layout.layout_dompet,this)
        rvDompet.adapter = adapter
        rvDompet.layoutManager = GridLayoutManager(this, 2)

        adapter.onClickListener = fun (view: View, position: Int): Unit {
            val popUp = PopupMenu(this, view)
            popUp.menuInflater.inflate(R.menu.dompet_menu, popUp.menu)
            popUp.setOnMenuItemClickListener {
                return@setOnMenuItemClickListener when(it.itemId){
                    R.id.topup->{
                        val intent = Intent(this, TopupWithdrawActivity::class.java)
                        intent.putExtra("indexUser", indexUser)
                        intent.putExtra("indexDompet", position)
                        intent.putExtra("status", "topup")
                        goToActionDompet.launch(intent)
                        true
                    }
                    R.id.withdraw->{
                        val intent = Intent(this, TopupWithdrawActivity::class.java)
                        intent.putExtra("indexUser", indexUser)
                        intent.putExtra("indexDompet", position)
                        intent.putExtra("status", "withdraw")
                        goToActionDompet.launch(intent)
                        true
                    }
                    R.id.delete -> {
                        if (position != 0) {
                            user.dompet[0].saldo += user.dompet[position].saldo
                            adapter.notifyItemChanged(0)
                            user.dompet.removeAt(position)
                            adapter.notifyItemRemoved(position)
                        } else {
                            Toast.makeText(this, "Main Pocket tidak dapat dihapus!", Toast.LENGTH_SHORT).show()
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

        goToTambah = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                res: ActivityResult ->
            if (res.resultCode == RESULT_OK) {
                val data = res.data
                if (data != null) {
                    adapter.notifyItemInserted(user.dompet.lastIndex)
                    updateTotalTabungan()
                }
            }
        }

        goToSettingsActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                res: ActivityResult ->
            if (res.resultCode == RESULT_OK) {
                val data = res.data
                if (data != null) {
                    updateTanggal()
                    adapter.notifyItemRangeChanged(0, adapter.itemCount)
                    updateTotalTabungan()
                }
            }
        }

        goToActionDompet = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                res: ActivityResult ->
            if (res.resultCode == RESULT_OK) {
                val data = res.data
                if (data != null) {
                    val indexDompet = data.getIntExtra("indexDompet", -1)
                    adapter.notifyItemChanged(indexDompet)
                    updateTotalTabungan()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTotalTabungan() {
        total = 0
        for (dompet in user.dompet) {
            total += dompet.saldo
        }
        val locale = Locale("id", "ID")
        val number = NumberFormat.getCurrencyInstance(locale)
        tvTotalTabungan.text = "Total Tabungan :\n${number.format(total)},00"
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun updateTanggal() {
        tvTanggal.text = "Tanggal Saat Ini\n${SimpleDateFormat("yyyy MMM dd").format(user.tanggal)}"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.wallet -> {
                val intent = Intent(this, TambahDompetActivity::class.java)
                intent.putExtra("indexUser", indexUser)
                goToTambah.launch(intent)
            }
            R.id.history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                intent.putExtra("indexUser", indexUser)
                startActivity(intent)
            }
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                intent.putExtra("indexUser", indexUser)
                goToSettingsActivity.launch(intent)
            }
            R.id.logout -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}