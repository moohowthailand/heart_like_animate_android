package com.example.soemsak.likeanimate.helper

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Handler
import com.example.heartlikeAnimate.R

class AnimateUtil{
    private var animation = AnimationDrawable()
    private var imageName: String? = ""
    private var duration = 40 //5000/180
    private var startIndex = 0

    fun initImages(likeValue: Int, activity: Activity, likeStickerImageView: ImageView){
        startIndex = likeValue
        while (startIndex < 225) {
            if (startIndex < 10) {
                imageName = "heart0000" + startIndex
            } else if (startIndex < 100) {
                imageName = "heart000" + startIndex
            } else {
                imageName = "heart00" + startIndex
            }
            val imagesId = activity.resources.getIdentifier(imageName, "drawable", activity.packageName)
            animation.addFrame(activity.resources.getDrawable(imagesId), duration)
            startIndex++
        }
        likeStickerImageView.setBackgroundDrawable(animation)
    }

    fun animateStartWhenHold(likeValue: Int, likeStickerImageView: ImageView, activity: Activity){
        animation = AnimationDrawable()
        animation.isOneShot = true
        likeStickerImageView.visibility = View.VISIBLE
        startIndex = ((likeValue * 1.25) + 0.5).toInt()
        while (startIndex < 225) {
            if (startIndex < 10) {
                imageName = "heart0000" + startIndex
            } else if (startIndex < 100) {
                imageName = "heart000" + startIndex
            } else {
                imageName = "heart00" + startIndex
            }
            val globeId = activity.resources.getIdentifier(imageName, "drawable", activity.packageName)
            animation.addFrame(activity.resources.getDrawable(globeId), duration)
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

    fun animateStartWhenClicked(likeValue: Int, likeStickerImageView: ImageView, activity: Activity): Int{
        var likeValue = likeValue
        if(likeValue >= 100){
            likeValue = 99
        }
        if(likeValue < 10){
            imageName = "heartstill0000" + likeValue
        } else if(likeValue <= 100){
            imageName = "heartstill000" + likeValue
        }
        val imageId = activity.resources.getIdentifier(imageName, "drawable", activity.packageName)
        likeStickerImageView.setImageResource(imageId)
        likeStickerImageView.visibility = View.VISIBLE
        val handler = Handler()
        handler.postDelayed(Runnable {
            likeStickerImageView.visibility = View.INVISIBLE
        }, 600)
        likeValue++
        return likeValue
    }

    fun playDelayEffect(soundEffect :MediaPlayer, likeValue: Int){
        val startTime = (likeValue * 50)
        soundEffect.seekTo(startTime)
        soundEffect.start()
    }

    fun getSoundEffect(activity: Activity): MediaPlayer{
        return MediaPlayer.create(activity, R.raw.heart_like)
    }
}