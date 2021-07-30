package ru.myitschool.nasa_bootcamp.lookbeyond.math

class Vector3D (var x: Float, var y: Float, var z: Float) {

    open fun copy(): Vector3D? {
        return Vector3D(x, y, z)
    }

    fun setupVector(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    fun setupVector(other: Vector3D) {
        x = other.x
        y = other.y
        z = other.z
    }

    //Длина вектора
    fun length(): Float {
        return Math.sqrt(length2().toDouble()).toFloat()
    }

    // Квадрат длины вектора
    fun length2(): Float {
        return x * x + y * y + z * z
    }

    //Норма вектора
    fun normalize() {
        val norm = length()
        x = x / norm
        y = y / norm
        z = z / norm
    }

    //масштабирование вектора
    fun scale(scale: Float) {
        x = x * scale
        y = y * scale
        z = z * scale
    }

    open fun toFloatArray(): FloatArray? {
        return floatArrayOf(x, y, z)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector3D) return false
        val other = other

        return other.x == x && other.y == y && other.z == z
    }
}
