package utils

import model.LifeForm
import model.SpeciesEnum

object LifeFormFactory {
    const val DEFAULT_BASE_WEIGHT = 10.0f
    const val DEFAULT_EFFICIENCY = 1.0f
    const val DEFAULT_ACCELERATION = 0.1f
    const val DEFAULT_ATTRACTION = 1.0f
    const val DEFAULT_SIGHT = 100.0f
    const val DEFAULT_REPRODUCTION_MUTATION_VALUE = 0.01f
    const val DEFAULT_MIN_REPRODUCTIVE_ENERGY = 50f
    const val DEFAULT_ENERGY_SPENT_TO_REPRODUCE = 50f

    fun createLifeForm(
        species: SpeciesEnum,
        posX: Float,
        posY: Float,
        baseWeight: Float = DEFAULT_BASE_WEIGHT,
        efficiency: Float = DEFAULT_EFFICIENCY,
        acceleration: Float = DEFAULT_ACCELERATION,
        attraction: Float = DEFAULT_ATTRACTION,
        sight: Float = DEFAULT_SIGHT,
        reproductionMutationValue: Float = DEFAULT_REPRODUCTION_MUTATION_VALUE,
        minRepEnergy: Float = DEFAULT_MIN_REPRODUCTIVE_ENERGY,
        energySpentToReproduce: Float = DEFAULT_ENERGY_SPENT_TO_REPRODUCE
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
            reproductionMutationValue = reproductionMutationValue,
            minRepEnergy = minRepEnergy,
            energySpentToReproduce = energySpentToReproduce
        )
    }
}