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
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import ru.fasdev.yandexlostphrase.manager.model.LostPhrase
import ru.fasdev.yandexlostphrase.manager.model.anim.Animation
import ru.fasdev.yandexlostphrase.manager.model.anim.Move
import ru.fasdev.yandexlostphrase.manager.model.anim.Rotate
import ru.fasdev.yandexlostphrase.manager.model.anim.Scale
import ru.fasdev.yandexlostphrase.manager.model.shape.Circle
import ru.fasdev.yandexlostphrase.manager.model.shape.PseudoShape
import ru.fasdev.yandexlostphrase.manager.model.shape.Rectangle
import java.lang.Exception

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
    private var isAnimStart: Boolean = false

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
                //val path: Path = Path()

                mainPaint.setColor(it.color.color)

                when (it)
                {
                    is Rectangle -> {
                        val top = (it.centerY - (it.height / 2)).toFloat()
                        val bottom = (it.centerY + (it.height / 2)).toFloat()
                        val left = (it.centerX - (it.width / 2)).toFloat()
                        val right = (it.centerX + (it.width / 2)).toFloat()

                        canvas?.save()
                        canvas?.rotate(it.angle.toFloat(), it.centerX.toFloat(), it.centerY.toFloat())
                        canvas?.drawRect(left, top, right, bottom, mainPaint)
                        canvas?.restore()
                    }
                    is Circle -> {
                        canvas?.drawCircle(it.centerX.toFloat(), it.centerY.toFloat(), it.radius.toFloat(), mainPaint)
                    }
                }

                //canvas?.drawPath(path, mainPaint)
            }

            if (!isAnimStart)
            {
                isAnimStart = true

                phrase?.shapes?.forEach {
                    it.animations.forEach {anim ->
                        var animator: ArrayList<ValueAnimator>? = ArrayList()

                        when (anim)
                        {
                            is Move -> {
                                val animationX = ValueAnimator.ofFloat(it.centerX.toFloat(), anim.destX.toFloat())
                                animationX.addUpdateListener { animator ->
                                    val values = animator.animatedValue as Float
                                    it.centerX = values.toDouble()

                                    invalidate()
                                }

                                val animationY = ValueAnimator.ofFloat(it.centerY.toFloat(), anim.destY.toFloat())
                                animationY.addUpdateListener { animator ->
                                    val values = animator.animatedValue as Float
                                    it.centerY = values.toDouble()

                                    invalidate()
                                }

                                animator?.add(animationX)
                                animator?.add(animationY)
                            }
                            is Rotate -> {
                                try {
                                    val shape = (it as Rectangle)

                                    val rotateAnim = ValueAnimator.ofFloat(shape.angle.toFloat(), shape.angle.toFloat() + anim.angle.toFloat())
                                    rotateAnim.addUpdateListener { animator->
                                        val values = animator.animatedValue as Float
                                        it.angle = values.toDouble()

                                        invalidate()
                                    }

                                    animator?.add(rotateAnim)
                                }
                                catch (ex: Exception)
                                {
                                    Log.e("ERROR", ex.toString())
                                }
                            }
                            is Scale -> {
                                if (it is Rectangle) {
                                    val scaleAnimationWidth = ValueAnimator.ofFloat(it.width.toFloat(), (it.width * anim.destScale).toFloat())
                                    scaleAnimationWidth.addUpdateListener { animator ->
                                        val values = animator.animatedValue as Float
                                        it.width = values.toDouble()

                                        invalidate()
                                    }

                                    val scaleAnimationHeight = ValueAnimator.ofFloat(it.height.toFloat(), (it.height * anim.destScale).toFloat())
                                    scaleAnimationHeight.addUpdateListener { animator ->
                                        val values = animator.animatedValue as Float
                                        it.height = values.toDouble()

                                        invalidate()
                                    }

                                    animator?.add(scaleAnimationHeight)
                                    animator?.add(scaleAnimationWidth)
                                }
                            }
                        }

                        animator?.forEach {
                            it.setDuration(anim.time.toLong())
                            //it.setInterpolator(LinearOutSlowInInterpolator())

                            if (anim.isCycle)
                            {
                                it.repeatMode = ValueAnimator.REVERSE
                                it.repeatCount = ValueAnimator.INFINITE
                            }

                            it.start()
                        }
                    }
                }
            }
        }
    }
}