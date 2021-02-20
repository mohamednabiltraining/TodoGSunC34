package com.route.todoappgsun

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.route.todoappgsun.database.model.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TasksAdapter(var tasks:MutableList<Task>)
    :RecyclerView.Adapter<TasksAdapter.ViewHolder> (){
    private var removedPosition:Int=0
    private lateinit var removedItems:Task
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
        holder.itemView.setOnClickListener(View.OnClickListener {onitemclick?.OnItemClick(position)})
        holder.itemView.setOnLongClickListener(View.OnLongClickListener {onlongitemclick?.OnUpdateLongClick(position);false})
    }

    fun changeData(tasks: List<Task>) {
        this.tasks= tasks as MutableList<Task>
        notifyDataSetChanged()
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.title
        val completed :CheckBox = itemView.completed
    }

    var onitemclick:OnItemClickListener?=null
    var onlongitemclick:OnLongItemClickListener?=null

    interface OnItemClickListener
    {
        fun OnItemClick(position: Int)
    }
    interface OnLongItemClickListener
    {
        fun OnUpdateLongClick(position: Int)
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder):Int
    {
        removedPosition=viewHolder.adapterPosition
        removedItems= tasks[viewHolder.adapterPosition]

        tasks.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar.make(viewHolder.itemView, "${removedItems.title} deleted", Snackbar.LENGTH_LONG).show()

        return  removedPosition
    }
}