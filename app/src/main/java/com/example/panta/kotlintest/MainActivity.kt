package com.example.panta.kotlintest

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var titleList:ArrayList<String> = ArrayList()
    var textList:ArrayList<String> = ArrayList()
    var idList:ArrayList<Int> = ArrayList()
    var dataList:ArrayList<DataBeans> = ArrayList()

    var arrayAdapter:ArrayAdapter<String>? = null
    private val sql:SQLController = SQLController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //縦に固定

        //リストとアダプターの処理
        this.reloadList().reloadAdapter()

        //検索ボタン
        val searchButton: Button = findViewById(R.id.searchButton) //as Button
        searchButton.setOnClickListener{
            val searchText:EditText = findViewById(R.id.searchBox)
            val text = searchText.text.toString()

            val newDataBeans = ArrayList<DataBeans>()

            //キーワードから選出
            dataList.filter{data -> data.title.contains(text) || data.mainText.contains(text)}
                    .forEach{data -> newDataBeans.add(data)}

            reloadList(newDataBeans).reloadAdapter()

        }

        //新規作成ボタン
        val newNoteButton:Button = findViewById(R.id.newNoteButton)// as Button
        newNoteButton.setOnClickListener{
            var intent = Intent(applicationContext,NewNoteActivity::class.java)
            startActivity(intent)
        }

        //ListViewの要素がクリックされたときのリスナー
        val listView:ListView = findViewById(R.id.listView)
        listView.setOnItemClickListener{parent, view, position ,id ->
            val intent= Intent(applicationContext,ViewActivity::class.java)

            var title:String = titleList[position]
            var text:String = textList[position]
            intent.putExtra("Title",title)
            intent.putExtra("Text",text)
            startActivity(intent)

        }

        //ListViewの要素が長押しされたときのリスナー
        listView.setOnItemLongClickListener { parent, view, position, id ->
            deleteCheck(position)
            return@setOnItemLongClickListener true
        }

        val sortDictionary:Button = findViewById(R.id.sortDictionary)   //並び替え
        sortDictionary.setOnClickListener{
            dataList.sort()
            reloadList(this.dataList).reloadAdapter()
        }

        //id順に並び替え
        val idSort:Button = findViewById(R.id.sortId)
        idSort.setOnClickListener{
            reloadList().reloadAdapter()
        }
    }
    private fun reloadList(list:ArrayList<DataBeans>):MainActivity{
        //各リストを初期化する
        titleList.clear(); textList.clear(); idList.clear()

        list.forEach{ data ->
            titleList.add(data.title)
            textList.add(data.mainText)
            idList.add(data.id)
        }
        return this
    }

    private fun reloadList():MainActivity{
        //val sql = SQLController(this)

        this.dataList = this.sql.getDataAll()
        this.reloadList(dataList)
        return this //メソッドチェーンをするために自分自身を返している
    }

    private fun reloadAdapter(){
        arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,titleList)
        listView.setAdapter(arrayAdapter)
    }

    private fun deleteCheck(position:Int){
        var alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("削除")
        alert.setMessage("本当に削除しますか")

        alert.setPositiveButton("YES"){dialog,which->
            deleteItem(position)    //問題点
        }

        alert.setNeutralButton("No"){dialog,which->

        }
        alert.show()
    }
    private fun deleteItem(position:Int){
        this.sql.delData(idList[position])

        //リストをリロード更新する
        this.reloadList().reloadAdapter()

    }
}
