package com.javier.channelupm

import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runners.MethodSorters
import java.sql.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class NewsApiTest {

    private var connection = DriverManager.getConnection(
        "jdbc:mysql://10.0.2.2:3306/ChannelUPMDB",
        "root", "root")
    private lateinit var query: PreparedStatement
    private lateinit var resultSet: ResultSet

    @Test
    fun a_testGetNews() {
        try {

            query = connection.prepareStatement("""
                INSERT INTO News(NewsItemId, Title, Description, SendDate, UserId, CategoryId)
                VALUES
                (1, "News 1", "Description 1", "14/05/2023T20:20:00.000", 1, 1),
                (2, "News 2", "Description 2", "14/05/2023T20:20:00.000", 1, 2),
                (3, "News 3", "Description 3", "14/05/2023T20:20:00.000", 2, 3)
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("SELECT * FROM News")
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(3, resultSet.row)

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun b_testAddNewsItem() {
        try {

            query = connection.prepareStatement("""
                INSERT INTO News VALUES 
                (4, "News 4", "Description 4", "14/05/2023T20:20:00.000", 1, 2)
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("SELECT * FROM News")
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(4, resultSet.row)

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun c_testSendNewsNotifications() {
        try {

            query = connection.prepareStatement("""
                INSERT INTO User VALUES
                (100, "ChannelUPMBot", "", "PasswordBot", "ChannelUPM bot", "", 0);
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("""
                INSERT INTO UserInterested(UserId, CategoryId)
                VALUES
                (1,1),
                (1,2),
                (2,2),
                (2,3);
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("""
                INSERT INTO News VALUES 
                (5, "News 5", "Description 5", "16/05/2023T20:20:00.000", 1, 2)
            """.trimIndent())
            query.executeUpdate()
            query.executeUpdate()

            query.connection.prepareStatement("""
                INSERT INTO PrivateMessage(MessageId, Text, SendDate, SenderId, ReceiverId)
                VALUES
                (20, "Se ha creado una nueva noticia de la cateogría Segundo!", "16/05/2023T20:20:00.000", 100, 1),
                (21, "Se ha creado una nueva noticia de la cateogría Segundo!", "16/05/2023T20:20:00.000", 100, 2);
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("""
                SELECT * FROM PrivateMessage WHERE SenderId = 100
            """.trimIndent())
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(2, resultSet.row)


        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun d_testEditNewsItem() {
        try {

            query = connection.prepareStatement("""
                UPDATE News SET Title = "Noticia editada" WHERE NewsItemId = 1
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("""
                SELECT * FROM News
                WHERE Title LIKE "%editada%"
            """.trimIndent())
            resultSet = query.executeQuery()
            assertEquals(true, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun e_testGetUserInterests() {
        try {

            query = connection.prepareStatement("SELECT * FROM UserInterested WHERE UserId = 1")
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(2, resultSet.row)

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun f_testRemoveUserInterests() {
        try {

            query = connection.prepareStatement("""
                DELETE FROM UserInterested WHERE UserId = 1
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("SELECT * FROM UserInterested WHERE UserId = 1")
            resultSet = query.executeQuery()
            assertEquals(false, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun g_testAddUserInterests() {
        try {

            query = connection.prepareStatement("""
                INSERT INTO UserInterested (UserId, CategoryId)
                VALUES
                (1, 1),
                (1, 2),
                (1, 3);
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("SELECT * FROM UserInterested WHERE UserId = 1")
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(3, resultSet.row)

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}