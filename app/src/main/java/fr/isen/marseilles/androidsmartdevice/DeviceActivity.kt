package fr.isen.marseilles.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.w3c.dom.Text

class DeviceActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        var textProgressing = findViewById<TextView>(R.id.textConnexionInProgress)

        val intent = intent

        val str = intent.getParcelableExtra<BluetoothDevice>("device")
        textProgressing.text = "Connexion à $str"

        val iconLED1 = findViewById<ImageView>(R.id.iconLED1)
        val iconLED2 = findViewById<ImageView>(R.id.iconLED2)
        val iconLED3 = findViewById<ImageView>(R.id.iconLED3)

        setLedColor()
    }

    private fun setLedColor(){
        val iconLED1 = findViewById<ImageView>(R.id.iconLED1)
        val iconLED2 = findViewById<ImageView>(R.id.iconLED2)
        val iconLED3 = findViewById<ImageView>(R.id.iconLED3)
        iconLED1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
        iconLED2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
        iconLED3.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))

        iconLED1.setOnClickListener {
            iconLED1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_deep_teal_200))
            iconLED2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            iconLED3.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
        }

        iconLED2.setOnClickListener {
            iconLED1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            iconLED2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_deep_teal_200))
            iconLED3.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
        }

        iconLED3.setOnClickListener {
            iconLED1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            iconLED2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            iconLED3.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_deep_teal_200))
        }

    }
}