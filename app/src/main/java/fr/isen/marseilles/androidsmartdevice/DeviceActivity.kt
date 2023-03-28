package fr.isen.marseilles.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class DeviceActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "MissingPermission")
    private var bluetoothGatt: BluetoothGatt? = null
    @SuppressLint("MissingPermission", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        val textProgressing = findViewById<TextView>(R.id.textConnexionInProgress)

        val intent = intent

        val str = intent.getParcelableExtra<BluetoothDevice>("device")
        textProgressing.text = "Connexion à " + str

        setLedColor()
        val bluetoothDevice : BluetoothDevice? = intent.getParcelableExtra("device")

        val bluetoothGatt = bluetoothDevice?.connectGatt(this, false, bluetoothGattCallback)

        bluetoothGatt?.connect()
    }
    @SuppressLint("MissingPermission")
    override fun onStop(){
        super.onStop()
        bluetoothGatt?.close()
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

    private val bluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                runOnUiThread{
                    displayContentWhenConnected()
                }

            }
        }
    }

    private fun displayContentWhenConnected() {
        val iconLED1 = findViewById<ImageView>(R.id.iconLED1)
        val iconLED2 = findViewById<ImageView>(R.id.iconLED2)
        val iconLED3 = findViewById<ImageView>(R.id.iconLED3)
        iconLED1.isVisible = true
        iconLED2.isVisible = true
        iconLED3.isVisible = true
        val progress = findViewById<ProgressBar>(R.id.progressBarLoading)
        progress.isVisible = false


    }


}