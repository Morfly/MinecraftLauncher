package com.astamobi.kristendate.domainlayer.extention

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun Completable.applyApiRequestSchedulers() = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.applyApiRequestSchedulers() = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.applyApiRequestSchedulers() = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.applyApiRequestSchedulers() = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


fun Completable.applyBeforeAndAfter(before : () -> Unit, after : () -> Unit) = this
        .doOnSubscribe { before.invoke() }
        .doFinally { after.invoke() }

fun <T> Single<T>.applyBeforeAndAfter(before : () -> Unit, after : () -> Unit) = this
        .doOnSubscribe { before.invoke() }
        .doFinally { after.invoke() }

fun <T> Maybe<T>.applyBeforeAndAfter(before : () -> Unit, after : () -> Unit) = this
        .doOnSubscribe { before.invoke() }
        .doFinally { after.invoke() }

fun <T> Observable<T>.applyBeforeAndAfter(before : () -> Unit, after : () -> Unit) = this
        .doOnSubscribe { before.invoke() }
        .doFinally { after.invoke() }
