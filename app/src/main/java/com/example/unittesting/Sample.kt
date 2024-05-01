package com.example.unittesting

fun main()
{

    val myList = mutableListOf<Int>(1,2,8,4,5,6,7,3,3)

    for(h in 0 until myList.size)
    {
        for (b in h+1 until myList.size){
            if(myList[b] > myList[h]){
                val temp = myList[h]
                myList[h] = myList[b]
                myList[b] = temp
            }
        }
    }

    print(myList)


    val mapList = mutableMapOf<Int,Int>()
    for (num: Int in myList){
        mapList[num] = (mapList[num] ?: 0) + 1
    }

    println(mapList)

}