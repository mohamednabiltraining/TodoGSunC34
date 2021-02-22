package com.route.todoappgsun

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.todoappgsun.database.model.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TasksAdapter(var tasks:List<Task>)
    :RecyclerView.Adapter<TasksAdapter.ViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks.get(position)

        holder.title.setText(task.title)
        holder.completed.isChecked = task.isCompleted?:false
    }

    fun changeData(tasks: List<Task>) {
        this.tasks=tasks
        notifyDataSetChanged()
    }

    fun getTaskposition(position: Int) :Task {
     return tasks.get(position)

    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.title
        val completed :CheckBox = itemView.completed
    }
}