package com.example.panta.kotlintest

class SearchClass{
    fun search(list:ArrayList<DataBeans>, searchString : String):ArrayList<DataBeans>{
        var newDataBeans = ArrayList<DataBeans>()
        for(data in list){
            if(data.title.contains(searchString)){
                newDataBeans.add(data)
            }else{
                if(data.mainText.contains(searchString)){
                    newDataBeans.add(data)
                }
            }

        }
        return newDataBeans
    }
}