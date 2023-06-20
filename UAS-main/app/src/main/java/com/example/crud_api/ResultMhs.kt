package com.example.crud_api

import com.google.gson.annotations.SerializedName

class ResultMhs {
    @field:SerializedName("pesan")
    val pesan: String? = null

    @field:SerializedName("mhs")
    val mhs: List<DataItem>? = null

    @field:SerializedName("status")
    val status: Int? = null
}