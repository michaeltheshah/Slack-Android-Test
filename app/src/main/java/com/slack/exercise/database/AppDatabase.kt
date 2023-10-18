package com.slack.exercise.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.slack.exercise.dao.UserDao
import com.slack.exercise.dao.entity.UserEntity
import com.slack.exercise.model.usersearch.User

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
