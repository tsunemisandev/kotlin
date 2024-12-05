package org.example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.io.File
import java.io.IOException

object JacksonUtils {

    // ObjectMapperのインスタンスを作成し、必要なモジュールを登録
    private val objectMapper: ObjectMapper = ObjectMapper().apply {
        registerModule(JavaTimeModule())     // JavaTimeモジュール（Java 8日付/時刻型対応）
    }

    /**
     * オブジェクトをJSON文字列にシリアライズします。
     *
     * @param obj シリアライズするオブジェクト
     * @return JSON文字列
     */
    fun <T> toJson(obj: T): String {
        return try {
            objectMapper.writeValueAsString(obj)
        } catch (e: IOException) {
            throw RuntimeException("Error serializing object", e)
        }
    }

    /**
     * オブジェクトをJSONファイルに書き込みます。
     *
     * @param obj シリアライズするオブジェクト
     * @param file 出力先のファイル
     */
    fun <T> writeToFile(obj: T, file: File) {
        try {
            objectMapper.writeValue(file, obj)
        } catch (e: IOException) {
            throw RuntimeException("Error writing object to file", e)
        }
    }

    /**
     * JSON文字列を指定したクラス型にデシリアライズします。
     *
     * @param json JSON文字列
     * @param clazz デシリアライズ先のクラス型
     * @return デシリアライズされたオブジェクト
     */
    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return try {
            objectMapper.readValue(json, clazz)
        } catch (e: IOException) {
            throw RuntimeException("Error deserializing JSON", e)
        }
    }
}