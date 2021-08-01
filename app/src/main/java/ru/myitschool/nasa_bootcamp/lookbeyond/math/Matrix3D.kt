package ru.myitschool.nasa_bootcamp.lookbeyond.math

class Matrix3D : Cloneable {

    var xx = 0.0
    var xy = 0.0
    var xz = 0.0
    var yx = 0.0
    var yy = 0.0
    var yz = 0.0
    var zx = 0.0
    var zy = 0.0
    var zz = 0.0


    constructor(
        xx: Double, xy: Double, xz: Double,
        yx: Double, yy: Double, yz: Double,
        zx: Double, zy: Double, zz: Double
    ) {
        this.xx = xx
        this.xy = xy
        this.xz = xz
        this.yx = yx
        this.yy = yy
        this.yz = yz
        this.zx = zx
        this.zy = zy
        this.zz = zz
    }


    constructor(v1: Vector3D, v2: Vector3D, v3: Vector3D, columnVectors: Boolean = true) {
        if (columnVectors) {
            xx = v1.x
            yx = v1.y
            zx = v1.z
            xy = v2.x
            yy = v2.y
            zy = v2.z
            xz = v3.x
            yz = v3.y
            zz = v3.z
        } else {
            xx = v1.x
            xy = v1.y
            xz = v1.z
            yx = v2.x
            yy = v2.y
            yz = v2.z
            zx = v3.x
            zy = v3.y
            zz = v3.z
        }
    }

    val det: Double
        get() = xx * yy * zz + xy * yz * zx + xz * yx * zy - xx * yz * zy - yy * zx * xz - zz * xy * yx

    val inverse: Matrix3D?
        get() {
            val det = det
            return if (det.toDouble() == 0.0) null else Matrix3D(
                (yy * zz - yz * zy) / det, (xz * zy - xy * zz) / det, (xy * yz - xz * yy) / det,
                (yz * zx - yx * zz) / det, (xx * zz - xz * zx) / det, (xz * yx - xx * yz) / det,
                (yx * zy - yy * zx) / det, (xy * zx - xx * zy) / det, (xx * yy - xy * yx) / det
            )
        }


    //Транспонирование
    fun tranps() {
        var tmp: Double = xy
        yx.also { xy = it }
        yx = tmp
        xz.also { tmp = it }
        xz = zx
        tmp.also { zx = it }
        tmp = yz
        zy.also { yz = it }
        zy = tmp
    }

    companion object {
        //единич
        val idMatrix: Matrix3D
            get() = Matrix3D(1.0, 0.0, 0.0,
                0.0, 1.0, 0.0,
                0.0, 0.0, 1.0)
    }
}
