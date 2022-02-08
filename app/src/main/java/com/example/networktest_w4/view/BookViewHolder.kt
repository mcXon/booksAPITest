package com.example.networktest_w4.view

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.networktest_w4.R
import com.example.networktest_w4.databinding.ItemLayoutBinding
import com.example.networktest_w4.model.presentation.VolumeItem
import com.squareup.picasso.Picasso

class BookViewHolder(private val binding : ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    fun onBind(bookItem : VolumeItem, callBack: (VolumeItem)->Unit){
        //String template
        binding.tvBookAuthor.text = binding.root.context.getString(R.string.book_authors, bookItem.authors)
        binding.tvBookTitle.text = binding.root.context.getString(R.string.book_title, bookItem.title)

        Picasso.get().load(bookItem.imageLinks.thumbnail).into(binding.ivBookThumb)
        binding.root.setOnClickListener{
            callBack(bookItem)
        }
    }
}