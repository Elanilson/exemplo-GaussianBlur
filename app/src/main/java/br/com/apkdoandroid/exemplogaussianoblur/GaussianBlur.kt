package br.com.apkdoandroid.exemplogaussianoblur

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.renderscript.*
import androidx.annotation.FloatRange

/**
 * Este código define uma função applyGaussianBlur que aplica o efeito de desfoque gaussiano a uma imagem. Aqui estão os parâmetros e detalhes relevantes:

context: O contexto atual do aplicativo Android.
image: A imagem original a ser desfocada. Deve ser uma instância de Bitmap.
density: A densidade do efeito de desfoque. Deve estar dentro do intervalo de 0 a 25. Valores menores que 0.1 serão ajustados para 0.1 e valores maiores que 25 serão ajustados para 25.
Retorna: A imagem desfocada, como uma instância de Bitmap.
O código utiliza a classe RenderScript do Android para realizar o desfoque gaussiano de forma eficiente. Ele cria uma nova instância de Bitmap para armazenar a imagem desfocada e usa o RenderScript para aplicar o desfoque gaussiano.
 *
 */

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
