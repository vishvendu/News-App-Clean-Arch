package com.vishvendu.cleanarch.news_app.data.local.db.dao

import androidx.room.*
import com.vishvendu.cleanarch.news_app.data.model.topheadlines.TopHeadlineArticle
import com.vishvendu.cleanarch.news_app.data.model.topheadlines.TopHeadlinesResponse

import kotlinx.coroutines.flow.Flow

@Dao
interface TopHeadlineDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topHeadlineArticle: List<TopHeadlineArticle>)

    @Update
    suspend fun update(topHeadlineArticle: TopHeadlineArticle)

    @Query("delete from top_headline_table")
    fun deleteAllTopHeadline()

    //TODO observe the flow in viewmodel
    @Query("select * from top_headline_table")
    fun getAllTopHeadlineFlow(): Flow<List<TopHeadlineArticle>>

    //TODO observe the flow in viewmodel
    @Query("select * from top_headline_table")
    fun getAllTopHeadline(): List<TopHeadlineArticle>
}