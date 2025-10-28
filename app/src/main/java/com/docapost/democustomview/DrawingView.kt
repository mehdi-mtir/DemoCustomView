package com.docapost.democustomview

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import kotlin.io.path.Path

class DrawingView(context: Context) : View(context)  {

    //Pinceau pour dessiner
    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 10f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }

    //Chemin de dessin
    private val path = Path()

    //Bitmap pour sauvegarder le dessin
    private lateinit var canvasBitmap : Bitmap
    private lateinit var drawCanvas: Canvas

    //Initialisation de la vue
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(canvasBitmap)
    }

    //Dessiner le contenu
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(canvasBitmap, 0f, 0f, null)
        canvas.drawPath(path, paint)
    }

    //Gérer les évènements tactiles
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x,y)
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x,y)
            }
            MotionEvent.ACTION_UP -> {
                drawCanvas.drawPath(path, paint)
                path.reset()
            }
        }

        invalidate()
        return true
    }

    //Méthode pour changer la couleur
    fun  setColor(color : Int){
        paint.color = color
    }

    //Méthode pour effacer
    fun clear(){
        path.reset()
        drawCanvas.drawColor(Color.WHITE)
        invalidate()
    }

}