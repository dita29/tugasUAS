package com.example.crud_api

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.btnInfo
import kotlinx.android.synthetic.main.activity_main.btnTambah
import kotlinx.android.synthetic.main.activity_main.rvCategory
import kotlinx.android.synthetic.main.activity_main.tvDataNotFound
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity(), CrudView {
    private lateinit var presenter: Presenter
    private lateinit var tvTanggal: TextView
    private lateinit var etSearch: EditText
    private lateinit var adapter: DataAdapter
    private val dataList: MutableList<DataItem> = mutableListOf()
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvTanggal = findViewById(R.id.tvTanggal)

        updateTanggal()

        etSearch = findViewById(R.id.etSearch)

        btnInfo.setOnClickListener {
            showInfoPopup()
        }

        presenter = Presenter(this)
        presenter.getData()
        setupSearch()

        adapter = DataAdapter(dataList, object : DataAdapter.onClickItem {
            override fun clicked(item: DataItem?) {
                val bundle = Bundle()
                bundle.putSerializable("dataItem", item)
                val intent = Intent(applicationContext, UpdateData::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            override fun delete(item: DataItem?) {
                item?.mhsId?.let { id ->
                    presenter.hapusData(this@MainActivity, id)
                    presenter.getData()
                }
            }
        })

        rvCategory.adapter = adapter

        btnTambah.setOnClickListener{
            startActivity(Intent(applicationContext, UpdateData::class.java))
            finish()
        }
    }

    override fun onSuccessGet(data: List<DataItem>?) {
        dataList.clear()
        data?.let { dataList.addAll(it) }
        adapter.notifyDataSetChanged()
    }

    override fun onFailedGet(msg: String) {
        // Handle the failure case
    }

    override fun onSuccessDelete(msg: String) {
        presenter.getData() // Refresh data after deleting
    }

    override fun onErrorDelete(msg: String) {
        Toast.makeText(this, "Delete tidak berhasil", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessAdd(msg: String) {
        // Handle the success case
    }

    override fun onErrorAdd(msg: String) {
        // Handle the error case
    }

    override fun onSuccessUpdate(msg: String) {
        // Handle the success case
    }

    override fun onErrorUpdate(msg: String) {
        // Handle the error case
    }

    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                search(s.toString())
            }
        })
    }

    private fun search(query: String) {
        val filteredData = dataList.filter { data ->
            data.mhsName?.contains(query, ignoreCase = true) == true ||
                    data.mhsKembali?.contains(query, ignoreCase = true) == true ||
                    data.mhsPinjam?.contains(query, ignoreCase = true) == true ||
                    data.mhsNameBook?.contains(query, ignoreCase = true) == true
        }
        if (filteredData.isEmpty()) {
            tvDataNotFound.visibility = View.VISIBLE
        } else {
            tvDataNotFound.visibility = View.GONE
        }
//        if (filteredData.isEmpty()) {
//            val rootView = findViewById<View>(android.R.id.content)
//            Snackbar.make(rootView, "Data tidak ditemukan", Snackbar.LENGTH_SHORT).show()
//        }

        adapter.setData(filteredData)
        adapter.notifyDataSetChanged()
    }

    private fun showInfoPopup() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.popup, null)
        dialogBuilder.setView(dialogView)

        val popupText = dialogView.findViewById<TextView>(R.id.popupText)
        popupText.text = "Bagi pengunjung yang mau meminjam buku, harap inputkan nama, tanggal peminjaman, juga tanggal pengembalian dan jangan lupa untuk mengembalikan tepat pada waktunya."

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun updateTanggal() {
        val dateTimeFormat = SimpleDateFormat("d MMMM yyyy, HH:mm:ss", Locale.getDefault())

        handler.post(object : Runnable {
            override fun run() {
                val currentDateTime = dateTimeFormat.format(Date())
                tvTanggal.text = "$currentDateTime"

                handler.postDelayed(this, 1000) // Update every second
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Clean up the handler when the activity is destroyed
    }
}
