package com.example

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.resources.Resources
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.resources.delete
import io.ktor.server.resources.put
import io.ktor.server.resources.post
import io.ktor.server.resources.get
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

fun Application.configureResources() {
    install(Resources)
    routing {
        // Put route to update a post by ID
        put<Posts.Update> {
            val postId = it.id
            val updatedPost = call.receive<PostData>()

            val rowsUpdated = newSuspendedTransaction(Dispatchers.IO) {
                Post.update({ Post.id eq postId }) {
                    it[content] = updatedPost.text
                    it[postTime] = System.currentTimeMillis()
                }
            }

            // Check if any rows were updated
            if (rowsUpdated > 0) {
                call.respondText("Post with ID $postId was successfully updated.")
            } else {
                call.respond(HttpStatusCode.NotFound, "Post with ID $postId not found.")
            }
        }

        // Get all posts
        get<Posts> {
            call.respond(
                newSuspendedTransaction(Dispatchers.IO) {
                    Post.selectAll().map {
                        GetPostData(
                            post = it[Post.content],
                            time = it[Post.postTime],
                            id = it[Post.id].value
                        )
                    }
                }
            )
        }

        // Get posts based on a timestamp
        get<Posts.ByTimestamp> {
            call.respond(
                newSuspendedTransaction(Dispatchers.IO) {
                    Post.selectAll().where { Post.postTime greaterEq it.time }
                        .map {
                            GetPostData(
                                post = it[Post.content],
                                time = it[Post.postTime],
                                id = it[Post.id].value
                            )
                        }
                }
            )
        }

        // Get a specific post by its ID
        get<Posts.ById> {
            val postId = it.id
            val post = newSuspendedTransaction(Dispatchers.IO) {
                Post.selectAll().where { Post.id eq postId }
                    .map { result ->
                        GetPostData(
                            post = result[Post.content],
                            time = result[Post.postTime],
                            id = result[Post.id].value
                        )
                    }.singleOrNull()
            }

            if (post != null) {
                call.respond(post)
            } else {
                call.respond(HttpStatusCode.NotFound, "Post with ID $postId not found.")
            }
        }

        // Delete a post by ID
        delete<Posts.Delete> {
            val postId = it.id
            val rowsDeleted = newSuspendedTransaction(Dispatchers.IO) {
                Post.deleteWhere { Post.id eq postId }
            }

            if (rowsDeleted > 0) {
                call.respondText("Successfully deleted post $postId.")
            } else {
                call.respond(HttpStatusCode.NotFound, "No post $postId found.")
            }
        }

        // Post a new one with content
        post<Posts> {
            val contentInput = call.receive<PostData>()
            val postId = newSuspendedTransaction(Dispatchers.IO) {
                Post.insertAndGetId {
                    it[content] = contentInput.text
                    it[postTime] = System.currentTimeMillis()
                }.value
            }
            call.respondText("Post created with ID $postId and content: ${contentInput.text}")
        }
    }
}

// Data class for receiving post content via POST and PUT requests
@Serializable
data class PostData(val text: String)

// Data class for returning post data in responses
@Serializable
data class GetPostData(val post: String, val time: Long, val id: Int)

// Resource class representing the /posts endpoint and nested resources
@Resource("/posts")
class Posts {

    @Resource("{id}")
    class ById(val parent: Posts = Posts(), val id: Int)

    @Resource("{id}/delete")
    class Delete(val parent: Posts = Posts(), val id: Int)

    @Resource("{id}/update")
    class Update(val parent: Posts = Posts(), val id: Int)

    @Resource("time/{time}")
    class ByTimestamp(val parent: Posts = Posts(), val time: Long)
}

