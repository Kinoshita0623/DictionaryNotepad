package com.example.panta.kotlintest

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var titleList:ArrayList<String> = ArrayList<String>()
    var textList:ArrayList<String> = ArrayList<String>()
    var idList:ArrayList<Int> = ArrayList<Int>()
    var dataList:ArrayList<DataBeans> = ArrayList<DataBeans>()

    var arrayAdapter:ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //リストとアダプターの処理
        this.reloadList().reloadAdapter()

        /*ここまで*/
        /*イベント変数登録群*/
        val searchButton: Button = findViewById(R.id.searchButton) //as Button
        searchButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v:View){
                //検索
                val searchText:EditText = findViewById(R.id.searchBox)
                var searchClass = SearchClass()
                var list:ArrayList<DataBeans> = searchClass.search(dataList,searchText.getText().toString())
                System.out.println(searchText.toString())
                reloadList(list).reloadAdapter()
            }
        })
        val newNoteButton:Button = findViewById(R.id.newNoteButton)// as Button
        newNoteButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v:View){
                //メモ作成
                var intent:Intent = Intent(getApplication(),NewNoteActivity::class.java)
                        startActivity(intent)

            }
        })

        val listView:ListView = findViewById(R.id.listView)
        listView.setOnItemClickListener{parent, view, position ,id ->
            val intent= Intent(this.getApplicationContext(),ViewActivity::class.java)

            var title:String = titleList.get(position)
            var text:String = textList.get(position)
            intent.putExtra("Title",title)
            intent.putExtra("Text",text)
            startActivity(intent)

        }

        listView.setOnItemLongClickListener { parent, view, position, id ->
            deleateCheck(position)
            return@setOnItemLongClickListener true
        }

        val sortDictionary:Button = findViewById(R.id.sortDictionary)   //並び替え
        sortDictionary.setOnClickListener{view ->
            dataList.sort()
            reloadList(this.dataList).reloadAdapter()
        }

        val idSort:Button = findViewById(R.id.sortId)
        idSort.setOnClickListener{view ->
            reloadList().reloadAdapter()
        }
    }
    fun reloadList(list:ArrayList<DataBeans>):MainActivity{


        //各リストを初期化する
        titleList.clear()
        textList.clear()
        idList.clear()

        for(data in list){
            titleList.add(data.title)//title
            textList.add(data.mainText)
            idList.add(data.id)
        }
        return this
    }

    fun reloadList():MainActivity{
        val sc = SQLController(this)

        this.dataList = sc.getDataAll()
        this.reloadList(dataList)
        return this
    }

    fun reloadAdapter(){
        arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,titleList)
        listView.setAdapter(arrayAdapter)
    }

    fun deleateCheck(position:Int){
        var alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("削除")
        alert.setMessage("本当に削除しますか")

        alert.setPositiveButton("YES"){dialog,which->
            deleatItem(position)    //問題点
        }
        alert.setNeutralButton("No"){dialog,which->

        }
        alert.show()
    }
    fun deleatItem(position:Int){
        //タイトル、本文、リストから削除する
        titleList.remove(titleList.get(position))
        textList.remove(textList.get(position))

        //リストアダプターを更新する
        this.reloadAdapter()

        var w = idList.get(position)
        var sql = SQLController(this)
        sql.delData(w)  //問題点

        //リストをリロード更新する
        this.reloadList().reloadAdapter()

    }





}
