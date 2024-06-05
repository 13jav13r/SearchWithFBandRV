package com.example.buttonnavigationviewlesson.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buttonnavigationviewlesson.R
import com.example.buttonnavigationviewlesson.databinding.ProductItemBinding
import com.squareup.picasso.Picasso

class AdapterRVHomeFrag(private val list: ArrayList<Product>, private val listener: Listener) :
    RecyclerView.Adapter<AdapterRVHomeFrag.ProductHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductHolder(view)
    }

    override fun getItemCount(): Int { // В него надо передавать сколько раз должен заполняться вью холдер
        return list.size
    }

    // Наполнение вью холдера после его создания
    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val currentProduct = list[position]
        holder.bind(holder, currentProduct, listener)

    }

    class ProductHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding = ProductItemBinding.bind(item)

        private lateinit var firstListener: OnClickListener
        private lateinit var secondListener: OnClickListener


        fun bind(holder: ProductHolder, product: Product, listener: Listener) {
            firstListener = OnClickListener {
                holder.binding.imageButton.setImageResource(R.drawable.ic_favorite_true)
                holder.binding.imageButton.setOnClickListener(secondListener)
            }
            secondListener = OnClickListener {
                holder.binding.imageButton.setImageResource(R.drawable.ic_favorites)
                holder.binding.imageButton.setOnClickListener(firstListener)
            }

            Picasso.get().load(product.image).into(holder.binding.imProduct)
            holder.binding.tvTitle.text = product.moduleName
            holder.binding.tvPrice.text = product.itemBasePrice.toString()
            holder.itemView.setOnClickListener {
                listener.onClick(product)
            }
            holder.binding.imageButton.setOnClickListener(firstListener)
        }
    }

    interface Listener {
        fun onClick(product: Product)
    }
}