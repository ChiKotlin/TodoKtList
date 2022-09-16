package com.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.common.model.RoomModel
import com.app.ui.R
import com.app.ui.databinding.RoomTodoViewBinding
import com.thekhaeng.pushdownanim.PushDownAnim
import java.util.*

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.MyViewHolder>() {
    val roomModelList = ArrayList<RoomModel>()
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: RoomTodoViewBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.room_todo_view,
                parent,
                false
            )
        return MyViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todo = roomModelList[position]

        holder.binding.txtTitle.text = todo.getTitle()
        holder.binding.txtDesc.text = todo.getDescription()
        holder.binding.txtSalary.text = todo.getSalary().toString()
        holder.binding.txtTime.text = todo.getTime()
        holder.binding.txtType.text = todo.getType()
    }

    override fun getItemCount() = roomModelList.size

    fun addAll(list: List<RoomModel>) {
        roomModelList.clear()
        roomModelList.addAll(list)
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RoomTodoViewBinding, mListener: OnItemClickListener?) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    mListener?.onItemClick(position)
                }
            }
            PushDownAnim.setPushDownAnimTo(itemView)
            binding.imageDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    mListener?.onDeleteClick(position)
                }
            }
        }
    }
}
