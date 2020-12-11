package com.example.weathertop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

/*
All the id like this:
                     android:id="@id/caseName_Activity"
Example:
                     android:id="@id/txtWeather_Main"
 */

class MainActivity : AppCompatActivity() {
    private var city = ""
    private var temp = ""
    private var text_condition  = ""
    private var image_condition = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            val client=AsyncHttpClient()
            val url="http://api.openweathermap.org/data/2.5/weather?q=Tehran&appid=8ba361a8c728b0d78542841ad3af1b01&units=metric"

            client.get(url,object :JsonHttpResponseHandler(){
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                    super.onSuccess(statusCode, headers, response)
                    val weather=Gson().fromJson(response.toString(),OpenWeatherMap::class.java)
                    city=weather.name
                    temp=weather.main.temp.toString()
                    text_condition=weather.weather.get(0).main
                    image_condition=weather.weather.get(0).icon
                }

                override fun onFailure(statusCode: Int, headers: Array<out Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
                    super.onFailure(statusCode, headers, throwable, errorResponse)
                    println(throwable?.message)
                }
            })

            val recycler=findViewById<RecyclerView>(R.id.recycler_Main)
            val list=generate_list(city,temp,text_condition,image_condition)
            recycler.adapter=RecycleAdapter(list)
            recycler.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
            recycler.setHasFixedSize(true)

            /////////////////////////////added later//////////////////////////
            val edit_city=findViewById<EditText>(R.id.edtCity_Main)
            val done=findViewById<ImageView>(R.id.imgDone_Main)
            done.setOnClickListener(View.OnClickListener {
                val intent=Intent(this,MoreActivity::class.java)
                val city=edit_city.text.toString()
                intent.putExtra("city",city)
                startActivity(intent)
            //////////////////////////////////////////////////////////////////
            })

        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun generate_list(cityName: String , temperature: String , condition: String , image: String) : List<ElementItem>{
        val myList=ArrayList<ElementItem>()

        val dayInt=Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toString()
        val day=when (dayInt){
            "0" -> "Saturday"
            "1" -> "Sunday"
            "2" -> "Monday"
            "3" -> "Tuesday"
            "4" -> "Wednesday"
            "5" -> "Thursday"
            "6" -> "Friday"
            else -> ""
        }

        val picture=when (image){
            "01d","01n" -> R.drawable.clear
            "02d","02n" -> R.drawable.few_clouds
            "03d","03n" -> R.drawable.scatterd_clouds
            "04d","04n" -> R.drawable.broken_clouds
            "09d","09n" -> R.drawable.shower_rain
            "10d","10n" -> R.drawable.rain
            "11d","11n" -> R.drawable.thunderstorm
            "13d","13n" -> R.drawable.snow
            "50d","50n" -> R.drawable.mist
            else  -> R.drawable.clear
        }

        val items=ElementItem(cityName,day,temperature,condition,picture)
        myList+=items
        return myList
    }
}