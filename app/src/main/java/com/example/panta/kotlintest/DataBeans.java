package com.example.panta.kotlintest;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.List;

public class DataBeans implements Comparable<DataBeans> ,Serializable{
    private int id;
    private String title;
    private String reading;
    private String mainText;
    private int readView;
    private List<DataBeans> dataList;

    public DataBeans(){}

    public DataBeans(int id, String title, String reading, String mainText,int readView){
        this.id = id; this.title = title; this.reading = reading; this.mainText = mainText; this.readView = readView;
    }
    public DataBeans(int id, String title, String reading, String mainText, int readView, List<DataBeans> dataList){
        this(id,title,reading,mainText,readView);
        this.dataList = dataList;

    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getReading(){
        return this.reading;
    }
    public void setReading(String reading){
        this.reading = reading;
    }
    public String getMainText(){
        return this.mainText;
    }
    public void setMainText(String text){
        this.mainText = text;
    }

    public DataBeans setDataList(List<DataBeans> dataList){
        this.dataList = dataList;
        return this;
    }

    public List<DataBeans> getDataList() { return this.dataList; }



    @Override
    public int compareTo(DataBeans data){
        return reading.compareTo(data.reading);
    }
}
