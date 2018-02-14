package com.example.bttidolslikeanimation.Model

interface xxxxx {
    fun onHoldStart()
    fun onHoldRelease(): Int
}

class IDUser {
    var firsname: String? = null
    var lastname: String? = null
    var age: Int = 0

    var listener: xxxxx? = null

    fun initButton(percent: Int){
        if(listener != null){
            listener!!.onHoldRelease()
        }
    }


}