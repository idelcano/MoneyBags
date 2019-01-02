package com.idelcano.moneycontrol.moneycontrol.domain.entity

import com.idelcano.moneycontrol.moneycontrol.utils.DhisCodeGenerator
import java.io.Serializable
import java.util.*
data class MoneyBag (val uid:String = DhisCodeGenerator.generateCode(),
                     val name:String, val amount:Long,
                     val dateLimit: Date, val createdDate:Date,
                     val iconPath:String, val priority:Int,
                     var amountList: List<MoneyAmount> = ArrayList()): Serializable {

    companion object{
        const val MAXIMUN_RANGE = 5
        const val MINIMUN_RANGE = 0
    }

    init {
        if (!(priority in MINIMUN_RANGE .. MAXIMUN_RANGE)) {
            throw IllegalArgumentException("${priority} is not a valid priority range")
        }
    }
    fun result(): String? {
        var result : Long = 0
        for(moneyAmount in amountList){
            result += moneyAmount.amount
        }
        return result.toString()
    }
}
