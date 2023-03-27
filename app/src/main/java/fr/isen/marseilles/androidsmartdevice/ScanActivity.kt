package fr.isen.marseilles.androidsmartdevice

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScanActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        val scanText = findViewById<TextView>(R.id.scanText)
        scanText.text = getString(R.string.scan_text)
        val imageView = findViewById<ImageView>(R.id.imageViewPlay)
        val scanprogressBar = findViewById<ProgressBar>(R.id.progressbarScan)
        var isPlaying = false
        scanprogressBar.visibility = View.INVISIBLE
        imageView.setOnClickListener {
            val pauseImage = this.resources.getDrawable(R.drawable.pause, null)
            val playImage = resources.getDrawable(R.drawable.play, null)

            if (!isPlaying) {
                scanText.text=getString(R.string.onchangetext)
                imageView.setImageDrawable(pauseImage)
                scanprogressBar.visibility = View.VISIBLE
                scanprogressBar.isIndeterminate = true
                isPlaying = true

            } else {
                scanText.text=getString(R.string.scan_text)
                imageView.setImageDrawable(playImage)
                scanprogressBar.isIndeterminate = false
                isPlaying = false
            }
        }

        val myrecycler : RecyclerView = findViewById(R.id.recyclerviewScan)
        // set all layout of the recycler view
        myrecycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)


        val items = fetchData()
        // set adaptor
        val adaptor = RVAdaptor(items)
        myrecycler.adapter = adaptor




    }
    fun fetchData() : ArrayList<String>{
        val list = ArrayList<String>()
        for (i in 0 until 20){
            list.add("Item $i")
        }
        return list
    }

}
