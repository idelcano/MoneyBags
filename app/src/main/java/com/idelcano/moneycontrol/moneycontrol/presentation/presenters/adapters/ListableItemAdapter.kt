package com.idelcano.moneycontrol.moneycontrol.presentation.presenters.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.domain.entity.DayCounter
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.utils.DateParser
import kotlinx.android.synthetic.main.view_money_bag.view.add_button
import kotlinx.android.synthetic.main.view_money_bag.view.amount_container
import kotlinx.android.synthetic.main.view_money_bag.view.amount_header
import kotlinx.android.synthetic.main.view_money_bag.view.date
import kotlinx.android.synthetic.main.view_money_bag.view.date_info_title
import kotlinx.android.synthetic.main.view_money_bag.view.date_remaining
import kotlinx.android.synthetic.main.view_money_bag.view.delete_button
import kotlinx.android.synthetic.main.view_money_bag.view.item_amount
import kotlinx.android.synthetic.main.view_money_bag.view.item_amount_result
import kotlinx.android.synthetic.main.view_money_bag.view.item_title
import kotlinx.android.synthetic.main.view_money_bag.view.log_button
import kotlinx.android.synthetic.main.view_money_bag.view.remaining_days_title

class ListableItemAdapter(
    val addlistener: (MoneyBag) -> Unit,
    val loglistener: (MoneyBag) -> Unit,
    val removelistener: (IListable) -> Unit
) :
    RecyclerView.Adapter<ListableItemAdapter.ViewHolder>() {

    private var items: MutableList<IListable?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.view_money_bag))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.itemView) {
        val listable: IListable = items[position]!!
        var visibility: Int = View.VISIBLE
        when (listable) {
            is MoneyBag -> {
                item_title.setText(listable.name)
                date.text = DateParser().formatToUI(listable.dateLimit)
                // item_image.setImageDrawable(moneyBag.iconPath)
                date.text = DateParser().formatToUI(listable.dateLimit)
                date_remaining.text = listable.remainingTime().toString()
                item_amount.text = listable.amount.toString()
                item_amount_result.text = listable.remainingMoney().toString()

                add_button.setOnClickListener { addlistener(listable) }
                log_button.setOnClickListener { loglistener(listable) }
            }
            is DayCounter -> {
                visibility = View.GONE
                item_title.setText(listable.name)
                date.text = DateParser().formatToUI(listable.createdDate)
                date_remaining.text = listable.passTime().toString()
                add_button.visibility = View.INVISIBLE
                log_button.visibility = View.INVISIBLE
                amount_container.visibility = View.INVISIBLE
                amount_header.visibility = View.INVISIBLE
                date_info_title.text = context.getText(R.string.creation_date_hint)
                remaining_days_title.text = context.getText(R.string.days)
            }
        }
        add_button.visibility = visibility
        log_button.visibility = visibility
        amount_container.visibility = visibility
        amount_header . visibility = visibility
        delete_button.setOnClickListener { removelistener(listable) }
    }

    fun setItems(items: List<IListable?>) {
        this.items.addAll(items)
        this.items
    }

    fun showItems() {
        this.notifyDataSetChanged()
    }

    fun clearItems() {
        items = ArrayList()
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(this.context).inflate(layoutRes, this, false)
    }
}