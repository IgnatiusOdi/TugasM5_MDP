package com.example.m5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*

class LoginActivity : AppCompatActivity() {
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var btLogin: Button
    lateinit var btToRegister: Button

    private var indexUser = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsernameLogin)
        etPassword = findViewById(R.id.etPasswordLogin)
        btLogin = findViewById(R.id.btLogin)
        btToRegister = findViewById(R.id.btToRegister)

        // DEBUG
        User.listUser.add(User("David", "a", "a", "1234", "1589762555", Date()))
        val dompet = User.listUser[0].dompet
        dompet.add(Dompet("Business Pocket",15000000,"Spending Pocket"))
        dompet.add(Dompet("Online Payment Pocket",15000000,"Spending Pocket"))
        dompet.add(Dompet("Tabungan Pensiun",60000000,"Saving Pocket"))
        val history = User.listUser[0].dompet[0].history
        history.add(History(3000000,"Tabungan dari gaji","Umum","2022 Oct 21",1))
        history.add(History(5000000,"Pemberian orang tua","Sumbangan","2022 Oct 21",1))
        history.add(History(1000000,"Bonus kerja","Umum","2022 Oct 21",1))
        history.add(History(6000000,"Bayar uang kuliah","Pendikikan","2022 Oct 21",-1))
        history.add(History(3000000,"Bunga Deposito","Umum","2022 Oct 21",1))
        history.add(History(2000000,"Sumbangan","Sumbangan","2022 Oct 21",-1))
        history.add(History(10000000,"Cicilan Iphone","Belanja","2022 Oct 21",-1))

        btLogin.setOnClickListener {
            indexUser = loginCheck()
            if (indexUser != -1) {
                val intent = Intent(this, HomepageActivity::class.java)
                intent.putExtra("indexUser", indexUser)
                startActivity(intent)
            }
        }

        btToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginCheck(): Int {
        if (etUsername.text.isBlank() || etPassword.text.isBlank()) {
            // FIELD KOSONG
            Toast.makeText(this, "Field kosong!", Toast.LENGTH_SHORT).show()
        } else {
            // CEK USER
            for (user in User.listUser) {
                if (user.username == etUsername.text.toString()) {
                    // CEK PASSWORD
                    return if (user.password == etPassword.text.toString()) {
                        User.listUser.indexOf(user)
                    } else {
                        // PASSWORD SALAH
                        Toast.makeText(this, "Password salah!", Toast.LENGTH_SHORT).show()
                        -1
                    }
                }
            }
            // USER TIDAK DITEMUKAN
            Toast.makeText(this, "User tidak ditemukan!", Toast.LENGTH_SHORT).show()
        }
        return -1
    }
}