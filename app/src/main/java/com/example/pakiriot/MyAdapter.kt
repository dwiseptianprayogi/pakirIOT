package com.example.pakiriot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (private val fpList: ArrayList<dataSensor>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val context: Context? = null
    var list: java.util.ArrayList<dataSensor> = java.util.ArrayList<dataSensor>()
    fun setItems(emp: java.util.ArrayList<dataSensor>) {
        list.addAll(emp)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_history_data, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        val currentItem = fpList[position]

        holder.historyWaktu.text = currentItem.historyWaktu
        holder.historySaldo.text = currentItem.historySaldo
//        holder.co2.text = currentItem.gas
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val historyWaktu = itemView.findViewById<TextView>(R.id.tvWaktuRiwayatParkitVal)
//        val status = itemView.findViewById<TextView>(R.id.tvStatusRiwayatPemakaianVal)
        val historySaldo = itemView.findViewById<TextView>(R.id.tvSaldoRiwayatParkitval)
    }

    override fun getItemCount(): Int {
        return fpList.size
    }
//
//    fun updateData(viewModels: ArrayList<ViewModel?>?) {
//        item.clear()
//        item.addAll(viewModels)
//        notifyDataSetChanged()
//    }
}