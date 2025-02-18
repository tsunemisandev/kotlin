package com.example.dbresponsetime.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.BeanUtils

@JvmInline
value class 専用車リスト(val value: List<String> = mutableListOf())

@JvmInline
value class 配送車リスト(val value: List<String> = mutableListOf())

fun test(list: 専用車リスト) {
    println(list)
}

fun main() {
    val list = 専用車リスト(listOf("Is senyosha list"))
    val list2 = 配送車リスト(listOf("Is haisousha list"))

    data class MyBean(var senyoList: 専用車リスト = 専用車リスト(), var list2: 配送車リスト = 配送車リスト())

    val test = MyBean()
    BeanUtils.copyProperties(list, test.senyoList)
    BeanUtils.copyProperties(list2, test.list2)
    println(jacksonObjectMapper().writeValueAsString(test))
}