package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var imageViews: List<ImageView>
    private val fadeDuration = 1500L // Длительность анимации (1 секунда)
    private val delayBetweenImages = 100L // Задержка между изображениями (2 секунды)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        imageViews = listOf(
            findViewById(R.id.image1),
            findViewById(R.id.image2),
            findViewById(R.id.image4),
            findViewById(R.id.image5)
        )
        // Устанавливаем обработчик нажатия на кнопку
        val startAnimationButton = findViewById<Button>(R.id.button)
        startAnimationButton.setOnClickListener {
            startAnimationSequence()
        }
    }
    private fun startAnimationSequence() {
        for (i in imageViews.indices) {
            Handler(Looper.getMainLooper()).postDelayed({
                if (i > 0) {
                    fadeOutImage(imageViews[i - 1]) // Исчезаем предыдущее изображение
                }
                fadeInImage(imageViews[i]) // Появляем текущее изображение

                // Если это последнее изображение, добавим логику для его исчезновения
                if (i == imageViews.lastIndex) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        fadeOutImage(imageViews[i]) // Исчезаем последнее изображение
                    }, fadeDuration + delayBetweenImages) // Задержка до исчезновения
                }
            }, i * (fadeDuration + delayBetweenImages))
        }
    }

    private fun fadeInImage(imageView: ImageView) {
        imageView.visibility = View.VISIBLE
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        imageView.startAnimation(fadeInAnimation)

        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    private fun fadeOutImage(imageView: ImageView) {
        val fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        imageView.startAnimation(fadeOutAnimation)

        fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                imageView.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }


}