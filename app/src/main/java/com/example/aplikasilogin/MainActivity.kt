package com.example.aplikasilogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DBHelper
    fun getMD5Hash(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bytes = md.digest(input.toByteArray())
        // Konversi ke Hexa
        val hexString = StringBuilder()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return hexString.toString()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnkeluar: Button = findViewById(R.id.btnkeluar)
        val btnMasuk: Button = findViewById(R.id.btnMasuk)
        val btnDaftar: Button = findViewById(R.id.btnDaftar)
        var inputNIM = findViewById<EditText>(R.id.inputNIM)
        var inputPassword = findViewById<EditText>(R.id.inputPassword)

        databaseHelper = DBHelper(this)

        btnDaftar.setOnClickListener {
            // Buat variabel intent
            val intent:Intent = Intent(this,Daftaractivity::class.java)
            // Jalankan Intent
            startActivity(intent)

        }

        btnMasuk.setOnClickListener {
            val dataNIM: String = inputNIM.text.toString()
            val dataPassword: String = inputPassword.text.toString()

            if (dataNIM.equals("") || dataPassword.equals("")) {
                Toast.makeText(applicationContext, dataNIM + " " + dataPassword, Toast.LENGTH_SHORT)
                    .show()
            } else {
                val hashedPassword = getMD5Hash(dataPassword)  // iki
                val query =
                    "SELECT * FROM TBL_MHS WHERE nim ='" + dataNIM + "'AND password='" + dataPassword + "'"



                val db = databaseHelper.readableDatabase
                val cursor = db.rawQuery(query, null)

                val result = cursor.moveToFirst()
                if (result == true) {
                    val dataNama = cursor.getString(cursor
                        .getColumnIndexOrThrow("nama"))
                    val dataEmail = cursor.getString(cursor
                        .getColumnIndexOrThrow("email"))
                    // Kirimkan data via Intent
                    var intent:Intent = Intent(this,ProfilMahasiswa::class.java)
                    intent.putExtra("nim",dataNIM)
                    intent.putExtra("nama",dataNama)
                    intent.putExtra("email",dataEmail)
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        applicationContext, "login gagal",
                        Toast.LENGTH_LONG
                    ).show()
                    inputNIM.setText("")
                    inputPassword.setText("")
                }
            }
    }



            btnkeluar.setOnClickListener {
                finish()
            }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
