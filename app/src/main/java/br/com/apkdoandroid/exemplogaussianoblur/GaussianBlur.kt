package br.com.apkdoandroid.exemplogaussianoblur

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.renderscript.*
import androidx.annotation.FloatRange

fun applyGaussianBlur(context: Context, image: Bitmap, @FloatRange(from = 0.0, to = 25.0) density: Float): Bitmap {
    val radius = if (density < 0.1f) 0.1f else if (density > 25.0f) 25.0f else density

    val blurredBitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)

    val renderScript = RenderScript.create(context)
    val inputAllocation = Allocation.createFromBitmap(renderScript, image)
    val outputAllocation = Allocation.createFromBitmap(renderScript, blurredBitmap)

    val blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
    blurScript.setRadius(radius)
    blurScript.setInput(inputAllocation)
    blurScript.forEach(outputAllocation)

    outputAllocation.copyTo(blurredBitmap)

    renderScript.destroy()

    return blurredBitmap
}
