package com.example.networktest_w4.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.networktest_w4.databinding.ItemLayoutBinding
import com.example.networktest_w4.model.presentation.VolumeItem

class BookAdapter(private val dataSet : List<VolumeItem>,
                  private val openDisplayFragment : (VolumeItem) -> Unit) : RecyclerView.Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BookViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        )

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.onBind(dataSet[position]){
            openDisplayFragment(it)
        }
    }

    override fun getItemCount() = dataSet.size

}