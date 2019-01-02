package com.idelcano.moneycontrol.moneycontrol.domain.usecase

import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.Executor
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag


class GetMoneyBagUseCase (private val moneyBagRepository: MoneyBagRepository,
                           private val executor: Executor): UseCase(executor) {

        fun execute(uid : String, onResult: (MoneyBag?) -> Unit) {
            asyncExecute {
                val moneyBag = moneyBagRepository.get(uid)

                uiExecute {
                    onResult(moneyBag)
                }
            }
        }
    }