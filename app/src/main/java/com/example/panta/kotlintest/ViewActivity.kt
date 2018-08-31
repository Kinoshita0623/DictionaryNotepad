package com.example.panta.kotlintest

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView

class ViewActivity : AppCompatActivity() {
    private var pusshedView:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //val intent: Intent = getIntent()
        val intent: Intent = intent
        val data = intent.getSerializableExtra("Data") as? Data
        //val view: Switch = findViewById(R.id.viewMode)
        val viewButton:Button = findViewById(R.id.viewButton)
        val titleView: TextView = findViewById(R.id.titleView)
        val textView: TextView = findViewById(R.id.textView)

        viewButton.visibility = View.GONE

        fun showOrNot(bool:Boolean){
            if(bool){
                textView.visibility = View.GONE
            }else{
                textView.visibility = View.VISIBLE
            }
        }

        if(data != null){

            titleView.text = data.dataBeans.title

           // view.isChecked = data.show

            if(data.show){
                showOrNot(data.show)
                viewButton.visibility = View.VISIBLE
            }else{
                showOrNot(data.show)
                textView.text = data.dataBeans.mainText
            }

        }

        val editButton:Button = findViewById(R.id.edit)
        editButton.setOnClickListener{
            val editIntent = Intent(applicationContext,NewNoteActivity::class.java)
            editIntent.putExtra("Data",data)

            startActivity(intent)

        }

        viewButton.setOnClickListener{
            if(this.pusshedView){
                viewButton.text = "表示する"
                showOrNot(pusshedView)
                this.pusshedView = false

            }else{
                viewButton.text = "非表示にする"
                showOrNot(pusshedView)
                this.pusshedView = true
            }
        }


    }
}
