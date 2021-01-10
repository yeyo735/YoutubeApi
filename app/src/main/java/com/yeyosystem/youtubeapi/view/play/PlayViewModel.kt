package com.yeyosystem.youtubeapi.view.play

import androidx.lifecycle.ViewModel
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class PlayViewModel: ViewModel() {
    val playerView: YouTubePlayerView? = null
    var player: YouTubePlayer? = null
    lateinit var videoId: String

}