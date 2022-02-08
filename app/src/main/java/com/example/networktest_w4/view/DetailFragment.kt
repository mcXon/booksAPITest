package com.example.networktest_w4.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.networktest_w4.R
import com.example.networktest_w4.databinding.BookDetailsFragmentLayoutBinding
import com.example.networktest_w4.model.presentation.VolumeItem

class DetailFragment: Fragment() {

    private lateinit var binding: BookDetailsFragmentLayoutBinding

    companion object {
        const val DETAIL_BOOK = "DetailBook"
        private const val TAG = "DetailFragment"
        fun newInstance(book: VolumeItem): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply{
                    putParcelable(DETAIL_BOOK, book)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BookDetailsFragmentLayoutBinding.inflate(inflater,container, false)

        arguments?.getParcelable<VolumeItem>(DETAIL_BOOK).let {
            //TODO add layout to the detail fragment
            Log.d(TAG, "onCreateView: $it")
            binding.tvDetailsTitle.text = binding.root.context.getString(R.string.book_title, it?.title)
            binding.tvBookAuthor.text = binding.root.context.getString(R.string.book_authors, it?.authors)

            return binding.root
        }
        return super.onCreateView(inflater, container, savedInstanceState)

    }
}