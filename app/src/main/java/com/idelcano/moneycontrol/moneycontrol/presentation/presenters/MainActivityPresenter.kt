package com.idelcano.moneycontrol.moneycontrol.presentation.presenters

import android.os.Bundle
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.DeleteMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagsUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.views.MainActivity
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.BaseFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.DayCounterCreatorDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MenuDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyAmountCreatorDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyAmountLogDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyBagCreatorDialogFragment

class MainActivityPresenter {
    lateinit var getMoneyBagListUseCase: GetMoneyBagsUseCase
    lateinit var deleteMoneyBagUseCase: DeleteMoneyBagUseCase
    var view: MainActivity? = null

    fun initPresenter(view: MainActivity, getMoneyBagListUseCase: GetMoneyBagsUseCase, deleteMoneyBagUseCase: DeleteMoneyBagUseCase) {
        this.view = view
        this.getMoneyBagListUseCase = getMoneyBagListUseCase
        this.deleteMoneyBagUseCase = deleteMoneyBagUseCase
    }

    fun loadData() {
        loadingMoneyBags()
        loadMoneyBags()
    }

    private fun loadMoneyBags() {
        getMoneyBagListUseCase.execute {
            showMoneyBags(it)
        }
    }

    private fun loadingMoneyBags() {
        view?.showLoading()
        view?.clearMoneyBags()
    }

    private fun showMoneyBags(moneyBags: List<MoneyBag?>) {
        view?.hideLoading()
        view?.showMoneyBags(moneyBags)
        view?.showTotalMoneyBags(moneyBags.size)
    }

    fun detachView() {
        view = null
    }

    fun onAddButtonClicked(item: MoneyBag) {
        openFragment(item, MoneyAmountCreatorDialogFragment(),
            MoneyAmountCreatorDialogFragment().companion?.TAG_DIALOG)
    }

    fun onLogButtonClicked(item: MoneyBag) {
        openFragment(item, MoneyAmountLogDialogFragment(),
            MoneyAmountLogDialogFragment().companion?.TAG_DIALOG)
    }
    fun onRemoveButtonClicked(item: MoneyBag) {
        removeMoneyBag(item)
    }

    private fun removeMoneyBag(item: MoneyBag) {
        view!!.showDialog({
                deleteMoneyBagUseCase.execute(item)
                loadMoneyBags()
            }, R.string.are_you_sure)
    }

    fun openMenuDialog() {
        val ft = view!!.supportFragmentManager
        val dialogFragment = MenuDialogFragment()
        dialogFragment.addActions(
            fun (action: Actions) {
            when(action){
                Actions.CREATE_MONEY_BAG -> {
                    openMoneyBagCreatorFragment()
                }
                Actions.CREATE_DAY_COUNTER -> {
                    openDayCounterFragment()
                }
            }
        })
        dialogFragment.show(ft, dialogFragment.companion?.TAG_DIALOG)
    }

    fun openDayCounterFragment() {
        val ft = view!!.supportFragmentManager
        val dialogFragment = DayCounterCreatorDialogFragment()
        dialogFragment.show(ft, dialogFragment.companion?.TAG_DIALOG)
    }

    fun openMoneyBagCreatorFragment() {
        val ft = view!!.supportFragmentManager
        val dialogFragment = MoneyBagCreatorDialogFragment()
        dialogFragment.show(ft, dialogFragment.companion?.TAG_DIALOG)
    }

    fun openFragment(item: MoneyBag, fragment: BaseFragment, tag: String) {
        val ft = view!!.supportFragmentManager
        val dialogFragment = fragment
        val args = Bundle()
        args.putString(MoneyBag.javaClass.canonicalName, item.uid)
        dialogFragment.setArguments(args)
        dialogFragment.show(ft, tag)
    }

    interface View {
        fun showMoneyBags(moneyBags: List<MoneyBag?>)
        fun clearMoneyBags()
        fun showLoading()
        fun hideLoading()
        fun showTotalMoneyBags(count: Int)
    }
}
