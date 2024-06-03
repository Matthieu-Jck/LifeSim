import controller.SimulationController
import model.SpeciesEnum
import utils.LifeFormFactory
import kotlin.random.Random

fun main() {
    val simulationController = SimulationController()

    initializeLifeForms(simulationController, 500);

    // Simulation loop
    while (true) {
        simulationController.updateLifeFormsDirection()
        simulationController.updatePositions()
        simulationController.updateFriction()

        simulationController.gui.updateGUI()

        Thread.sleep(5)
    }
}

fun initializeLifeForms(simulationController: SimulationController, numberOfLifeForms: Int) {
    // Assuming there's a way to get all enum values for SpeciesEnum
    val speciesValues = SpeciesEnum.entries.toTypedArray()
    val random = Random(System.currentTimeMillis())

    // Create a specified number of life forms with random species and positions within a 1000x1000 area
    repeat(numberOfLifeForms) {
        val species = speciesValues[random.nextInt(speciesValues.size)]
        val posX = random.nextFloat() * 800f
        val posY = random.nextFloat() * 800f
        createAndAddLifeForm(simulationController, species, posX, posY)
    }
}

fun createAndAddLifeForm(
    controller: SimulationController,
    species: SpeciesEnum,
    posX: Float,
    posY: Float,
    baseWeight: Float? = null,
    efficiency: Float? = null,
    acceleration: Float? = null,
    attraction: Float? = null,
    sight: Float? = null,
    reproductionMutationValue: Float? = null
) {
    val lifeForm = LifeFormFactory.createLifeForm(
        species = species,
        posX = posX,
        posY = posY,
        baseWeight = baseWeight ?: LifeFormFactory.DEFAULT_BASE_WEIGHT,
        efficiency = efficiency ?: LifeFormFactory.DEFAULT_EFFICIENCY,
        acceleration = acceleration ?: LifeFormFactory.DEFAULT_ACCELERATION,
        attraction = attraction ?: LifeFormFactory.DEFAULT_ATTRACTION,
        sight = sight ?: LifeFormFactory.DEFAULT_SIGHT,
        reproductionMutationValue = reproductionMutationValue ?: LifeFormFactory.DEFAULT_REPRODUCTION_MUTATION_VALUE
    )
    controller.addLifeForm(lifeForm)
}







