package view

import model.LifeFormLabel
import model.LifeForm
import java.awt.Color
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

class GUI {
    val frame: JFrame = JFrame("Life Sim")
    private val lifeFormLabels: MutableMap<LifeForm, JLabel> = mutableMapOf()
    private val borderColor: Color = Color.BLACK
    val borderWidth: Int = 5

    init {
        frame.pack()
        frame.setSize(frame.width + borderWidth * 2, frame.height + borderWidth * 2)
        frame.layout = null // This will allow absolute positioning
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

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

        // Set the bounds of borderPanel to cover the entire frame
        borderPanel.setBounds(insets.left, insets.top, contentWidth, contentHeight)
        frame.add(borderPanel)
        frame.isVisible = true
        borderPanel.isOpaque = false

    }

    fun updateGUI() {
        for ((lifeForm, label) in lifeFormLabels) {
            if (label is LifeFormLabel) {
                val diameter = label.height // Assuming width and height are the same since it's a circle
                // Update life form label position during the simulation
                label.setBounds(
                    (lifeForm.position.x - diameter / 2).toInt(),
                    (lifeForm.position.y - diameter / 2).toInt(),
                    diameter,
                    diameter
                )

                label.isVisible = true
            }
        }
        frame.revalidate()
        frame.repaint()
    }

    fun addLifeFormLabel(lifeForm: LifeForm, diameter: Int) {
        val color = lifeForm.species.color
        val label = LifeFormLabel(color, diameter)
        lifeFormLabels[lifeForm] = label

        // Position the label in the center of the life form's position
        // When adding a LifeFormLabel
        val posX = lifeForm.position.x.coerceIn(radius.toDouble(), contentWidth - radius.toDouble()).toInt() - diameter / 2
        val posY = lifeForm.position.y.coerceIn(radius.toDouble(), contentHeight - radius.toDouble()).toInt() - diameter / 2

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
