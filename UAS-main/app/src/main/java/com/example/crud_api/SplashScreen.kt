package com.example.crud_api

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.media.MediaPlayer
import kotlinx.android.synthetic.main.activity_splash_screen.logo_image

class SplashScreen : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 3000 // Durasi tampilan splash screen (dalam milidetik)
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Inisialisasi dan memutar suara SplashScreen
        mediaPlayer = MediaPlayer.create(this, R.raw.sound)
        mediaPlayer?.start()

        // Animasi fadeIn pada logo
        val fadeInAnimator = ObjectAnimator.ofFloat(logo_image, "alpha", 0f, 1f)
        fadeInAnimator.duration = 2000
        fadeInAnimator.start()

        // Animasi fadeOut pada logo setelah durasi tampilan splash screen selesai
        Handler().postDelayed({
            val fadeOutAnimator = ObjectAnimator.ofFloat(logo_image, "alpha", 1f, 0f)
            fadeOutAnimator.duration = 2000
            fadeOutAnimator.start()
            fadeOutAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // Pindah ke activity berikutnya setelah animasi selesai
                    startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    finish()
                }
            })
        }, SPLASH_DELAY)
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        super.onDestroy()
    }
}
