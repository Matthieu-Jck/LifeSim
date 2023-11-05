package controller

import model.LifeForm
import view.GUI
import kotlin.math.*

const val DIAMETER = 10

class SimulationController {
    private val lifeForms: MutableList<LifeForm> = mutableListOf()
    val gui: GUI = GUI()

    fun addLifeForm(lifeForm: LifeForm) {
        lifeForms.add(lifeForm)
        gui.addLifeFormLabel(lifeForm, DIAMETER)
    }


    fun removeLifeForm(lifeForm: LifeForm) {
        lifeForms.remove(lifeForm)
        gui.removeLifeFormLabel(lifeForm)
    }

    fun updatePositions() {

        for (lifeForm in lifeForms) {
            // Calculate the change in speed based on acceleration
            val speedChange = lifeForm.acceleration
            // Use the current direction or, if NaN, infer direction from velocity
            val direction = if (!lifeForm.direction.isNaN()) lifeForm.direction else atan2(lifeForm.velocityY, lifeForm.velocityX)

            // Calculate the components of the speed change
            val speedChangeX = speedChange * cos(direction)
            val speedChangeY = speedChange * sin(direction)

            // Update the velocity components by considering the previous velocity (inertia)
            lifeForm.velocityX += speedChangeX
            lifeForm.velocityY += speedChangeY

            // Apply friction to the new velocity
            lifeForm.velocityX *= (1 - lifeForm.friction)
            lifeForm.velocityY *= (1 - lifeForm.friction)

            lifeForm.speed = hypot(lifeForm.velocityX, lifeForm.velocityY)

            // Ensure that reversing direction takes time
            if (lifeForm.velocityX.sign == -speedChangeX.sign && abs(speedChangeX) > abs(lifeForm.velocityX)) {
                lifeForm.velocityX = 0.0
            }
            if (lifeForm.velocityY.sign == -speedChangeY.sign && abs(speedChangeY) > abs(lifeForm.velocityY)) {
                lifeForm.velocityY = 0.0
            }

            // Update the position based on the new velocity
            lifeForm.position.x += lifeForm.velocityX
            lifeForm.position.y += lifeForm.velocityY

            checkAndHandleCollisions(lifeForm)

            // Limit the speed to a maximum value
            val speedLimit = lifeForm.maxSpeed
            val currentSpeed = hypot(lifeForm.velocityX, lifeForm.velocityY)
            if (currentSpeed > speedLimit) {
                val scaleFactor = speedLimit / currentSpeed
                lifeForm.velocityX *= scaleFactor
                lifeForm.velocityY *= scaleFactor
            }
        }
    }

    fun updateLifeFormsDirection() {
        lifeForms.forEach { it.updateDirection(lifeForms) }
    }

    private fun checkAndHandleCollisions(lifeForm: LifeForm) {

        val borderWidth = gui.borderWidth
        val insets = gui.frame.insets
        val contentWidth = gui.frame.width - (insets.left + insets.right + borderWidth * 2)
        val contentHeight = gui.frame.height - (insets.top + insets.bottom + borderWidth * 2)

        // Calculate the radius from the diameter
        val radius = DIAMETER / 2

        // Collision with left wall
        if (lifeForm.position.x < radius + insets.left + borderWidth) {
            lifeForm.velocityX = -lifeForm.velocityX * lifeForm.bounceEfficiency
            lifeForm.position.x = (radius + insets.left + borderWidth).toDouble()
        }

// Collision with right wall
        if (lifeForm.position.x > contentWidth - radius) {
            lifeForm.velocityX = -lifeForm.velocityX * lifeForm.bounceEfficiency
            lifeForm.position.x = (contentWidth - radius).toDouble()
        }

// Collision with bottom wall
        if (lifeForm.position.y > contentHeight - radius) {
            lifeForm.velocityY = -lifeForm.velocityY * lifeForm.bounceEfficiency
            lifeForm.position.y = (contentHeight - radius).toDouble()
        }

        // Collision with top wall
        if (lifeForm.position.y < radius + insets.top + borderWidth) {
            lifeForm.velocityY = -lifeForm.velocityY * lifeForm.bounceEfficiency
            lifeForm.position.y = (radius + insets.top + borderWidth).toDouble()
        }
    }

    fun updateFriction() {
        val frictionCoefficient = 0.1 // This value should depend on the environment or life form properties
        for (lifeForm in lifeForms) {
            val frictionForce = frictionCoefficient * (lifeForm.currentWeight/1000 + lifeForm.speed/50)
            // Assuming the direction of friction is always opposite to the direction of motion
            lifeForm.speed -= frictionForce // Friction will decrease the speed
            if (lifeForm.speed < 0) lifeForm.speed = 0.0 // Speed cannot be negative
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
