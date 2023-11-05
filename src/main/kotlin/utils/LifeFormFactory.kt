package utils

import model.LifeForm
import model.SpeciesEnum
import java.awt.geom.Point2D

object LifeFormFactory {
    const val DEFAULT_BASE_WEIGHT = 1.0
    const val DEFAULT_EFFICIENCY = 1.0
    const val DEFAULT_ACCELERATION = 0.1
    const val DEFAULT_ATTRACTION = 1.0
    const val DEFAULT_SIGHT = 1000.0
    const val DEFAULT_REPRODUCTION_MUTATION_VALUE = 0.01

    fun createLifeForm(
        species: SpeciesEnum,
        position: Point2D.Double,
        baseWeight: Double = DEFAULT_BASE_WEIGHT,
        efficiency: Double = DEFAULT_EFFICIENCY,
        acceleration: Double = DEFAULT_ACCELERATION,
        attraction: Double = DEFAULT_ATTRACTION,
        sight: Double = DEFAULT_SIGHT,
        reproductionMutationValue: Double = DEFAULT_REPRODUCTION_MUTATION_VALUE
    ): LifeForm {
        return LifeForm(
            species = species,
            position = position,
            baseWeight = baseWeight,
            efficiency = efficiency,
            acceleration = acceleration,
            attraction = attraction,
            sight = sight,
            reproductionMutationValue = reproductionMutationValue
        )
    }
}
