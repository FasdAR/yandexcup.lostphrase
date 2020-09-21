package ru.fasdev.yandexlostphrase

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import ru.fasdev.yandexlostphrase.manager.model.LostPhrase
import ru.fasdev.yandexlostphrase.manager.model.anim.Animation
import ru.fasdev.yandexlostphrase.manager.model.anim.Move
import ru.fasdev.yandexlostphrase.manager.model.shape.Circle
import ru.fasdev.yandexlostphrase.manager.model.shape.PseudoShape
import ru.fasdev.yandexlostphrase.manager.model.shape.Rectangle

class LostPhraseView : View
{
    constructor(context: Context): super(context) {  }
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {  }
    constructor(context: Context, attributeSet: AttributeSet, defStyleArr: Int, defStyleRes: Int): super(context, attributeSet, defStyleArr, defStyleRes) { }

    private var mainPaint: Paint = Paint().apply {
        strokeWidth = 1f
    }

    private var phrase: LostPhrase? = null
    private var isInitDraw: Boolean = false

    fun setPhase(lostPhase: LostPhrase)
    {
        layoutParams = layoutParams.apply {
            width = lostPhase.width.toInt()
            height  = lostPhase.height.toInt()
        }

        setBackgroundColor(Color.LTGRAY)
        this.phrase = lostPhase
        this.isInitDraw = false
    }

    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)

        if (phrase != null)
        {
            phrase?.shapes?.forEach {
                val path: Path = Path()
                when (it)
                {
                    is Rectangle -> {
                        val top = (it.centerY - (it.height / 2)).toFloat()
                        val bottom = (it.centerY + (it.height / 2)).toFloat()
                        val left = (it.centerX - (it.width / 2)).toFloat()
                        val right = (it.centerX + (it.width / 2)).toFloat()

                        path.addRect(left, top, right, bottom, Path.Direction.CW)
                    }
                    is Circle -> {

                    }
                }

                mainPaint.setColor(it.color.color)

                canvas?.drawPath(path, mainPaint)

                it.animations.forEach {anim ->
                    when (anim)
                    {
                        is Move -> {
                            val pathAnimator = ObjectAnimator.ofFloat(this, "x", "y", path)
                            pathAnimator.setDuration(anim.time.toLong())
                            pathAnimator.start()
                        }
                    }
                }
            }
        }
    }
}