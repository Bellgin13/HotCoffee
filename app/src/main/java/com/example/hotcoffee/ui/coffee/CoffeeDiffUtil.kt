package com.example.hotcoffee.ui.coffee

import androidx.recyclerview.widget.DiffUtil
import com.example.hotcoffee.model.Coffee

class CoffeeDiffUtil(
    private val oldList: List<Coffee>,
    private val newList: List<Coffee>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Aynı kahve mi? (örneğin title'ı baz alıyoruz)
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Tüm içeriği birebir aynı mı?
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
