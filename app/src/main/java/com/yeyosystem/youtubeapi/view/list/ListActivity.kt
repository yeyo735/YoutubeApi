package com.yeyosystem.youtubeapi.view.list

import android.os.Bundle
import com.yeyosystem.youtubeapi.R
import com.yeyosystem.youtubeapi.tools.BaseActivity
import kotlinx.android.synthetic.main.item_search_video.*

class ListActivity : BaseActivity() {

    lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        loadViewModel()
        loadReferences()
    }

    private fun loadViewModel() {
        viewModel = ListViewModel()
    }

    private fun loadReferences(){
        btn_search.setOnClickListener {
            viewModel.search("")
        }
    }
}