package com.javier.channelupm

import org.junit.Assert.assertEquals
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.sql.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ContactsApiTest {

    private var connection = DriverManager.getConnection(
        "jdbc:mysql://10.0.2.2:3306/ChannelUPMDB",
        "root", "root")
    private lateinit var query: PreparedStatement
    private lateinit var resultSet: ResultSet

    @Test
    fun a_testGetAllUsers() {
        try {
            query = connection.prepareStatement("SELECT * FROM User")
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(2, resultSet.row)
            
        } catch (e: SQLException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun b_testSearchUser() {
        try {

            query = connection.prepareStatement("""
                SELECT * FROM User
                WHERE Name LIKE "%1%"
            """.trimIndent())
            resultSet = query.executeQuery()
            assertEquals(true, resultSet.next())

        } catch (e: SQLException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun c_testGetContacts() {
        try {

            query = connection.prepareStatement("""
                INSERT INTO SaveUser VALUES (1,2)
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("""
                SELECT * FROM User u
                    INNER JOIN SaveUser s ON u.UserId = s.ContactId
                WHERE s.UserId = 1
            """.trimMargin())
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(1, resultSet.row)

        } catch (e: SQLException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun d_testSearchContact() {
        try {

            query = connection.prepareStatement("""
                SELECT * FROM User u
                    INNER JOIN SaveUser s ON u.UserId = s.ContactId
                WHERE u.Name LIKE "%2%"
            """.trimIndent())
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(1, resultSet.row)

        } catch (e: SQLException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun e_testSaveUser() {
        try {

            query = connection.prepareStatement("INSERT INTO SaveUser VALUES(2,1)")
            query.executeUpdate()

            query = connection.prepareStatement("""
                SELECT * FROM User u
                    INNER JOIN SaveUser s ON u.UserId = s.ContactId
                WHERE s.UserId = 2
            """.trimMargin())
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(1, resultSet.row)

        } catch (e: SQLException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}