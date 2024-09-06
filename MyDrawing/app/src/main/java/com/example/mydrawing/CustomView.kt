package com.example.mydrawing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CustomView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var bitmap: Bitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888)
    private var bitmapCanvas: Canvas = Canvas(bitmap)
    private val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }
    private var lastX = -1f
    private var lastY = -1f
    private val rect: Rect by lazy { Rect(0, 0, width, height) }

    init {
        paint.isAntiAlias = true
        paint.strokeWidth = 10f
        paint.style = Paint.Style.STROKE
    }

    // Called to draw on the canvas
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the bitmap within the rectangle
        canvas.drawBitmap(bitmap, null, rect, null)
    }

    // Retrieve the current bitmap
    fun getBitmap(): Bitmap {
        return bitmap
    }

    // Method to pass a bitmap from the ViewModel
    fun passBitmap(newBitmap: Bitmap) {
        bitmap = newBitmap
        bitmapCanvas = Canvas(bitmap)
        invalidate()  // Redraw the view
    }

    // Helper method to draw lines on the canvas
    private fun drawLine(x: Float, y: Float, color: Int) {
        paint.color = color
        if (lastX >= 0 && lastY >= 0) {
            bitmapCanvas.drawLine(lastX, lastY, x, y, paint)
        }
        lastX = x
        lastY = y
        invalidate() // Redraw the view
    }

    // Handle touch events to draw lines
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                drawLine(event.x, event.y, paint.color)
            }
            MotionEvent.ACTION_UP -> {
                lastX = -1f
                lastY = -1f
            }
        }
        return true
    }
}