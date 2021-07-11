package com.example.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.input_activity_layout.*

/**
 * Created by tanqing.cc
 * on 2021/7/11
 */

class InputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_activity_layout)
        setSupportActionBar(toolbar)

        bt_confirm.setOnClickListener {
            val str = et_content.text.toString()
            if (!str.isNullOrEmpty()) {
                Intent(this, MainActivity::class.java).apply {
                    putExtra(CONTENT_STR, str)
                    setResult(RESULT_OK, this)
                    finish()
                }
            }
        }
    }

    companion object {
        const val CONTENT_STR = "content_str"
    }

}