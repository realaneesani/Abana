package com.helium4.abana

import MachineLocker
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setMargins
import com.helium4.abana.model.Item
import com.helium4.abana.model.Result
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


import android.widget.LinearLayout.LayoutParams as LinearLayoutParams


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val jsonStr = application.assets.open("data.json").bufferedReader().use {
            it.readText()
        }

        // Parse the JSON data
        val jsonObject = JSONObject(jsonStr)
        val itemsArray = jsonObject.getJSONObject("result").getJSONArray("items")

        // Convert JSON data to a list of Item objects and sort by lockerSequenceNo
        val items = (0 until itemsArray.length())
            .map { itemsArray.getJSONObject(it) }
            .map { Item(it.getJSONObject("machineLocker").toMachineLocker()) }
            .sortedBy { it.machineLocker.lockerSequenceNo }

        val columnSequenceMap = items.groupBy { it.machineLocker.columnSequence }

        for (sequence in columnSequenceMap.keys) {
            val linearLayout = createDynamicLinearLayout(this)
            // Variables to count the number of image views and text views
            var numImageViews = 0
            var numTextViews = 0

            for (item in columnSequenceMap[sequence]!!) {
                if (item.machineLocker.lockerSequenceNo == 2 && item.machineLocker.columnSequence == 1) {
                    val imageView = createDynamicImageView(0,columnSequenceMap[sequence]!!.size,this)

                    linearLayout.addView(imageView)
                  //  numImageViews++
                } else {
                    val textView = createDynamicTextView(item.machineLocker.lockerNo,item.machineLocker.size,this,columnSequenceMap[sequence]!!.size,item.machineLocker.columnSequence.toString())
                    linearLayout.addView(textView)
                 //   numTextViews++
                }
            }
            // Add weight sums for LinearLayout to distribute space evenly
            linearLayout.weightSum = numImageViews.toFloat() + numTextViews.toFloat()
            findViewById<LinearLayout>(R.id.layout1).addView(linearLayout)
        }
    }
    private fun JSONObject.toMachineLocker(): MachineLocker {
        return MachineLocker(
            getInt("machineID"),
            getString("machineName"),
            getInt("lockerNo"),
            getBoolean("active"),
            getInt("lockerSequenceNo"),
            getInt("columnSequence"),
            getInt("size"),
            getString("modelName"),
            getInt("id")
        )
    }

    private fun createDynamicImageView(lockerNo: Int, size: Int, homeActivity: HomeActivity): ImageView {

        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.sd)
        imageView.setPadding(18,18,18,18)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        val imageParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        imageParams.weight = 1.0f / size
        imageView.layoutParams = imageParams
        imageView.layoutParams = imageParams
        return imageView
    }

    private fun createDynamicLinearLayout(homeActivity: HomeActivity): LinearLayout {
        val linearLayout = LinearLayout(homeActivity)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        linearLayout.orientation = LinearLayout.VERTICAL
        return linearLayout
    }

    private fun createDynamicTextView(
        text: Int,
        size: Int,
        homeActivity: HomeActivity,
        size1: Int,
        toString: String
    ): TextView {
        val width = 250
        val textView = TextView(homeActivity)
        textView.setPadding(18,18,18,18)
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.WHITE)





        textView.setBackgroundResource(R.color.green)

        textView.text = if (text ==0){
            ""
        }else{

            text.toString() }

        textView.setOnClickListener {
            Toast.makeText(homeActivity,"You have selected Locker No:$text from Sequence $toString",Toast.LENGTH_SHORT).show()
        }
        when (size) {
            6 -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    300 // Height
                )
                layoutParams.setMargins(8)
                layoutParams.weight = 1.0f / size1

                textView.layoutParams = layoutParams

            }
            5 -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    350 // Height
                )
                layoutParams.setMargins(8)
                layoutParams.weight = 1.0f / size1

                textView.layoutParams = layoutParams

            }
            4 -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    280 // Height
                )
                layoutParams.setMargins(8)
                layoutParams.weight = 1.0f / size1

                textView.layoutParams = layoutParams

            }
            3 -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    180 // Height
                )
                layoutParams.setMargins(8)
                layoutParams.weight = 1.0f / size1

                textView.layoutParams = layoutParams


            }
            2 -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    95 // Height
                )
                layoutParams.setMargins(8)
                layoutParams.weight = 1.0f / size1

                textView.layoutParams = layoutParams
            }

            1 -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    80 // Height
                )
                layoutParams.setMargins(8)
                layoutParams.weight = 1.0f / size1

                textView.layoutParams = layoutParams
            }
            else -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    LinearLayoutParams.WRAP_CONTENT // Height
                )
                layoutParams.weight = 1.0f / size1

                layoutParams.setMargins(8)
                textView.layoutParams = layoutParams

            }
        }


        return textView
    }


}