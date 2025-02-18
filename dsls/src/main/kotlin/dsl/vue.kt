package org.example.dsl

class VueComponent(val name: String) {
    private val props = mutableMapOf<String, Any?>()
    private val dynamicProps = mutableMapOf<String, String>()
    private val events = mutableMapOf<String, String>()
    private val directives = mutableMapOf<String, String>()
    private val computedProperties = mutableMapOf<String, String>()
    private val watchers = mutableMapOf<String, String>()
    private val children = mutableListOf<VueComponent>()
    private val slots = mutableMapOf<String, VueComponent>()
    private var textContent: String? = null

    fun prop(key: String, value: Any?) {
        props[key] = value
    }

    fun dynamicProp(key: String, expression: String) {
        dynamicProps[key] = expression
    }

    fun event(name: String, handler: String) {
        events[name] = handler
    }

    fun directive(name: String, expression: String) {
        directives[name] = expression
    }

    fun vModel(variable: String) {
        dynamicProp("v-model", variable)
    }

    fun vIf(condition: String) {
        directive("v-if", condition)
    }

    fun vShow(condition: String) {
        directive("v-show", condition)
    }

    fun vFor(loopExpression: String) {
        directive("v-for", loopExpression)
    }

    fun computed(name: String, expression: String) {
        computedProperties[name] = expression
    }

    fun watch(property: String, handler: String) {
        watchers[property] = handler
    }

    fun text(value: String) {
        textContent = value
    }

    fun child(name: String, init: VueComponent.() -> Unit) {
        val component = VueComponent(name).apply(init)
        children.add(component)
    }

    fun slot(name: String, scopedProps: String? = null, init: VueComponent.() -> Unit) {
        val component = VueComponent("template").apply {
            prop("v-slot${if (scopedProps != null) ":$name" else ""}", scopedProps ?: name)
            init()
        }
        slots[name] = component
    }

    fun build(): String {
        val propsString = props.entries.joinToString(" ") { """${it.key}="${it.value}"""" }
        val dynamicPropsString = dynamicProps.entries.joinToString(" ") { """:${it.key}="${it.value}"""" }
        val eventsString = events.entries.joinToString(" ") { """@${it.key}="${it.value}"""" }
        val directivesString = directives.entries.joinToString(" ") { """${it.key}="${it.value}"""" }
        val childrenString = children.joinToString("\n") { it.build() }
        val slotsString = slots.values.joinToString("\n") { it.build() }

        return if (textContent != null) {
            "<$name $propsString $dynamicPropsString $eventsString $directivesString>$textContent</$name>"
        } else {
            "<$name $propsString $dynamicPropsString $eventsString $directivesString>\n$childrenString\n$slotsString\n</$name>"
        }
    }
}

fun vueComponent(name: String, init: VueComponent.() -> Unit): VueComponent {
    return VueComponent(name).apply(init)
}
