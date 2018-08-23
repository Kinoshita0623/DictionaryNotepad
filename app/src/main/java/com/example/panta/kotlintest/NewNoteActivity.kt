package com.example.panta.kotlintest

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.lang.*

class NewNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        val submitButton: Button = findViewById(R.id.submitButton)
        val titleField: EditText = findViewById(R.id.titleEdit)
        val readingField:EditText = findViewById(R.id.readingEdit)
        val mainText:EditText = findViewById(R.id.textEdit)

        submitButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                val title = titleField.getText().toString()
                val reading  = readingField.getText().toString()
                val text = mainText.getText().toString()



                if(title.length > 1 && reading.length > 1 && text.length > 1){
                    val sql = SQLController(getApplication())
                    sql.setData(title,reading,text)
                    val intent = Intent(getApplication(),MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{

                    var erralert = AlertDialog.Builder(getApplication())
                    erralert.setTitle("エラー")
                    erralert.setMessage("入力してください")
                    erralert.setNeutralButton("YES"){dialog,which->

                    }
                    erralert.show()

                }
            }
        })

    }
}
