package com.example.wather

import android.content.ContentValues
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.internal.util.HalfSerializer.onComplete
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.util.concurrent.TimeUnit

public class Client {

    val publishSubject = PublishSubject.create<ArrayList<WeatherData>>();

    val hoge: String = "Hoge"
    private val httpBuilder: OkHttpClient.Builder get() {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(Interceptor { chain ->

            val original = chain.request()
            val request = original.newBuilder()
                .header("Accept", "application/json")
                .method(original.method, original.body)
                .build()

            return@Interceptor chain.proceed(request)
        })
            .readTimeout(30, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        return httpClient
    }

    public fun createService(){

        var weatherData: ArrayList<WeatherData> = ArrayList()
        var httpClient = httpBuilder.build()

        var retrofit = Retrofit.Builder()
            .baseUrl("https://www.jma.go.jp/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        var API = retrofit.create(WatherInterface::class.java)


        //var client = Client()
        //val data = mutableListOf<WeatherData>()
        //var model: Model = Model()

        API.GetWather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onSuccess = {
                    //WeatherFragment().LoadWeatherData()
                    //weatherData = it
                    println(weatherData.size)
                    Log.e("Client", "onSuccess")
                    publishSubject.onNext(it)
                    //weatherData = it as ArrayList<WeatherData>
                    //println(weatherData.size)
                },
                onError = {
                    Log.e("Client", it.toString())
                    publishSubject.onErrorComplete()
                }
            )

                //selectModel()
                //model.name = weatherData!![0].timeSeries[0].areas[0].area.name
                //println(weatherData.get(0).timeSeries[0].areas[0].area.name)
                // var source: = weatherData.get(0).precipAverage.areas[0].area.name

                //for (data in it) {
                    //model.name = data.precipAverage.areas[0].area.name
                    //Log.i(ContentValues.TAG, String.format("%s", data))
                    //println(data.precipAverage.areas[0].area.name)
                //}


    }

    public fun selectModel(){
        //var weatherData: ArrayList<WeatherData> = createService()
        Log.e("Client", "GetWeatherData")
        //println(weatherData[0].publishingOffice)

        //var source = weatherData[0].timeSeries[0].areas[0]
        //println(source.area.name)
        //return Model(
          //  weatherData.get(0).publishingOffice,
            //source.area.name
        //)
/*
return Model(
    name = source.area.name,
    weathers = source.weathers,
    temps = source.temps
)

 */
    }
}