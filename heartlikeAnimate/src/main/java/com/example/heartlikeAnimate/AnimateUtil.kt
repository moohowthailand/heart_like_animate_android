package com.example.soemsak.heartlikeAnimate

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import com.example.heartlikeAnimate.R
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import com.example.heartlikeAnimate.AnimationDrawableResource


open class AnimateUtil{
    private var animation = AnimationDrawable()
    private var imageName: String? = ""
    private var duration = 40 //5000/180
    private var startIndex = 0
    var soundEffect:MediaPlayer? = null

    fun prepareImages(likeValue: Int, likeStickerImageView: ImageView, context: Context){
        likeStickerImageView.setBackgroundDrawable(null)
        animation = AnimationDrawable()// likeStickerImageView.background as AnimationDrawable
        animation.isOneShot = true
        var index = ((likeValue * 1.25)).toInt()
        while (index < 180) {
            animation.addFrame(AnimationDrawableResource.getInstance(context).getDrawables()[index],duration)
            index++
        }
        likeStickerImageView.setBackgroundDrawable(animation)
    }

    fun animateStartWhenHold(likeValue: Int, likeStickerImageView: ImageView, context: Context){
        likeStickerImageView.visibility = View.VISIBLE
        soundEffect = getSoundEffect(context)
        soundEffect!!.seekTo((likeValue * 50))
        animation.start()
        soundEffect!!.start()
    }

    fun disapearAnimateWhenTouchOutFromHold(likeStickerImageView: ImageView, timeClicked: Long, timeReleased: Long, cuurentLike: Int): Int{
        var like = (((timeReleased - timeClicked)/1000.0)* 20.0).toInt()
        likeStickerImageView.visibility = View.INVISIBLE
        animation.stop()
        soundEffect!!.stop()
        likeStickerImageView.setImageResource(0)
        likeStickerImageView.setImageDrawable(null)
        likeStickerImageView.setBackgroundDrawable(null)
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

    open fun getSoundEffect(context: Context): MediaPlayer{
        return MediaPlayer.create(context, R.raw.heart_like)
    }
}