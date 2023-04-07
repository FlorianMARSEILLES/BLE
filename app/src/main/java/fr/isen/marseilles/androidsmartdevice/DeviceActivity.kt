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
import android.bluetooth.*
import android.widget.CheckBox
import org.w3c.dom.Text
import java.util.*

class DeviceActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "MissingPermission")
    private var bluetoothGatt: BluetoothGatt? = null
    private val serviceUUID = UUID.fromString("0000feed-cc7a-482a-984a-7f2ed5b3e58f")
    private val characteristicLedUUID = UUID.fromString("0000abcd-8e22-4541-9d4c-21edae82ed19")
    private val characteristicButtonUUID = UUID.fromString("00001234-8e22-4541-9d4c-21edae82ed19")
    //private var cptClick=0 //utilisé pour la partie compteur que je n'ai pas réussi à achever
    @SuppressLint("MissingPermission", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        val textProgressing = findViewById<TextView>(R.id.textConnexionInProgress)

        val str = intent.getParcelableExtra<BluetoothDevice>("device")
        textProgressing.text = "Connexion à $str"

        val bluetoothDevice : BluetoothDevice? = intent.getParcelableExtra("device")

        bluetoothGatt = bluetoothDevice?.connectGatt(this, false, bluetoothGattCallback)
        setLedColor()
        //bluetoothGatt?.connect()
    }
    @SuppressLint("MissingPermission")
    override fun onStop(){
        super.onStop()
        bluetoothGatt?.close()
    }
    @SuppressLint("MissingPermission")
    private fun setLedColor(){

        val iconLED1 = findViewById<ImageView>(R.id.iconLED1)
        val iconLED2 = findViewById<ImageView>(R.id.iconLED2)
        val iconLED3 = findViewById<ImageView>(R.id.iconLED3)
        iconLED1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
        iconLED2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
        iconLED3.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))

        iconLED1.setOnClickListener {
            val characteristic = bluetoothGatt?.getService(serviceUUID)?.getCharacteristic(characteristicLedUUID)
            iconLED1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            iconLED2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            iconLED3.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            if(iconLED1.imageTintList ==ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_deep_teal_200))){
                iconLED1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
                characteristic?.value = byteArrayOf(0x00)
                bluetoothGatt?.writeCharacteristic(characteristic)

                }
            else{
                iconLED1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_deep_teal_200))
                characteristic?.value = byteArrayOf(0x01)
                bluetoothGatt?.writeCharacteristic(characteristic)

            }
        }

        iconLED2.setOnClickListener {
            val characteristic = bluetoothGatt?.getService(serviceUUID)?.getCharacteristic(characteristicLedUUID)
            iconLED1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            iconLED2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            iconLED3.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            if(iconLED2.imageTintList ==ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_deep_teal_200))){
                iconLED2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
                characteristic?.value = byteArrayOf(0x00)
                bluetoothGatt?.writeCharacteristic(characteristic)

            }
            else{
                iconLED2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_deep_teal_200))
                characteristic?.value = byteArrayOf(0x02)
                bluetoothGatt?.writeCharacteristic(characteristic)

            }
        }

        iconLED3.setOnClickListener {
            val characteristic = bluetoothGatt?.getService(serviceUUID)?.getCharacteristic(characteristicLedUUID)
            iconLED1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            iconLED2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            iconLED3.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            if(iconLED3.imageTintList ==ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_deep_teal_200))){
                iconLED3.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
                characteristic?.value = byteArrayOf(0x00)
                bluetoothGatt?.writeCharacteristic(characteristic)

            }
            else{
                iconLED3.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, androidx.appcompat.R.color.material_deep_teal_200))
                characteristic?.value = byteArrayOf(0x03)
                bluetoothGatt?.writeCharacteristic(characteristic)

            }
        }

    }
    private val bluetoothGattCallback = object: BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if(newState == BluetoothProfile.STATE_CONNECTED) {
                runOnUiThread{
                    displayContentWhenConnected()
                }
                bluetoothGatt?.discoverServices()
            }
        }
        @SuppressLint("MissingPermission")
        override fun onServicesDiscovered(bluetoothGatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(bluetoothGatt, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
// Enable notifications for the desired characteristic
                val characteristicButton3 = bluetoothGatt?.getService(serviceUUID)?.getCharacteristic(characteristicButtonUUID)
                bluetoothGatt?.setCharacteristicNotification(characteristicButton3, true)
                characteristicButton3?.descriptors?.forEach { descriptor ->
                    descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                    bluetoothGatt.writeDescriptor(descriptor)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
            super.onCharacteristicChanged(gatt, characteristic)
            val textB = findViewById<TextView>(R.id.textButton)
            textB.text = "Vous n'avez pas cliqué sur un boutton "
            if (characteristic.uuid == characteristicButtonUUID) {
                val value = characteristic.value
                val clicks = value[0].toInt()
                runOnUiThread {
                    textB.text = "Nombre de click: ${clicks.toString()}"
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun displayContentWhenConnected() {
        val iconLED1 = findViewById<ImageView>(R.id.iconLED1)
        val iconLED2 = findViewById<ImageView>(R.id.iconLED2)
        val iconLED3 = findViewById<ImageView>(R.id.iconLED3)
        val textB = findViewById<TextView>(R.id.textButton)
        textB.text = "Vous n'avez pas cliqué sur un boutton "

        val textNL = findViewById<TextView>(R.id.textNL)
        textNL.text = "Abonnez-vous a cette NewsLetter"

        val check = findViewById<CheckBox>(R.id.checkBox)
        val textProgressing = findViewById<TextView>(R.id.textConnexionInProgress)
        val str = intent.getParcelableExtra<BluetoothDevice>("device")
        textProgressing.text = "Connecté à $str"

        check.isVisible = true
        iconLED1.isVisible = true
        iconLED2.isVisible = true
        iconLED3.isVisible = true
        val progress = findViewById<ProgressBar>(R.id.progressBarLoading)
        progress.isVisible = false
        textB.isVisible = true


    }


}