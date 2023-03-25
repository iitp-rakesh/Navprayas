package com.example.navprayas.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class ImageUtils {
    fun LoadImageWithLoader(context: Context, imageView: ImageView, loader: ImageView,image:String){
        Glide.with(context)
            .load(image)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    // hide the loader if the image fails to load
                    loader.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // hide the loader if the image loads successfully
                    loader.visibility = View.GONE
                    return false
                }
            })
            .into(imageView)
    }
}
