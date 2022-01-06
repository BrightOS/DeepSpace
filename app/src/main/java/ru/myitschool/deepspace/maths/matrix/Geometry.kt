package ru.myitschool.deepspace.navigator.maths

import ru.myitschool.deepspace.maths.matrix.Vector3D
import kotlin.math.PI
import kotlin.math.ceil
import kotlin.math.floor

const val DEGREES_TO_RADIANS = Math.PI / 180.0
const val RADIANS_TO_DEGREES = 180.0 / Math.PI

const val TWO_PI = 2f *  PI.toFloat()

fun absDown(x: Double): Double {
    return if (x >= 0.0) floor(x) else ceil(x)
}

fun mod(x: Double): Double {
    val factor = x / TWO_PI
    var result = TWO_PI * (factor - absDown(factor))
    if (result < 0.0) {
        result += TWO_PI
    }
    return result
}

fun Matrix3Dimension.multVector(v: Vector3D): Vector3D {
    return Vector3D(
        this.matrix[0][0] * v.x + this.matrix[0][1] * v.y + this.matrix[0][2] * v.z,
        this.matrix[1][0] * v.x + this.matrix[1][1] * v.y + this.matrix[1][2] * v.z,
        this.matrix[2][0] * v.x + this.matrix[2][1] * v.y + this.matrix[2][2] * v.z
    )
}
