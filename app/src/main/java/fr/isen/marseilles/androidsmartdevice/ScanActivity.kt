package fr.isen.marseilles.androidsmartdevice

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager

class ScanActivity : AppCompatActivity() {

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }
    private lateinit var adapter: RVAdaptor

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions.all { it.value }){
                scanBLEDevices()
            }
        }



    private var scanning = false
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("MissingPermission")
    fun stopScan(){
        super.onStop()
        if(bluetoothAdapter.isEnabled && allPermissionGranted()){
            adapter.clearData()
            scanning = false
            bluetoothAdapter.bluetoothLeScanner?.stopScan(leScanCallback)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewScan)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = RVAdaptor(arrayListOf()) {
            val intent = Intent(this, DeviceActivity::class.java)
            intent.putExtra("device",it)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        if (bluetoothAdapter.isEnabled) {
            initToggleAction()
        } else {
            handleBLENotAvailable()
        }


        // set all layout of the recycler view

    }


    // private fun action(){}
    private fun scandeviceWithPermission() {
        if(allPermissionGranted())
        {
            scanBLEDevices()

        }else{

            requestPermissionLauncher.launch(getAllPermissions())
        }
    }

    private fun handleBLENotAvailable() {
        Toast.makeText(this, "Bluetooth is not available on this device", Toast.LENGTH_SHORT).show()
        finish()
    }
    companion object{
        val PERMISSION_REQUEST_CODE =1
        val SCAN_PERIOD : Long = 10000000
    }


    private fun allPermissionGranted(): Boolean {
        val allPermissions = getAllPermissions()
        return allPermissions.all {
            ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun getAllPermissions(): Array<String>{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT
            )
        } else {
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )

        }
    }



    // Stops scanning after 10 seconds.


    @SuppressLint("MissingPermission")
    private fun scanBLEDevices() {

        if (!scanning) { // Stops scanning after a pre-defined scan period.
            handler.postDelayed({
                scanning = false
                bluetoothAdapter.bluetoothLeScanner?.stopScan(leScanCallback)
                togglePlayPauseAction()
            }, SCAN_PERIOD)
            scanning = true
            bluetoothAdapter.bluetoothLeScanner?.startScan(leScanCallback)
        } else {
            scanning = false
            bluetoothAdapter.bluetoothLeScanner?.stopScan(leScanCallback)
        }

    }



    private val leScanCallback: ScanCallback = object : ScanCallback() {
        @SuppressLint("NotifyDataSetChanged")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            Log.d("Scan", "result : $result")
            super.onScanResult(callbackType, result)
            adapter.addDevice(result.device)
            adapter.notifyDataSetChanged()
        }
    }

    private var isPlaying = false
    private fun togglePlayPauseAction() {
        val scanText = findViewById<TextView>(R.id.scanText)
        val imageView = findViewById<ImageView>(R.id.imageViewPlay)
        val scanprogressBar = findViewById<ProgressBar>(R.id.progressbarScan)

        if (isPlaying) {
            // Pause button clicked
            scanText.text = getString(R.string.scan_text)
            imageView.setImageResource(R.drawable.play)
            scanprogressBar.isIndeterminate = false
            isPlaying = false
            stopScan()
        } else {
            // Play button clicked
            scanText.text = getString(R.string.onchangetext)
            imageView.setImageResource(R.drawable.pause)
            scanprogressBar.isIndeterminate = true
            scandeviceWithPermission()
            isPlaying = true
        }
    }

    private fun initToggleAction(){
        val scanText = findViewById<TextView>(R.id.scanText)
        scanText.text = getString(R.string.scan_text)

        val imageView = findViewById<ImageView>(R.id.imageViewPlay)
        val scanprogressBar = findViewById<ProgressBar>(R.id.progressbarScan)
        scanprogressBar.visibility = View.VISIBLE
        scanprogressBar.isIndeterminate = false
        imageView.setOnClickListener {
            togglePlayPauseAction()
        }
    }
    //salut
    //lol


}



