import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.rxkotlin.toFlowable

class FakeDB : Db {
    val userMap = mutableMapOf(
        1 to User(1, "Peter"), 2 to User(2, "Laura"), 3 to User(
            3,
            "John"
        ), 4 to User(4, "Carl")
    )
    val userPointsMap = mutableMapOf(1 to 40, 2 to 23, 3 to 0, 4 to 5)

    override fun getAllUser() = userMap.values.toFlowable()

    override fun getUserById(id: Int): Single<User> {
        return getAllUser()
            .filter { it.id == id }
            .firstOrError()
            .onErrorResumeNext { e -> Single.error(UserNotFound("Id $id not found on records")) }
    }

    override fun getPointsForUserId(id: Int): Single<Int> {
        return if (userPointsMap.containsKey(id))
            Single.just(userPointsMap[id])
        else
            Single.error { UserNotFound("Id: $id is not on the records") }
    }

    override fun addUser(user: User): Completable {
        return Completable.fromAction { userMap[user.id] = user }
    }
}

interface Db {
    fun getAllUser(): Flowable<User>
    fun getUserById(id: Int): Single<User> // Single
    fun getPointsForUserId(id: Int): Single<Int>
    fun addUser(user: User): Completable
}

data class User(val id: Int, val name: String)
class UserNotFound(message: String?) : Exception(message)