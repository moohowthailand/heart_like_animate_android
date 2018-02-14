package com.example.soemsak.likeanimate.helper

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.widget.Toast


class AnimateUtil {
    companion object {
        var animation = AnimationDrawable()
        var imageName: String? = ""
        var duration = 50 //5000/180
        var startIndex = 0
        var imagesId:Array<Int> = arrayOf()

            fun initImages(likeValue: Int, activity: Activity, likeStickerImageView: ImageView){
                startIndex = likeValue
                while (startIndex < 180) {
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
            }


            fun animateStartWhenHold(likeValue: Int, likeStickerImageView: ImageView, activity: Activity){
                animation = AnimationDrawable()
                animation.setOneShot(true)
                likeStickerImageView.visibility = View.VISIBLE
                startIndex = likeValue
                while (startIndex < 180) {
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


    }
}