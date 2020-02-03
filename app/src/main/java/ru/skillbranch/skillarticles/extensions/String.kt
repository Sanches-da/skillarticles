package ru.skillbranch.skillarticles.extensions

fun String?.indexesOf(query: String, ignoreCase: Boolean) : List<Int>{
    if (this.isNullOrEmpty() || query.isEmpty()) return emptyList()
    val result: ArrayList<Int> = arrayListOf()
    var currentPosition = 0
    while (true){
        currentPosition = this.indexOf(query,currentPosition, ignoreCase)
        if (currentPosition == -1) break
        result.add(currentPosition)
        currentPosition++
    }
    return result
}