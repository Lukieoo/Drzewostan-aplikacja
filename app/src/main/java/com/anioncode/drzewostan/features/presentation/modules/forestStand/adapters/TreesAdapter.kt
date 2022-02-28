package com.anioncode.drzewostan.features.presentation.modules.forestStand.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anioncode.drzewostan.core.adapter.BindableAdapter
import com.anioncode.drzewostan.databinding.ItemTreeBinding
import com.anioncode.drzewostan.features.presentation.model.TreesDataDisplayable

class TreesAdapter(itemView: View) : BindableAdapter<TreesDataDisplayable>,
    RecyclerView.Adapter<TreesAdapter.TreesDataViewHolder>() {
    
    var listener: ((TreesDataDisplayable) -> Unit)? = null
   
    private val treesDataList by lazy {
        mutableListOf<TreesDataDisplayable>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreesDataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTreeBinding.inflate(inflater, parent, false)

        return TreesDataViewHolder(binding)
    }

    override fun getItemCount(): Int = treesDataList.size

    override fun onBindViewHolder(holder: TreesDataViewHolder, position: Int) {
        val treesData = treesDataList[position]
        holder.bind(treesData, listener)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setItems(items: List<TreesDataDisplayable>) {
        if (items.isNotEmpty()) {
            this.treesDataList.clear()
        }
        this.treesDataList.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnTreesDataClickListener(listener: (TreesDataDisplayable) -> Unit) {
        this.listener = listener
    }

    inner class TreesDataViewHolder(private val binding: ItemTreeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            treesData: TreesDataDisplayable,
            listener: ((TreesDataDisplayable) -> Unit)?
        ) {
            with(binding) {
                binding.item = treesData
                listener?.let { root.setOnClickListener { it(treesData) } }
                binding.executePendingBindings()
            }
        }
    }
}