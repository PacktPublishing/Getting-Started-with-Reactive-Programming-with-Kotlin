import io.reactivex.rxkotlin.toFlowable
import io.reactivex.schedulers.Schedulers

fun main(args: Array<String>) {

    var postFix = 0

    listOf("Alberto", "John").toFlowable()
        .doOnNext { println("Observable created on thread ${Thread.currentThread().name}") }
        .observeOn(Schedulers.newThread())
        .map { it.decapitalize() }
        .doOnNext { println("Executed decapitalize on thread ${Thread.currentThread().name}") }
        .observeOn(Schedulers.newThread())
        .doOnNext { println("Executed the toUpperCase on thread: ${Thread.currentThread().name}") }
        .map { "${it.toUpperCase()}-${postFix++}" }
        .observeOn(Schedulers.newThread())
        .subscribeOn(Schedulers.newThread())
        .subscribe { println("Subscription of name: $it received on thread: ${Thread.currentThread().name}") }
    Thread.sleep(100)
}