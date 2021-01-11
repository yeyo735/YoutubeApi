package com.yeyosystem.youtubeapi.view.list

import androidx.lifecycle.ViewModel
import com.yeyosystem.youtubeapi.tools.Search
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import java.io.IOException


class ListViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext = Job() + Dispatchers.IO

    fun search( queryTerm: String) {
        val search= Search()
        async(coroutineContext) {
            try {
                search.main(null)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.onAwait

    }


}