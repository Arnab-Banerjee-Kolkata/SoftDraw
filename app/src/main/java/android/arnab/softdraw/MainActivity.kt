package android.arnab.softdraw

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import yuku.ambilwarna.AmbilWarnaDialog
import java.nio.file.Files.size
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import android.R.attr.action
import android.os.Handler
import android.util.Log


class MainActivity : Activity(), View.OnClickListener, View.OnTouchListener
{
    lateinit var brushRel: RelativeLayout
    lateinit var pickerTool: RelativeLayout
    lateinit var brushBtn: RoundedImageView
    lateinit var textBtn: ImageButton
    lateinit var moustacheBtn: ImageButton
    lateinit var addImgBtn: ImageButton
    lateinit var threeDotsBtn: ImageButton
    lateinit var brush1: RoundedImageView
    lateinit var brush2: RoundedImageView
    lateinit var brush3: RoundedImageView
    lateinit var brush4: RoundedImageView
    lateinit var brush5: RoundedImageView
    lateinit var brush6: RoundedImageView
    lateinit var brush7: RoundedImageView
    lateinit var brush8: RoundedImageView
    lateinit var brush9: RoundedImageView
    lateinit var brush10: RoundedImageView
    lateinit var tickcrossBtn: RoundedImageView
    lateinit var fourArrowBtn: RoundedImageView
    lateinit var undoBtn: ImageButton
    lateinit var redoBtn: ImageButton
    lateinit var pagesBtn: ImageButton
    lateinit var twoArrowBtn: ImageButton
    lateinit var color: ArrayList<Circle>
    lateinit var border: ArrayList<Circle>
    lateinit var selectColorBtn: ImageButton
    lateinit var pickColorBtn: ImageButton
    lateinit var colorBtn: Circle
    lateinit var colors: ArrayList<Int>
    var pickedColor: Int=0
    lateinit var opBar: SeekBar
    lateinit var opVal: TextView
    lateinit var sizeBar: SeekBar
    lateinit var sizeVal: TextView
    lateinit var pickTool: RelativeLayout
    private val handler = Handler()
    private val closeHandler=Handler()
    var started=false
    var closed=false
    var value=500
    var check=0
    companion object {
        var startX=0
        var startY=0
        var currentX=0
        var currentY=0

    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fullSCreen()
        color= ArrayList<Circle>()
        border= ArrayList<Circle>()
        colors=ArrayList<Int>()


        brushRel=findViewById(R.id.brushRel) as RelativeLayout
        brushBtn=findViewById(R.id.brushBtn) as RoundedImageView
        textBtn=findViewById(R.id.textBtn) as ImageButton
        moustacheBtn=findViewById(R.id.moustacheBtn) as ImageButton
        addImgBtn=findViewById(R.id.addImgBtn) as ImageButton
        threeDotsBtn=findViewById(R.id.threeDotBtn) as ImageButton
        brush1=findViewById(R.id.brush1) as RoundedImageView
        brush2=findViewById(R.id.brush2) as RoundedImageView
        brush3=findViewById(R.id.brush3) as RoundedImageView
        brush4=findViewById(R.id.brush4) as RoundedImageView
        brush5=findViewById(R.id.brush5) as RoundedImageView
        brush6=findViewById(R.id.brush6) as RoundedImageView
        brush7=findViewById(R.id.brush7) as RoundedImageView
        brush8=findViewById(R.id.brush8) as RoundedImageView
        brush9=findViewById(R.id.brush9) as RoundedImageView
        brush10=findViewById(R.id.brush10) as RoundedImageView
        tickcrossBtn=findViewById(R.id.tickCrossBtn) as RoundedImageView
        fourArrowBtn=findViewById(R.id.fourArrowBtn) as RoundedImageView
        undoBtn=findViewById(R.id.undoBtn) as ImageButton
        redoBtn=findViewById(R.id.redoBtn) as ImageButton
        pagesBtn=findViewById(R.id.pagesBtn) as ImageButton
        twoArrowBtn=findViewById(R.id.twoArrowBtn) as ImageButton
        selectColorBtn=findViewById(R.id.selectColorBtn) as ImageButton
        pickColorBtn=findViewById(R.id.pickColorBtn) as ImageButton
        colorBtn=findViewById(R.id.colorBtn) as Circle
        sizeBar=findViewById(R.id.sizeBar) as SeekBar
        sizeVal=findViewById(R.id.sizeVal) as TextView
        opBar=findViewById(R.id.opBar) as SeekBar
        opVal=findViewById(R.id.opVal) as TextView
        pickerTool=findViewById(R.id.pickerTool) as RelativeLayout
        pickTool=findViewById(R.id.pickTool) as RelativeLayout

        initImages(applicationContext)
        initColorPalette()
        pickerTool.visibility=View.GONE
        pickedColor= ContextCompat.getColor(this,R.color.colorPrimary)

        for(a in 0..border.size-1)
        {
            border[a].draw(Color.TRANSPARENT)
        }

        for(a in 0..color.size-1)
        {
            color[a].setOnClickListener(object: View.OnClickListener{
                override fun onClick(p0: View?)
                {
                    for(b in 0..border.size-1) {
                        border[b].draw(Color.TRANSPARENT)
                    }
                    border[a].draw(colors[a])
                }

            })
        }

        sizeBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean)
            {
                sizeVal.text=p1.toString()+"px"

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        opBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean)
            {
                opVal.text=p1.toString()+"%"

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })


        brushRel.visibility=View.GONE
        brushBtn.setOnClickListener(this)
        tickcrossBtn.setOnClickListener(this)
        selectColorBtn.setOnClickListener(this)
        pickColorBtn.setOnClickListener(this)
        pickTool.setOnTouchListener(this)

    }
    override fun onClick(v: View?)
    {
        if(v==brushBtn)
        {
            if(brushRel.visibility==View.GONE) {
                brushRel.visibility = View.VISIBLE
                loadIconOrMenuImg(applicationContext,tickcrossBtn,R.drawable.cross)
            }
            else {
                brushRel.visibility = View.GONE
                tickcrossBtn.setImageResource(R.drawable.tick)
            }
        }
        else if(v==tickcrossBtn)
        {
            if(brushRel.visibility==View.VISIBLE)
            {
                brushRel.visibility = View.GONE
                tickcrossBtn.setImageResource(R.drawable.tick)
            }
        }
        else if(v==selectColorBtn)
        {
            openColorPicker()
        }
        else if(v==pickColorBtn)
        {
            var params: RelativeLayout.LayoutParams=RelativeLayout.LayoutParams(4000,4000)
            params.addRule(RelativeLayout.CENTER_IN_PARENT)
            pickTool.layoutParams=params
            brushRel.visibility=View.GONE
            pickerTool.visibility=View.VISIBLE
            value=400
            start()
        }

    }

    fun openColorPicker() {
        val colorPicker = AmbilWarnaDialog(this, pickedColor, object : OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}

            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                pickedColor = color
            }
        })
        colorPicker.show()
    }


    fun initImages(context: Context)
    {
        loadIconOrMenuImg(context,brushBtn,R.drawable.sketch_pen)
        loadIconOrMenuImg(context,textBtn,R.drawable.t)
        loadIconOrMenuImg(context,moustacheBtn,R.drawable.moustache)
        loadIconOrMenuImg(context,addImgBtn,R.drawable.add_image)
        loadIconOrMenuImg(context,threeDotsBtn,R.drawable.three_dots)
        loadIconOrMenuImg(context,brush1,R.drawable.sketch_pen)
        loadIconOrMenuImg(context,brush2,R.drawable.highlighter)
        loadIconOrMenuImg(context,brush3,R.drawable.pencil)
        loadIconOrMenuImg(context,brush4,R.drawable.fountain_pen)
        loadIconOrMenuImg(context,brush5,R.drawable.spray_gun)
        loadIconOrMenuImg(context,brush6,R.drawable.eraser)
        loadIconOrMenuImg(context,brush7,R.drawable.rounded_eraser)
        loadIconOrMenuImg(context,brush8,R.drawable.smudge)
        loadIconOrMenuImg(context,brush9,R.drawable.rub)
        loadIconOrMenuImg(context,brush10,R.drawable.can)
        loadIconOrMenuImg(context,tickcrossBtn,R.drawable.tick)
        loadIconOrMenuImg(context,fourArrowBtn,R.drawable.four_arrow)
        loadIconOrMenuImg(context,undoBtn,R.drawable.undo)
        loadIconOrMenuImg(context,redoBtn,R.drawable.redo)
        loadIconOrMenuImg(context,pagesBtn,R.drawable.pages)
        loadIconOrMenuImg(context,twoArrowBtn,R.drawable.two_arrows)
        loadIconOrMenuImg(context,selectColorBtn,R.drawable.color_disc)
        loadIconOrMenuImg(context,pickColorBtn,R.drawable.color_picker)

    }

    fun loadIconOrMenuImg(context: Context, roundedImageView: RoundedImageView, image: Int) {
        GlideApp
            .with(context)
            .load(image)
            .into(roundedImageView)
    }
    fun loadIconOrMenuImg(context: Context, imgBtn: ImageButton, image: Int) {
        GlideApp
            .with(context)
            .load(image)
            .into(imgBtn)
    }
    fun loadIconOrMenuImg(context: Context, imgView: ImageView, image: Int) {
        GlideApp
            .with(context)
            .load(image)
            .into(imgView)
    }

    fun fullSCreen()
    {
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun initColorPalette()
    {
        color.add(findViewById(R.id.circle1))
        color.add(findViewById(R.id.circle2))
        color.add(findViewById(R.id.circle3))
        color.add(findViewById(R.id.circle4))
        color.add(findViewById(R.id.circle5))
        color.add(findViewById(R.id.circle6))
        color.add(findViewById(R.id.circle7))
        color.add(findViewById(R.id.circle8))
        color.add(findViewById(R.id.circle9))
        color.add(findViewById(R.id.circle10))
        color.add(findViewById(R.id.circle11))
        color.add(findViewById(R.id.circle12))
        color.add(findViewById(R.id.circle13))
        color.add(findViewById(R.id.circle14))
        color.add(findViewById(R.id.circle15))
        color.add(findViewById(R.id.circle16))
        color.add(findViewById(R.id.circle17))
        color.add(findViewById(R.id.circle18))
        color.add(findViewById(R.id.circle19))
        color.add(findViewById(R.id.circle20))
        color.add(findViewById(R.id.circle21))

        border.add(findViewById(R.id.border1))
        border.add(findViewById(R.id.border2))
        border.add(findViewById(R.id.border3))
        border.add(findViewById(R.id.border4))
        border.add(findViewById(R.id.border5))
        border.add(findViewById(R.id.border6))
        border.add(findViewById(R.id.border7))
        border.add(findViewById(R.id.border8))
        border.add(findViewById(R.id.border9))
        border.add(findViewById(R.id.border10))
        border.add(findViewById(R.id.border11))
        border.add(findViewById(R.id.border12))
        border.add(findViewById(R.id.border13))
        border.add(findViewById(R.id.border14))
        border.add(findViewById(R.id.border15))
        border.add(findViewById(R.id.border16))
        border.add(findViewById(R.id.border17))
        border.add(findViewById(R.id.border18))
        border.add(findViewById(R.id.border19))
        border.add(findViewById(R.id.border20))
        border.add(findViewById(R.id.border21))

        colors.add(R.color.color1)
        colors.add(R.color.color2)
        colors.add(R.color.color3)
        colors.add(R.color.color4)
        colors.add(R.color.color5)
        colors.add(R.color.color6)
        colors.add(R.color.color7)
        colors.add(R.color.color8)
        colors.add(R.color.color9)
        colors.add(R.color.color10)
        colors.add(R.color.color11)
        colors.add(R.color.color12)
        colors.add(R.color.color13)
        colors.add(R.color.color14)
        colors.add(R.color.color15)
        colors.add(R.color.color16)
        colors.add(R.color.color17)
        colors.add(R.color.color18)
        colors.add(R.color.color19)
        colors.add(R.color.color20)
        colors.add(R.color.color21)
    }

    override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean
    {
        val action = motionEvent!!.getAction()
        when (action and MotionEvent.ACTION_MASK)
        {
            MotionEvent.ACTION_MOVE ->
            {
                setCurrentX(motionEvent.rawX.toInt())
                setCurrentY(motionEvent.rawY.toInt())

                val diffX = (startX - currentX).toFloat()
                val diffY = (startY - currentY).toFloat()

                setStartX(currentX)
                setStartY(currentY)


                pickTool.x=pickTool.x-diffX
                pickTool.y=pickTool.y-diffY

            }
            MotionEvent.ACTION_DOWN ->
            {
                setStartX(motionEvent.rawX.toInt())
                setStartY(motionEvent.rawY.toInt())
            }
            MotionEvent.ACTION_UP ->
            {
                value=300
                close()
            }
        }
        return true
    }

    internal fun setCurrentX(x: Int) {
        currentX = x
    }

    internal fun setCurrentY(y: Int) {
        currentY = y
    }

    internal fun setStartX(x: Int) {
        startX = x
    }

    internal fun setStartY(y: Int) {
        startY = y
    }

    private val runnable = Runnable{
        var params: RelativeLayout.LayoutParams=RelativeLayout.LayoutParams(value*10,value*10)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        pickTool.layoutParams=params
        if(value>300)
        {
            value-=10
            start()
        }
        else
        {
            started=false
        }

    }

    fun start()
    {
        //Toast.makeText(applicationContext,"Shrink",Toast.LENGTH_SHORT).show()
        started=true
        handler.postDelayed(runnable,10)
    }

    private val closeRunnable = Runnable{
        var params: RelativeLayout.LayoutParams=RelativeLayout.LayoutParams(value*10,value*10)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        pickTool.layoutParams=params
        if(value>0)
        {
            value-=10
            close()
        }
        else
        {
            closed=false
            pickerTool.visibility=View.GONE
            brushRel.visibility=View.VISIBLE
        }

    }

    fun close()
    {
        started=true
        closeHandler.postDelayed(closeRunnable,10)
    }
}
