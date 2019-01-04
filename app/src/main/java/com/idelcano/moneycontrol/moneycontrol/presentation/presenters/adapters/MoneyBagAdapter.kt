package com.idelcano.moneycontrol.moneycontrol.presentation.presenters.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.utils.DateParser
import kotlinx.android.synthetic.main.view_money_bag.view.*


class MoneyBagAdapter(val addlistener: (MoneyBag) -> Unit, val loglistener: (MoneyBag) -> Unit) :
    RecyclerView.Adapter<MoneyBagAdapter.ViewHolder>() {

    private var moneyBags: List<MoneyBag?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.view_money_bag))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.itemView) {
        val moneyBag: MoneyBag = moneyBags[position]!!

        item_title.setText(moneyBag.name)
        //item_image.setImageDrawable(moneyBag.iconPath)
        date.text = DateParser().formatToUI(moneyBag.dateLimit)
        date_remaining.text = moneyBag.remainingTime().toString()
        item_amount.text = moneyBag.amount.toString()
        item_amount_result.text = moneyBag.remainingMoney().toString()

        add_button.setOnClickListener { addlistener(moneyBag) }
        log_button.setOnClickListener { loglistener(moneyBag) }
    }

    fun setMoneyBags (moneys: List<MoneyBag?>){
        this.moneyBags = moneys
        this.notifyDataSetChanged()
    }

    fun clearMoneyBags() {
        moneyBags = ArrayList()
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = moneyBags.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(this.context).inflate(layoutRes, this, false)
    }
}