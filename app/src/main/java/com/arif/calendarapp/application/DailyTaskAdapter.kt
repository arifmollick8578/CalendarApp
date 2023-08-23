package com.arif.calendarapp.application

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arif.calendarapp.R
import com.arif.calendarapp.common.getColor
import com.arif.calendarapp.common.getDrawable
import com.arif.calendarapp.data.remote.dto.TaskDetails
import kotlin.random.Random

/** Adapter class to display task item in daily task section. */
class DailyTaskAdapter(private val dataSet: ArrayList<TaskDetails>) :
    RecyclerView.Adapter<DailyTaskAdapter.DailyTaskViewHolder>() {
    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyTaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.daily_task_item,
            parent,
            false
        )
        return DailyTaskViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: DailyTaskViewHolder, position: Int) {
        holder.bind(dataSet[position], isItemSelected = selectedPosition == position)
        holder.itemView.setOnClickListener {
            val previousSelected = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousSelected)
            notifyItemChanged(selectedPosition)
        }
    }

    /** ViewHolder class to set data to task items. */
    inner class DailyTaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.daily_task_title)
        private val icon: ImageView = view.findViewById(R.id.task_image)
        private val endIcon: ImageView = view.findViewById(R.id.end_icon)
        private val textOverImage: TextView = view.findViewById(R.id.text_over_image)

        fun bind(item: TaskDetails, isItemSelected: Boolean) {
            title.text = item.taskModel.title
            textOverImage.text = item.taskId.toString()
            with(itemView) {
                if (isItemSelected) {
                    // Update background color/icon tint on item selected.
                    background = getDrawable(R.drawable.daily_task_margin)
                    icon.setColorFilter(getColor(R.color.white))
                    icon.setBackgroundColor(getColor(R.color.toolbar_icon_background))
                    endIcon.setColorFilter(getColor(R.color.white))
                    title.setTextColor(getColor(R.color.white))
                    elevation = 8F
                    textOverImage.setTextColor(getColor(R.color.gray))
                } else {
                    // Restore background color/icon tint on item deselected.
                    setBackgroundColor(getColor(R.color.white))
                    icon.setColorFilter(getRandomColor())
                    icon.setBackgroundColor(getColor(R.color.white))
                    endIcon.setColorFilter(getColor(R.color.gray))
                    title.setTextColor(getColor(R.color.gray))
                    itemView.elevation = 0F
                    textOverImage.setTextColor(getColor(R.color.white))
                }
            }

        }
    }

    /** Dispatch task adapter data comparing with diffUtil. */
    fun dispatchData(data: List<TaskDetails>) {
        val diff = DataDiffUtil(data, dataSet)
        val callback = DiffUtil.calculateDiff(diff)
        dataSet.apply {
            clear()
            addAll(data)
        }
        callback.dispatchUpdatesTo(this@DailyTaskAdapter)
    }

    /** Class to optimize data updating on adapter. */
    inner class DataDiffUtil(
        private val newList: List<TaskDetails>,
        private val oldList: List<TaskDetails>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].taskModel == newList[newItemPosition].taskModel
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

    private fun getRandomColor(): Int {
        val r = Random.nextInt(255)
        val g = Random.nextInt(255)
        val b = Random.nextInt(255)
        return Color.rgb(r, g, b)
    }
}