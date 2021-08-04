package ru.myitschool.nasa_bootcamp.lookbeyond.maths

import android.util.Log
import kotlin.collections.ArrayList
import kotlin.math.*


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


//Целая часть числа
fun real_side(x: Double): Double {
    return if (x >= 0.0) floor(x) else ceil(x)
}

//Масштабировать на какое то число
fun scaleVector(v: Vector3D, scale: Double): Vector3D {
    return Vector3D(scale * v.x, scale * v.y, scale * v.z)
}

//сумма вектороф ( по координатам складываем)
fun sumVector(first: Vector3D, second: Vector3D): Vector3D {
    return Vector3D(first.x + second.x, first.y + second.y, first.z + second.z)
}


//Умножить две матрицы 3x3
fun matrixMultiply(m1: Matrix3D, m2: Matrix3D): Matrix3D {
    return Matrix3D(
        arrayListOf(
            doubleArrayOf(
                m1.arr[0][0] * m2.arr[0][0] + m1.arr[0][1] * m2.arr[1][0] + m1.arr[0][2] * m2.arr[2][0],
                m1.arr[0][0] * m2.arr[0][1] + m1.arr[0][1] * m2.arr[1][1] + m1.arr[0][2] * m2.arr[2][1],
                m1.arr[0][0] * m2.arr[0][2] + m1.arr[0][1] * m2.arr[1][2] + m1.arr[0][2] * m2.arr[2][2]
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

//Матрица на вектор
fun matrixVectorMultiplyScalars(matrix: Matrix3D, v: Vector3D): Vector3D {
    return Vector3D(
        matrix.arr[0][0] * v.x + matrix.arr[0][1] * v.y + matrix.arr[0][2] * v.z,
        matrix.arr[1][0] * v.x + matrix.arr[1][1] * v.y + matrix.arr[1][2] * v.z,
        matrix.arr[2][0] * v.x + matrix.arr[2][1] * v.y + matrix.arr[2][2] * v.z
    )
}


// Градусы в радианты
const val DEGREES_TO_RADIANS = Math.PI / 180.0f

// Радианы в градусы
const val RADIANS_TO_DEGREES = 180.0f / Math.PI


class Matrix4x4(contents: DoubleArray) {
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

        //Центральная проекция
        //https://songho.ca/opengl/gl_projectionmatrix.html
        fun perspectiveProjection(
            width: Double, height: Double, lookAngle: Double
        ): Matrix4x4 {
            val near = 0.01
            val far = 10000.0
            val obratnoyeSootnosheniye = height / width
            val overRadiusOfView = 1.0 / tan(lookAngle)

            return Matrix4x4(
                doubleArrayOf(
                    obratnoyeSootnosheniye * overRadiusOfView, 0.0, 0.0, 0.0,
                    0.0, overRadiusOfView, 0.0, 0.0,
                    0.0, 0.0, -(far + near) / (far - near), -1.0,
                    0.0, 0.0, -far * near / (far - near), 0.0
                )
            )
        }

        fun createMatrixViaLookDirection(
            lookDir: Vector3D,
            upVector: Vector3D,
            rightVector: Vector3D
        ): Matrix4x4 {
            return Matrix4x4(
                doubleArrayOf(
                    rightVector.x, upVector.x, -lookDir.x, 0.0,
                    rightVector.y, upVector.y, -lookDir.y, 0.0,
                    rightVector.z, upVector.z, -lookDir.z, 0.0,
                    0.0, 0.0, 0.0, 1.0
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

        fun multiplyMatr44(mat1: Matrix4x4, mat2: Matrix4x4): Matrix4x4 {

            val matRes = ArrayList<DoubleArray>(4)
            matRes[0] = getZerosArray()
            matRes[1] = getZerosArray()
            matRes[2] = getZerosArray()
            matRes[3] = getZerosArray()

            for (i in 0 until 4)
                for (j in 0 until 4)
                    for (k in 0 until 4) {
                        matRes[i][j] += mat1.finalValues[i][k] * mat2.finalValues[k][j]
                    }


            return Matrix4x4(
                matrixToList(matRes)
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


open class Vector3D(var x: Double, var y: Double, var z: Double) {

    fun getZeroVector(): DoubleArray {
        return doubleArrayOf(0.0, 0.0, 0.0)
    }

    var array: DoubleArray = getZeroVector()

    init {
        array[0] = x
        array[1] = y
        array[2] = z
    }

    constructor(_array: DoubleArray) : this(_array[0], _array[1], _array[2]) {
        array = _array
    }

    open fun copy(): Vector3D? {
        return Vector3D(x, y, z)
    }

    fun setupVector(other: Vector3D) {
        x = other.x
        y = other.y
        z = other.z
    }

    //Длина вектора
    fun length(): Double {
        return Math.sqrt(length2())
    }

    // Квадрат длины вектора
    fun length2(): Double {
        return x * x + y * y + z * z
    }

    //Норма вектора
    fun normalize() {
        val norm = length()
        x /= norm
        y /= norm
        z /= norm
    }

    //масштабирование вектора
    fun scale(scale: Double) {
        x *= scale
        y *= scale
        z *= scale
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector3D) return false
        val other = other

        return other.x == x && other.y == y && other.z == z
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + array.contentHashCode()
        return result
    }
}


fun normalized(v: Vector3D): Vector3D {
    val len = length(v)
    return if (len < 0.000001f) {
        unitVector()
    } else scale(v, 1.0 / len)
}

fun crossV(p1: Vector3D, p2: Vector3D): Vector3D {
    return Vector3D(
        p1.y * p2.z - p1.z * p2.y,
        -p1.x * p2.z + p1.z * p2.x,
        p1.x * p2.y - p1.y * p2.x
    )
}

//единичный вектор
fun unitVector(): Vector3D {
    return Vector3D(0.0, 0.0, 0.0)
}

//скалярное произведегние
fun dotProduct(p1: Vector3D, p2: Vector3D): Double {
    return p1.x * p2.x + p1.y * p2.y + p1.z * p2.z
}

//длина вектора
fun length(v: Vector3D): Double {
    return Math.sqrt(lengthSqr(v))
}

fun lengthSqr(v: Vector3D): Double {
    return dotProduct(v, v)
}

fun mirrorVector(v: Vector3D): Vector3D {
    return Vector3D(-v.x, -v.y, -v.z)
}

//масштабирование
fun scale(v: Vector3D, koef: Double): Vector3D {
    return Vector3D(v.x * koef, v.y * koef, v.z * koef)
}


fun sum(v1: Vector3D, v2: Vector3D): Vector3D {
    return Vector3D(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z)
}

fun difference(v1: Vector3D, v2: Vector3D): Vector3D {
    return sum(v1, mirrorVector(v2))
}
