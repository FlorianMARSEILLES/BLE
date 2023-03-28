package fr.isen.marseilles.androidsmartdevice

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVAdaptor(private val items : ArrayList<BluetoothDevice>, var onDeviceClickListener: (BluetoothDevice) -> Unit) : RecyclerView.Adapter<RVAdaptor.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //textView view
        val textView : TextView = itemView.findViewById(R.id.textrc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdaptor.ViewHolder {
        // create a viewHolder every time it needed
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_rc,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RVAdaptor.ViewHolder, position: Int) {
        // bind data with the viewHolder
        holder.textView.text = items[position].address
        holder.itemView.setOnClickListener{
            onDeviceClickListener(items[position])
        }
    }

    override fun getItemCount(): Int {
        //return size of items
        return items.size
    }


    fun addDevice(device: BluetoothDevice) {
        var shouldAddDevice = true
        items.forEachIndexed { index, element ->
            if (element.address == device.address) {
                items[index] = device
                shouldAddDevice= false
                }
            }
        if(shouldAddDevice){
            items.add(device)
        }

    }
}