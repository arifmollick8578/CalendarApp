package com.arif.calendarapp.application

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arif.calendarapp.R
import com.arif.calendarapp.data.remote.dto.TaskDetails
import com.arif.calendarapp.data.remote.dto.TaskModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CalendarTaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DailyTaskAdapter
    private lateinit var addTaskButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = getString(R.string.empty_string)
        viewModel = ViewModelProvider(this).get(CalendarTaskViewModel::class.java)

        recyclerView = findViewById<RecyclerView>(R.id.daily_task_recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            this@MainActivity.adapter = DailyTaskAdapter(ArrayList(viewModel.calendarEvent.value))
            adapter = this@MainActivity.adapter
        }

        addTaskButton = findViewById<ImageView>(R.id.add_task_image).apply {
            setOnClickListener {
                // Taking input to add TaskDetails using AlertDialog
                val taskIdView = EditText(context).apply {
                    hint = context.getString(R.string.enter_task_id)
                    layoutParams =
                        ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                    inputType = InputType.TYPE_CLASS_NUMBER
                }

                val taskTitle = EditText(context).apply {
                    hint = context.getString(R.string.enter_task_title)
                    layoutParams =
                        ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                }

                val linearLayout = LinearLayout(context).apply {
                    val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                    lp.setMargins(10, 10, 10, 10)
                    layoutParams = lp
                    orientation = LinearLayout.VERTICAL
                    // Adding 2 edit text to take input of taskId and taskTitle
                    addView(taskIdView)
                    addView(taskTitle)
                }
                AlertDialog.Builder(context)
                    .setView(linearLayout)
                    .setPositiveButton(context.getString(R.string.add)) { _, _ ->
                        viewModel.addDailyEvent(
                            TaskDetails(
                                Integer.valueOf(taskIdView.text.toString()),
                                TaskModel(taskTitle.text.toString(), taskTitle.text.toString())
                            )
                        )
                    }
                    .setNegativeButton("Cancel") { _, _ -> }
                    .show()
            }
        }

        viewModel.calendarEvent.observe(this) {
            adapter.dispatchData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_event_menu -> showToast("Add event menu")
            R.id.people_menu -> showToast("People menu event")
            R.id.setting_menu -> showToast("Setting menu clicked")
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    private fun showToast(title: String) {
        Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
    }
}
