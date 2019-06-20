package sharedcode.extension

import sharedcode.model.Either

internal fun <L, R, T> Either<L, R>.fold(onFailed: (L) -> T, onSuccess: (R) -> T): T = when (this) {
    is Either.Failure -> onFailed(error)
    is Either.Success -> onSuccess(value)
}

internal typealias NetworkListener<L, R> = (Either<L, R>) -> Unit