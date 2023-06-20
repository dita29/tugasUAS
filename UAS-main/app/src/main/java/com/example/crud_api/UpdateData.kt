package com.example.crud_api

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_update_data.btnAction
import kotlinx.android.synthetic.main.activity_update_data.editNama
import kotlinx.android.synthetic.main.activity_update_data.editNamaBuku
import kotlinx.android.synthetic.main.activity_update_data.editTanggalPeminjaman
import kotlinx.android.synthetic.main.activity_update_data.editTanggalPengembalian
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Suppress("SENSELESESS_COMPARISON")
class UpdateData : AppCompatActivity(), CrudView {
    private lateinit var presenter: Presenter2
    private lateinit var kembali: EditText
    private lateinit var pinjam: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)

        presenter = Presenter2(this)
        val itemDataItem = intent.getSerializableExtra("dataItem")

        kembali = findViewById(R.id.editTanggalPengembalian)
        pinjam = findViewById(R.id.editTanggalPeminjaman)

        if (itemDataItem == null) {
            btnAction.text = "Tambah"
            btnAction.setOnClickListener() {
                val nama = editNama.text.toString().trim()
                val tanggalPeminjaman = editTanggalPeminjaman.text.toString().trim()
                val tanggalPengembalian = editTanggalPengembalian.text.toString().trim()
                val namaBuku = editNamaBuku.text.toString().trim()

                if (nama.isEmpty()) {
                    editNama.error = "Nama harus diisi"
                } else if (tanggalPeminjaman.isEmpty()) {
                    editTanggalPeminjaman.error = "Tanggal peminjaman harus diisi"
                } else if (tanggalPengembalian.isEmpty()) {
                    editTanggalPengembalian.error = "Tanggal pengembalian harus diisi"
                } else if (namaBuku.isEmpty()) {
                    editNamaBuku.error = "Nama buku harus diisi"
                } else {
                    presenter.addData(
                        nama,
                        convertToDatabaseDateFormat(tanggalPeminjaman),
                        convertToDatabaseDateFormat(tanggalPengembalian),
                        namaBuku
                    )
                }
            }
        } else if (itemDataItem != null) {
            btnAction.text = "Update"
            val item = itemDataItem as DataItem?
            editNama.setText(item?.mhsName.toString())
            editTanggalPeminjaman.setText(convertToDisplayDateFormat(item?.mhsPinjam.toString()))
            editTanggalPengembalian.setText(convertToDisplayDateFormat(item?.mhsKembali.toString()))
            editNamaBuku.setText(item?.mhsNameBook.toString())
            btnAction.setOnClickListener() {
                val nama = editNama.text.toString().trim()
                val tanggalPeminjaman = editTanggalPeminjaman.text.toString().trim()
                val tanggalPengembalian = editTanggalPengembalian.text.toString().trim()
                val namaBuku = editNamaBuku.text.toString().trim()

                if (nama.isEmpty()) {
                    editNama.error = "Nama harus diisi"
                } else if (tanggalPeminjaman.isEmpty()) {
                    editTanggalPeminjaman.error = "Tanggal peminjaman harus diisi"
                } else if (tanggalPengembalian.isEmpty()) {
                    editTanggalPengembalian.error = "Tanggal pengembalian harus diisi"
                } else if (namaBuku.isEmpty()) {
                    editNamaBuku.error = "Nama buku harus diisi"
                } else {
                    presenter.updateData(
                        item?.mhsId ?: "",
                        nama,
                        convertToDatabaseDateFormat(tanggalPeminjaman),
                        convertToDatabaseDateFormat(tanggalPengembalian),
                        namaBuku
                    )
                    finish()
                }
            }
        }
    }

    override fun onSuccessAdd(msg: String) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onErrorAdd(msg: String) {
    }

    override fun onSuccessUpdate(msg: String) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onErrorUpdate(msg: String) {
    }

    override fun onSuccessGet(data: List<DataItem>?) {
    }

    override fun onFailedGet(msg: String) {
    }

    override fun onSuccessDelete(msg: String) {
    }

    override fun onErrorDelete(msg: String) {
    }

    fun showDatePicker(view: View) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val selectedDateString = dateFormat.format(selectedDate.time)
                editTanggalPengembalian.setText(selectedDateString)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.setOnCancelListener {
            editTanggalPengembalian.setText("Default Value")
        }
        datePickerDialog.show()
    }

    fun showDatePickerPinjam(view: View) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val selectedDateString = dateFormat.format(selectedDate.time)
                editTanggalPeminjaman.setText(selectedDateString)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    private fun convertToDisplayDateFormat(date: String): String {
        val databaseDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val displayDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return try {
            val parsedDate = databaseDateFormat.parse(date)
            displayDateFormat.format(parsedDate)
        } catch (e: Exception) {
            ""
        }
    }

    private fun convertToDatabaseDateFormat(date: String): String {
        val displayDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val databaseDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val parsedDate = displayDateFormat.parse(date)
            databaseDateFormat.format(parsedDate)
        } catch (e: Exception) {
            ""
        }
    }
}
