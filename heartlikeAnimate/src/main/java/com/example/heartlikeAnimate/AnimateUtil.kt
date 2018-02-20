package com.example.soemsak.heartlikeAnimate

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import com.example.heartlikeAnimate.R
import com.example.heartlikeAnimate.AnimationDrawableResource
import android.animation.ObjectAnimator
import android.annotation.SuppressLint


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
        likeStickerImageView.alpha = 1f
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

        if(likeStickerImageView.alpha < 1f){

        }

        likeStickerImageView.setImageResource(0)
        likeStickerImageView.setImageDrawable(null)
        likeStickerImageView.setBackgroundDrawable(null)
        val imageId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        likeStickerImageView.alpha = 1f
        likeStickerImageView.setImageResource(imageId)
        likeStickerImageView.visibility = View.VISIBLE
        fadeOutAndHideImage(likeStickerImageView)
        likeValue++
        return likeValue
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun fadeOutAndHideImage(img: ImageView) {
        img.animate().alpha(0F).setDuration(1000).start()
    }

    open fun getSoundEffect(context: Context): MediaPlayer{
        return MediaPlayer.create(context, R.raw.heart_like)
    }
}