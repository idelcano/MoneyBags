package com.idelcano.moneycontrol.moneycontrol.presentation.executers

import com.idelcano.moneycontrol.moneycontrol.domain.boundary.Executor
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class CoroutinesExecutor : Executor {
    override fun uiExecute(uiFun: suspend () -> Unit) {
        launch(UI) {
            uiFun()
        }
    }

    override fun asyncExecute(asyncFun: suspend() -> Unit) {
        async(CommonPool) {
            asyncFun()
        }
    }
}