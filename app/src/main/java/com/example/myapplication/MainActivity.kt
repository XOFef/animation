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
    private val fadeDuration = 1500L // длительность анимации
    private val delayBetweenImages = 0L // задержка между изображениями

    private lateinit var imageViewsTwo: List<ImageView>
    private val fadeDurationTwo = 1000L // длительность появления
    private val rotateDuration = 1000L // длительность вращения
    private val delayBetweenImagesTwo = 500L // дадержка между изображениями

    private lateinit var imageView: ImageView
    private val animationDuration = 3000L // длительность анимации

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

        for (i in imageViews.indices) {
            Handler(Looper.getMainLooper()).postDelayed({
                if (i > 0) {
                    fadeOutImage(imageViews[i - 1]) // убирает предыдущее изображение
                }
                fadeInImage(imageViews[i]) // показывает текущее изображение

                // если это последнее изображение
                if (i == imageViews.lastIndex) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        fadeOutImage(imageViews[i]) // убирает последнее изображение
                    }, fadeDuration + delayBetweenImages) // задержка до исчезновения
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
                rotateImage(imageView, i % 2 == 0) // влево для четных, вправо для нечетных
            }, i * (fadeDurationTwo + delayBetweenImagesTwo))
        }


        Handler(Looper.getMainLooper()).postDelayed({
            fadeOutAllImages()
        }, imageViewsTwo.size * (fadeDurationTwo + delayBetweenImagesTwo) + 100)
    }

    private fun fadeInImageTwo(imageView: ImageView) {
        imageView.alpha = 0f
        imageView.visibility = View.VISIBLE
        imageView.animate()
            .alpha(1f)
            .setDuration(fadeDurationTwo)
            .start()
    }

    private fun fadeOutAllImages() {
        for (imageView in imageViewsTwo) {
            imageView.animate()
                .alpha(0f)
                .setDuration(fadeDurationTwo)
                .withEndAction {
                    imageView.visibility = View.GONE
                }
                .start()
        }
    }

    private fun rotateImage(imageView: ImageView, rotateLeft: Boolean) {
        val rotationAngle = if (rotateLeft) -360f else 360f // бесконечная анимация вращения
        val rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, rotationAngle).apply {
            duration = rotateDuration
            repeatCount = ObjectAnimator.INFINITE // бесконечное повторение
            repeatMode = ObjectAnimator.RESTART
        }
        // запуск анимации
        rotateAnimator.start()
    }

    // конец анимации цветков


    private fun startInfiniteUpwardAnimation() {
        // начальное положение изображения
        val Button = findViewById<Button>(R.id.button3)
        Button.visibility = View.INVISIBLE
        imageView.visibility = View.VISIBLE
        imageView.translationY = heightOfScreen().toFloat()
        //  анимация движения вверх
        val upwardAnimator = ObjectAnimator.ofFloat(imageView, "translationY", heightOfScreen().toFloat(), -imageView.height.toFloat()).apply {
            duration = animationDuration
            repeatMode = ObjectAnimator.RESTART
        }

        // запуск анимации
        upwardAnimator.start()
        Button.visibility = View.VISIBLE
    }

    private fun heightOfScreen(): Int {
        return resources.displayMetrics.heightPixels //  высота экрана
    }
}