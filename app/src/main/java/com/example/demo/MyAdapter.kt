package com.example.demo

import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by tanqing.cc
 * on 2021/7/9
 */

class MyAdapter(
    private val data: MutableList<TodoData>,
    private val clickCheckBoxAction: (() -> Unit),
    private val clickCloseAction: (() -> Unit)
) : RecyclerView.Adapter<MyViewHolder>() {

    private val sdf = SimpleDateFormat("E, d MMM yyyy hh:mm:ss", Locale.ENGLISH)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_list_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Log.d("onBindViewHolder", "$position")
        holder.apply {
            checkBox.isChecked = data[position].checked
            val flags = content.paint.flags
            content.paint.flags = getPaintFlags(flags, checkBox.isChecked)
            content.text = data[position].content
            time.text = sdf.format(data[position].time)
            close.setOnClickListener {
                // 点击item删除该item
                deleteData(position)
                // 数据库删除该条记录
                clickCloseAction.invoke()
            }
            checkBox.setOnClickListener {
                // 更新UI
                val flags = content.paint.flags
                content.paint.flags = getPaintFlags(flags, checkBox.isChecked)
                content.postInvalidate()
                // 数据库数据状态更新
                clickCheckBoxAction.invoke()
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun getPaintFlags(flags: Int, checked: Boolean): Int {
        return if (checked) {
            flags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            0
        }
    }

    fun addData(position: Int, newData: TodoData) {
        data.add(position, newData)
        notifyItemInserted(position)
        notifyDataSetChanged()
    }

    fun deleteData(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

}

class MyViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
    val content: TextView = itemView.findViewById(R.id.tv_content)
    val time: TextView = itemView.findViewById(R.id.tv_time)
    val close: ImageView = itemView.findViewById(R.id.iv_close)
}