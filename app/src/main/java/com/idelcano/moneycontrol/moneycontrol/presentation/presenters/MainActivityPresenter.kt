package com.idelcano.moneycontrol.moneycontrol.presentation.presenters

import com.idelcano.moneycontrol.moneycontrol.MainActivity
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.domain.usecase.GetMoneyBagsUseCase


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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface View {
        fun showMoneyBags(moneyBags: List<MoneyBag?>)
        fun clearMoneyBags()
        fun showLoading()
        fun hideLoading()
        fun showTotalMoneyBags(count: Int)
    }
}
