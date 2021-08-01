package ru.myitschool.nasa_bootcamp.lookbeyond.math

open class Vector3D(var x: Double, var y: Double, var z: Double) {

    open fun copy(): Vector3D? {
        return Vector3D(x, y, z)
    }

    fun setupVector(x: Double, y: Double, z: Double) {
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
        x = x / norm
        y = y / norm
        z = z / norm
    }

    //масштабирование вектора
    fun scale(scale: Double) {
        x = x * scale
        y = y * scale
        z = z * scale
    }

    open fun toDoubleArray(): DoubleArray? {
        return doubleArrayOf(x, y, z)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector3D) return false
        val other = other

        return other.x == x && other.y == y && other.z == z
    }


}