package com.example.wather

import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface WatherInterface {

    @GET("bosai/forecast/data/forecast/130000.json")
    fun GetWather() : Observable<MutableList<Json>>
}