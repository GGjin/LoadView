package com.gg.loadview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.RelativeLayout

/**
 * Creator : GG
 * Time    : 2018/12/15
 * Mail    : gg.jin.yu@gmail.com
 * Explain :
 */
class LoadView : RelativeLayout {


    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mTranslationDistance = 30f

    private val ANIMATION_TIME = 300L

    private var mStopAnimation = false

    private val mLeftView: CircleView by lazy {
        CircleView(context).apply {
            exchangeColor(Color.BLUE)
            layoutParams = RelativeLayout.LayoutParams(dip2sp(10f), dip2sp(10f)).apply {
                addRule(CENTER_IN_PARENT)
            }

        }
    }

    private fun dip2sp(dip: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, resources.displayMetrics).toInt()
    }

    private val mMiddleView: CircleView by lazy {
        CircleView(context).apply {
            exchangeColor(Color.RED)
            layoutParams = RelativeLayout.LayoutParams(dip2sp(10f), dip2sp(10f)).apply {
                addRule(CENTER_IN_PARENT)
            }
        }
    }
    private val mRightView: CircleView by lazy {
        CircleView(context).apply {
            exchangeColor(Color.GREEN)
            layoutParams = RelativeLayout.LayoutParams(dip2sp(10f), dip2sp(10f)).apply {
                addRule(CENTER_IN_PARENT)
            }
        }
    }

    init {
        mTranslationDistance = dip2sp(mTranslationDistance).toFloat()
        addView(mLeftView)
        addView(mRightView)
        addView(mMiddleView)
        post {
            expendAnimation()
        }

    }

    private fun expendAnimation() {
        if (mStopAnimation)
            return
        val leftAnimation = ObjectAnimator.ofFloat(mLeftView, "translationX", -mTranslationDistance, 0f)
        val rightAnimation = ObjectAnimator.ofFloat(mRightView, "translationX", 0f, mTranslationDistance)

        AnimatorSet().apply {
            interpolator = AccelerateInterpolator()
            duration = ANIMATION_TIME
            playTogether(leftAnimation, rightAnimation)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    innerAnimation()
                }
            })
            start()
        }
    }

    private fun innerAnimation() {
        if (mStopAnimation)
            return
        val leftAnimation = ObjectAnimator.ofFloat(mLeftView, "translationX", 0f, -mTranslationDistance)
        val rightAnimation = ObjectAnimator.ofFloat(mRightView, "translationX", mTranslationDistance, 0f)

        AnimatorSet().apply {
            interpolator = AccelerateInterpolator()
            duration = ANIMATION_TIME
            playTogether(leftAnimation, rightAnimation)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    val leftColor = mLeftView.getColor()
                    val middleColor = mMiddleView.getColor()
                    val rightColor = mRightView.getColor()
                    mLeftView.exchangeColor(rightColor)
                    mMiddleView.exchangeColor(leftColor)
                    mRightView.exchangeColor(middleColor)
                    expendAnimation()
                }
            })
            start()
        }
    }


    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            mLeftView.clearAnimation()
            mMiddleView.clearAnimation()
            mRightView.clearAnimation()

            mStopAnimation = true
            removeAllViews()
        }
    }
}