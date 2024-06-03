package controller

import model.LifeForm
import view.GUI

class ReproductionHandler {
    fun handleReproduction(lifeForm: LifeForm, lifeForms: MutableList<LifeForm>, quadtree: QuadTree, gui: GUI) {
        if (lifeForm.energy >= lifeForm.minRepEnergy - lifeForm.energySpentToReproduce) {
            /*// Reduce the energy of the parent life form
            lifeForm.energy -= lifeForm.energySpentToReproduce

            // Create the new life form (duplicate)
            val newLifeForm = lifeForm.reproduce()

            // Add the new life form to the simulation
            lifeForms.add(newLifeForm)
            quadtree.insert(newLifeForm)
            gui.addLifeFormLabel(newLifeForm, newLifeForm.calculateDiameter())*/
        }
    }
}