package org.example.component.annotations

import org.example.dsl.VueComponent
import org.example.dsl.vueComponent
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

fun buildVueComponent(dtoClass: KClass<*>): VueComponent {
    return vueComponent("div") {
        prop("id", "app")

        for (property in dtoClass.memberProperties) {
            val name = property.name

            property.findAnnotation<InputText>()?.let { annotation ->
                child("input-text", inputText(label = annotation.label, vModel = name))
            }

            property.findAnnotation<SelectItem>()?.let { annotation ->
                child(selectItem(label = annotation.label, vModel = name, list = annotation.list))
            }
        }
    }
}
