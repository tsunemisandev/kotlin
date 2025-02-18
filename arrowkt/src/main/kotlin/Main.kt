package org.example

import arrow.core.*
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure

fun main() {
//    val out = test(2).map { println(it) }
//    val result = test(2).getOrElse { println("Failed") }
    val result = (1..10).mapOrAccumulate { isEven2(it).bind() }
    println(result)
}

object CustomError

fun test(param: Int): Either<CustomError, String> {
    return if (param == 1) {
        "Success".right()
    } else {
        CustomError.left()
    }
}

data class NotEven(val i: Int)

fun Raise<NotEven>.isEven(i: Int): Int =
    i.also { ensure(i % 2 == 0) { NotEven(i) } }

fun isEven2(i: Int): Either<NotEven, Int> =
    either { isEven(i) }