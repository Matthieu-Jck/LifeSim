package view

import model.LifeForm
import model.LifeFormLabel
import model.SpeciesEnum
import java.awt.Color
import javax.swing.JFrame
import javax.swing.JLabel

class GUI {
    val frame: JFrame = JFrame("Life Sim")
    val lifeFormLabels: MutableMap<LifeForm, JLabel> = mutableMapOf()
    private val borderSize: Int = 800

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = null // This will allow absolute positioning

        // Set the size of the frame to account for the border size
        frame.setSize(borderSize, borderSize)
        frame.isVisible = true

        // Set the background color inside the borders
        frame.contentPane.background = Color.BLACK
        frame.background = Color.WHITE
    }

    fun updateGUI() {
        for ((lifeForm, label) in lifeFormLabels) {
            if (label is LifeFormLabel) {
                val diameter = lifeForm.calculateDiameter()
                label.updateDiameter(diameter)
                // Update life form label position during the simulation
                label.setBounds(
                    (lifeForm.posX - diameter / 2).toInt(),
                    (lifeForm.posY - diameter / 2).toInt(),
                    diameter.toInt(),
                    diameter.toInt()
                )

                label.isVisible = true
            }
        }
        frame.revalidate()
        frame.repaint()
    }

    fun addLifeFormLabel(lifeForm: LifeForm, diameter: Float) {
        val label = LifeFormLabel(lifeForm.species.color, diameter)
        label.setBounds(
            (lifeForm.posX - diameter / 2).toInt(),
            (lifeForm.posY - diameter / 2).toInt(),
            diameter.toInt(),
            diameter.toInt()
        )
        frame.add(label)
        lifeFormLabels[lifeForm] = label
    }

    fun removeLifeFormLabel(lifeForm: LifeForm) {
        lifeFormLabels[lifeForm]?.let {
            frame.contentPane.remove(it)
            lifeFormLabels.remove(lifeForm)
            frame.contentPane.revalidate()
            frame.contentPane.repaint()
        }
    }
}