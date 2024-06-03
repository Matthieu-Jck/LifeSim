package model

import java.util.*
import kotlin.math.atan2
import kotlin.math.sqrt
import kotlin.math.PI

class LifeForm(
    val species: SpeciesEnum,
    var posX: Float,
    var posY: Float,
    var baseWeight: Float,
    var efficiency: Float,
    var acceleration: Float,
    var attraction: Float,
    var sight: Float,
    var reproductionMutationValue: Float,
    var minRepEnergy: Float,  // Minimum energy required to reproduce
    var energySpentToReproduce: Float,  // Energy spent to reproduce
    var reproductionMethod: String = "duplicate"  // Method of reproduction
) {
    private val id: String = UUID.randomUUID().toString()
    var energy: Float = 100.0f
    var speed: Float = 0.0f
    var direction: Float = 0.0f
    var velocityX: Float = 0.0f
    var velocityY: Float = 0.0f
    var maxSpeed: Float = 10.0f
    var friction: Float = 0.0f
    var bounceEfficiency: Float = 1.0f
    var lifetime: Timer = Timer()
    var currentWeight: Float = baseWeight + energy * 2
    var aliveStatus: Boolean = true

    // Map to hold attraction values towards each species
    val speciesAttraction: MutableMap<SpeciesEnum, Float> = mutableMapOf()

    init {
        // Initialize the attraction values for each species with random values between -100 and 100 for testing
        for (sp in SpeciesEnum.entries) {
            speciesAttraction[sp] = (Math.random() * 200 - 100).toFloat()
        }
    }

    fun updateDirection(lifeForms: List<LifeForm>) {
        var directionX = 0.0f
        var directionY = 0.0f
        // Accumulate the attraction from all other life forms within sight
        for (otherLifeForm in lifeForms) {
            if (otherLifeForm != this) {
                val dx = otherLifeForm.posX - this.posX
                val dy = otherLifeForm.posY - this.posY
                val distance = sqrt(dx * dx + dy * dy)
                // Prevent division by zero or extremely strong attractions when too close
                if (distance > 0.001f) {
                    val attractionStrength = (this.speciesAttraction[otherLifeForm.species] ?: 0.0f) / distance
                    directionX += attractionStrength * dx
                    directionY += attractionStrength * dy
                }
            }
        }
        if (directionX != 0.0f || directionY != 0.0f) {
            this.direction = atan2(directionY.toDouble(), directionX.toDouble()).toFloat()
        } else {
            this.direction = 0.0f
        }

        consumeEnergy()
    }

    fun regenerateEnergy() {
        energy += 0.05f
        if (energy > 100.0f) {
            energy = 100.0f
        }
        currentWeight = baseWeight + energy
    }

    private fun consumeEnergy() {
        // Energy consumption based on speed increase
        val speedIncrease = sqrt(velocityX * velocityX + velocityY * velocityY) - speed
        val energyCost = speedIncrease / efficiency
        energy -= energyCost
        if (energy < 0) {
            aliveStatus = false;
        }
        currentWeight = baseWeight + energy
    }

    fun calculateDiameter(): Float {
        // Calculate diameter based on the current weight
        return sqrt(currentWeight / PI).toFloat() * 2
    }

    fun reproduce(): LifeForm {
        // Create a new LifeForm with slight variations (mutations)
        return LifeForm(
            species = this.species,
            posX = this.posX,
            posY = this.posY,
            baseWeight = this.baseWeight,
            efficiency = this.efficiency + (Math.random().toFloat() * reproductionMutationValue),
            acceleration = this.acceleration + (Math.random().toFloat() * reproductionMutationValue),
            attraction = this.attraction + (Math.random().toFloat() * reproductionMutationValue),
            sight = this.sight + (Math.random().toFloat() * reproductionMutationValue),
            reproductionMutationValue = this.reproductionMutationValue,
            minRepEnergy = this.minRepEnergy,
            energySpentToReproduce = this.energySpentToReproduce,
            reproductionMethod = this.reproductionMethod
        )
    }

    override fun toString(): String {
        val directionDegrees =
            Math.toDegrees(direction.toDouble()).toFloat() % 360
        return "UUID: $id, Species: ${species.name}, Position: ($posX, $posY), Energy: $energy, Direction: ${
            "%.2f".format(
                directionDegrees
            )
        } degrees, Velocity[X,Y]:[$velocityX,$velocityY]."
    }
}