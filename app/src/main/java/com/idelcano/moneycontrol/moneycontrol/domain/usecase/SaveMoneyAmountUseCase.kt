package com.idelcano.moneycontrol.moneycontrol.domain.usecase

import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyAmountRepository
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.Executor
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyAmount

class SaveMoneyAmountUseCase(
    private val moneyAmountRepository: MoneyAmountRepository,
    private val executor: Executor
) : UseCase(executor) {

        fun execute(moneyAmount: MoneyAmount) {
            asyncExecute {
                moneyAmountRepository.save(moneyAmount)

                uiExecute { moneyAmount }
            }
        }
    }