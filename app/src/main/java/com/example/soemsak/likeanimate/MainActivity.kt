package com.example.soemsak.likeanimate
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.graphics.drawable.StateListDrawable
import com.example.soemsak.heartlikeAnimate.AnimateUtil

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    var isLongPressLike = false
    val _handler = Handler()
    val LONG_PRESS_TIME = 600 // Time in miliseconds (100)
    private var mylikeValue: Int = 0
    var animation = AnimationDrawable()
    var timeClicked: Long = 0
    var timeReleased: Long  = 0
    var animateUtil = AnimateUtil()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val _handler = Handler()
        val likeStickerImageView = findViewById<ImageView>(R.id.likeStickerImageView)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val likeButton = findViewById<Button>(R.id.likeButton)
        var soundEffect = animateUtil.getSoundEffect(this@MainActivity)
        seekBar.setOnSeekBarChangeListener(this)

        val _longPressed = Runnable {
            soundEffect = MediaPlayer.create(this@MainActivity, R.raw.heart_like)
            animateUtil.playDelayEffect(soundEffect, mylikeValue)
            likeStickerImageView.setImageDrawable(StateListDrawable())
            animateUtil.animateStartWhenHold(mylikeValue, likeStickerImageView, this@MainActivity)
            isLongPressLike = true
        }
        animateUtil.initImages(mylikeValue, this@MainActivity, likeStickerImageView)
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
                    soundEffect.stop()
                    if(isLongPressLike){ //ดึงมือขึ้นจากการ hold
                        isLongPressLike = false
                        animation.stop()
                        likeStickerImageView.visibility = View.INVISIBLE
                        mylikeValue = animateUtil.disapearAnimateWhenTouchOutFromHold(likeStickerImageView, timeClicked, timeReleased, mylikeValue)
                        Toast.makeText(this@MainActivity, mylikeValue.toString(), Toast.LENGTH_LONG).show()
                    }else { //ดึงมือขึ้นจากการ hold
                        likeStickerImageView.setImageDrawable(StateListDrawable())
//                        mylikeValue = AnimateUtil.animateStartWhenClicked(mylikeValue,likeStickerImageView, this@MainActivity)
                        mylikeValue = animateUtil.animateStartWhenClicked(mylikeValue,likeStickerImageView, this@MainActivity)
                        Toast.makeText(this@MainActivity, mylikeValue.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
            true
        }

        reset.setOnClickListener {
            mylikeValue = 0
            likeStickerImageView.setImageDrawable(StateListDrawable())
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
