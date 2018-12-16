package com.gg.loadview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Creator : GG
 * Time    : 2018/12/15
 * Mail    : gg.jin.yu@gmail.com
 * Explain :
 */
class CircleView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mColor: Int = Color.BLUE

    private val mPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = mColor
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val cx = width / 2f
        val cy = height / 2f
        canvas?.drawCircle(cx, cy, cx, mPaint)
    }


    fun exchangeColor(color: Int) {
        mColor = color

        mPaint.color = mColor
        invalidate()
    }

    fun getColor():Int = mColor
}