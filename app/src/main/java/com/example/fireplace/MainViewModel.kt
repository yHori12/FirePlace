package com.example.fireplace

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util.getUserAgent
import java.util.*


class MainViewModel(application: Application) : AndroidViewModel(application) {

    val moviePlayer: ExoPlayer = ExoPlayerFactory.newSimpleInstance(application.applicationContext)
    private val audioPlayer: ExoPlayer = ExoPlayerFactory.newSimpleInstance(application.applicationContext)

    companion object {
        private const val NAME = "HomeChannelWorker"
        private fun getWork(): OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<HomeChannelWorker>().build()

        private val movieUrl: Uri = Uri.parse("asset:///fireplace_7070.mp4")
        private val audioUri: Uri = Uri.parse("asset:///fire_audio.mp3")
    }

    init {
        createHomeChannel(application)
        playFireMovie(application)
    }

    private fun createHomeChannel(context: Context): UUID {
        val work = getWork()
        WorkManager.getInstance(context)
            .beginUniqueWork(NAME, ExistingWorkPolicy.APPEND, work)
            .enqueue()

        return work.id
    }

    private fun playFireMovie(application: Application) {
        //ここで再生含めて全部やる。
        val dataSourceFactory =
            DefaultDataSourceFactory(
                application,
                getUserAgent(application, application.packageName),
                null
            )
        val movieMediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(movieUrl)
        val audioMediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(audioUri)

        moviePlayer.apply {
            this.prepare(movieMediaSource)
            this.playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ALL
        }
        audioPlayer.apply {
            this.prepare(audioMediaSource)
            this.playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ALL
        }
    }

    fun changePlayStatus() {
        when (moviePlayer.playWhenReady) {
            true -> {
                moviePlayer.playWhenReady = false
                audioPlayer.playWhenReady = false
            }
            false -> {
                moviePlayer.playWhenReady = true
                audioPlayer.playWhenReady = true
            }
        }
    }

}
