package model

import java.awt.*
import javax.swing.JLabel

internal class LifeFormLabel(private val color: Color, private var diameter: Float) : JLabel() {

    init {
        isOpaque = false
        updateSize(diameter)
    }

    private fun updateSize(diameter: Float) {
        this.diameter = diameter
        size = Dimension(diameter.toInt(), diameter.toInt())
        preferredSize = size
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        g2d.color = color
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.fillOval(0, 0, width, height)
    }

    fun updateDiameter(newDiameter: Float) {
        updateSize(newDiameter)
        revalidate()
        repaint()
    }
}