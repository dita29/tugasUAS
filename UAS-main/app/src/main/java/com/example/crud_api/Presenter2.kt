package com.example.crud_api

import retrofit2.Call
import retrofit2.Response

class Presenter2 (val crudView : UpdateData) {
    //add data
    fun addData(name: String, pinjam: String, kembali: String, buku: String) {
        Api.getService()
            .addMhs(name, pinjam, kembali, buku)
            .enqueue(object  : retrofit2.Callback<ResultStatus> {
                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    crudView.onErrorAdd(t.localizedMessage)
                }

                override fun onResponse(call: Call<ResultStatus>, response: Response<ResultStatus>
                ) {
                    if (response.isSuccessful && response.body()?.status == 200) {
                        crudView.onSuccessAdd(response.body()?.pesan ?:  "")
                    }else {
                        crudView.onErrorAdd(response.body()?.pesan ?: "")
                    }
                }
            })
    }

    //update data
    fun updateData(id: String, name: String, pinjam: String, kembali: String, buku: String) {
        Api.getService()
            .updateMhs(id, name, pinjam, kembali, buku)
            .enqueue(object : retrofit2.Callback<ResultStatus> {
                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    crudView.onErrorUpdate(t.localizedMessage)
                }

                override fun onResponse(call: Call<ResultStatus>, response: Response<ResultStatus>
                ) {
                    if (response.isSuccessful && response.body()?.status == 200) {
                        crudView.onSuccessUpdate(response.body()?.pesan ?: "")
                    }else{
                        crudView.onErrorUpdate(response.body()?.pesan ?: "")
                    }
                }
            })
    }
}