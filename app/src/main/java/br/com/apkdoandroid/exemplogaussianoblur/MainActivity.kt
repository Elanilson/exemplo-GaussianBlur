package br.com.apkdoandroid.exemplogaussianoblur

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.imageView)

        val image = BitmapFactory.decodeResource(resources, R.drawable.gato)

        val density = 20f // Ajuste a densidade conforme necessário. O limite é de 25. então : 0 á 25

        val blurredImage = applyGaussianBlur(this, image, density)

        imageView.setImageBitmap(blurredImage)
    }
}