package com.example.fireplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        player.player = viewModel.moviePlayer
        player_controller_status.requestFocus()
        player_controller_status.setOnClickListener(getImageOnClickListener())
    }

    private fun getImageOnClickListener(): View.OnClickListener? =
        View.OnClickListener {
            player_controller_status.apply {
                this.setImageDrawable(
                    if (viewModel.moviePlayer.playWhenReady)
                        resources.getDrawable(R.drawable.ic_pause_circle_outline_24px, null)
                    else
                        resources.getDrawable(R.drawable.ic_play_circle_outline_24px, null)
                )
                fadeWithDiminish()
            }
            Handler().postDelayed({ viewModel.changePlayStatus() },500)
        }
}
