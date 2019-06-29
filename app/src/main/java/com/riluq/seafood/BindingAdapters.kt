package com.riluq.seafood

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.riluq.seafood.network.Seafood
import com.riluq.seafood.overview.OverviewAdapter
import com.riluq.seafood.overview.TheMealDBApiStatus
import androidx.coordinatorlayout.widget.CoordinatorLayout



@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(data: List<Seafood>?) {
    val adapter = adapter as OverviewAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(this)
    }
}

@BindingAdapter("textSeafoodName")
fun TextView.bindSeafoodName(text: String?) {
    text?.let {
        this.text = text
    }
}

@BindingAdapter("seafoodApiStatus")
fun ImageView.bindStatus(status: TheMealDBApiStatus?) {

    when(status) {
        TheMealDBApiStatus.LOADING -> {
            visibility = visible()
            setImageResource(R.drawable.loading_animation)
        }
        TheMealDBApiStatus.ERROR -> {
            visibility = visible()
            setImageResource(R.drawable.ic_connection_error)
        }
        TheMealDBApiStatus.DONE -> {
            visibility = gone()
        }
    }
}
