package model

import java.util.*
import kotlin.math.atan2
import kotlin.math.sqrt

class LifeForm(
    val species: SpeciesEnum,
    var posX: Float,  // Replacing position with individual float coordinates
    var posY: Float,
    private var baseWeight: Float,
    var efficiency: Float,
    var acceleration: Float,
    var attraction: Float,
    var sight: Float,
    var reproductionMutationValue: Float,
) {
    val id: String = UUID.randomUUID().toString()
    private var energy: Float = 100.0f
    var speed: Float = 0.0f  // Using float for speed
    var direction: Float = 0.0f  // Using float for direction
    var velocityX: Float = 0.0f  // Using float for velocity components
    var velocityY: Float = 0.0f
    var maxSpeed: Float = 10.0f  // Using float for maxSpeed
    var friction: Float = 0.0f  // Using float for friction
    var bounceEfficiency: Float = 1.0f  // Using float for bounceEfficiency
    var lifetime: Timer = Timer()
    var currentWeight: Float = baseWeight + energy
    var aliveStatus: Boolean = true

    fun updateDirection(lifeForms: List<LifeForm>) {
        var directionX = 0.0f  // Using float
        var directionY = 0.0f  // Using float
        // Accumulate the attraction from all other life forms within sight
        for (otherLifeForm in lifeForms) {
            if (otherLifeForm != this) {
                // Calculate distance using Float arithmetic
                val dx = otherLifeForm.posX - this.posX
                val dy = otherLifeForm.posY - this.posY
                val distance = sqrt(dx * dx + dy * dy)

                if (distance <= this.sight) {
                    // Prevent division by zero or extremely strong attractions when too close
                    if (distance > 0.001f) {  // Using float comparison
                        val attractionStrength = this.attraction.toFloat() / distance  // Casting to float
                        directionX += attractionStrength * dx
                        directionY += attractionStrength * dy
                    }
                }
            }
        }

        // Set the new direction based on the calculated attraction forces
        this.direction = if (directionX != 0.0f || directionY != 0.0f) atan2(
            directionY.toDouble(),
            directionX.toDouble()
        ).toFloat() else this.direction
    }

    override fun toString(): String {
        val directionDegrees =
            Math.toDegrees(direction.toDouble()).toFloat() % 360 // Converting to degrees with float precision
        return "UUID: $id, Species: ${species.name}, Position: ($posX, $posY), Energy: $energy, Direction: ${
            "%.2f".format(
                directionDegrees
            )
        } degrees, Velocity[X,Y]:[$velocityX,$velocityY]."
    }
}
