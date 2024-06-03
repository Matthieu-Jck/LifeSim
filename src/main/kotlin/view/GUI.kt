package view

import model.LifeFormLabel
import model.LifeForm
import java.awt.Color
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Rectangle

class GUI {
    val frame: JFrame = JFrame("Life Sim")
    private val lifeFormLabels: MutableMap<LifeForm, JLabel> = mutableMapOf()
    private val borderColor: Color = Color.BLACK
    val borderWidth: Int = 5

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = null // This will allow absolute positioning

        val borderPanel = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                super.paintComponent(g)
                // Draw the borders directly without accounting for insets
                g.color = borderColor
                g.fillRect(0, 0, this.width, borderWidth) // Top border
                g.fillRect(0, 0, borderWidth, this.height) // Left border
                g.fillRect(this.width - borderWidth, 0, borderWidth, this.height) // Right border
                g.fillRect(0, this.height - borderWidth, this.width, borderWidth) // Bottom border
            }
        }

        // Set the size of the frame to account for the desired content size plus borders
        frame.setSize(1000 + borderWidth * 2, 1000 + borderWidth * 2)

        // Set the borderPanel to opaque and add to frame
        borderPanel.isOpaque = false
        frame.add(borderPanel)
        frame.isVisible = true

        // Update the borderPanel bounds after the frame is visible to ensure correct insets
        borderPanel.bounds = frame.insets.run {
            val contentWidth = frame.width - (left + right)
            val contentHeight = frame.height - (top + bottom)
            Rectangle(left, top, contentWidth, contentHeight)
        }

        frame.contentPane.background = Color.BLACK
    }

    fun updateGUI() {
        for ((lifeForm, label) in lifeFormLabels) {
            if (label is LifeFormLabel) {
                val diameter = label.height // Assuming width and height are the same since it's a circle
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
        frame.revalidate()
        frame.repaint()
    }

    // If radius is a constant value, declare it in your GUI class
    companion object {
        const val LIFE_FORM_RADIUS: Int = 10
    }

    fun addLifeFormLabel(lifeForm: LifeForm, diameter: Int) {
        val color = lifeForm.species.color
        val label = LifeFormLabel(color, diameter)
        lifeFormLabels[lifeForm] = label

        val contentWidth = frame.contentPane.width
        val contentHeight = frame.contentPane.height
        val insets = frame.insets

        // Adjust the position to account for the borders
        val posX = ((lifeForm.posX + insets.left).coerceIn(borderWidth.toFloat(), (contentWidth - LIFE_FORM_RADIUS * 2).toFloat()) - LIFE_FORM_RADIUS).toInt()
        val posY = ((lifeForm.posY + insets.top).coerceIn(borderWidth.toFloat(), (contentHeight - LIFE_FORM_RADIUS * 2).toFloat()) - LIFE_FORM_RADIUS).toInt()

        label.setBounds(posX, posY, diameter, diameter)

        label.isOpaque = false
        frame.contentPane.add(label)
        frame.contentPane.revalidate()
        frame.contentPane.repaint()
    }

    fun removeLifeFormLabel(lifeForm: LifeForm) {
        lifeFormLabels[lifeForm]?.let {
            frame.remove(it)
            lifeFormLabels.remove(lifeForm)
            frame.revalidate()
            frame.repaint()
        }
    }

}
