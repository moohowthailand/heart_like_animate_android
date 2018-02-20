package com.example.heartlikeAnimate


import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat

class AnimationDrawableResource private constructor(context: Context){

    private var imageIds:ArrayList<Int> = ArrayList()
    private var drawables:ArrayList<Drawable> = ArrayList()

    companion object {
        @Volatile private var instance : AnimationDrawableResource? = null

        fun  getInstance(context: Context): AnimationDrawableResource {
            if (instance == null) {
                instance = AnimationDrawableResource(context)
            }
            return instance!!
        }

    }

    init {
        var index = 0
        while (index < 180) {
            var imageName = String.format("heartpumping%05d",index)
            val globeId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
            imageIds.add(globeId)
            drawables.add(ContextCompat.getDrawable(context, imageIds.get(index)))
            index++
        }
    }

    fun getDrawables():ArrayList<Drawable> {
        return instance!!.drawables
    }

}