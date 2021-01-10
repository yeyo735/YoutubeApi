package com.yeyosystem.youtubeapi.view.play

import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener
import com.yeyosystem.youtubeapi.R
import com.yeyosystem.youtubeapi.tools.Constants
import com.yeyosystem.youtubeapi.tools.showShortToast
import kotlinx.android.synthetic.main.activity_play.*


class PlayActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    lateinit var viewModel: PlayViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        loadViewModel()
        loadYouTube()
    }

    override fun onPause() {
        super.onPause()
        if (viewModel.player != null) {
            viewModel.player?.release()
        }
    }

    override fun onStop() {
        if (viewModel.player != null) {
            viewModel.player?.release()
        }
        viewModel.player = null
        super.onStop()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun loadViewModel() {
        viewModel = PlayViewModel()
        viewModel.videoId = "7gwO8-oqwFw"
    }

    private fun loadYouTube() {
        ytp_view.initialize(Constants.apiYoutube, this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        showShortToast("Youtube Api Initialization Success")
        player?.let {
            viewModel.player = it
        }
        if (!wasRestored) {
            player?.cueVideo(viewModel.videoId)
        }
        listeners()
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        showShortToast("Youtube Api Initialization Failure")
    }

    private fun listeners() {
        viewModel.player?.let {
            it.setPlayerStateChangeListener(object : PlayerStateChangeListener {
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

            it.setPlaybackEventListener(object : PlaybackEventListener {
                override fun onBuffering(arg0: Boolean) {}
                override fun onPaused() {}
                override fun onPlaying() {}
                override fun onSeekTo(arg0: Int) {}
                override fun onStopped() {}
            })
        }
    }
}