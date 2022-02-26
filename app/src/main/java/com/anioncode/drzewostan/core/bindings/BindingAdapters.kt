package com.anioncode.drzewostan.core.bindings

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anioncode.drzewostan.core.adapter.BindableAdapter
import com.anioncode.drzewostan.core.base.UiState
import com.bumptech.glide.Glide

object BindingAdapters {

    @BindingAdapter("app:showOnPendingState")
    @JvmStatic
    fun showOnPendingState(swipeRefreshLayout: SwipeRefreshLayout, uiState: UiState) {
        swipeRefreshLayout.isRefreshing = uiState == UiState.Pending
    }

    @BindingAdapter("app:showOnPendingState")
    @JvmStatic
    fun showOnPendingState(progressBar: ProgressBar, uiState: UiState) {
        progressBar.visibility = if (uiState == UiState.Pending) View.VISIBLE else View.INVISIBLE
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun <T> setItems(recyclerView: RecyclerView, items: List<T>?) {
        if (items.isNullOrEmpty()) return
        (recyclerView.adapter as? BindableAdapter<T>)?.setItems(items)
    }

    @BindingAdapter("setWebViewClient")
    @JvmStatic
    fun setWebViewClient(view: WebView, client: WebViewClient?) {
        view.webViewClient = client!!
    }

    @BindingAdapter("webViewUrl")
    @JvmStatic
    fun WebView.updateUrl(url: String?) {
        url?.let {
            loadUrl(url)
        }
    }

    @BindingAdapter(value = ["app:imageUrl", "app:placeholder"], requireAll = false)
    @JvmStatic
    fun setImage(imageView: ImageView, imageUrl: String, @DrawableRes placeholder: Int) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(placeholder)
            .into(imageView)
    }
}