package com.simplemobiletools.launcher.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simplemobiletools.commons.extensions.getProperTextColor
import com.simplemobiletools.commons.extensions.portrait
import com.simplemobiletools.commons.extensions.realScreenSize
import com.simplemobiletools.launcher.R
import com.simplemobiletools.launcher.activities.SimpleActivity
import com.simplemobiletools.launcher.models.AppLauncher
import kotlinx.android.synthetic.main.item_launcher_label.view.*

class LaunchersAdapter(
    val activity: SimpleActivity,
    val launchers: ArrayList<AppLauncher>,
    val itemClick: (Any) -> Unit
) : RecyclerView.Adapter<LaunchersAdapter.ViewHolder>() {
    private var textColor = activity.getProperTextColor()
    private var iconPadding = 0

    init {
        calculateIconWidth()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_launcher_label, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(launchers[position])
    }

    override fun getItemCount() = launchers.size

    private fun calculateIconWidth() {
        val currentColumnCount = if (activity.portrait) {
            activity.resources.getInteger(R.integer.portrait_column_count)
        } else {
            activity.resources.getInteger(R.integer.landscape_column_count)
        }

        val iconWidth = activity.realScreenSize.x / currentColumnCount
        iconPadding = (iconWidth * 0.1f).toInt()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(launcher: AppLauncher): View {
            itemView.apply {
                launcher_label.text = launcher.title
                launcher_label.setTextColor(textColor)
                launcher_icon.setImageDrawable(launcher.drawable!!)
                launcher_icon.setPadding(iconPadding, iconPadding, iconPadding, 0)
                setOnClickListener { itemClick(launcher) }
            }

            return itemView
        }
    }
}