package com.route.todoappgsun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.route.base.BaseActivity
import com.route.todoappgsun.database.TasksDataBase
import com.route.todoappgsun.database.model.Task
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    var tasks = mutableListOf<Task>()
    lateinit var adapter: TasksAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        add_btn.setOnClickListener {
            openAddTaskActivity()
        }

    }

    private fun initRecyclerView() {
        adapter = TasksAdapter(tasks)
        recycler_view.adapter = adapter

        val simplecallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = adapter.getTaskposition(position)
                TasksDataBase.getInstance(applicationContext).tasksDao().deleteTask(task)
                reload()

                makeSnackBar(message = "Task deleted succesfully",
                    click = View.OnClickListener {
                        TasksDataBase.getInstance(applicationContext).tasksDao().addTask(task)
                        reload()
                    }
                    , view = recycler_view
                    , actionSting = "Undo")

            }
        }
        val itemTouchHelper = ItemTouchHelper(simplecallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }

    fun reload() {
        val tasks =
            TasksDataBase.getInstance(applicationContext)
                .tasksDao()
                .getAllTasks()
        adapter.changeData(tasks)
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        reload()
    }

    private fun openAddTaskActivity() {
        val intent = Intent(this, AddTaskActivity::class.java)
        startActivity(intent)
    }

}