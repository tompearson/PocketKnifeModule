package com.example.pocketknifemodule1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var toast: Toast
    private lateinit var textMessage: TextView
    lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button_Map_id)

        textMessage = findViewById(R.id.message)
        textMessage.setTextColor(Color.BLACK)
        textMessage.setText("")//clear screen

        methodWithPermissions(this)

        textMessage.setText(getNetworkStatus(this))
        textMessage.setText(getLocationStatus(this))
        textMessage.setText(getBlueToothStatus(this))
        textMessage.setText(getMACAddress(this))

//        Map feature

        button.setOnClickListener({ v ->
            val intent: Intent = Intent(this, MapsActivity::class.java).apply {}
            startActivity(intent)
        })


    }
}
