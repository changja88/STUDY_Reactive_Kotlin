package com.rivuchk.packtpub.reactivekotlin.chapter04

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) {
    val observable = Observable.just(1,2,3,4,5,6,7,8,9)//(1)

    val subject = BehaviorSubject.create<Int>()

    subject.observeOn(Schedulers.computation())//(2)
            .subscribe({//(3)
                println("Subs 1 Received $it")
                runBlocking { delay(200) }//(4)
            })

    subject.observeOn(Schedulers.computation())//(5)
            .subscribe({//(6)
                println("Subs 2 Received $it")
            })

    observable.subscribe(subject)//(7)

    runBlocking { delay(2000) }//(8)
}

// * 정리
// - Understanding backpressure
//      - Observer가 아직 처리할 준비가 되지 않았는데, Observable이 아이템을 emitt 하면 문제가 된다
//      - 하지만 디폴트로 chains work by pushing items synchronously to the observer 이기 때문에 문제가 되지않지만
//        하나의 처리 작업이 컴퓨팅 파워를 많이 먹게되면 문제가 된다

// - 첫번째 subject1 의 처리시간이 너무 길어서 큐가 쌓이게 된다
//   결과적으로 subject2 만 먼저 2,3,4,5,6,7,8,9를 출력하게 된다

// - 이러한 문제를 해결 하기 위한 방법으로 Consumer 와 Producer 사이에 Feedback channel을 만들어서
//   Feedback channel이 work load를 알아서 잘 분배 하게 만든다
//   -> Feedback channel을 Backpressure라고 한다
//   -> 이를 해결 하기 위해서 Flowables와 Subscribers 를 이용한다