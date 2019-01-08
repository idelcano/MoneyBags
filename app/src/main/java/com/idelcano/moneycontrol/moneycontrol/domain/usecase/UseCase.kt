package com.idelcano.moneycontrol.moneycontrol.domain.usecase

import com.idelcano.moneycontrol.moneycontrol.domain.boundary.Executor

abstract class UseCase(private val executor: Executor) {

    fun uiExecute(uiFun: suspend () -> Unit) {
        executor.uiExecute { uiFun() }
    }
    fun asyncExecute(asyncFun: suspend() -> Unit) {
        executor.asyncExecute { asyncFun() }
    }
}