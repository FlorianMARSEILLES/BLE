package fr.isen.marseilles.androidsmartdevice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mainTitle = findViewById<TextView>(R.id.mainWelcome)
        mainTitle.text = getString(R.string.main_title)

        var mainDesc = findViewById<TextView>(R.id.mainDescription)
        mainDesc.text = getString(R.string.main_description)

        var mainButton = findViewById<Button>(R.id.mainButton)
        mainButton.text = getString(R.string.main_button_text)
        mainButton.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
        }


    }
}