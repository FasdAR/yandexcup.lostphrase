package ru.fasdev.yandexlostphrase.manager

import android.util.Log
import androidx.core.text.isDigitsOnly
import ru.fasdev.yandexlostphrase.manager.model.ColorShape
import ru.fasdev.yandexlostphrase.manager.model.LostPhrase
import ru.fasdev.yandexlostphrase.manager.model.anim.Animation
import ru.fasdev.yandexlostphrase.manager.model.anim.Move
import ru.fasdev.yandexlostphrase.manager.model.anim.Rotate
import ru.fasdev.yandexlostphrase.manager.model.anim.Scale
import ru.fasdev.yandexlostphrase.manager.model.shape.Circle
import ru.fasdev.yandexlostphrase.manager.model.shape.PseudoShape
import ru.fasdev.yandexlostphrase.manager.model.shape.Rectangle

class PseudoCodeManager
{
    companion object {
        const val LINE_SIZE = 0
        const val LINE_FIGURES_COUNT = 1

        const val SPACE_SEPARATOR = " "

        const val CMD_RECTANGLE = "rectangle"
        const val CMD_CIRCLE = "circle"
        const val CMD_MOVE = "move"
        const val CMD_ROTATE = "rotate"
        const val CMD_SCALE = "scale"
    }

    private lateinit var pseudoCodeResult: List<String>

    fun getLostPhrase(pseudoCode: String): LostPhrase
    {
        pseudoCodeResult = pseudoCode.split(System.lineSeparator())

        val width = getWidth()
        val height = getHeight()
        val figuresCount = getFiguresCount()

        val shapes: ArrayList<PseudoShape> = arrayListOf()

        for (i in LINE_FIGURES_COUNT..pseudoCodeResult.size) {
            val index = i-1

            val line = pseudoCodeResult[index]
            val cmd = line.split(SPACE_SEPARATOR).get(0)

            when (cmd)
            {
                CMD_RECTANGLE -> {
                    shapes.add(getRectangle(index))
                }
                CMD_CIRCLE -> {
                    shapes.add(getCircle(index))
                }
            }
        }

        return LostPhrase(width, height, figuresCount, shapes)
    }

    fun getRectangle(indexLine: Int): Rectangle
    {
        val line = pseudoCodeResult[indexLine]
        val parseLine = line.split(SPACE_SEPARATOR)

        val centerX: Double = parseLine.get(1).toDouble()
        val centerY: Double = parseLine.get(2).toDouble()
        val width: Double = parseLine.get(3).toDouble()
        val height: Double = parseLine.get(4).toDouble()
        val angle: Double = parseLine.get(5).toDouble()
        val color: ColorShape = ColorShape.valueOf(parseLine.get(6).toUpperCase())
        val animations: List<Animation> = getAnimations(indexLine)

        return Rectangle(centerX, centerY, width, height, angle, color, animations)
    }

    fun getCircle(indexLine: Int): Circle
    {
        val line = pseudoCodeResult[indexLine]
        val parseLine = line.split(SPACE_SEPARATOR)

        val centerX: Double = parseLine.get(1).toDouble()
        val centerY: Double = parseLine.get(2).toDouble()
        val radius: Double = parseLine.get(3).toDouble()
        val color: ColorShape = ColorShape.valueOf(parseLine.get(4).toUpperCase())
        val animations: List<Animation> = getAnimations(indexLine)

        return Circle(centerX, centerY, radius, color, animations)
    }

    fun getAnimations(cmdLine: Int): List<Animation>
    {
        val startIndex = cmdLine + 1
        val startLine = pseudoCodeResult[startIndex]

        if (startLine.toInt() == 0)
        {
            return emptyList()
        }
        else
        {
            val countAnimationStroke: Int = startLine.toInt()

            val startAnimIndex = startIndex + 1
            val endAnimIndex = (startAnimIndex + countAnimationStroke)-1

            val listAnimation: ArrayList<Animation> = arrayListOf()

            for (i in startAnimIndex..endAnimIndex)
            {
                val cmd = pseudoCodeResult[i].split(SPACE_SEPARATOR).get(0)

                when(cmd)
                {
                    CMD_MOVE -> {
                        listAnimation.add(getMoveAnimation(i))
                    }
                    CMD_ROTATE -> {
                        listAnimation.add(getRotateAnimation(i))
                    }
                    CMD_SCALE -> {
                        listAnimation.add(getScaleAnimation(i))
                    }
                }
            }

            return listAnimation
        }
    }

    private fun getMoveAnimation(indexLine: Int): Animation
    {
        val line = pseudoCodeResult[indexLine]
        val parseLine = line.split(SPACE_SEPARATOR)

        val destX: Double = parseLine.get(1).toDouble()
        val destY: Double = parseLine.get(2).toDouble()
        val time: Double = parseLine.get(3).toDouble()
        val isCycle: String? = if (parseLine.size <= 4) null else parseLine.get(4)

        return Move(destX,destY,time, !isCycle.isNullOrEmpty())
    }

    private fun getRotateAnimation(indexLine: Int): Animation
    {
        val line = pseudoCodeResult[indexLine]
        val parseLine = line.split(SPACE_SEPARATOR)

        val angle: Double = parseLine.get(1).toDouble()
        val time: Double = parseLine.get(2).toDouble()
        val isCycle: String? = if (parseLine.size <= 3) null else parseLine.get(3)

        return Rotate(angle, time, !isCycle.isNullOrEmpty())
    }

    private fun getScaleAnimation(indexLine: Int): Animation
    {
        val line = pseudoCodeResult[indexLine]
        val parseLine = line.split(SPACE_SEPARATOR)

        val destScale: Double = parseLine.get(1).toDouble()
        val time: Double = parseLine.get(2).toDouble()
        val isCycle: String? = if (parseLine.size <= 3) null else parseLine.get(3)

        return Scale(destScale, time, !isCycle.isNullOrEmpty())
    }

    fun getWidth(): Double
    {
        return getSizeLine().get(0).toDouble()
    }

    fun getHeight(): Double
    {
        return getSizeLine().get(1).toDouble()
    }

    fun getSizeLine(): List<String> {
        return pseudoCodeResult.get(LINE_SIZE).split(SPACE_SEPARATOR)
    }

    fun getFiguresCount(): Int {
        return pseudoCodeResult.get(LINE_FIGURES_COUNT).toInt()
    }
}