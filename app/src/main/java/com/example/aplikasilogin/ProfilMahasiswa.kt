package com.example.aplikasilogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ProfilMahasiswa : AppCompatActivity() {
    private lateinit var editProfileLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_mahasiswa)

        // Inisialisasi Objek
        var tvNIM = findViewById<TextView>(R.id.txtNIM)
        var tvNama = findViewById<TextView>(R.id.txtNama)
        var tvEmail = findViewById<TextView>(R.id.txtEmail)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnKeluar = findViewById<Button>(R.id.btnKeluar)

        // Ambil Data Intent
        val dataNIM = intent.getStringExtra("nim").toString()
        val dataNama = intent.getStringExtra("nama").toString()
        val dataEmail = intent.getStringExtra("email").toString()

        // Tampilkan Data Intent
        tvNIM.text = dataNIM
        tvNama.text = dataNama
        tvEmail.text = dataEmail

        // Nambah ini Inisialisasi ActivityResultLauncher
        editProfileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val resultIntent = result.data
                val updatedNama = resultIntent?.getStringExtra("updated_nama")
                val updatedEmail = resultIntent?.getStringExtra("updated_email")

                // Update TextView dengan data yang diperbarui
                tvNIM.text = dataNIM
                tvNama.text = updatedNama
                tvEmail.text = updatedEmail

            }
        }

        // Button Ubah
        btnEdit.setOnClickListener {
            val intent = Intent(this, EditProfil::class.java)
            intent.putExtra("nim", dataNIM)
            intent.putExtra("nama", dataNama)
            intent.putExtra("email", dataEmail)
           startActivity(intent)
        }

        // Button Keluar
        btnKeluar.setOnClickListener {
            finish()
        }
    }
}
