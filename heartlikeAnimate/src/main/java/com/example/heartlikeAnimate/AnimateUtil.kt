package com.example.soemsak.heartlikeAnimate

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Handler
import com.example.heartlikeAnimate.R
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation



open class AnimateUtil{
    private var animation = AnimationDrawable()
    private var imageName: String? = ""
    private var duration = 40 //5000/180
    private var startIndex = 0

    open fun initImages(likeValue: Int, context: Context, likeStickerImageView: ImageView){
        startIndex = likeValue
        while (startIndex < 180) {
            if (startIndex < 10) {
                imageName = "heartpumping0000" + startIndex
            } else if (startIndex < 100) {
                imageName = "heartpumping000" + startIndex
            } else {
                imageName = "heartpumping00" + startIndex
            }
            val imagesId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
            animation.addFrame(context.resources.getDrawable(imagesId), duration)
            startIndex++
        }
        likeStickerImageView.setBackgroundDrawable(animation)
    }

    fun animateStartWhenHold(likeValue: Int, likeStickerImageView: ImageView, context: Context){
        animation = AnimationDrawable()
        animation.isOneShot = true
        likeStickerImageView.visibility = View.VISIBLE
        startIndex = ((likeValue * 1.25) + 0.5).toInt()
        while (startIndex < 180) {
            if (startIndex < 10) {
                imageName = "heartpumping0000" + startIndex
            } else if (startIndex < 100) {
                imageName = "heartpumping000" + startIndex
            } else {
                imageName = "heartpumping00" + startIndex
            }
            val globeId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
            animation.addFrame(context.resources.getDrawable(globeId), duration)
            startIndex++
        }
        likeStickerImageView.setBackgroundDrawable(animation)
        likeStickerImageView.visibility = View.VISIBLE
        animation.start()
    }

    fun disapearAnimateWhenTouchOutFromHold(likeStickerImageView: ImageView, timeClicked: Long, timeReleased: Long, cuurentLike: Int): Int{
        var like = (((timeReleased - timeClicked)/1000.0)* 20.0).toInt()
        likeStickerImageView.visibility = View.INVISIBLE
        animation.stop()
        if(like < cuurentLike){
            like = cuurentLike
        }
        if(like > 100){
            like = 100
        }
        return like
    }

    fun animateStartWhenClicked(likeValue: Int, likeStickerImageView: ImageView, context: Context): Int{
        var likeValue = likeValue
        if(likeValue >= 100){
            likeValue = 99
        }
        if(likeValue < 10){
            imageName = "heartstill0000" + likeValue
        } else if(likeValue <= 100){
            imageName = "heartstill000" + likeValue
        }
        val imageId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        likeStickerImageView.setImageResource(imageId)
        likeStickerImageView.visibility = View.VISIBLE
//        val handler = Handler()
//        handler.postDelayed(Runnable {
//            likeStickerImageView.visibility = View.INVISIBLE
//        }, 300)
        fadeOutAndHideImage(likeStickerImageView)
        likeValue++
        return likeValue
    }

    private fun fadeOutAndHideImage(img: ImageView) {
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = AccelerateInterpolator()
        fadeOut.duration = 200
        fadeOut.setAnimationListener(object : AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                img.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
        })
        img.startAnimation(fadeOut)
    }

    fun playDelayEffect(soundEffect :MediaPlayer, likeValue: Int){
        val startTime = (likeValue * 50)
        soundEffect.seekTo(startTime)
        soundEffect.start()
    }

    open fun getSoundEffect(context: Context): MediaPlayer{
        return MediaPlayer.create(context, R.raw.heart_like)
    }
}