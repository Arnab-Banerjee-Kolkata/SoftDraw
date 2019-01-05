package android.arnab.softdraw

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import android.view.View
import android.arnab.softdraw.Circle


class Circle @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    View(context, attrs, defStyle) {

    lateinit var p: Paint
    internal var color: Int = 0
    var myCanvas: Canvas?=null

    init {
        // real work here
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Circle,
            0, 0
        )

        try {

            color = a.getColor(R.styleable.Circle_circleColor, -0x1000000)
        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle()
        }
        init()
    }

    fun init() {
        p = Paint()
        p.color = color
    }

    override fun onDraw(canvas: Canvas?) {
        // TODO Auto-generated method stub
        super.onDraw(canvas)
        myCanvas=canvas
        canvas?.drawCircle((height / 2).toFloat(), (width / 2).toFloat(), (width / 2).toFloat(), p)
    }
    fun draw(color: Int)
    {
        this.color=color
        invalidate()
        requestLayout()
    }

}