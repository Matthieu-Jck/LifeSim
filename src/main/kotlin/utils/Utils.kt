package utils

class Utils {
    companion object {
        fun roundToNearestTenth(number: Double): Double {
            return Math.round(number * 10) / 10.0
        }
    }
}
