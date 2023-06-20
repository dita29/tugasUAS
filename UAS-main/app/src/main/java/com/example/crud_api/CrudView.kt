package com.example.crud_api

interface CrudView {
    fun onSuccessGet(data: List<DataItem>?)
    fun onFailedGet(msg: String)

    fun onSuccessAdd(msg: String)
    fun onErrorAdd(msg: String)

    fun onSuccessUpdate(msg: String)
    fun onErrorUpdate(msg: String)

    fun onSuccessDelete(msg: String)
    fun onErrorDelete(msg: String)

}