package com.example.weathertop

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class RecycleAdapter(val list : List<ElementItem>) : RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder>() {

    class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val more=itemView.findViewById<LinearLayout>(R.id.lnrMore_Recycler)

        val city=itemView.findViewById<TextView>(R.id.txtCity_Recycler)
        val day=itemView.findViewById<TextView>(R.id.txtDay_Recycler)
        val temp=itemView.findViewById<TextView>(R.id.txtTemp_Recycler)
        val text_condition=itemView.findViewById<TextView>(R.id.txtCondition_Recycler)
        val image_condition=itemView.findViewById<ImageView>(R.id.imgCondition_Recycler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        val holder=RecycleViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val current=list[position]
        holder.city.text=current.city
        holder.day.text=current.day
        holder.temp.text=current.temp
        holder.text_condition.text=current.text_condition
        holder.image_condition.setImageResource(current.image_condition)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}