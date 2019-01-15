package com.idelcano.moneycontrol.moneycontrol.presentation.presenters

import android.os.Bundle
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.domain.entity.DayCounter
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.DeleteDayCounterUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.DeleteMoneyBagUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetDayCounterUseCase
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagsUseCase
import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.adapters.IListable
import com.idelcano.moneycontrol.moneycontrol.presentation.views.MainActivity
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.BaseFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.DayCounterCreatorDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MenuDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyAmountCreatorDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyAmountLogDialogFragment
import com.idelcano.moneycontrol.moneycontrol.presentation.views.fragments.MoneyBagCreatorDialogFragment

class MainActivityPresenter {
    lateinit var getMoneyBagListUseCase: GetMoneyBagsUseCase
    lateinit var getDayCountersUseCase: GetDayCounterUseCase
    lateinit var deleteMoneyBagUseCase: DeleteMoneyBagUseCase
    lateinit var deleteDayCounterUseCase: DeleteDayCounterUseCase
    var view: MainActivity? = null

    fun initPresenter(
        view: MainActivity,
        getMoneyBagListUseCase: GetMoneyBagsUseCase,
        getDayCountersUseCase: GetDayCounterUseCase,
        deleteMoneyBagUseCase: DeleteMoneyBagUseCase,
        deleteDayCounterUseCase: DeleteDayCounterUseCase
    ) {
        this.view = view
        this.getMoneyBagListUseCase = getMoneyBagListUseCase
        this.getDayCountersUseCase = getDayCountersUseCase
        this.deleteMoneyBagUseCase = deleteMoneyBagUseCase
        this.deleteDayCounterUseCase = deleteDayCounterUseCase
    }

    fun loadData() {
        loadingItems()
        loadItems()
    }

    private fun loadItems() {
        getMoneyBagListUseCase.execute {
            addItems(it)
            showItems()
        }
        getDayCountersUseCase.execute {
            addItems(it)
            showItems()
        }
    }

    private fun loadingItems() {
        view?.showLoading()
        view?.clearItems()
    }

    private fun addItems(items: List<IListable?>) {
        view?.addItems(items)
    }

    private fun showItems() {
        view?.hideLoading()
        view?.showItems()
    }

    fun detachView() {
        view = null
    }

    fun onAddButtonClicked(item: MoneyBag) {
        openFragment(
            item, MoneyAmountCreatorDialogFragment(),
            MoneyAmountCreatorDialogFragment().companion?.TAG_DIALOG
        )
    }

    fun onLogButtonClicked(item: MoneyBag) {
        openFragment(item, MoneyAmountLogDialogFragment(),
            MoneyAmountLogDialogFragment().companion?.TAG_DIALOG)
    }
    fun onRemoveButtonClicked(item: IListable) {
        if (item is MoneyBag) {
            removeMoneyBag(item)
        } else if (item is DayCounter) {
            removeDayCounter(item)
        }
    }

    private fun removeDayCounter(item: DayCounter) {
        view!!.showDialog({
            deleteDayCounterUseCase.execute(item)
            loadData()
        }, R.string.are_you_sure)
    }

    private fun removeMoneyBag(item: MoneyBag) {
        view!!.showDialog({
                deleteMoneyBagUseCase.execute(item)
                loadData()
            }, R.string.are_you_sure)
    }

    fun openMenuDialog() {
        val ft = view!!.supportFragmentManager
        val dialogFragment = MenuDialogFragment()
        val actions: (Actions) -> Unit = { action: Actions -> when (action) {
                Actions.CREATE_MONEY_BAG -> {
                    openMoneyBagCreatorFragment()
                }
                Actions.CREATE_DAY_COUNTER -> {
                    openDayCounterFragment()
                }
            }
        }
        dialogFragment.addActions(
            actions
        )
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
        fun addItems(items: List<IListable?>)
        fun clearItems()
        fun showItems()
        fun showLoading()
        fun hideLoading()
        fun showTotalItems(count: Int)
    }
}
