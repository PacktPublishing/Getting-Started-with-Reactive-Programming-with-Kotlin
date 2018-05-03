import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith

fun main(args: Array<String>) {
    val db = FakeDB()

    db.getAllUser()
        .flatMapSingle {
            Single.just(it)
                .zipWith(db.getPointsForUserId(it.id), { user, points -> "${user.name} has $points points!" })
        }
        .subscribe { println(it) }
}