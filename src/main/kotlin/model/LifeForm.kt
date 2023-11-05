package model

import java.awt.geom.Point2D
import java.util.*
import kotlin.math.atan2

class LifeForm(
    val species: SpeciesEnum,
    var position: Point2D.Double,
    private var baseWeight: Double,
    var efficiency: Double,
    var acceleration: Double,
    var attraction: Double,
    var sight: Double,
    var reproductionMutationValue: Double
) {
    val id: String = UUID.randomUUID().toString()
    private var energy: Double = 100.0
    var speed: Double = 0.0
    var direction: Double = 0.0
    var velocityX: Double = 0.0
    var velocityY: Double = 0.0
    var maxSpeed: Double = 10.0
    var friction: Double = 0.0
    var bounceEfficiency: Double = 1.0
    var lifetime: Timer = Timer()
    var currentWeight: Double = baseWeight+energy
    var aliveStatus: Boolean = true

    fun updateDirection(lifeForms: List<LifeForm>) {
        var directionX = 0.0
        var directionY = 0.0
        println(this)
        // Accumulate the attraction from all other life forms within sight
        for (otherLifeForm in lifeForms) {
            if (otherLifeForm != this) {
                val distance = this.position.distance(otherLifeForm.position)
                // Prevent division by zero or extremely strong attractions when too close
                if (distance > 0.001) {
                    val attractionStrength = this.attraction / distance
                    directionX += attractionStrength * (otherLifeForm.position.x - this.position.x)
                    directionY += attractionStrength * (otherLifeForm.position.y - this.position.y)
                }
            }
        }

        // Set the new direction based on the calculated attraction forces
        this.direction = if (directionX != 0.0 || directionY != 0.0) atan2(directionY, directionX) else this.direction
    }

    override fun toString(): String {
        val directionDegrees = Math.toDegrees(direction) % 360
        return "UUID: $id, Species: ${species.name}, Position: (${position.x}, ${position.y}), Energy: $energy, Direction: ${"%.2f".format(directionDegrees)} degrees, Velocity[X,Y]:[$velocityX,$velocityY] ."
    }
}
