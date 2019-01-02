package com.idelcano.moneycontrol.moneycontrol.domain.entity

import com.idelcano.moneycontrol.moneycontrol.utils.DhisCodeGenerator
import java.util.*

data class MoneyAmount (val uid:String = DhisCodeGenerator.generateCode(),
                        val name:String, val amount:Long,
                        val creationDate: Date,
                        val moneyBagUid:String){
}
