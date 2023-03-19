package com.example.mirea_app.utils

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mirea_app.dataClasses.ListsForData
import org.json.JSONObject

class AppNetworkManager(private val context: Context) {

    private val url = "https://dt.miet.ru/ppo_it_final"

    private val queue = Volley.newRequestQueue(context)

    fun gettingAFlightAssignment() {
        val jsonReq = object : StringRequest(
            Method.GET,
            url,
            {response ->
                Log.d("LogResponse", response.toString())
                ListsForData.ListOfCorsList = parseJsonWay(response)
            },
            {error ->
                Log.e("LogResponse", error.toString())
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = mutableMapOf<String, String>()
                headers["X-Auth-Token"] = "u4gqesn5"
                return headers
            }
        }
        queue.add(jsonReq)
    }

    fun parseJsonWay(response: String): MutableList<List<List<Int>>> {
        val res = mutableListOf<List<List<Int>>>()

        val jsonObject = JSONObject(response)
        val listOfJsonCors = jsonObject.getJSONArray("message")


        for (jsObj in 0 until listOfJsonCors.length()) {
            val listOfPoints = mutableListOf<List<Int>>()
            val item = listOfJsonCors.getJSONObject(jsObj)
//            Log.d("JsonList", item.toString())
            val pointsList = item.getJSONArray("points")
//            Log.d("JsonList", pointsList.toString())

            for (pointJsObj in 0 until pointsList.length()) {
                val pointList = listOf<Int>(
                    pointsList.getJSONObject(pointJsObj).getInt("SH"),
                    pointsList.getJSONObject(pointJsObj).getInt("distance")
                )
                listOfPoints.add(pointList)
            }
            res.add(listOfPoints)
        }
//        Log.d("JsonList", res.toString())
        return res
    }
}