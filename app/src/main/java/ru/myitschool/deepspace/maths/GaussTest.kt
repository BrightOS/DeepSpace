package ru.myitschool.deepspace.maths

import kotlin.math.abs

fun swapLines(_i: Int, _j: Int, _matr: List<FloatArray>): List<FloatArray> {
    val matr = _matr
    for (j in matr.indices) {
        val temp = matr[_i][j]
        matr[_i][j] = matr[_j][j]
        matr[_j][j] = temp
    }
    return matr
}

fun List<FloatArray>.toStringMatrix() {
    for (i in this.indices) {
        for (j in this.indices) {
            print("${this[i][j]} ")
        }
        println()
    }
}

class Determinant internal constructor(private val matrix: List<FloatArray>) {
    var sign = 1
        private set

    fun determinant(): Float {
        val deter: Float = if (isUpperTriangular || isLowerTriangular) multiplyDiameter() * sign else {
            makeTriangular()
            multiplyDiameter() * sign
        }
        return deter
    }

    fun makeTriangular() {
        for (j in matrix.indices) {
            sortCol(j)
            for (i in matrix.size - 1 downTo j + 1) {
                if (matrix[i][j] == 0f) continue
                val x = matrix[i][j]
                val y = matrix[i - 1][j]
                multiplyRow(i, -y / x)
                addRow(i, i - 1)
                multiplyRow(i, -x / y)
            }
        }
    }

    val isUpperTriangular: Boolean
        get() {
            if (matrix.size < 2) return false
            for (i in matrix.indices) {
                for (j in 0 until i) {
                    if (matrix[i][j] != 0f) return false
                }
            }
            return true
        }
    val isLowerTriangular: Boolean
        get() {
            if (matrix.size < 2) return false
            for (j in matrix.indices) {
                var i = 0
                while (j > i) {
                    if (matrix[i][j] != 0f) return false
                    i++
                }
            }
            return true
        }

    fun multiplyDiameter(): Float {
        var result = 1f
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                if (i == j) result *= matrix[i][j]
            }
        }
        return result
    }

    fun addRow(row1: Int, row2: Int) {
        for (j in matrix.indices) matrix[row1][j] += matrix[row2][j]
    }

    fun multiplyRow(row: Int, num: Float) {
        if (num < 0) sign *= -1
        for (j in matrix.indices) {
            matrix[row][j] *= num
        }
    }

    fun sortCol(col: Int) {
        for (i in matrix.size - 1 downTo col) {
            for (k in matrix.size - 1 downTo col) {
                val tmp1 = matrix[i][col]
                val tmp2 = matrix[k][col]
                if (abs(tmp1) < abs(tmp2)) replaceRow(i, k)
            }
        }
    }

    fun replaceRow(row1: Int, row2: Int) {
        if (row1 != row2) sign *= -1
        val tempRow = FloatArray(matrix.size)
        for (j in matrix.indices) {
            tempRow[j] = matrix[row1][j]
            matrix[row1][j] = matrix[row2][j]
            matrix[row2][j] = tempRow[j]
        }
    }
}
