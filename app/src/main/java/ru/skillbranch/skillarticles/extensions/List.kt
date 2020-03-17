package ru.skillbranch.skillarticles.extensions

fun List<Pair<Int, Int>>.groupByBounds(bounds : List<Pair<Int, Int>>) : List<MutableList<Pair<Int, Int>>>{
    return bounds.map {(bStart, bEnd) ->
        val bRange = bStart..bEnd
        this@groupByBounds.filter { (start, end)->
            start in bRange && end in bRange
        }.toMutableList()
    }
}