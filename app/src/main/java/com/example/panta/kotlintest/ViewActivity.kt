package com.example.panta.kotlintest

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //val intent: Intent = getIntent()
        val intent: Intent = intent
        val data= intent.getSerializableExtra("Data")
        if(data is DataBeans){
            val titleView: TextView = findViewById(R.id.titleView)
            val textView: TextView = findViewById(R.id.textView)
            titleView.text = data.title
            textView.text = data.mainText

        }

        val editButton:Button = findViewById(R.id.edit)
        editButton.setOnClickListener{
            val intent = Intent(applicationContext,NewNoteActivity::class.java).apply{
                putExtra("Data",data)
            }

            startActivity(intent)

        }

    }
}
