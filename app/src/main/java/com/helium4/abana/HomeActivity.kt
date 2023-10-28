package com.helium4.abana

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setMargins
import com.google.gson.Gson
import com.helium4.abana.databinding.ActivityHomeBinding
import com.helium4.abana.model.DataModel
import com.helium4.abana.viewModel.HomeViewModel

import android.widget.LinearLayout.LayoutParams as LinearLayoutParams


class HomeActivity : AppCompatActivity() {

    lateinit var mModel:HomeViewModel
    private lateinit var  binding: ActivityHomeBinding
    private lateinit var  view :View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        view = binding.root
        mModel = HomeViewModel(application)

        val jsonFileName = "data.json" // Replace with your JSON file name
        val json = parseJsonFromAssets(this, jsonFileName)

        if (json != null) {
            val result = json.result
            println("Total Count: ${result.totalCount}")
            val itemCount = result.totalCount
            val itemsPerLayout = 10


            for (i in 0 until itemCount step itemsPerLayout) {
                val linearLayout = createDynamicLinearLayout()
                for (j in i until i + itemsPerLayout) {
                    if (j >= itemCount) {
                        break
                    }
                    val item = json.result.items[j]
                    val machineName = json.result.items[j].machineLocker.id
                    val size = json.result.items[j].machineLocker.size
                    val textView = createDynamicTextView(machineName,size)

                    textView.setOnClickListener {

                        Toast.makeText(application,"You selected Locker $machineName",Toast.LENGTH_SHORT).show()

                    }
                    linearLayout.addView(textView)
                }
                findViewById<LinearLayout>(R.id.layout1).addView(linearLayout)
            }



//            for (i in 0 until result.totalCount step itemsPerLayout) {
//                val linearLayout = LinearLayout(this)
//                linearLayout.orientation = LinearLayout.VERTICAL
//
//                for (j in i until (i + itemsPerLayout).coerceAtMost(result.totalCount)) {
//                    val jsonObject = jsonArray.getJSONObject(j)
//                    val item = jsonObject.getString("item")
//                    val textView = TextView(this)
//                    textView.text = item
//                    linearLayout.addView(textView)
//                }
//
//                parentLayout.addView(linearLayout)
//            }

//            val columns = ((result.totalCount + 9) / 10 * 10)/10
//
//            Log.wtf("dsfds",columns.toString())
//            for (i in 1..columns) {
//                val linearLayout = LinearLayout(this)
//                linearLayout.layoutParams = LayoutParams(75, LayoutParams.WRAP_CONTENT)
//
//                val textView = TextView(this)
//                textView.text = "Row $i"
//                textView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
//
//                linearLayout.addView(textView)
//
//                findViewById<LinearLayout>(R.id.layout1).addView(linearLayout)
//                Log.wtf("dsfds","Added $i")
//
//            }

            for (item in result.items) {


                Log.wtf("iafwuqiiuegff","Machine ID: ${item.machineLocker.machineID}," +
                        " Machine Name: ${item.machineLocker.machineName}, " +
                        "Locker No: ${item.machineLocker.lockerNo}")


                // Add more properties as needed
            }
        } else {
            println("Failed to parse JSON")
        }
    }

    private fun parseJsonFromAssets(context: Context, fileName: String): DataModel? {
        try {
            val inputStream = context.assets.open(fileName)
            val json = inputStream.bufferedReader().use { it.readText() }
            val gson = Gson()
            return gson.fromJson(json, DataModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun createDynamicLinearLayout(): LinearLayout {
        val linearLayout = LinearLayout(this)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        linearLayout.orientation = LinearLayout.VERTICAL
        return linearLayout
    }

    private fun createDynamicTextView(text: Int, size: Int): TextView {
        val width = 220
        val textView = TextView(this)
        textView.setPadding(18,18,18,18)
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.WHITE)



        textView.setBackgroundResource(R.color.green)

        textView.text = text.toString()
        when (size) {
            6 -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    300 // Height
                )
                layoutParams.setMargins(8)

                textView.layoutParams = layoutParams

            }
            5 -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    250 // Height
                )
                layoutParams.setMargins(8)
                textView.layoutParams = layoutParams

            }
            4 -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    220 // Height
                )
                layoutParams.setMargins(8)
                textView.layoutParams = layoutParams

            }
            3 -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    150 // Height
                )
                layoutParams.setMargins(8)
                textView.layoutParams = layoutParams


            }
            2 -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    100 // Height
                )
                layoutParams.setMargins(8)
                textView.layoutParams = layoutParams
            }

           1 -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    80 // Height
                )
                layoutParams.setMargins(8)
                textView.layoutParams = layoutParams
            }
            else -> {

                val layoutParams = LinearLayoutParams(
                    width, // Width
                    LinearLayoutParams.WRAP_CONTENT // Height
                )
                layoutParams.setMargins(8)

            }
        }


        return textView
    }
}