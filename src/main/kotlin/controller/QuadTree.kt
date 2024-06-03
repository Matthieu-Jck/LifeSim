package controller

import model.LifeForm

data class Boundary(val x: Float, val y: Float, val width: Float, val height: Float) {
    fun contains(lifeForm: LifeForm): Boolean {
        return lifeForm.posX >= x - width &&
                lifeForm.posX < x + width &&
                lifeForm.posY >= y - height &&
                lifeForm.posY < y + height
    }

    fun intersects(range: Boundary): Boolean {
        return !(range.x - range.width > x + width ||
                range.x + range.width < x - width ||
                range.y - range.height > y + height ||
                range.y + range.height < y - height)
    }
}

class QuadTree(private val boundary: Boundary, private val capacity: Int) {
    private var lifeForms: MutableList<LifeForm> = mutableListOf()
    private var divided: Boolean = false
    private var northwest: QuadTree? = null
    private var northeast: QuadTree? = null
    private var southwest: QuadTree? = null
    private var southeast: QuadTree? = null

    private fun subdivide() {
        val x = boundary.x
        val y = boundary.y
        val w = boundary.width / 2
        val h = boundary.height / 2

        northwest = QuadTree(Boundary(x - w, y - h, w, h), capacity)
        northeast = QuadTree(Boundary(x + w, y - h, w, h), capacity)
        southwest = QuadTree(Boundary(x - w, y + h, w, h), capacity)
        southeast = QuadTree(Boundary(x + w, y + h, w, h), capacity)
        divided = true
    }

    fun insert(lifeForm: LifeForm): Boolean {
        if (!boundary.contains(lifeForm)) {
            return false
        }

        if (lifeForms.size < capacity) {
            lifeForms.add(lifeForm)
            return true
        } else {
            if (!divided) {
                subdivide()
            }
            if (northwest!!.insert(lifeForm)) return true
            if (northeast!!.insert(lifeForm)) return true
            if (southwest!!.insert(lifeForm)) return true
            if (southeast!!.insert(lifeForm)) return true
        }
        return false
    }

    fun query(range: Boundary, found: MutableList<LifeForm>): MutableList<LifeForm> {
        if (!boundary.intersects(range)) {
            return found
        } else {
            for (lifeForm in lifeForms) {
                if (range.contains(lifeForm)) {
                    found.add(lifeForm)
                }
            }
            if (divided) {
                northwest!!.query(range, found)
                northeast!!.query(range, found)
                southwest!!.query(range, found)
                southeast!!.query(range, found)
            }
        }
        return found
    }
}