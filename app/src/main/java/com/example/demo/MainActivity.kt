package com.example.demo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.demo.InputActivity.Companion.CONTENT_STR
import com.example.demo.dao.AppDatabase
import com.example.demo.dao.TodoListDao
import com.example.demo.dao.TodoListEntity
import com.example.demo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyAdapter

    private lateinit var db: TodoListDao
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDB()
        initView()
    }

    private fun initDB() {
        sp = this.getPreferences(Context.MODE_PRIVATE)
        db = Room.databaseBuilder(
            MyApplication.context,
            AppDatabase::class.java,
            "todolist.db"
        )
            .allowMainThreadQueries()
            .build()
            .getDao()
    }


    private fun ClosedRange<Char>.randomString(length: Int) =
        (1..length)
            .map { (Random().nextInt(endInclusive.toInt() - start.toInt()) + start.toInt()).toChar() }
            .joinToString("")

    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

//        val data = mutableListOf<TodoListEntity>().apply {
//            for (i in 1..20) {
//                val data = TodoListEntity(
//                    i,
//                    ('a'..'z').randomString(10),
//                    System.currentTimeMillis() + (0..100000000).random(),
//                    (0..1).random() == 0
//                )
//            }
//        }

        val data = db.getAllData()

        myAdapter = MyAdapter(data,{ data ->
            db.updateData(data)
        }, { data ->
            db.deleteData(data)
        })


        binding.rvList.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))
            itemAnimator = DefaultItemAnimator()
        }

        binding.fab.setOnClickListener {
            Intent(this, InputActivity::class.java).apply {
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
                val id = sp.getInt(DATA_BASE_ID, 0)
                sp.edit().apply {
                    putInt(DATA_BASE_ID, id + 1)
                    apply()
                }
                val data = TodoListEntity(id ,it, time, false)
                db.addData(data)
                myAdapter.addData(0, data)
                binding.rvList.smoothScrollToPosition(0)
            }
        }
    }

    companion object {
        const val REQUEST_CODE_INPUT = 10086
        const val DATA_BASE_ID = "database_id"
    }
}