package com.example.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.InputActivity.Companion.CONTENT_STR
import com.example.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val data = mutableListOf<TodoData>().apply {
            for (i in 0..19) {
                add(
                    TodoData(
                        (0..100000).random().toString(),
                        System.currentTimeMillis() + (0..100000000).random(),
                        (0..1).random() == 0
                    )
                )
            }
        }

        // todo 数据库更新
        myAdapter = MyAdapter(data, {

        }, {

        })


        binding.rvList.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))
            itemAnimator = DefaultItemAnimator()
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java).apply {
                startActivityForResult(this, REQUEST_CODE_INPUT)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_INPUT && resultCode == RESULT_OK) {
            val content = data?.getStringExtra(CONTENT_STR)
            content?.let {
                val time = System.currentTimeMillis()
                myAdapter.addData(1, TodoData(it, time, false))
            }
        }
    }

    companion object {
        const val REQUEST_CODE_INPUT = 10086
    }
}