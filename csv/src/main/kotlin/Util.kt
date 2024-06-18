package org.example

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Annotation based aproach
 */
fun <T : Any> writeCsv(file: File, data: List<T>) {
    if (data.isEmpty()) return

    val properties = data.first()::class.memberProperties
        .filter { it.findAnnotation<CsvHeader>() != null }
        .sortedBy { it.findAnnotation<CsvHeader>()?.order }

    val headers = properties.mapNotNull { it.findAnnotation<CsvHeader>()?.name }

    val writer = Files.newBufferedWriter(Paths.get(file.toURI()))
    val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(*headers.toTypedArray()))

    data.forEach { item ->
        val row = properties.map { prop ->
            prop.isAccessible = true
            val annotation = prop.findAnnotation<CsvHeader>()
            val value = prop.call(item)
            when {
                value == null -> ""
                value is LocalDateTime && annotation?.dateTimeFormat?.isNotEmpty() == true ->
                    value.format(DateTimeFormatter.ofPattern(annotation.dateTimeFormat))
                value is LocalDate && annotation?.dateTimeFormat?.isNotEmpty() == true ->
                    value.format(DateTimeFormatter.ofPattern(annotation.dateFormat))
                value is Number && annotation?.numberFormat?.isNotEmpty() == true ->
                    DecimalFormat(annotation.numberFormat).format(value)
                else -> value.toString()
            }
        }
        csvPrinter.printRecord(row)
    }

    csvPrinter.flush()
    csvPrinter.close()
}

/**
 * Configuration object list approach
 */
fun <T : Any> writeCsv(file: File, data: List<T>, headerInfoList: List<CsvHeaderInfo<T>>) {
    if (data.isEmpty()) return

    val writer = Files.newBufferedWriter(Paths.get(file.toURI()))
    val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(*headerInfoList.map { it.name }.toTypedArray()))

    data.forEach { item ->
        val row = headerInfoList.map { headerInfo ->
            val value = headerInfo.property.get(item)
            when {
                value == null -> ""
                value is LocalDateTime && headerInfo.dateTimeFormat.isNotEmpty() ->
                    value.format(DateTimeFormatter.ofPattern(headerInfo.dateTimeFormat))
                value is LocalDate && headerInfo.dateFormat.isNotEmpty() ->
                    value.format(DateTimeFormatter.ofPattern(headerInfo.dateFormat))
                value is Number && headerInfo.numberFormat.isNotEmpty() ->
                    DecimalFormat(headerInfo.numberFormat).format(value)
                else -> value.toString()
            }
        }
        csvPrinter.printRecord(row)
    }

    csvPrinter.flush()
    csvPrinter.close()
}