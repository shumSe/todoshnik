package ru.shumikhin.todoshnik.utils

enum class Importance(value: Int) {
    BASIC(0), LOW(1), IMPORTANT(2);

    fun toList(): List<String>{
        val res =  mutableListOf<String>()
        for(i in Importance.entries){
            res.add(i.ordinal.toString())
        }
        return res
    }

    companion object
}