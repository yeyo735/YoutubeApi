package com.yeyosystem.youtubeapi.view.play

import androidx.lifecycle.ViewModel
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class PlayViewModel: ViewModel() {
    val playerView: YouTubePlayerView? = null
    var player: YouTubePlayer? = null
    lateinit var videoId: String

    fun listeners() {
        player?.let {
            it.setPlayerStateChangeListener(object : YouTubePlayer.PlayerStateChangeListener {
                override fun onAdStarted() {}
                override fun onError(arg0: YouTubePlayer.ErrorReason) {}
                override fun onLoaded(arg0: String) {
                    it.setFullscreen(true)
                    it.play()
                }

                override fun onLoading() {}
                override fun onVideoEnded() {}
                override fun onVideoStarted() {}
            })

            it.setPlaybackEventListener(object : YouTubePlayer.PlaybackEventListener {
                override fun onBuffering(arg0: Boolean) {}
                override fun onPaused() {}
                override fun onPlaying() {}
                override fun onSeekTo(arg0: Int) {}
                override fun onStopped() {}
            })
        }
    }
}