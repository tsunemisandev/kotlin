@file:Suppress("UNCHECKED_CAST")

package org.example


interface Row<T> {
    var updateDatetime: String
    var rowEditKbn: RowEditKbn
    fun get(): T
}

enum class RowEditKbn {
    NEW,
    UPD,
    DEL,
    NOT_EDITED,
}

fun <T> List<Row<T>>.newRows(): List<Row<T>> {
    return this.filter {
        it.rowEditKbn == RowEditKbn.NEW && it.rowEditKbn !in listOf(
            RowEditKbn.DEL,
            RowEditKbn.NOT_EDITED
        )
    }
}

fun <T> List<Row<T>>.updRows(): List<Row<T>> {
    return this.filter {
        it.rowEditKbn == RowEditKbn.UPD && it.rowEditKbn !in listOf(
            RowEditKbn.DEL,
            RowEditKbn.NOT_EDITED
        )
    }
}

fun <T> List<Row<T>>.delRows(): List<Row<T>> {
    return this.filter { it.rowEditKbn == RowEditKbn.DEL }
}

fun <T> List<Row<T>>.editedRows(): List<Row<T>> {
    return this.filter { it.rowEditKbn != RowEditKbn.NOT_EDITED }
}

fun <T> List<Row<T>>.ifNotEmpty(): List<Row<T>> {
    if (this.isNotEmpty()) {
        return this
    }
    return emptyList()
}

data class MyRow(
    var text: String = "",
    override var updateDatetime: String = "", override var rowEditKbn: RowEditKbn = RowEditKbn.NEW,
) : Row<MyRow> {
    override fun get(): MyRow {
        return this
    }
}

data class Detail(var value: String = "")

fun main() {
    val test = mutableListOf<MyRow>()

    test.add(MyRow(rowEditKbn = RowEditKbn.NEW, text = "Hi this is new row"))
    test.add(MyRow(rowEditKbn = RowEditKbn.NEW, text = "Hi"))
    test.add(MyRow(rowEditKbn = RowEditKbn.DEL, text = "Hi this is del row"))

    test.newRows().ifNotEmpty()



}
