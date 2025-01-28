package com.example.myapplication

import android.animation.ObjectAnimator
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
    private lateinit var imageViews: List<ImageView>
    private val fadeDuration = 1500L // Длительность анимации
    private val delayBetweenImages = 0L // Задержка между изображениями

    private lateinit var imageViewsTwo: List<ImageView>
    private val fadeDurationTwo = 1000L // Длительность появления
    private val rotateDuration = 1000L // Длительность вращения
    private val delayBetweenImagesTwo = 500L // Задержка между изображениями

    private lateinit var imageView: ImageView
    private val animationDuration = 3000L // Длительность анимации

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

        imageViewsTwo = listOf(
            findViewById(R.id.image9),
            findViewById(R.id.image6),
            findViewById(R.id.image3),
            findViewById(R.id.image7),
            findViewById(R.id.image8)
        )

        imageView = findViewById(R.id.imageView)

        // Устанавливаем обработчик нажатия на кнопку
        val startAnimationButton = findViewById<Button>(R.id.button)
        startAnimationButton.setOnClickListener {
            startAnimationSequence()
        }

        val startAnimationButtonTwo = findViewById<Button>(R.id.button2)
        startAnimationButtonTwo.setOnClickListener {
            startAnimationSequenceTwo()
        }

        val startAnimationButtonTree = findViewById<Button>(R.id.button3)
        startAnimationButtonTree.setOnClickListener {
            startInfiniteUpwardAnimation()
        }
    }

    // Анимации для лапок
    private fun startAnimationSequence() {
        val Button = findViewById<Button>(R.id.button)
        Button.isEnabled = false
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
                    Button.isEnabled = true
                }
            }, i * (fadeDuration + delayBetweenImages) * 1)
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
// конец анимации лапок



    // анимации цветков
    private fun startAnimationSequenceTwo() {
        for (i in imageViewsTwo.indices) {
            val imageView = imageViewsTwo[i]
            Handler(Looper.getMainLooper()).postDelayed({
                fadeInImageTwo(imageView)
                rotateImage(imageView, i % 2 == 0) // Вращение влево для четных, вправо для нечетных
            }, i * (fadeDurationTwo + delayBetweenImagesTwo))
        }
    }

    private fun fadeInImageTwo(imageView: ImageView) {
        imageView.alpha = 0f
        imageView.visibility = View.VISIBLE
        imageView.animate()
            .alpha(1f)
            .setDuration(fadeDurationTwo)
            .start()
    }

    private fun rotateImage(imageView: ImageView, rotateLeft: Boolean) {
        val rotationAngle = if (rotateLeft) -360f else 360f
        // Создаем бесконечную анимацию вращения
        val rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, rotationAngle).apply {
            duration = rotateDuration
            repeatCount = ObjectAnimator.INFINITE // Бесконечное повторение
            repeatMode = ObjectAnimator.RESTART // Начинать заново после завершения
        }
        // Запускаем анимацию
        rotateAnimator.start()
    }
    // конец анимации цветков


    private fun startInfiniteUpwardAnimation() {
        // Устанавливаем начальное положение изображения (внизу экрана)
        val Button = findViewById<Button>(R.id.button3)
        Button.visibility = View.INVISIBLE
        imageView.visibility = View.VISIBLE
        imageView.translationY = heightOfScreen().toFloat()
        // Создаем анимацию движения вверх
        val upwardAnimator = ObjectAnimator.ofFloat(imageView, "translationY", heightOfScreen().toFloat(), -imageView.height.toFloat()).apply {
            duration = animationDuration
            //repeatCount = ObjectAnimator.INFINITE // Бесконечное повторение
            repeatMode = ObjectAnimator.RESTART // Начинать заново после завершения
        }

        // Запускаем анимацию
        upwardAnimator.start()
        Button.visibility = View.VISIBLE
    }

    private fun heightOfScreen(): Int {
        return resources.displayMetrics.heightPixels // Возвращаем высоту экрана в пикселях
    }
}