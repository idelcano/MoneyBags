package com.idelcano.moneycontrol.moneycontrol.presentation.presenters

import android.support.v4.app.DialogFragment
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyAmount
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.DeleteMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.SaveMoneyAmountUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyBagEditorDialogFragment
import kotlinx.android.synthetic.main.create_money_bag_dialog_layout.*
import java.util.*

class MoneyBagEditorDialogPresenter{
    lateinit var deleteMoneyBagUseCase: DeleteMoneyBagUseCase
    lateinit var saveMoneyAmountUseCase: SaveMoneyAmountUseCase
    lateinit var getMoneyBagUseCase : GetMoneyBagUseCase
    lateinit var moneyBag: MoneyBag
    var view : MoneyBagEditorDialogFragment? = null

    fun initPresenter(view : MoneyBagEditorDialogFragment, deleteMoneyBagUseCase : DeleteMoneyBagUseCase,
                      saveMoneyAmountUseCase : SaveMoneyAmountUseCase,
                      getMoneyBagUseCase : GetMoneyBagUseCase) {
        this.view = view
        this.deleteMoneyBagUseCase = deleteMoneyBagUseCase
        this.saveMoneyAmountUseCase = saveMoneyAmountUseCase
        this.getMoneyBagUseCase = getMoneyBagUseCase
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

    fun saveMoneyAmont() {
        val name : String = view!!.edit_name.text.toString()
        val amountValue : String = view!!.edit_amount.text.toString()
        if(name.length==0) {
            view!!.showNameError()
            return
        }
        if(amountValue.length==0) {
            view!!.showAmountError()
            return
        }
        val amount : Long = amountValue.toLong()
        saveMoneyAmountUseCase.execute(MoneyAmount(name = name, amount = amount,
            moneyBagUid = moneyBag.uid, creationDate = Date()))
        close()
    }

    fun destroyView() {
        view = null
    }

    fun remove() {
            view!!.showDialog(
                (fun() {
                    deleteMoneyBagUseCase.execute(moneyBag)
                    close()
                })
                , R.string.are_you_sure)
    }

    fun loadMoneyBag(uid: String) {
        getMoneyBagUseCase.execute (uid,  onResult = { moneyBag = it!! })
    }

    interface View {
        fun showNameError()

        fun showAmountError()

        fun saveMoneyAmount()

        fun cancel()

        fun remove()

        fun showDialog(func: () -> Unit, message: Int)
    }
}