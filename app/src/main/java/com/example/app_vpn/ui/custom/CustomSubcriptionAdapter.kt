package com.example.app_vpn.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_vpn.R
import com.example.app_vpn.data.local.Benefit
import com.example.app_vpn.data.local.Subcription
import com.example.app_vpn.util.enable

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

        holder.itemView.setOnClickListener {
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