package com.idelcano.moneycontrol.moneycontrol.domain.boundary

interface Executor {
    fun uiExecute(uiFun:suspend ()->Unit)
    fun asyncExecute(asyncFun:suspend()->Unit)
}