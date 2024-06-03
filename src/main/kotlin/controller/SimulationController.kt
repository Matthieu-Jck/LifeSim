package controller

import model.LifeForm
import model.LifeFormLabel
import view.GUI
import kotlin.math.*

const val QUADTREE_CAPACITY = 4

class SimulationController {
    private val lifeForms: MutableList<LifeForm> = mutableListOf()
    private val deadLiveForms: MutableList<LifeForm> = mutableListOf()
    private var quadtree: QuadTree
    val gui: GUI = GUI()

    init {
        val boundary =
            Boundary(gui.frame.width / 2f, gui.frame.height / 2f, gui.frame.width / 2f, gui.frame.height / 2f)
        quadtree = QuadTree(boundary, QUADTREE_CAPACITY)
    }

    fun addLifeForm(lifeForm: LifeForm) {
        lifeForms.add(lifeForm)
        quadtree.insert(lifeForm)
        gui.addLifeFormLabel(lifeForm, lifeForm.calculateDiameter())
    }

    fun killLifeForm(lifeForm: LifeForm) {
        lifeForms.remove(lifeForm)
        deadLiveForms.add(lifeForm)
        gui.removeLifeFormLabel(lifeForm)
    }

    fun updatePositions() {
        quadtree = QuadTree(
            Boundary(gui.frame.width / 2f, gui.frame.height / 2f, gui.frame.width / 2f, gui.frame.height / 2f),
            QUADTREE_CAPACITY
        )
        for (lifeForm in lifeForms) {
            if (!lifeForm.aliveStatus){
                killLifeForm(lifeForm)
                break
            }
            lifeForm.regenerateEnergy()
            if (!lifeForm.direction.isNaN() && lifeForm.acceleration != 0.0f) {
                val speedChange = lifeForm.acceleration
                val direction = lifeForm.direction

                // Calculate the components of the speed change
                val speedChangeX = speedChange * cos(direction.toDouble()).toFloat()
                val speedChangeY = speedChange * sin(direction.toDouble()).toFloat()

                // Update the velocity components by considering the previous velocity (inertia)
                lifeForm.velocityX += speedChangeX
                lifeForm.velocityY += speedChangeY

                // Apply friction to the new velocity
                lifeForm.velocityX *= (1 - lifeForm.friction)
                lifeForm.velocityY *= (1 - lifeForm.friction)

                lifeForm.speed = hypot(lifeForm.velocityX.toDouble(), lifeForm.velocityY.toDouble()).toFloat()

                // Ensure that reversing direction takes time
                if (lifeForm.velocityX.sign == -speedChangeX.sign && abs(speedChangeX) > abs(lifeForm.velocityX)) {
                    lifeForm.velocityX = 0f
                }
                if (lifeForm.velocityY.sign == -speedChangeY.sign && abs(speedChangeY) > abs(lifeForm.velocityY)) {
                    lifeForm.velocityY = 0f
                }
                lifeForm.posX += lifeForm.velocityX
                lifeForm.posY += lifeForm.velocityY

                checkAndHandleCollisions(lifeForm)
                quadtree.insert(lifeForm)
                val speedLimit = lifeForm.maxSpeed
                val currentSpeed = hypot(lifeForm.velocityX.toDouble(), lifeForm.velocityY.toDouble()).toFloat()
                if (currentSpeed > speedLimit) {
                    val scaleFactor = speedLimit / currentSpeed
                    lifeForm.velocityX *= scaleFactor
                    lifeForm.velocityY *= scaleFactor
                }
            }
        }
        updateGUI()
    }

    fun updateLifeFormsDirection() {
        lifeForms.forEach { lifeForm ->
            val range = Boundary(lifeForm.posX, lifeForm.posY, lifeForm.sight, lifeForm.sight)
            val nearbyLifeForms = quadtree.query(range, mutableListOf())
            lifeForm.updateDirection(nearbyLifeForms)
        }
    }

    private fun checkAndHandleCollisions(lifeForm: LifeForm) {
        val insets = gui.frame.insets
        val contentWidth = gui.frame.width - (insets.left + insets.right)
        val contentHeight = gui.frame.height - (insets.top + insets.bottom)
        val radius = lifeForm.calculateDiameter() / 2

        // Collision with left wall
        if (lifeForm.posX < radius + insets.left) {
            lifeForm.velocityX = -lifeForm.velocityX * lifeForm.bounceEfficiency
            lifeForm.posX = (radius + insets.left)
        }

        // Collision with right wall
        if (lifeForm.posX > contentWidth - radius) {
            lifeForm.velocityX = -lifeForm.velocityX * lifeForm.bounceEfficiency
            lifeForm.posX = (contentWidth - radius)
        }

        // Collision with bottom wall
        if (lifeForm.posY > contentHeight - radius) {
            lifeForm.velocityY = -lifeForm.velocityY * lifeForm.bounceEfficiency
            lifeForm.posY = (contentHeight - radius)
        }

        // Collision with top wall
        if (lifeForm.posY < radius + insets.top) {
            lifeForm.velocityY = -lifeForm.velocityY * lifeForm.bounceEfficiency
            lifeForm.posY = (radius + insets.top)
        }
    }

    fun updateFriction() {
        val frictionCoefficient = 0.1f
        for (lifeForm in lifeForms) {
            // Use a quadratic function for friction
            val frictionForce = frictionCoefficient * (lifeForm.speed) * (lifeForm.currentWeight/500f)

            // Reduce the speed by the friction force
            lifeForm.speed -= frictionForce

            // Ensure speed doesn't become negative
            if (lifeForm.speed < 0) lifeForm.speed = 0f

            // Update the velocity components based on the new speed
            if (lifeForm.speed == 0f) {
                lifeForm.velocityX = 0f
                lifeForm.velocityY = 0f
            } else {
                val speedRatio = lifeForm.speed / hypot(lifeForm.velocityX.toDouble(), lifeForm.velocityY.toDouble()).toFloat()
                lifeForm.velocityX *= speedRatio
                lifeForm.velocityY *= speedRatio
            }
        }
    }


    private fun updateGUI() {
        for ((lifeForm, label) in gui.lifeFormLabels) {
            if (label is LifeFormLabel) {
                val diameter = lifeForm.calculateDiameter().toInt()
                // Update life form label position during the simulation
                label.setBounds(
                    (lifeForm.posX - diameter / 2).toInt(),
                    (lifeForm.posY - diameter / 2).toInt(),
                    diameter,
                    diameter
                )

                label.isVisible = true
            }
        }
        gui.frame.revalidate()
        gui.frame.repaint()
    }
}
