package com.route.todoappgsun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.route.base.BaseActivity
import com.route.todoappgsun.database.TasksDataBase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    lateinit var adapter: TasksAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initRecyclerView()
        add_btn.setOnClickListener{
            openAddTaskActivity()
        }
        adapter.onitemclick=object : TasksAdapter.OnItemClickListener
        {
            override fun OnItemClick(position: Int) {
                openTaskInfoActivity(position)

            }



        }

        adapter.onlongitemclick=object : TasksAdapter.OnLongItemClickListener
        {
            override fun OnUpdateLongClick(position: Int) {
                openTaskUpdateActivity(position)
            }

        }
        swapDeleteTask()

    }

    private fun initRecyclerView() {
        adapter = TasksAdapter(mutableListOf())
        recycler_view.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        val tasks =
            TasksDataBase.getInstance(applicationContext)
                .tasksDao()
                .getAllTasks()
        adapter.changeData(tasks)

    }

    private fun openAddTaskActivity() {
        val intent = Intent(this,AddTaskActivity::class.java)
        startActivity(intent)
    }

    private fun openTaskInfoActivity(position: Int) {
        val intent = Intent(this,TaskInfoActivity::class.java)
        val tasks =
            TasksDataBase.getInstance(applicationContext)
                .tasksDao()
                .getAllTasks()
        adapter.changeData(tasks)

        intent.putExtra("infoTitle",tasks.get(position).title)
        intent.putExtra("infoDesc",tasks.get(position).description)
        intent.putExtra("infoCompleted",tasks.get(position).isCompleted)
        startActivity(intent)
    }

    private fun openTaskUpdateActivity(position: Int) {
        val intent = Intent(this,TaskUpdateActivity::class.java)
        val tasks =
            TasksDataBase.getInstance(applicationContext)
                .tasksDao()
                .getAllTasks()
        adapter.changeData(tasks)

        intent.putExtra("updateTitle",tasks.get(position).title)
        intent.putExtra("updateDesc",tasks.get(position).description)
        intent.putExtra("updateCompleted",tasks.get(position).isCompleted)
        intent.putExtra("taskId",tasks.get(position).id)
        startActivity(intent)
    }

    private fun swapDeleteTask()
    {
        val itemTouchHelperCallback= object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                var taskPos:Int= adapter.removeItem(viewHolder)
                var task=TasksDataBase.getInstance(applicationContext)
                    .tasksDao()
                    .getAllTasks()

                TasksDataBase.getInstance(applicationContext)
                    .tasksDao()
                    .deleteTask(task.get(taskPos))
            }

        }

        val itemTouchHelper= ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }
}