package com.iseab.fireplace

import android.content.ContentUris
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.tvprovider.media.tv.Channel
import androidx.tvprovider.media.tv.ChannelLogoUtils
import androidx.tvprovider.media.tv.TvContractCompat
import androidx.work.*

class HomeChannelWorker(private val context: Context,
                        workParams: WorkerParameters) :
    Worker(context, workParams) {

    //doWork() から返される Result の各メソッドにより、WorkManager に以下の情報が伝えられる。
    //
    //Result.success(): タスクが正常に終了したかどうか
    //Result.failure(): タスクが失敗したかどうか
    //Result.retry(): 後でタスクを再試行する必要があるかどうか

    override fun doWork(): Result {
        addChannel(context)
        return Result.success()
    }

    private fun addChannel(context: Context) {
        val savedChannelId = context.getChannelId()

        if (savedChannelId == -1L){
            val channel = Channel.Builder()
                .setType(TvContractCompat.Channels.TYPE_PREVIEW)
                .setDisplayName("Now on fire")
                .setAppLinkIntentUri(Uri.parse("fire://fire"))

            val channelUri = context.contentResolver.insert(
                TvContractCompat.Channels.CONTENT_URI,channel.build().toContentValues()) ?: return

            val channelId = ContentUris.parseId(channelUri)
            context.saveChannelId(channelId)
            ChannelLogoUtils.storeChannelLogo(context, channelId, BitmapFactory.decodeResource(context.resources, R.drawable.camp_wood_candle_fire)) // チャンネルのロゴを登録
            TvContractCompat.requestChannelBrowsable(context, channelId) // ホーム画面に表示(デフォルトチャンネルのみ)
        }

    }
}
