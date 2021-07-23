package ru.myitschool.nasa_bootcamp.ui.animation.core

import android.view.View
import ru.myitschool.nasa_bootcamp.ui.animation.ViewRefresh

abstract class AnimInstance {
    var viewRefresh: ViewRefresh? = null
    var viewsDependencies: MutableList<View> =  mutableListOf()
}
