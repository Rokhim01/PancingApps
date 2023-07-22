package com.example.pancingapps.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pancingapps.R
import com.example.pancingapps.model.Pancing


class PancingListAdapter(
    private val onItemClickListener: (Pancing) -> Unit

): ListAdapter<Pancing, PancingListAdapter.PancingViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PancingViewHolder {
       return PancingViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PancingViewHolder, position: Int) {
        val pancing =getItem(position)
        holder.bind(pancing)
        holder.itemView.setOnClickListener {
            onItemClickListener(pancing)
        }
    }

    class PancingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nametextView3)
        private val addressTextView: TextView = itemView.findViewById(R.id.addresstextview)
        private val sidedishTextView: TextView = itemView.findViewById(R.id.sidedishtextView4)
        private val drinkTextView: TextView = itemView.findViewById(R.id.drinktextView4)
        private val priceTextView: TextView = itemView.findViewById(R.id.pricetextView5)

        fun bind(pancing: Pancing) {
            nameTextView.text = pancing?.name
            addressTextView.text = pancing?.address
            sidedishTextView.text = pancing?.jumlah
            drinkTextView.text =   pancing?.berat
            priceTextView.text =   pancing?.harga

        }

        companion object {
            fun create (parent: ViewGroup): PancingListAdapter.PancingViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pancingtire, parent,false)
                return PancingViewHolder(view)

            }
        }
    }

    companion object{
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Pancing>(){
            override fun areItemsTheSame(oldItem: Pancing, newItem: Pancing): Boolean {
               return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Pancing, newItem: Pancing): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
