package ru.myitschool.deepspace.maths.matrix

import ru.myitschool.deepspace.navigator.maths.DEGREES_TO_RADIANS
import ru.myitschool.deepspace.navigator.maths.Matrix3Dimension
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

open class Vector3D(var x: Double, var y: Double, var z: Double) {

    operator fun plus(other: Vector3D): Vector3D {
        return Vector3D(this.x + other.x, this.y + other.y, this.z + other.z)
    }


    operator fun times(other: Vector3D): Vector3D {
        return Vector3D(
            this.y * other.z - this.z * other.y,
            -this.x * other.z + this.z * other.x,
            this.x * other.y - this.y * other.x
        )
    }

    fun update(other: Vector3D) {
        x = other.x
        y = other.y
        z = other.z
    }

    fun length(): Double {
        return x * x + y * y + z * z
    }

    fun norma(): Vector3D {
        val norm = sqrt(length())
        x /= norm
        y /= norm
        z /= norm
        return this
    }

    fun scale(scale: Double) : Vector3D {
        x *= scale
        y *= scale
        z *= scale
        return this
    }

    open fun toFloatArray(): FloatArray {
        return floatArrayOf(x.toFloat(), y.toFloat(), z.toFloat())
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector3D) return false
        return other.x == x && other.y == y && other.z == z
    }
}

fun Vector3D.scalarTimes(v2: Vector3D): Double {
    return this.x * v2.x + this.y * v2.y + this.z * v2.z
}

fun scaleVector(v: Vector3D, scale: Double): Vector3D {
    return Vector3D(scale * v.x, scale * v.y, scale * v.z)
}

fun cosineSimilarity(v1: Vector3D, v2: Vector3D): Double {
    return (v1.scalarTimes(v2) / sqrt(v1.scalarTimes(v1) * v2.scalarTimes(v2)))
}

fun zeroVector(): Vector3D {
    return Vector3D(0.0, 0.0, 0.0)
}

fun Vector3D.mult(p2: Vector3D): Vector3D {
    return Vector3D(
        this.y * p2.z - this.z * p2.y,
        -this.x * p2.z + this.z * p2.x,
        this.x * p2.y - this.y * p2.x
    )
}

fun Vector3D.normalized(): Vector3D {
    val len = sqrt(this.scalarTimes(this))
    return if (len < 0.000001) {
        zeroVector()
    } else scale(this, 1.0 / len)
}


fun mirror(v: Vector3D): Vector3D {
    return Vector3D(-v.x, -v.y, -v.z)
}

fun sum(v1: Vector3D, v2: Vector3D): Vector3D {
    return Vector3D(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z)
}

fun difference(v1: Vector3D, v2: Vector3D): Vector3D {
    return sum(v1, mirror(v2))
}

fun scale(factor: Double, v: Vector3D): Vector3D {
    return Vector3D(v.x * factor, v.y * factor, v.z * factor)
}

fun scale(v: Vector3D, factor: Double): Vector3D {
    return scale(factor, v)
}

fun rotationMatrix(degrees: Double, axis: Vector3D): Matrix3Dimension {
    val cosD = cos(degrees * DEGREES_TO_RADIANS)
    val sinD = sin(degrees * DEGREES_TO_RADIANS)
    with(axis) {
        val xs = x * sinD
        val ys = y * sinD
        val zs = z * sinD
        val xm = x * (1f - cosD)
        val ym = y * (1f - cosD)
        val zm = z * (1f - cosD)
        val xym = x * ym
        val yzm = y * zm
        val zxm = z * xm
        return Matrix3Dimension(
            axis.x * xm + cosD, xym + zs, zxm - ys,
            xym - zs, axis.y * ym + cosD, yzm + xs,
            zxm + ys, yzm - xs, axis.z * zm + cosD
        )
    }
}
