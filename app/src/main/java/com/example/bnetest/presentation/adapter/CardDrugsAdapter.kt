package com.example.bnetest.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bnetest.data.Drugs
import com.example.bnetest.databinding.CardDrugsBinding
import com.example.bnetest.utils.Utils
import com.example.bnetest.utils.Utils.Companion.BASE_URL


class CardDrugsAdapter(private val onItemClick: (Drugs) -> Unit) :
   PagingDataAdapter<Drugs, CardDrugsHolder>(CardDrugsDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDrugsHolder {
        return CardDrugsHolder(
            CardDrugsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CardDrugsHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            title.text = item!!.name
            text.text = item.description
            item.let {
                Glide.with(imageDrugs.context)
                    .load(BASE_URL + it.image)
                    .into(imageDrugs)
            }

            root.setOnClickListener {
                onItemClick(item)
            }

        }
    }
}

class CardDrugsDiffUtilCallBack : DiffUtil.ItemCallback<Drugs>() {
    override fun areItemsTheSame(oldItem: Drugs, newItem: Drugs): Boolean = oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Drugs, newItem: Drugs): Boolean = oldItem == newItem

}

class CardDrugsHolder(val binding: CardDrugsBinding) : RecyclerView.ViewHolder(binding.root)
