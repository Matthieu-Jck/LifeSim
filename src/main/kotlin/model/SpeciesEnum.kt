package model

import java.awt.Color

enum class SpeciesEnum(private val r: Int, private val g: Int, private val b: Int) {
    SPECIES_1(255, 0, 102),    // Neon Pink
    SPECIES_2(0, 255, 128),    // Neon Green
    SPECIES_3(64, 224, 208),   // Turquoise
    SPECIES_4(255, 165, 0);    // Neon Orange
    /*SPECIES_5(255, 255, 0),    // Neon Yellow
    SPECIES_6(0, 139, 139);    // Dark Cyan
    SPECIES_7(173, 216, 230),  // Light Blue
    SPECIES_8(255, 20, 147),   // Deep Pink
    SPECIES_9(0, 255, 255),    // Aqua
    SPECIES_10(147, 112, 219); // Medium Purple*/

    val color: Color
        get() = Color(r, g, b)
}

