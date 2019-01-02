package com.idelcano.moneycontrol.moneycontrol.presentation.presenters

import android.os.Bundle
import com.idelcano.moneycontrol.moneycontrol.MainActivity
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagsUseCase
import com.idelcano.moneycontrol.moneycontrol.fragments.MoneyBagCreatorDialogFragment
import com.idelcano.moneycontrol.moneycontrol.fragments.MoneyBagEditorDialogFragment




class MainActivityPresenter{
    lateinit var getMoneyBagListUseCase : GetMoneyBagsUseCase
    var view : MainActivity? = null

    fun initPresenter(view : MainActivity, getMoneyBagListUseCase : GetMoneyBagsUseCase) {
        this.view = view
        this.getMoneyBagListUseCase = getMoneyBagListUseCase
    }

    fun loadData(){
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

    fun onMoneyBagClicked(item: MoneyBag) {
        openMoneyBagEditrFragment(item)
    }

    fun openMoneyBagCreatorFragment() {
        val ft = view!!.supportFragmentManager
        val dialogFragment = MoneyBagCreatorDialogFragment()
        dialogFragment.show(ft, dialogFragment.TAG_DIALOG)
    }

    fun openMoneyBagEditrFragment(item: MoneyBag) {
        val ft = view!!.supportFragmentManager
        val dialogFragment = MoneyBagEditorDialogFragment()
        val args = Bundle()
        args.putString(MoneyBag.javaClass.canonicalName, item.uid)
        dialogFragment.setArguments(args)
        dialogFragment.show(ft, dialogFragment.TAG_DIALOG)
    }

    interface View {
        fun showMoneyBags(moneyBags: List<MoneyBag?>)
        fun clearMoneyBags()
        fun showLoading()
        fun hideLoading()
        fun showTotalMoneyBags(count: Int)
    }
}
