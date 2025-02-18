package org.example

import org.example.component.annotations.InputText
import org.example.component.annotations.SelectItem
import org.example.component.annotations.buildVueComponent

data class FormData(
    @InputText(label = "Hello")
    val inputText: String,

    @SelectItem(label = "Items", list = "myList")
    val selectedItem: String
)

fun main() {
    val vueApp = buildVueComponent(FormData::class)
    println(vueApp.build())
}
//fun main() {
//    val newInputText = inputText(label = "Hello", vModel = "userName")
//
//    val newSelectItem = selectItem(
//        label = "Items",
//        vModel = "selectedItem",
//        list = "myList"
//    )
//
//    val app = vueComponent("div") {
//        prop("id", "app")
//
//        child(newInputText)
//        child(newSelectItem)
//    }
//
//    println(app.build())
//}
//fun main() {
//    val app = vueComponent("div") {
//        prop("id", "app")
//
//        child("label") {
//            text("Enter your name:")
//        }
//
//        child("input") {
//            prop("type", "text")
//            vModel("userName") // Bind input to userName
//        }
//
//        child("label") {
//            text("Select an option:")
//        }
//
//        child("select") {
//            vModel("selectedOption") // Bind select to selectedOption
//
//            child("option") {
//                prop("value", "option1")
//                text("Option 1")
//            }
//
//            child("option") {
//                prop("value", "option2")
//                text("Option 2 (Default)")
//            }
//
//            child("option") {
//                prop("value", "option3")
//                text("Option 3")
//            }
//        }
//    }
//
//    println(app.build())
//}
