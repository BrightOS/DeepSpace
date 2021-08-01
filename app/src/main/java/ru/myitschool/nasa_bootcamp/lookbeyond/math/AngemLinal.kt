package ru.myitschool.nasa_bootcamp.lookbeyond.math

import ru.myitschool.nasa_bootcamp.lookbeyond.math.astronomy.LatLong
import java.util.*

// Градусы в радианты
const val DEGREES_TO_RADIANS = Math.PI / 180.0f

// Радианы в градусы
const val RADIANS_TO_DEGREES = 180.0f / Math.PI

//Целая часть числа
fun real_side(x: Double): Double {
    val result: Double
    result = if (x >= 0.0) Math.floor(x) else Math.ceil(x)
    return result
}


//остаток от деления на 2П, вернет угол от 0 до 2П
fun mod(x: Double): Double {
    val xDiv2Pi = x / Math.PI * 2

    return if ((Math.PI * 2 * (xDiv2Pi - real_side(xDiv2Pi))) < 0.0)
        Math.PI + (Math.PI * 2 * (xDiv2Pi - real_side(xDiv2Pi))) else (Math.PI * 2 * (xDiv2Pi - real_side(
        xDiv2Pi
    )))
}

//скалярное произв
fun scalarMult(v1: Vector3D, v2: Vector3D): Double {
    return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
}

fun vectorCross(v1: Vector3D, v2: Vector3D): Vector3D {
    return Vector3D(
        v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x
    )
}

//Масштабировать на какое то число
fun scaleVector(v: Vector3D, scale: Double): Vector3D {
    return Vector3D(scale * v.x, scale * v.y, scale * v.z)
}

//сумма вектороф ( по координатам складываем)
fun sumVector(first: Vector3D, second: Vector3D): Vector3D {
    return Vector3D(first.x + second.x, first.y + second.y, first.z + second.z)
}

//CrossProd(v1,v2)/||v1|*|v2|| (как в sklearn)
fun cosineSimilarity(v1: Vector3D, v2: Vector3D): Double {
    return (scalarMult(v1, v2) / Math.sqrt(
        scalarMult(v1, v1) * scalarMult(v2, v2)
    ))
}

/**
 * Compute celestial coordinates of zenith from utc, lat long.
 */
fun calculateRADecOfZenith(utc: Date?, location: LatLong): RaDec {
    // compute overhead RA in degrees
    val my_ra = TimeMachine.meanSiderealTime(utc, location.longitude) //
    val my_dec = location.latitude
    return RaDec(my_ra, my_dec)
}

//Умножить две матрицы 3x3
fun matrixMultiply(m1: Matrix3D, m2: Matrix3D): Matrix3D {
    return Matrix3D(
        m1.xx * m2.xx + m1.xy * m2.yx + m1.xz * m2.zx,
        m1.xx * m2.xy + m1.xy * m2.yy + m1.xz * m2.zy,
        m1.xx * m2.xz + m1.xy * m2.yz + m1.xz * m2.zz,
        m1.yx * m2.xx + m1.yy * m2.yx + m1.yz * m2.zx,
        m1.yx * m2.xy + m1.yy * m2.yy + m1.yz * m2.zy,
        m1.yx * m2.xz + m1.yy * m2.yz + m1.yz * m2.zz,
        m1.zx * m2.xx + m1.zy * m2.yx + m1.zz * m2.zx,
        m1.zx * m2.xy + m1.zy * m2.yy + m1.zz * m2.zy,
        m1.zx * m2.xz + m1.zy * m2.yz + m1.zz * m2.zz
    )
}

//Матрица на вектор
fun matrixVectorMultiply(m: Matrix3D, v: Vector3D): Vector3D {
    return Vector3D(
        m.xx * v.x + m.xy * v.y + m.xz * v.z,
        m.yx * v.x + m.yy * v.y + m.yz * v.z,
        m.zx * v.x + m.zy * v.y + m.zz * v.z
    )
}


//Поворот матрицы на degrees градусов
fun calculateRotationMatrix(degrees: Double): Matrix3D {

    val cosD = Math.cos(degrees * DEGREES_TO_RADIANS)
    val sinD = Math.sin(degrees * DEGREES_TO_RADIANS)
    val oneMinusCosD = 1f - cosD

    val xm = oneMinusCosD
    val ym = oneMinusCosD
    val zm = oneMinusCosD

    val getXym = ym
    val getYzm = zm
    val getZxm = xm

    return Matrix3D(
        xm + cosD, getXym + sinD, getZxm - sinD,
        getXym - sinD, ym + cosD, getYzm + sinD,
        getZxm + sinD, getYzm - sinD, zm + cosD
    )
}
