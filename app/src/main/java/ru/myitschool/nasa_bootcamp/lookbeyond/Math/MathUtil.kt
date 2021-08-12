package ru.myitschool.nasa_bootcamp.lookbeyond.Math

import ru.myitschool.nasa_bootcamp.utils.DEGREES_TO_RADIANS
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.*


open class Vector3D(var x: Double, var y: Double, var z: Double) {

    open fun copy(): Vector3D? {
        return Vector3D(x, y, z)
    }


    fun setupVetor(other: Vector3D) {
        x = other.x
        y = other.y
        z = other.z
    }


    fun length(): Double {
        return sqrt(lengthQuad())
    }
 
    fun lengthQuad(): Double {
        return x * x + y * y + z * z
    }


    fun normalize() {
        val norm = length()
        x /= norm
        y /= norm
        z /= norm
    }


    fun scale(scale: Double) {
        x *= scale
        y *= scale
        z *= scale
    }

    open fun toDoubleArray(): DoubleArray? {
        return doubleArrayOf(x, y, z)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector3D) return false
        return other.x == x && other.y == y && other.z == z
    }


}

class Matrix4D(contents: DoubleArray) {
    val floatArray: FloatArray
        get() {
            val fValue = FloatArray(16)
            for (i in fValue.indices) {
                fValue[i] = values[i].toFloat()
            }
            return fValue
        }
    private val values = DoubleArray(16)
    private var finalValues = ArrayList<DoubleArray>(4)

    private fun zeroMatrix(): ArrayList<DoubleArray> {
        return arrayListOf(
            doubleArrayOf(0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0)
        )
    }


    private fun initFinalValues(): ArrayList<DoubleArray> {
        val finalValues = zeroMatrix()
        var i = 0
        var j = 0
        for (m in values.indices) {
            finalValues[i][j] = values[m]
            j++
            if ((m + 1) % 4 == 0) {
                i++
                j = 0
            }
        }
        return finalValues
    }

    companion object {

        fun createView(lookDir: Vector3D, up: Vector3D, right: Vector3D): Matrix4D {
            return Matrix4D(
                doubleArrayOf(
                    right.x,
                    up.x,
                    -lookDir.x, 0.0,
                    right.y,
                    up.y,
                    -lookDir.y, 0.0,
                    right.z,
                    up.z,
                    -lookDir.z, 0.0, 0.0, 0.0, 0.0, 1.0
                )
            )
        }

        fun createIdentity(): Matrix4D {
            return putCoordsOnDiag(1.0, 1.0, 1.0)
        }

        private fun putCoordsOnDiag(x: Double, y: Double, z: Double): Matrix4D {
            return Matrix4D(
                doubleArrayOf(
                    x, 0.0, 0.0, 0.0,
                    0.0, y, 0.0, 0.0,
                    0.0, 0.0, z, 0.0,
                    0.0, 0.0, 0.0, 1.0
                )
            )
        }

        //Центральная проекция
        //https://songho.ca/opengl/gl_projectionmatrix.html
        fun perspectiveProjection(
            width: Double, height: Double, lookAngle: Double
        ): Matrix4D {
            val near = 0.01
            val far = 10000.0
            val obratnoyeSootnosheniye = height / width
            val overRadiusOfView = 1.0 / tan(lookAngle)

            return Matrix4D(
                doubleArrayOf(
                    obratnoyeSootnosheniye * overRadiusOfView, 0.0, 0.0, 0.0,
                    0.0, overRadiusOfView, 0.0, 0.0,
                    0.0, 0.0, -(far + near) / (far - near), -1.0,
                    0.0, 0.0, -far * near / (far - near), 0.0
                )
            )
        }

        private fun getZerosArray(): DoubleArray {
            return doubleArrayOf(
                0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0
            )
        }

        private fun matrixToList(arr: ArrayList<DoubleArray>): DoubleArray {

            val doublArr: DoubleArray = getZerosArray()

            var m = 0
            for (i in arr.indices) {
                for (j in arr.indices) {
                    m++
                    doublArr[m] = arr[i][j]
                }
            }
            return doublArr
        }

        fun multiplyMatr44(mat1: Matrix4D, mat2: Matrix4D): Matrix4D {

            val matRes = arrayListOf(
                doubleArrayOf(0.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 0.0),
            )

            for (i in 0 until 4)
                for (j in 0 until 4)
                    for (k in 0 until 4) {
                        matRes[i][j] += mat1.finalValues[i][k] * mat2.finalValues[k][j]
                    }


            return Matrix4D(
                matrixToList(matRes)
            )
        }

        fun multiplyMV(mat: Matrix4D, v: Vector3D): Vector3D {
            return Vector3D(
                mat.floatArray[0] * v.x + mat.floatArray[4] * v.y + mat.floatArray[8] * v.z + mat.floatArray[12],
                mat.floatArray[1] * v.x + mat.floatArray[5] * v.y + mat.floatArray[9] * v.z + mat.floatArray[13],
                mat.floatArray[2] * v.x + mat.floatArray[6] * v.y + mat.floatArray[10] * v.z + mat.floatArray[14]
            )
        }
    }


    init {
        System.arraycopy(contents, 0, values, 0, 16)
        finalValues = initFinalValues()
        //  Log.d("MATRIX0000", values[0].toString())
//
//        for (i in finalValues.indices) {
//            for (j in finalValues.indices) {
//                Log.d("MATRIX", finalValues[i][j].toString())
//            }
//        }
    }
}

fun zero(): Vector3D {
    return Vector3D(0.0, 0.0, 0.0)
}

fun dotProduct(p1: Vector3D, p2: Vector3D): Double {
    return p1.x * p2.x + p1.y * p2.y + p1.z * p2.z
}

fun crossProduct(p1: Vector3D, p2: Vector3D): Vector3D {
    return Vector3D(
        p1.y * p2.z - p1.z * p2.y,
        -p1.x * p2.z + p1.z * p2.x,
        p1.x * p2.y - p1.y * p2.x
    )
}


fun length(v: Vector3D): Double {
    return sqrt(lengthSqr(v))
}

fun lengthSqr(v: Vector3D): Double {
    return dotProduct(v, v)
}

fun normalized(v: Vector3D): Vector3D {
    val len = length(v)
    return if (len < 0.000001) {
        zero()
    } else scale(v, 1.0 / len)
}

fun differ(v: Vector3D): Vector3D {
    return Vector3D(-v.x, -v.y, -v.z)
}

fun sum(v1: Vector3D, v2: Vector3D): Vector3D {
    return Vector3D(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z)
}

fun sumNormalToOpposite(v1: Vector3D, v2: Vector3D): Vector3D {
    return sum(v1, differ(v2))
}

fun scale(factor: Double, v: Vector3D): Vector3D {
    return Vector3D(v.x * factor, v.y * factor, v.z * factor)
}

fun scale(v: Vector3D, factor: Double): Vector3D {
    return scale(factor, v)
}


fun realSide(x: Double): Double {
    val result: Double = if (x >= 0.0) floor(x) else ceil(x)
    return result
}

//остаток от деления на 2П, вернет угол от 0 до 2П
fun modPart(x: Double): Double {
    val xDiv2Pi = x / Math.PI * 2

    return if ((Math.PI * 2 * (xDiv2Pi - realSide(xDiv2Pi))) < 0.0)
        Math.PI + (Math.PI * 2 * (xDiv2Pi - realSide(xDiv2Pi))) else (Math.PI * 2 * (xDiv2Pi - realSide(
        xDiv2Pi
    )))
}

//скалярное произв
fun scalarMult(v1: Vector3D, v2: Vector3D): Double {
    return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
}

fun vectorCross(v1: Vector3D, v2: Vector3D): Vector3D {
    return Vector3D(
        v1.y * v2.z - v1.z * v2.y,
        -v1.x * v2.z + v1.z * v2.x,
        v1.x * v2.y - v1.y * v2.x
    )
}

//Масштабировать на какое то число
fun scaleVector(v: Vector3D, scale: Double): Vector3D {
    return Vector3D(scale * v.x, scale * v.y, scale * v.z)
}


fun addVectors(first: Vector3D, second: Vector3D): Vector3D {
    return Vector3D(first.x + second.x, first.y + second.y, first.z + second.z)
}

fun zenitRaDec(utc: Date?, location: LatLong): RaDec {
    val ra = TimeMachine.meanSiderealTime(utc, location.longitude)
    val dec = location.latitude
    return RaDec(ra, dec)
}

//TODO : Сделать общий класс для N-мерных матриц
class Matrix3D {

    fun getZerosMatrix(): ArrayList<DoubleArray> {
        return arrayListOf(
            doubleArrayOf(0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0)
        )
    }

    var arr: ArrayList<DoubleArray> = getZerosMatrix()

    constructor(_arr: ArrayList<DoubleArray>) {
        arr = _arr
    }


    constructor(v1: Vector3D, v2: Vector3D, v3D: Vector3D) {

        arr[0][0] = v1.x
        arr[0][1] = v1.y
        arr[0][2] = v1.z
        arr[1][0] = v2.x
        arr[1][1] = v2.y
        arr[1][2] = v2.z
        arr[2][0] = v3D.x
        arr[2][1] = v3D.y
        arr[2][2] = v3D.z
    }


    companion object {
        //единич
        val matrixE: Matrix3D
            get() = Matrix3D(
                arrayListOf(
                    doubleArrayOf(1.0, 0.0, 0.0),
                    doubleArrayOf(0.0, 1.0, 0.0),
                    doubleArrayOf(0.0, 0.0, 1.0)
                )
            )
    }
}

fun matrixMultiply(m1: Matrix3D, m2: Matrix3D): Matrix3D {
    return Matrix3D(
        arrayListOf(
            doubleArrayOf(
                m1.arr[0][0] * m2.arr[0][0] + m1.arr[0][1] * m2.arr[1][0] + m1.arr[0][2] * m2.arr[2][0],
                m1.arr[0][0] * m2.arr[0][1] + m1.arr[0][1] * m2.arr[1][1] + m1.arr[0][2] * m2.arr[2][1],
                m1.arr[0][0] * m2.arr[0][2] + m1.arr[0][1] * m2.arr[0][2] + m1.arr[0][2] * m2.arr[2][2]
            ),
            doubleArrayOf(
                m1.arr[1][0] * m2.arr[0][0] + m1.arr[1][1] * m2.arr[1][0] + m1.arr[1][2] * m2.arr[2][0],
                m1.arr[1][0] * m2.arr[0][1] + m1.arr[1][1] * m2.arr[1][1] + m1.arr[1][2] * m2.arr[2][1],
                m1.arr[1][0] * m2.arr[0][2] + m1.arr[1][1] * m2.arr[1][2] + m1.arr[1][2] * m2.arr[2][2]
            ),
            doubleArrayOf(
                m1.arr[2][0] * m2.arr[0][0] + m1.arr[2][1] * m2.arr[1][0] + m1.arr[2][2] * m2.arr[2][0],
                m1.arr[2][0] * m2.arr[0][1] + m1.arr[2][1] * m2.arr[1][1] + m1.arr[2][2] * m2.arr[2][1],
                m1.arr[2][0] * m2.arr[0][2] + m1.arr[2][1] * m2.arr[1][2] + m1.arr[2][2] * m2.arr[2][2]
            )
        )
    )
}

//хардкожжж
fun matrixVectorMultiply(matrix: Matrix3D, vector: Vector3D): Vector3D {
    return Vector3D(
        matrix.arr[0][0] * vector.x + matrix.arr[0][1] * vector.y + matrix.arr[0][2] * vector.z,
        matrix.arr[1][0] * vector.x + matrix.arr[1][1] * vector.y + matrix.arr[1][2] * vector.z,
        matrix.arr[2][0] * vector.x + matrix.arr[2][1] * vector.y + matrix.arr[2][2] * vector.z
    )
}


fun calculateRotationMatrix(degrees: Double): Matrix3D {
    val cosD = cos(degrees * DEGREES_TO_RADIANS)
    val sinD = sin(degrees * DEGREES_TO_RADIANS)
    val oneMinusCosD = 1f - cosD

    return Matrix3D(
        arrayListOf(
            doubleArrayOf(oneMinusCosD + cosD, oneMinusCosD + sinD, oneMinusCosD - sinD),
            doubleArrayOf(oneMinusCosD - sinD, oneMinusCosD + cosD, oneMinusCosD + sinD),
            doubleArrayOf(oneMinusCosD + sinD, oneMinusCosD - sinD, oneMinusCosD + cosD)
        )
    )
}

