package com.idelcano.moneycontrol.moneycontrol.presentation.presenters.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyAmount
import com.idelcano.moneycontrol.moneycontrol.utils.DateParser
import kotlinx.android.synthetic.main.view_money_amount_item.view.amount_quantity
import kotlinx.android.synthetic.main.view_money_amount_item.view.amount_title
import kotlinx.android.synthetic.main.view_money_amount_item.view.creation_date
import kotlinx.android.synthetic.main.view_money_amount_item.view.delete_amount_button

class MoneyAmountAdapter(val listener: (MoneyAmount) -> Unit) :
    RecyclerView.Adapter<MoneyAmountAdapter.ViewHolder>() {

    private var moneyAmounts: List<MoneyAmount?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.view_money_amount_item))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.itemView) {
        val moneyAmount: MoneyAmount = moneyAmounts[position]!!

        amount_title.text = moneyAmount.name
        var symbol: String = "-"
        if (moneyAmount.isPositive) {
            symbol = "+"
        }
        amount_quantity.text = symbol + moneyAmount.amount.toString()
        creation_date.text = DateParser().formatToUI(moneyAmount.creationDate)
        delete_amount_button.setOnClickListener { listener(moneyAmount) }
    }

    fun setMoneyAmounts(moneyAmounts: List<MoneyAmount?>) {
        this.moneyAmounts = moneyAmounts
        this.notifyDataSetChanged()
    }

    fun clearMoneyAmounts() {
        moneyAmounts = ArrayList()
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = moneyAmounts.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(this.context).inflate(layoutRes, this, false)
    }
}