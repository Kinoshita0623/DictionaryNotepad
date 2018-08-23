package com.example.panta.kotlintest;

public class DataBeans implements Comparable<DataBeans> {
    private int id;
    private String title;
    private String reading;
    private String mainText;
    private int readView;
    public DataBeans(int id, String title, String reading, String mainText,int readView){
        this.id = id; this.title = title; this.reading = reading; this.mainText = mainText; this.readView = readView;
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



    @Override
    public int compareTo(DataBeans data){
        return reading.compareTo(data.reading);
    }
}
