package com.idelcano.moneycontrol.moneycontrol.domain.usecase

import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.Executor
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag

class DeleteMoneyBagUseCase(
    private val moneyBagRepository: MoneyBagRepository,
    private val executor: Executor
) : UseCase(executor) {

        fun execute(moneyBag: MoneyBag) {
            asyncExecute {
                moneyBagRepository.delete(moneyBag)

                uiExecute { moneyBag }
            }
        }
    }