package ru.myitschool.nasa_bootcamp.utils

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class TrimDelegate : ReadWriteProperty<Any?, String> {

    private var trimmedValue: String = ""

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): String {
        return trimmedValue
    }

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>, value: String
    ) {
        trimmedValue = value.trim()
    }
}

class Example {
    var param: String by TrimDelegate()
}

fun main(){
    val exp = Example()
    print(exp.param)
}
