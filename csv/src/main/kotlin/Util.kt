package org.example

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.isAccessible


fun <T : Any> writeCsv(file: File, data: List<T>) {
    if (data.isEmpty()) return

    val headers = data.first().javaClass.kotlin.declaredMemberProperties
        .mapNotNull { it.findAnnotation<CsvHeader>()?.name }

    val writer = Files.newBufferedWriter(Paths.get(file.toURI()))
    val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(*headers.toTypedArray()))

    data.forEach { item ->
        val row = item.javaClass.kotlin.declaredMemberProperties.map { prop ->
            prop.isAccessible = true
            val annotation = prop.findAnnotation<CsvHeader>()
            val value = prop.call(item)
            when {
                value is LocalDateTime && annotation?.dateTimeFormat?.isNotEmpty() == true ->
                    value.format(DateTimeFormatter.ofPattern(annotation.dateTimeFormat))

                value is Number && annotation?.numberFormat?.isNotEmpty() == true ->
                    DecimalFormat(annotation.numberFormat).format(value)

                else -> value?.toString() ?: ""
            }
        }
        csvPrinter.printRecord(row)
    }

    csvPrinter.flush()
    csvPrinter.close()
}