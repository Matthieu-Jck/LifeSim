package controller

import model.LifeForm
import view.GUI
import kotlin.math.*

const val DIAMETER = 10
const val QUADTREE_CAPACITY = 4

class SimulationController {
    private val lifeForms: MutableList<LifeForm> = mutableListOf()
    val gui: GUI = GUI()
    private var quadtree: QuadTree

    init {
        val boundary = Boundary(gui.frame.width / 2f, gui.frame.height / 2f, gui.frame.width / 2f, gui.frame.height / 2f)
        quadtree = QuadTree(boundary, QUADTREE_CAPACITY)
    }

    fun addLifeForm(lifeForm: LifeForm) {
        lifeForms.add(lifeForm)
        quadtree.insert(lifeForm)
        gui.addLifeFormLabel(lifeForm, DIAMETER)
    }

    fun updatePositions() {
        quadtree = QuadTree(Boundary(gui.frame.width / 2f, gui.frame.height / 2f, gui.frame.width / 2f, gui.frame.height / 2f), QUADTREE_CAPACITY)
        for (lifeForm in lifeForms) {
            // Calculate the change in speed based on acceleration
            val speedChange = lifeForm.acceleration
            // Use the current direction or, if NaN, infer direction from velocity
            val direction = if (!lifeForm.direction.isNaN()) {
                lifeForm.direction
            } else {
                atan2(lifeForm.velocityY.toDouble(), lifeForm.velocityX.toDouble()).toFloat()
            }

            // Calculate the components of the speed change
            val speedChangeX = speedChange * cos(direction.toDouble()).toFloat() // Convert to Float after calculations
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

            // Update the position based on the new velocity
            lifeForm.posX += lifeForm.velocityX
            lifeForm.posY += lifeForm.velocityY

            checkAndHandleCollisions(lifeForm)

            // Re-insert updated life form into quadtree
            quadtree.insert(lifeForm)

            // Limit the speed to a maximum value
            val speedLimit = lifeForm.maxSpeed
            val currentSpeed = hypot(lifeForm.velocityX.toDouble(), lifeForm.velocityY.toDouble()).toFloat()
            if (currentSpeed > speedLimit) {
                val scaleFactor = speedLimit / currentSpeed
                lifeForm.velocityX *= scaleFactor
                lifeForm.velocityY *= scaleFactor
            }
        }
    }

    fun updateLifeFormsDirection() {
        lifeForms.forEach { lifeForm ->
            val range = Boundary(lifeForm.posX, lifeForm.posY, lifeForm.sight, lifeForm.sight)
            val nearbyLifeForms = quadtree.query(range, mutableListOf())
            lifeForm.updateDirection(nearbyLifeForms)
        }
    }

    private fun checkAndHandleCollisions(lifeForm: LifeForm) {
        val borderWidth = gui.borderWidth
        val insets = gui.frame.insets
        val contentWidth = gui.frame.width - (insets.left + insets.right + borderWidth * 2)
        val contentHeight = gui.frame.height - (insets.top + insets.bottom + borderWidth * 2)

        // Calculate the radius from the diameter
        val radius = DIAMETER / 2

        // Collision with left wall
        if (lifeForm.posX < radius + insets.left + borderWidth) {
            lifeForm.velocityX = -lifeForm.velocityX * lifeForm.bounceEfficiency
            lifeForm.posX = ((radius + insets.left + borderWidth).toFloat())
        }

        // Collision with right wall
        if (lifeForm.posX > contentWidth - radius) {
            lifeForm.velocityX = -lifeForm.velocityX * lifeForm.bounceEfficiency
            lifeForm.posX = ((contentWidth - radius).toFloat())
        }

        // Collision with bottom wall
        if (lifeForm.posY > contentHeight - radius) {
            lifeForm.velocityY = -lifeForm.velocityY * lifeForm.bounceEfficiency
            lifeForm.posY = ((contentHeight - radius).toFloat())
        }

        // Collision with top wall
        if (lifeForm.posY < radius + insets.top + borderWidth) {
            lifeForm.velocityY = -lifeForm.velocityY * lifeForm.bounceEfficiency
            lifeForm.posY = (radius + insets.top + borderWidth).toFloat()
        }
    }

    fun updateFriction() {
        val frictionCoefficient = 0.1f // Make sure to use Float
        for (lifeForm in lifeForms) {
            // Ensure all operands are Floats to avoid Double result
            val frictionForce = frictionCoefficient * ((lifeForm.currentWeight / 1000f) + (lifeForm.speed / 50f))
            // Assuming the direction of friction is always opposite to the direction of motion
            lifeForm.speed -= frictionForce // Friction will decrease the speed
            if (lifeForm.speed < 0) lifeForm.speed = 0f // Ensure speed is not negative, using Float
        }
    }

    fun updateGravity() {
        // Implement gravity update logic here
    }

    fun updateDeath() {
        // Implement death update logic here
    }

    fun updateMutationEvents() {
        // Implement mutation event update logic here
    }
}