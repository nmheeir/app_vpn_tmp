package com.example.app_vpn.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.app_vpn.R
import com.example.app_vpn.data.local.Benefit
import com.example.app_vpn.data.local.Subcription
import com.example.app_vpn.util.enable
import com.google.android.material.card.MaterialCardView

class CustomSubcriptionAdapter(
    private val list: List<Subcription>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<CustomSubcriptionAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val number: TextView = itemView.findViewById<TextView>(R.id.number)
        val duration: TextView = itemView.findViewById<TextView>(R.id.duration)
        val price: TextView = itemView.findViewById<TextView>(R.id.price)
        val radioButtonPricing: RadioButton =
            itemView.findViewById<RadioButton>(R.id.radioButtonPricing)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pricing, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subscription = list[position]
        holder.number.text = subscription.number.toString()
        holder.price.text = subscription.price
        holder.duration.text = subscription.duration

        // Xác định trạng thái checked của RadioButton
        holder.radioButtonPricing.isChecked = subscription.selected

        // Sửa màu border dựa vào trạng thái checked của RadioButton
        val materialCardView = holder.itemView as MaterialCardView

        holder.radioButtonPricing.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                materialCardView.strokeColor = ContextCompat.getColor(holder.itemView.context, android.R.color.holo_orange_light)
            } else {
                materialCardView.strokeColor = ContextCompat.getColor(holder.itemView.context, android.R.color.white)
            }
        }

        holder.radioButtonPricing.setOnClickListener {
            // Thực hiện logic chọn item
            list.forEach { it.selected = false }
            subscription.selected = true

            // Thực hiện hành động khi item được chọn
            onClick(holder.adapterPosition)

            // Cập nhật giao diện
            notifyDataSetChanged()
        }
    }
}