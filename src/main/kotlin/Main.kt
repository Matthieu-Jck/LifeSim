import controller.SimulationController
import model.LifeForm
import model.SpeciesEnum
import utils.LifeFormFactory
import java.awt.geom.Point2D
import kotlin.random.Random

fun main() {
    val simulationController = SimulationController()

    initializeLifeForms(simulationController,500)

    // Simulation loop
    while (true) {
        simulationController.updateLifeFormsDirection()
        simulationController.updatePositions()
        simulationController.updateFriction()
        simulationController.updateGravity()
        simulationController.updateDeath()
        simulationController.updateMutationEvents()


        simulationController.gui.updateGUI()

        Thread.sleep(50) // Delay for 1 second before the next update
    }
}

fun initializeLifeForms(simulationController: SimulationController, numberOfLifeForms: Int) {
    // Assuming there's a way to get all enum values for SpeciesEnum
    val speciesValues = SpeciesEnum.entries.toTypedArray()
    val random = Random(System.currentTimeMillis())

    // Create a specified number of life forms with random species and positions
    repeat(numberOfLifeForms) {
        val species = speciesValues[random.nextInt(speciesValues.size)]
        val position = Point2D.Double(random.nextDouble() * 1000, random.nextDouble() * 1500) // Assuming a 1000x1000 area
        createAndAddLifeForm(simulationController, species, position)
    }
}

fun createAndAddLifeForm(
    controller: SimulationController,
    species: SpeciesEnum,
    position: Point2D.Double,
    baseWeight: Double? = null,
    efficiency: Double? = null,
    acceleration: Double? = null,
    attraction: Double? = null,
    sight: Double? = null,
    reproductionMutationValue: Double? = null
) {
    val lifeForm = LifeFormFactory.createLifeForm(
        species = species,
        position = position,
        baseWeight = baseWeight ?: LifeFormFactory.DEFAULT_BASE_WEIGHT,
        efficiency = efficiency ?: LifeFormFactory.DEFAULT_EFFICIENCY,
        acceleration = acceleration ?: LifeFormFactory.DEFAULT_ACCELERATION,
        attraction = attraction ?: LifeFormFactory.DEFAULT_ATTRACTION,
        sight = sight ?: LifeFormFactory.DEFAULT_SIGHT,
        reproductionMutationValue = reproductionMutationValue ?: LifeFormFactory.DEFAULT_REPRODUCTION_MUTATION_VALUE
    )
    controller.addLifeForm(lifeForm)
}





