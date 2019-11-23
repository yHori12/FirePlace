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
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util.getUserAgent
import java.util.*


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var player: ExoPlayer = ExoPlayerFactory.newSimpleInstance(application.applicationContext)
    private val uri: Uri = Uri.parse("asset:///video/video.mp4")

    companion object {
        private const val NAME = "HomeChannelWorker"
        private fun getWork(): OneTimeWorkRequest = OneTimeWorkRequestBuilder<HomeChannelWorker>().build()
    }

    private fun createHomeChannel(context: Context): UUID {
        val work = getWork()
        WorkManager.getInstance(context)
            .beginUniqueWork(NAME, ExistingWorkPolicy.APPEND,work)
            .enqueue()

        return work.id
    }


    init {
        createHomeChannel(application)


        //ここで再生含めて全部やる。
        val dataSourceFactory =
            DefaultDataSourceFactory(application, getUserAgent(application, application.packageName), null)
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)

        player.apply {
            this.prepare(mediaSource)
            this.playWhenReady = true
        }
    }
}

//todo animation
/*
* youtubeみたいな
* 最初 濃&小
* 最後 薄&大
* */
