package ru.myitschool.nasa_bootcamp.lookbeyond.Math

open class SpaceNavException(_classWhereOccurred: String, _errorMessage: String) : Exception() {
    var classWhereOccurred = _classWhereOccurred
        private set
    var errorMessage = _errorMessage
        private set

}

class UNKNOWN_PLANET(_classWhereOccurred: String, _errorMessage: String) :
    SpaceNavException(_classWhereOccurred, _errorMessage)
