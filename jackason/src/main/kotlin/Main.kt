package org.example

import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import java.time.LocalDateTime

fun main() {
    // サンプルDTOオブジェクトを作成
    val myDTO = MyDTO(
        id = 1L,
        name = "Test",
        date = LocalDate.of(2024, 12, 5),
        timestamp = LocalDateTime.now()
    )

    // DTOをJSON文字列にシリアライズ
    val jsonString = JacksonUtils.toJson(myDTO)
    println("JSON String: $jsonString")

    // DTOをファイルに保存
    val file = File("test_output.json")
    JacksonUtils.writeToFile(myDTO, file)

    println("DTO saved to file: ${file.absolutePath}")

    // JSON文字列からオブジェクトをデシリアライズ
    val deserializedDTO = JacksonUtils.fromJson(jsonString, MyDTO::class.java)
    println("Deserialized DTO: $deserializedDTO")

    val test = ListDTO(
        title = "Testing",
        list = mutableListOf(MyDTO())
    )
    JacksonUtils.writeToFile(test, file)
    val test2 = JacksonUtils.fromJson(Files.readString(file.toPath()), ListDTO::class.java)
    println(test2)
    println(test2 == test)
}