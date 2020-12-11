package com.example.weathertop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*

class MoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)
        try {
            val city=findViewById<TextView>(R.id.txtCity_More)
            val day=findViewById<TextView>(R.id.txtDay_More)
            val temp=findViewById<TextView>(R.id.txtTemp_More)
            val text_condition=findViewById<TextView>(R.id.txtCondition_More)
            val image_condition=findViewById<ImageView>(R.id.imgCondition_More)

            /////////////////////added later/////////////////
            val city_name=intent.getStringExtra("city")
            /////////////////////////////////////////////////

            val client= AsyncHttpClient()
            val url="http://api.openweathermap.org/data/2.5/weather?q=$city_name&appid=8ba361a8c728b0d78542841ad3af1b01&units=metric"

            client.get(url,object : JsonHttpResponseHandler(){
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                    super.onSuccess(statusCode, headers, response)
                    val weather= Gson().fromJson(response.toString(),OpenWeatherMap::class.java)

                    city.text=weather.name

                    temp.text=weather.main.temp.toString()+"°C"

                    text_condition.text=weather.weather.get(0).main

                    val dayInt= Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toString()
                    day.text=when (dayInt){
                        "0" -> "Saturday"
                        "1" -> "Sunday"
                        "2" -> "Monday"
                        "3" -> "Tuesday"
                        "4" -> "Wednesday"
                        "5" -> "Thursday"
                        "6" -> "Friday"
                        else -> ""
                    }

                    when (weather.weather.get(0).icon){
                            "01d","01n" -> image_condition.setImageResource(R.drawable.clear)
                            "02d","02n" -> image_condition.setImageResource(R.drawable.few_clouds)
                            "03d","03n" -> image_condition.setImageResource(R.drawable.scatterd_clouds)
                            "04d","04n" -> image_condition.setImageResource(R.drawable.broken_clouds)
                            "09d","09n" -> image_condition.setImageResource(R.drawable.shower_rain)
                            "10d","10n" -> image_condition.setImageResource(R.drawable.rain)
                            "11d","11n" -> image_condition.setImageResource(R.drawable.thunderstorm)
                            "13d","13n" -> image_condition.setImageResource(R.drawable.snow)
                            "50d","50n" -> image_condition.setImageResource(R.drawable.mist)
                            else  -> image_condition.setImageResource(R.drawable.clear)
                    }
                }

                override fun onFailure(statusCode: Int, headers: Array<out Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
                    super.onFailure(statusCode, headers, throwable, errorResponse)
                    println(throwable?.message)
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
        }
    }
}