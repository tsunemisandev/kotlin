package org.example.component.annotations

import org.example.dsl.VueComponent
import org.example.dsl.vueComponent

fun inputText(label: String = "Label", vModel: String = ""): VueComponent {
    return vueComponent("div") {
        child("label") {
            text(label)
        }
        child("input") {
            prop("type", "text")
            vModel(vModel)
        }
    }
}

fun selectItem(label: String = "Select an option", vModel: String = "", list: String = ""): VueComponent {
    return vueComponent("div") {
        child("label") {
            text(label)
        }
        child("select") {
            vModel(vModel)
            vFor("$list as option")

            child("option") {
                dynamicProp("value", "option.value")
                text("{{ option.label }}")
            }
        }
    }
}
