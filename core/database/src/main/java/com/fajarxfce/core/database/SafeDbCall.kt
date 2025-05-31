package com.fajarxfce.core.database

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import com.fajarxfce.core.exception.BaseException
import com.fajarxfce.core.exception.UnknownException
import com.fajarxfce.core.result.Resource

suspend fun <T : Any> safeDbCall(dbCall: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(dbCall())
    } catch (e: SQLiteConstraintException) {
        Resource.Error(BaseException("Database constraint violation: ${e.message}"))
    } catch (e: SQLiteException) {
        Resource.Error(BaseException("Database error: ${e.message}"))
    } catch (e: Exception) {
        Resource.Error(UnknownException("Unexpected database error: ${e.message}"))
    }
}