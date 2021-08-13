package com.ambush.ambushchallenge.data.local

import io.paperdb.Book
import io.paperdb.Paper

fun paperProvider(): Book {
    return Paper.book()
}
