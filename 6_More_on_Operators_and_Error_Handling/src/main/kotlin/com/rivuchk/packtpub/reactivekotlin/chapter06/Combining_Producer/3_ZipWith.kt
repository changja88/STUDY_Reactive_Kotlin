package com.rivuchk.packtpub.reactivekotlin.chapter06.Combining_Producer

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.rxkotlin.zipWith

fun main(args: Array<String>) {
    val observable1 = Observable.range(1,10)
    val observable2 = listOf("String 1","String 2","String 3","String 4",
            "String 5","String 6","String 7","String 8","String 9","String 10").toObservable()

    observable1.zipWith(observable2,{e1:Int,e2:String -> "$e2 $e1"})//(1)
            .subscribe {
                println("Received $it")
            }
}

// * 정리
// - zipWith
//      - instance version of zip
//      - zipWith를 사용 하면 obervable을 두개밖에 사용 하지 못한다