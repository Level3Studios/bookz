package net.level3studios.bookz.network

import android.util.Log
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import java.io.Reader

object NetworkLayer {

    private const val baseURL: String = "https://www.googleapis.com/books/v1/volumes"
    private const val baseSearchURL: String = "https://books.google.com/books/content"

    fun searchForBooks(
        searchTerm: String,
        maxResults: Int,
        responseHandler: (result: List<BooksModel>) -> Unit?
    ) {
        val key = PrivateKeys.APIKey
        val query = baseURL +
                "?q=$searchTerm" +
                "&orderBy=newest" +
                "&langRestrict=en" +
                "&printType=books" +
                "&maxResults=$maxResults" +
                "&key=$key"
        query.httpGet().responseObject(BooksResponseDeserializer()) { _, _, result ->
            when (result) {
                is Result.Failure -> {
                    result.getException().message?.let { Log.i("Error", it) }
                    throw Exception(result.getException())
                }
                is Result.Success -> {
                    val (data, _) = result
                    data?.let {
                        Log.i("Success Books found:", "{${it.items.count()}}")
                        responseHandler.invoke(it.items)
                    }
                }
            }
        }
    }


    class BooksResponseDeserializer : ResponseDeserializable<BooksResponse> {
        override fun deserialize(reader: Reader): BooksResponse =
            Gson().fromJson(reader, BooksResponse::class.java)
    }
}