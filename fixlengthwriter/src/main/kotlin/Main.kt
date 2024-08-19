package org.example

import java.nio.charset.Charset

fun main() {
    // サンプルデータ
    val records = listOf(
        listOf(
            FixedLengthField("John Doe", start = 0, end = 19),
            FixedLengthField("25", start = 20, end = 22, padChar = '0', alignLeft = false),
            FixedLengthField("USA", start = 23, end = 32)
        ),
        listOf(
            FixedLengthField("Jane Smith", start = 0, end = 19),
            FixedLengthField("30", start = 20, end = 22, padChar = '0', alignLeft = false),
            FixedLengthField("Canada", start = 23, end = 32)
        ),
        listOf(
            FixedLengthField("Yuki Tanaka", start = 0, end = 19),
            FixedLengthField("22", start = 20, end = 22, padChar = '0', alignLeft = false),
            FixedLengthField("Japan", start = 23, end = 32)
        )
    )
}


// 固定長項目を表すデータクラス
data class FixedLengthField(
    val value: String,
    val start: Int,  // フィールドの開始位置（0ベース）
    val end: Int,    // フィールドの終了位置（0ベース）
    val padChar: Char = ' ',
    val alignLeft: Boolean = true // 左寄せ (true) または右寄せ (false)
) {
    val length: Int get() = end - start + 1
}

// 各フィールドを固定長文字列に変換する関数
fun formatField(field: FixedLengthField, charset: Charset): ByteArray {
    val byteArray = field.value.toByteArray(charset)
    return if (field.alignLeft) {
        byteArray.padEnd(field.length, field.padChar, charset)
    } else {
        byteArray.padStart(field.length, field.padChar, charset)
    }
}

// 各フィールド結果を指定長さで調整し連結する関数
// 固定長文字列の書き出し関数
fun writeFixedLengthString(fields: List<FixedLengthField>, totalLength: Int, charset: Charset): String {
    val result = ByteArray(totalLength) { ' '.toByte() }

    fields.forEach { field ->
        val formattedField = formatField(field, charset)
        for (i in field.start until field.end + 1) {
            if (i - field.start < formattedField.size) {
                result[i] = formattedField[i - field.start]
            }
        }
    }

    return String(result, charset)
}

// Stringを指定されたバイト数にパディングする拡張関数
fun ByteArray.padEnd(length: Int, padChar: Char, charset: Charset): ByteArray {
    if (this.size >= length) return this.copyOf(length)
    val paddingSize = length - this.size
    val padding = ByteArray(paddingSize) { padChar.toString().toByteArray(charset)[0] }
    return this + padding
}

fun ByteArray.padStart(length: Int, padChar: Char, charset: Charset): ByteArray {
    if (this.size >= length) return this.copyOf(length)
    val paddingSize = length - this.size
    val padding = ByteArray(paddingSize) { padChar.toString().toByteArray(charset)[0] }
    return padding + this
}