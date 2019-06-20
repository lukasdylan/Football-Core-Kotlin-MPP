package sharedcode.model

sealed class Either<out F, out S> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Failure<F>(val error: F) : Either<F, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Success<S>(val value: S) : Either<Nothing, S>()

    val isSuccess get() = this is Success<S>
    val isFailure get() = this is Failure<F>
}