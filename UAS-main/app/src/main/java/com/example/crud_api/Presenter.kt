package com.example.crud_api

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Response

class Presenter(val crudView: MainActivity) {
    //fungsi get data
    fun getData() {
        Api.getService().getData()
            .enqueue(object : retrofit2.Callback<ResultMhs> {
                override fun onFailure(call: Call<ResultMhs>, t: Throwable) {
                    crudView.onFailedGet(t.localizedMessage)
                    Log.d("Error", "Error Data")
                }

                override fun onResponse(call: Call<ResultMhs>, response: Response<ResultMhs>) {
                    if (response.isSuccessful) {
                        val status = response.body()?.status
                        if (status == 200) {
                            val  data = response.body()?.mhs
                            crudView.onSuccessGet(data)
                        }else {
                            crudView.onFailedGet("Error $status")
                        }
                    }
                }
            })
    }

    //untuk hapus data
    fun hapusData(context: Context, id: String?) {
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Konfirmasi Selesai")
            .setMessage("Apakah Anda yakin ingin menghapus data ini?")
            .setPositiveButton("Ya") { dialog, _ ->
                dialog.dismiss()
                // Memanggil API hapus setelah konfirmasi "Ya"
                Api.getService()
                    .deleteMhs(id)
                    .enqueue(object : retrofit2.Callback<ResultStatus> {
                        override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                            crudView.onErrorDelete(t.localizedMessage)
                        }

                        override fun onResponse(
                            call: Call<ResultStatus>,
                            response: Response<ResultStatus>
                        ) {
                            if (response.isSuccessful && response.body()?.status == 200) {
                                crudView.onSuccessDelete(response.body()?.pesan ?: "")
                            } else {
                                crudView.onErrorDelete(response.body()?.pesan ?: "")
                            }
                        }
                    })
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
                // Tidak melakukan apa-apa jika konfirmasi "Tidak"
            }
            .create()
        alertDialog.show()
    }
}