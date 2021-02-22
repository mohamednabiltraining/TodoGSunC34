package com.route.todoappgsun

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.route.base.BaseActivity
import com.route.todoappgsun.database.TasksDataBase
import com.route.todoappgsun.database.model.Task
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        setupViews()
        save_btn.setOnClickListener{
            addTask()
        }
    }

    private fun setupViews() {
        title_layout.editText?.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                title_layout.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        desc_layout.editText?.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                desc_layout.error= null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun addTask() {
        if(!validData())return
        val taskTitle = title_layout.editText?.text.toString()
        val taskDesc = desc_layout.editText?.text.toString()
        val task = Task(title =taskTitle,description = taskDesc,
        isCompleted = completed.isChecked )

        TasksDataBase.getInstance(applicationContext)
            .tasksDao()
            .addTask(task)

        showDialoge(messageId = R.string.task_added_successfully,
        posActionName = R.string.ok,
        posAction = DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            finish()
        })
    }

    private fun validData(): Boolean {
        var isValid = true;
        if(title_layout.editText?.text.toString().isBlank()){
            isValid = false
            title_layout.error = "please enter valid title"
        }
        if(desc_layout.editText?.text.toString().isBlank()){
            isValid = false
            desc_layout.error = "please enter valid description"
        }

        return isValid
    }
}