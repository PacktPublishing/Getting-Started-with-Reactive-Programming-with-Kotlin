import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toFlowable

fun main(args: Array<String>) {

    listOf("Alberto", "Lara", "Carl", "James").toFlowable()
        .flatMap {
            if (it == "Carl")
                Flowable.empty()
            else
                Flowable.just(it)
        }
        .subscribeBy(
            onNext = {
                println(it)
            },
            onError = {
                println("We had an exception")
            },
            onComplete = {
                println("Stream is complete")
            }
        )
}