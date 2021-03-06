package com.example.pocketknifemodule1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes;
import com.microsoft.appcenter.distribute.Distribute;
import com.microsoft.appcenter.utils.async.AppCenterConsumer

class MainActivity : AppCompatActivity() {

    lateinit var toast: Toast
    private lateinit var textMessage: TextView
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button_Map_id)
        button.setText("Map");

        textMessage = findViewById(R.id.message)
        textMessage.setTextColor(Color.BLACK)
        textMessage.setText("")//clear screen

        AppCenter.start(
            application, "a0d1200a-73c9-4154-87ce-031ec875e3e6",
            Analytics::class.java, Crashes::class.java, Distribute::class.java
        )

        val future = Crashes.hasCrashedInLastSession()
        future.thenAccept(AppCenterConsumer {
            if (it) {
                Toast.makeText(this, "Oops! Sorry about that crash!", Toast.LENGTH_LONG).show()
            }
        })

        methodWithPermissions(this)

        textMessage.setText(getNetworkStatus(this))
        textMessage.setText(getLocationStatus(this))
        textMessage.setText(getBlueToothStatus(this))
        textMessage.setText(getMACAddress(this))
        textMessage.setText(isItRooted(this))

//        Map feature

        button.setOnClickListener {
//             Crashes.generateTestCrash()
            val intent: Intent = Intent(this, MapsActivity::class.java).apply {}
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, getString(R.string.onStop), Toast.LENGTH_LONG)
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, getString(R.string.onRestart), Toast.LENGTH_LONG)
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, getString(R.string.onResume), Toast.LENGTH_LONG)
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, getString(R.string.onStart), Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, getString(R.string.onPause), Toast.LENGTH_LONG)

    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, getString(R.string.onDestroy), Toast.LENGTH_LONG)
    }
}
