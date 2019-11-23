package com.example.fireplace

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.view.View
import android.widget.ImageView

const val SHARE_FILE_NAME = "SHARE_DEFAULT_CHANNEL"
const val SHARE_KEY_CHANNEL = "KEY_DEFAULT_CHANNEL"
fun Context.saveChannelId(value: Long) =
    this.getSharedPreferences(SHARE_FILE_NAME, Context.MODE_PRIVATE).edit().putLong(
        SHARE_KEY_CHANNEL, value
    )

fun Context.getChannelId(): Long = this.getSharedPreferences(
    SHARE_FILE_NAME, Context.MODE_PRIVATE
).getLong(SHARE_KEY_CHANNEL, -1L)

fun ImageView.fadeWithDiminish() {
//    val animatorList = AnimatorSet()
//    val fadeAnimation = ObjectAnimator.ofFloat(this, "alpha", 0.7f, 0f).setDuration(1000)
//    val diminishAnimationX = ObjectAnimator.ofFloat(this, "scaleX", 0.6f,1f).setDuration(1000)
//    val diminishAnimationY = ObjectAnimator.ofFloat(this, "scaleY", 0.6f,1f).setDuration(1000)
//
//    animatorList.addListener(object : SimpleAnimatorListener() {
//        override fun onAnimationEnd(animation: Animator?) {
//            this@fadeWithDiminish.visibility = View.GONE
//        }
//    })
//
//    animatorList.playTogether(fadeAnimation, diminishAnimationX, diminishAnimationY)
//    animatorList.start()


    ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat(View.ALPHA, 0.7f, 0f),
        PropertyValuesHolder.ofFloat(View.SCALE_X, 0.6f,1f),
        PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.6f,1f))
        .apply {
            duration = 1000
            addListener(object :SimpleAnimatorListener(){
                override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                    this@fadeWithDiminish.visibility = View.GONE
                }
            })
        }.start()
}



open class SimpleAnimatorListener:Animator.AnimatorListener{
    override fun onAnimationRepeat(animation: Animator?) {
    }

    override fun onAnimationEnd(animation: Animator?) {
    }

    override fun onAnimationCancel(animation: Animator?) {
    }

    override fun onAnimationStart(animation: Animator?) {
    }

}
