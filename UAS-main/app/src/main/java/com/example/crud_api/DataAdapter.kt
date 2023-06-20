package com.example.crud_api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.data.view.btnHapus
import kotlinx.android.synthetic.main.data.view.btnPerpanjang
import kotlinx.android.synthetic.main.data.view.nama
import kotlinx.android.synthetic.main.data.view.namaBuku
import kotlinx.android.synthetic.main.data.view.tglKembali
import kotlinx.android.synthetic.main.data.view.tglPinjam

class DataAdapter(private var data: List<DataItem>?, private val click: onClickItem) :
    RecyclerView.Adapter<DataAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.onBind(data?.get(position))
        holder.itemView.btnPerpanjang.setOnClickListener {
            click.clicked(data?.get(position))
        }
        holder.itemView.btnHapus.setOnClickListener {
            click.delete(data?.get(position))
        }
    }

    fun setData(newDataList: List<DataItem>) {
        data = newDataList
        notifyDataSetChanged()
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(dataItem: DataItem?) {
            itemView.nama.text = dataItem?.mhsName
            itemView.tglPinjam.text = dataItem?.mhsPinjam
            itemView.tglKembali.text = dataItem?.mhsKembali
            itemView.namaBuku.text = dataItem?.mhsNameBook
        }
    }

    interface onClickItem {
        fun clicked(item: DataItem?)
        fun delete(item: DataItem?)
    }
}
