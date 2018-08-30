package com.example.panta.kotlintest

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
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
        val data = intent.getSerializableExtra("Data") as? DataBeans
        //</intent>


        val submitButton: Button = findViewById(R.id.submitButton)
        val titleField: EditText = findViewById(R.id.titleEdit)
        val readingField:EditText = findViewById(R.id.readingEdit)
        val mainText:EditText = findViewById(R.id.textEdit)


        //<変数更新>
        if(data !=  null && data.id != -100){
            titleField.setText(data.title)
            readingField.setText(data.reading)
            mainText.setText(data.mainText)
            this.whatEdit = data.id
        }
        //</変数更新>

        //<メイン画面移動>
        fun backOperation(){
            val intent = Intent(application,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        //</メイン画面移動>

        //リスナー
        submitButton.setOnClickListener{
            val title = titleField.text.toString()
            val reading = readingField.text.toString()
            val text = mainText.text.toString()

            if(title.length > 1 && reading.length > 1 && text.length > 1 && data != null){
                val sql = SQLController(application)

                data.apply{
                    setTitle(title)
                    setMainText(text)
                    setReading(reading)
                }

                val count:Int = data.dataList
                        .filter{ data -> data.title == title || data.mainText == text}
                        .count()
                
                when{
                    whatEdit != -100 -> {
                        sql.updateData(data)
                        backOperation()
                    }
                    count == 0 -> {
                        sql.setData(title,reading,text)
                        backOperation()
                    }
                    else -> {
                        AlertDialog.Builder(this).apply{
                            setTitle("重複が発見されました")
                            setMessage("続行しますか?")
                            setPositiveButton("YES"){ dialog, which ->
                                sql.setData(title,reading,text)
                                backOperation()
                            }
                            setNegativeButton("NO"){ notA, notB -> }

                        }.show()
                    }
                }



            }else{
                //エラー
                AlertDialog.Builder(this).apply{
                    setTitle("エラー")
                    setMessage("入力不備を確認の上デベロッパーに問い合わせてください")
                    setPositiveButton("OK"){dialog, which ->  }

                }.show()
            }
        }
    }
}
