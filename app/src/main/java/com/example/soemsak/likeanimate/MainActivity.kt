package com.example.soemsak.likeanimate

import android.animation.AnimatorSet
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import com.example.soemsak.likeanimate.helper.AnimateUtil
import android.graphics.drawable.AnimationDrawable
import android.widget.*
import com.example.bttidolslikeanimation.Model.IDUser
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*




class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    var isLongPressLike = false
    val _handler = Handler()
    val LONG_PRESS_TIME = 600 // Time in miliseconds (100)
    private var mylikeValue: Int = 0
    var imageName = ""
    var animation = AnimationDrawable()
    var timeClicked: Long = 0
    var timeReleased: Long  = 0
    var user : IDUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val _handler = Handler()
        val likeStickerImageView = findViewById<ImageView>(R.id.likeStickerImageView)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val likeButton = findViewById<Button>(R.id.likeButton)
        var percentTextView = findViewById<TextView>(R.id.percentTextView)
        seekBar.setOnSeekBarChangeListener(this)
        val _longPressed = Runnable {
            likeStickerImageView.setImageResource(0);
            AnimateUtil.animateStartWhenHold(mylikeValue, likeStickerImageView, this@MainActivity)
            isLongPressLike = true
        }
        AnimateUtil.initImages(mylikeValue, this@MainActivity, likeStickerImageView)
        likeButton.setOnTouchListener { view, motionEvent ->
            when (motionEvent.getAction()) {
                MotionEvent.ACTION_DOWN -> {
                    timeClicked = Date().time
                    _handler.postDelayed(_longPressed, LONG_PRESS_TIME.toLong())
                }
                MotionEvent.ACTION_CANCEL -> {
                    _handler.removeCallbacks(_longPressed)
                    if(isLongPressLike){
                        isLongPressLike = false
                        animation.stop()
                        likeStickerImageView.visibility = View.INVISIBLE
                    }
                }
                MotionEvent.ACTION_UP -> {
                    timeReleased = Date().time
                    _handler.removeCallbacks(_longPressed)
                    if(isLongPressLike){ //ดึงมือขึ้นจากการ hold
                        isLongPressLike = false
                        animation.stop()
                        likeStickerImageView.visibility = View.INVISIBLE
                        mylikeValue = AnimateUtil.disapearAnimateWhenTouchOutFromHold(likeStickerImageView, timeClicked, timeReleased, mylikeValue)
                        Toast.makeText(this@MainActivity, mylikeValue, Toast.LENGTH_LONG).show()
                    }else { //ดึงมือขึ้นจากการ hold
                        likeStickerImageView.setImageResource(0)
                        mylikeValue = AnimateUtil.animateStartWhenClicked(mylikeValue,likeStickerImageView, this@MainActivity)
                        Toast.makeText(this@MainActivity, mylikeValue.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
            true
        }

        
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        mylikeValue = progress
        percentTextView.text = mylikeValue.toString()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        percentTextView.text = mylikeValue.toString()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        percentTextView.text = mylikeValue.toString()
    }



}
