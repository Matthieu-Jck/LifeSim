package utils

import model.LifeForm
import model.SpeciesEnum
import java.awt.geom.Point2D
object LifeFormFactory {
    const val DEFAULT_BASE_WEIGHT = 1.0f
    const val DEFAULT_EFFICIENCY = 1.0f
    const val DEFAULT_ACCELERATION = 0.1f
    const val DEFAULT_ATTRACTION = 1.0f
    const val DEFAULT_SIGHT = 100.0f
    const val DEFAULT_REPRODUCTION_MUTATION_VALUE = 0.01f

    fun createLifeForm(
        species: SpeciesEnum,
        posX: Float,  // Using float for position x
        posY: Float,  // Using float for position y
        baseWeight: Float = DEFAULT_BASE_WEIGHT,
        efficiency: Float = DEFAULT_EFFICIENCY,
        acceleration: Float = DEFAULT_ACCELERATION,
        attraction: Float = DEFAULT_ATTRACTION,
        sight: Float = DEFAULT_SIGHT,
        reproductionMutationValue: Float = DEFAULT_REPRODUCTION_MUTATION_VALUE
    ): LifeForm {
        return LifeForm(
            species = species,
            posX = posX,
            posY = posY,
            baseWeight = baseWeight,
            efficiency = efficiency,
            acceleration = acceleration,
            attraction = attraction,
            sight = sight,
            reproductionMutationValue = reproductionMutationValue
        )
    }
}
