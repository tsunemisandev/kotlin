package org.example


@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class CsvHeader(
    val name: String,
    val order: Int,
    val dateTimeFormat: String = "",
    val dateFormat: String = "",
    val numberFormat: String = ""
)