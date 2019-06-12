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