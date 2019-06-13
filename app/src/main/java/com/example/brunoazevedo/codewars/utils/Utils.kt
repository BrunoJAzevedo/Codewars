package com.example.brunoazevedo.codewars.utils

fun <T> List<T>.listToString() : String {
    if (isEmpty())
        throw NoSuchElementException("List is empty.")
    var listToString = "${this[0]}"
    for(index in 1 until this.size) {
        listToString += ", ${this[index]}"
    }
    return listToString
}

fun Set<String>.setToString() : String {
    if (isEmpty())
        throw NoSuchElementException("Set is empty.")
    var string = ""
    var aux = 1
    var size = this.size
    for (elem in this) {
        when(aux) {
            size -> {
                if(size == 1) string += elem
                else string += " $elem"
            }
            1 -> string += "$elem,"
            else -> string += " $elem,"
        }
        aux++
    }
    return string
}