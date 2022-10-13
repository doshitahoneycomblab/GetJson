package com.example.wather

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.reactivex.rxjava3.internal.util.HalfSerializer.onNext
import io.reactivex.rxjava3.kotlin.subscribeBy

class WeatherFragment : Fragment() {

    //private var weatherData: ArrayList<WeatherData> = ArrayList()
    private lateinit var cityText: TextView
    private lateinit var weatherText: TextView

    private val  client: Client = Client()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cityText = view.findViewById(R.id.cityTextView)
        weatherText = view.findViewById(R.id.weatherTextView)
        val weatherData: ArrayList<WeatherData> = ArrayList()

        client.createService()

        /*
        val subscriber = Client().publishSubject
            .subscribe {
                println(weatherData.size)
                cityText.text = weatherData[0].timeSeries[0].areas[0].area.name
            }

         */

        //cityText.text = model.name
    }

    val subscriber = client.publishSubject
        .subscribeBy(onNext = {
            var weatherData: ArrayList<WeatherData> = ArrayList()
            weatherData = it
            println("GetWeather")
            println(weatherData.size)
            cityText.text = weatherData[0].timeSeries[0].areas[0].area.name
            weatherText.text = weatherData[0].timeSeries[0].areas[0].weathers[0]
        })


/*
    public fun LoadWeatherData(){
        println("GetWeather")
        println(weatherData.size)
    }

 */

    companion object {
        @JvmStatic
        fun newInstance() = WeatherFragment()
    }
}