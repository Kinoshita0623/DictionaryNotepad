package com.example.panta.kotlintest

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.*

class NewNoteActivity : AppCompatActivity() {
    private var whatEdit:Int = -100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT



        //<intent>
        val intent = intent
        var data = intent.getSerializableExtra("Data")
        //</intent>


        val submitButton: Button = findViewById(R.id.submitButton)
        val titleField: EditText = findViewById(R.id.titleEdit)
        val readingField:EditText = findViewById(R.id.readingEdit)
        val mainText:EditText = findViewById(R.id.textEdit)


        if(data is DataBeans){
            titleField.setText(data.title)
            readingField.setText(data.reading)
            mainText.setText(data.mainText)
            this.whatEdit = data.id

        }




        submitButton.setOnClickListener{
            val title = titleField.text.toString()
            val reading = readingField.text.toString()
            val text = mainText.text.toString()

            if(title.length > 1 && reading.length > 1 && text.length > 1){
                val sql = SQLController(application)

                if(whatEdit != -100){
                    if(data is DataBeans){
                        data.title = title
                        data.reading = reading
                        data.mainText = text
                        sql.updateData(data)
                    }


                }else{

                    sql.setData(title,reading,text)
                }

                val intent = Intent(application,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}
