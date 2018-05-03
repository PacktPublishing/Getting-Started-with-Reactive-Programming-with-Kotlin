import io.reactivex.rxkotlin.blockingSubscribeBy
import io.reactivex.rxkotlin.toFlowable
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.rx2.rxFlowable

fun main(args: Array<String>) {

    // Example using RxJava
    listOf("Alberto", "Peter", "James").toFlowable()
        .map {
            it.toUpperCase()
        }
        .blockingSubscribeBy(
            onNext = {
                println(it)
            }
        )

    // Example using Kotlin coroutines with RxJava
    rxFlowable {
        listOf("Alberto", "Peter", "John")
            .forEach {
                delay(1000L)
                send(it)
            }
    }.map {
        it.toUpperCase()
    }
        .blockingSubscribeBy(
            onNext = {
                println(it)
            }
        )
}