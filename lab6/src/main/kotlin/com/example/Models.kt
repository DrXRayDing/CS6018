package com.example

import org.jetbrains.exposed.dao.id.IntIdTable

object Post : IntIdTable() {
    var content = varchar("content", 255)
    var postTime = long("postTime");
}
