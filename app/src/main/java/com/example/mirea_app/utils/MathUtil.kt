package com.example.mirea_app.utils

import com.example.mirea_app.dataClasses.ListsForData
import kotlin.math.max

class MathUtil {
    val M: Int = 192  // тонн
    val SH = 1  // тонн
    val MINSH: Int = 8  // единиц
    val UnitOfOxygen = 7  // кредитов
    val FuelUnit = 10  // кредитов
    val Vmax = 2
    val V = 1.7


    /**
     * формулы
     * V = Vmax * w/80 * 200/M
     * Kp = sin( -п/2 + п * (T + 0.5 * Oxi)/40 )
     */


    // TODO
    //Загрузить топливо, кислород и растения
    fun countAllDistance(): Int {
        val list = ListsForData.ListOfCorsList
        var distance = 0
        for (item in list) {
            for (point in item) {
                distance += point[1]
            }
        }

        return distance
    }

    fun countAllSH(): Int {
        var sh = 0
        val list = ListsForData.ListOfCorsList
        for (item in list) {
            for (point in item) {
                sh += point[0]
            }
        }
        return sh
    }

    fun countMass(): Int {
        return countAllSH() + 192
    }

    fun countV(): Int {
        val v = 2 * 1 / (192 + 8 + countAllSH())
        return v
    }

    fun countTakeOnTheShip(): Int {
        val res = mutableListOf<Int>()
        val list = ListsForData.ListOfCorsList
        return max((countAllSH() / countAllDistance()) + 1, 8)
    }

    fun countOxygen(): Int {
        return 20 * countTakeOnTheShip()
    }

    fun countFuel(): Int {
        return 100 * countAllDistance() / countV()
    }



}