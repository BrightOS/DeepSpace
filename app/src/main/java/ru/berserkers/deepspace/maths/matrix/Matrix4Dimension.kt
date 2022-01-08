package ru.berserkers.deepspace.navigator.maths

import ru.berserkers.deepspace.maths.matrix.Vector3D
import kotlin.math.tan

fun multMatrix(
    result: List<DoubleArray>,
    n: List<DoubleArray>,
    m: List<DoubleArray>,
) {
    for (i in n.indices)
        for (j in n[i].indices)
            for (k in m.indices)
                result[i][j] += n[i][k] * m[k][j]
}

class Matrix4Dimension(contents: DoubleArray) {
    val doubleArray = DoubleArray(16)
    var floatArray = FloatArray(16)

    operator fun times(other: Matrix4Dimension): Matrix4Dimension {
        val m = this.simpleArrayToComplex()
        val n = other.simpleArrayToComplex()

        val result: List<DoubleArray> = listOf(
            doubleArrayOf(0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0)
        )

        multMatrix(result, m, n)

        return Matrix4Dimension(
            doubleArrayOf(
                result[0][0], result[0][1], result[0][2], result[0][3],
                result[1][0], result[1][1], result[1][2], result[1][3],
                result[2][0], result[2][1], result[2][2], result[2][3],
                result[3][0], result[3][1], result[3][2], result[3][3],
            )
        )
    }

    companion object {

        fun createScaling(x: Double, y: Double, z: Double): Matrix4Dimension {
            return Matrix4Dimension(
                doubleArrayOf(
                    x, 0.0, 0.0, 0.0,
                    0.0, y, 0.0, 0.0,
                    0.0, 0.0, z, 0.0,
                    0.0, 0.0, 0.0, 1.0))
        }

        fun createTranslation(x: Double, y: Double, z: Double): Matrix4Dimension {
            return Matrix4Dimension(
                doubleArrayOf(
                    1.0, 0.0, 0.0, 0.0,
                    0.0, 1.0, 0.0, 0.0,
                    0.0, 0.0, 1.0, 0.0,
                    x, y, z, 1.0
                ))
        }

        fun createPerspectiveProjection(width: Double, height: Double, fovyInRadians: Double): Matrix4Dimension {
            val near = 0.01
            val far = 10000.0
            val inverseAspectRatio = height / width
            val oneOverTanHalfRadiusOfView = 1.0 / tan(fovyInRadians)
            return Matrix4Dimension(
                doubleArrayOf(
                    inverseAspectRatio * oneOverTanHalfRadiusOfView, 0.0, 0.0, 0.0, 0.0,
                    oneOverTanHalfRadiusOfView, 0.0, 0.0, 0.0, 0.0,
                    -(far + near) / (far - near), -1.0, 0.0, 0.0,
                    -2 * far * near / (far - near), 0.0
                ))
        }

        fun createView(lookDir: Vector3D, up: Vector3D, right: Vector3D): Matrix4Dimension {
            return Matrix4Dimension(
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
                ))
        }

        fun Matrix4Dimension.simpleArrayToComplex(): List<DoubleArray> {
            val res = listOf(
                doubleArrayOf(0.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 0.0)
            )

            var count = 0
            val size = 4
            for (i in 0 until size) {
                for (j in 0 until size) {
                    res[i][j] = this.doubleArray[count]
                    count++
                }
            }
            return res
        }
    }

    init {
        assert(contents.size == 16)
        System.arraycopy(contents, 0, doubleArray, 0, 16)

        val floatContent = FloatArray(16)
        for ((i, value) in contents.withIndex()) {
            floatContent[i] = value.toFloat()
        }
        floatArray = floatContent.clone()
    }
}

class Matrix3Dimension : Cloneable {

    var matrix: List<DoubleArray> = listOf(
        doubleArrayOf(0.0, 0.0, 0.0),
        doubleArrayOf(0.0, 0.0, 0.0),
        doubleArrayOf(0.0, 0.0, 0.0),
    )

    operator fun times(other: Matrix3Dimension): Matrix3Dimension {
        val m = this.matrix
        val n = other.matrix

        val result: List<DoubleArray> = listOf(
            doubleArrayOf(0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0),
        )

        multMatrix(result, m, n)

        return Matrix3Dimension(
            result[0][0], result[0][1], result[0][2],
            result[1][0], result[1][1], result[1][2],
            result[2][0], result[2][1], result[2][2]
        )
    }

    constructor(
        xx: Double, xy: Double, xz: Double,
        yx: Double, yy: Double, yz: Double,
        zx: Double, zy: Double, zz: Double,
    ) {
        matrix = listOf(
            doubleArrayOf(xx, xy, xz),
            doubleArrayOf(yx, yy, yz),
            doubleArrayOf(zx, zy, zz),
        )

    }

    @JvmOverloads
    constructor(v1: Vector3D, v2: Vector3D, v3: Vector3D, columnVectors: Boolean = true) {
        matrix = if (columnVectors)
            listOf(
                doubleArrayOf(v1.x, v2.x, v3.x),
                doubleArrayOf(v1.y, v2.y, v3.y),
                doubleArrayOf(v1.z, v2.z, v3.z),
            )
        else
            listOf(
                doubleArrayOf(v1.x, v1.y, v1.z),
                doubleArrayOf(v2.x, v2.y, v2.z),
                doubleArrayOf(v3.x, v3.y, v3.z),
            )
    }

    public override fun clone(): Matrix3Dimension {
        return Matrix3Dimension(
            matrix[0][0], matrix[0][1], matrix[0][2],
            matrix[1][0], matrix[1][1], matrix[1][2],
            matrix[2][0], matrix[2][1], matrix[2][2]
        )
    }

    companion object {
        val idMatrix: Matrix3Dimension
            get() = Matrix3Dimension(1.0, 0.0, 0.0,
                0.0, 1.0, 0.0,
                0.0, 0.0, 1.0)
    }
}
