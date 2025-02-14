package com.sonozaki.deezerplayer.mappers

interface Mapper<T, S> {
    fun map(t1: T): S
}