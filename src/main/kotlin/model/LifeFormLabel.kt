package model

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JLabel

internal class LifeFormLabel(private val color: Color, private val diameter: Int) : JLabel() {

    init {
        isOpaque = false
        // Correctly setting the size using Dimension
        size = Dimension(diameter, diameter)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.color = color
        g.fillOval(0, 0, diameter, diameter) // Fill a circle
    }
}
