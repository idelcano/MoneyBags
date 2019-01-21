package com.idelcano.moneycontrol.moneycontrol.presentation.presenters

import android.support.v4.app.DialogFragment
import android.widget.CompoundButton
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyAmount
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.DeleteMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.SaveMoneyAmountUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyAmountCreatorDialogFragment
import kotlinx.android.synthetic.main.create_money_amount_dialog_layout.amount_type
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.edit_amount
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.edit_name
import java.util.Date

class MoneyAmountCreatorDialogPresenter {
    lateinit var deleteMoneyBagUseCase: DeleteMoneyBagUseCase
    lateinit var saveMoneyAmountUseCase: SaveMoneyAmountUseCase
    lateinit var getMoneyBagUseCase: GetMoneyBagUseCase
    lateinit var moneyBag: MoneyBag
    var view: MoneyAmountCreatorDialogFragment? = null

    fun initPresenter(
        view: MoneyAmountCreatorDialogFragment,
        deleteMoneyBagUseCase: DeleteMoneyBagUseCase,
        saveMoneyAmountUseCase: SaveMoneyAmountUseCase,
        getMoneyBagUseCase: GetMoneyBagUseCase
    ) {
        this.view = view
        this.deleteMoneyBagUseCase = deleteMoneyBagUseCase
        this.saveMoneyAmountUseCase = saveMoneyAmountUseCase
        this.getMoneyBagUseCase = getMoneyBagUseCase
    }

    fun detachView() {
        view?.fragmentManager?.findFragmentByTag(view?.companion?.TAG_DIALOG)?.let {
            (it as DialogFragment).dismiss()
        }
        view?.onDetach()
        destroyView()
    }

    fun close() {
        detachView()
    }

    fun saveMoneyAmount() {
        val name: String = view!!.edit_name.text.toString()
        val amountValue: String = view!!.edit_amount.text.toString()
        val isPositive: Boolean = view!!.amount_type.isChecked
        if (name.length == 0) {
            view!!.showNameError()
            return
        }
        if (amountValue.length == 0) {
            view!!.showAmountError()
            return
        }
        val amount: Long = amountValue.toLong()
        saveMoneyAmountUseCase.execute(MoneyAmount(name = name, amount = amount,
            moneyBagUid = moneyBag.uid, creationDate = Date(),
            isPositive = isPositive))
        close()
    }

    fun destroyView() {
        view = null
    }

    fun loadMoneyBag(uid: String) {
        getMoneyBagUseCase.execute(uid, onResult = { moneyBag = it!! })
    }

    fun toggleAmount(buttonView: CompoundButton, checkedStatus: Boolean) {
        buttonView.isChecked = checkedStatus
        if (checkedStatus) {
            view!!.showPlusSymbol(buttonView)
        } else {
            view!!.showMinusSymbol(buttonView)
        }
    }

    fun initAmount(buttonView: CompoundButton) {
        view!!.showMinusSymbol(buttonView)
    }

    interface View {
        fun showNameError()

        fun showAmountError()

        fun saveMoneyAmount()

        fun cancel()
    }
}