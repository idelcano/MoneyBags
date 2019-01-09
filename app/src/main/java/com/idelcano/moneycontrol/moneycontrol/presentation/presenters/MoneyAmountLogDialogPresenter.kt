package com.idelcano.moneycontrol.moneycontrol.presentation.presenters

import android.support.v4.app.DialogFragment
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyAmount
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.DeleteMoneyAmountUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyAmountLogDialogFragment

class MoneyAmountLogDialogPresenter {
    lateinit var getMoneyBagUseCase: GetMoneyBagUseCase
    lateinit var deleteMoneyAmountUseCase: DeleteMoneyAmountUseCase
    var view: MoneyAmountLogDialogFragment? = null

    fun initPresenter(
        view: MoneyAmountLogDialogFragment,
        getMoneyBagUseCase: GetMoneyBagUseCase,
        deleteMoneyAmountUseCase: DeleteMoneyAmountUseCase
    ) {
        this.view = view
        this.getMoneyBagUseCase = getMoneyBagUseCase
        this.deleteMoneyAmountUseCase = deleteMoneyAmountUseCase
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

    fun destroyView() {
        view = null
    }

    fun remove(item: MoneyAmount) {
            view!!.showDialog(
                ({
                    deleteMoneyAmountUseCase.execute(item)
                    loadMoneyBag(item.moneyBagUid)
                }), R.string.are_you_sure)
    }

    fun loadMoneyBag(uid: String) {
        getMoneyBagUseCase.execute(uid, onResult = {
            view!!.showMoneyAmounts(it!!.amountList)
        })
    }

    fun onMoneyAmountTouch(item: MoneyAmount) {
        remove(item)
    }

    interface View {

        fun cancel()

        fun showDialog(func: () -> Unit, message: Int)

        fun showMoneyAmounts(moneyAmounts: List<MoneyAmount?>)

        fun clearMoneyAmounts()
    }
}