package com.example.crud_api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

object Api {
    fun getInterceptor() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val  okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return okHttpClient
    }
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.36/server/index.php/ServerUas/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService() = getRetrofit().create(MhsService::class.java)
}

interface MhsService{
    //fungsi create data
    @FormUrlEncoded
    @POST("addMhs")
    fun addMhs(
        @Field("name") name: String,
        @Field("pinjam") pinjam: String,
        @Field("kembali") kembali: String,
        @Field("buku") buku: String) : Call<ResultStatus>

    //fungsi get data
    @GET("getDataMhs")
    fun getData() : Call<ResultMhs>

    //fungsi update data
    @FormUrlEncoded
    @POST("updateMhs")
    fun  updateMhs(@Field("id") id: String,
                     @Field("name") name: String,
                   @Field("pinjam") pinjam: String,
                   @Field("kembali") kembali: String,
                   @Field("buku") buku: String) : Call<ResultStatus>
    //fungsi delete
    @FormUrlEncoded
    @POST("deleteMhs")
    fun deleteMhs(@Field("id") id: String?): Call<ResultStatus>
}