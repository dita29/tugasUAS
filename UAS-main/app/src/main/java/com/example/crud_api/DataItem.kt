package com.example.crud_api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DataItem: Serializable {
    @field:SerializedName("mhs_name")
    val mhsName: String? = null

    @field:SerializedName("mhs_id")
    val mhsId: String? = null

    @field:SerializedName("mhs_kembali")
    val mhsKembali: String? = null

    @field:SerializedName("mhs_pinjam")
    val mhsPinjam: String? = null

    @field:SerializedName("mhs_buku")
    val mhsNameBook: String? = null
}