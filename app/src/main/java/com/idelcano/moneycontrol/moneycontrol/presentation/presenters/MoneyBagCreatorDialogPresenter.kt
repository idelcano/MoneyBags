package com.idelcano.moneycontrol.moneycontrol.presentation.presenters

import android.support.v4.app.DialogFragment
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.SaveMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.fragments.MoneyBagCreatorDialogFragment
import com.idelcano.moneycontrol.moneycontrol.utils.DateParser
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.*
import java.util.*

class MoneyBagCreatorDialogPresenter{
    lateinit var saveMoneyBagUseCase : SaveMoneyBagUseCase
    var view : MoneyBagCreatorDialogFragment? = null

    fun initPresenter(view : MoneyBagCreatorDialogFragment, saveMoneyBagUseCase : SaveMoneyBagUseCase) {
        this.view = view
        this.saveMoneyBagUseCase = saveMoneyBagUseCase
    }

    fun detachView() {
        view?.fragmentManager?.findFragmentByTag(view?.TAG_DIALOG)?.let {
            (it as DialogFragment).dismiss()
        }
        view?.onDetach()
        destroyView()
    }

    fun close() {
        detachView()
    }

    fun saveMoneyBag() {
        val name : String = view!!.edit_name.text.toString()
        val dateValue : String = view!!.edit_date.text.toString()
        val amountValue : String = view!!.edit_amount.text.toString()
        if(name.length==0) {
            view!!.showNameError()
            return
        }
        if(amountValue.length==0) {
            view!!.showAmountError()
            return
        }
        if(dateValue.length==0) {
            view!!.showDateError()
            return
        }
        val date : Date = parseFromUI(dateValue)
        val amount : Long = amountValue.toLong()
        val priority : Int = view!!.priority_seek_bar.progress+1
        saveMoneyBagUseCase.execute(MoneyBag(name = name, dateLimit = date, amount = amount, createdDate = Date(), priority = priority, iconPath = ""))
        close()
    }

    fun formatDateToUI(year : Int, month: Int, day : Int): String {
        var calendar : Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return DateParser().formatToUI(calendar.time)
    }

    fun parseFromUI(dateValue : String): Date {
        return DateParser().parseFromUI(dateValue)
    }

    fun destroyView() {
        view = null
    }

    interface View {
        fun showNameError()

        fun showDateError()

        fun showAmountError()

        fun saveMoneyBag()

        fun cancel()
    }
}