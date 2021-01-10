package com.yeyosystem.youtubeapi.view.main

import android.content.Intent
import android.os.Bundle
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.yeyosystem.youtubeapi.R
import com.yeyosystem.youtubeapi.tools.BaseActivity
import com.yeyosystem.youtubeapi.view.play.PlayActivity

class MainActivity : BaseActivity() {

    private val mTransport: HttpTransport = NetHttpTransport()
    private val mJsonFactory: GsonFactory = GsonFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, PlayActivity::class.java)
        startActivity(intent)
    }
}