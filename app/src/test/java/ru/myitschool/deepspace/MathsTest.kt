package ru.myitschool.deepspace

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.myitschool.deepspace.navigator.maths.Matrix4Dimension
import ru.myitschool.deepspace.navigator.maths.Matrix4Dimension.Companion.simpleArrayToComplex


class MathsTest {
    companion object {
        const val EPSILON = 0.000000000000000001
    }

    @Test
    fun matrixConvertToComplex() {
        val matrix = Matrix4Dimension(doubleArrayOf(1.0, 2.0, 3.0, 4.0,
            5.0, 6.0, 7.0, 8.0,
            5.0, 6.0, 7.0, 8.0,
            5.0, 6.0, 7.0, 8.0))
        val elementsInFirstLine = doubleArrayOf(1.0, 2.0, 3.0, 4.0).reduce { prev, cur -> prev + cur }

        val result = matrix.simpleArrayToComplex()
        val countedResult = result[0].reduce { prev, cur -> prev + cur }

        assertEquals(elementsInFirstLine, countedResult, EPSILON)
    }

    @Test
    fun testMatrixMult() {
        val m1 = Matrix4Dimension(doubleArrayOf(
            1.0, 1.0, 1.0, 1.0,
            2.0, 2.0, 2.0, 2.0,
            3.0, 3.0, 3.0, 3.0,
            4.0, 4.0, 4.0, 4.0,
        ))

        val m2 = Matrix4Dimension(doubleArrayOf(
            5.0, 6.0, 1.0, 4.0,
            5.0, 6.0, 2.0, 4.0,
            5.0, 6.0, 3.0, 4.0,
            5.0, 6.0, 4.0, 4.0,
        ))

        val mExpected = Matrix4Dimension(doubleArrayOf(
            20.0, 24.0, 10.0, 16.0,
            40.0, 48.0, 20.0, 32.0,
            60.0, 72.0, 30.0, 48.0,
            80.0, 96.0, 40.0, 64.0,
        ))

        val res = m1 * m2

        val expected = mExpected.doubleArray.reduce { acc, d -> acc + d }
        val got = res.doubleArray.reduce { acc, d -> acc + d }
        assertEquals(expected, got, EPSILON)
    }
}

