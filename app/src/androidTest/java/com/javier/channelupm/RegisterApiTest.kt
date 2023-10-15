package com.javier.channelupm

import org.junit.Assert.assertEquals
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.sql.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RegisterApiTest{

    private var connection = DriverManager.getConnection(
        "jdbc:mysql://10.0.2.2:3306/ChannelUPMDB",
        "root", "root")
    private lateinit var query: PreparedStatement
    private lateinit var resultSet: ResultSet

    @Test
    fun a_testMailRegisteredTrue() {
        try {

            query = connection.prepareStatement("""SELECT * FROM User WHERE Mail = "user1@upm.es"""".trimIndent())
            resultSet = query.executeQuery()
            assertEquals(true, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun b_testMailRegisteredFalse() {
        try {

            query = connection.prepareStatement("""SELECT * FROM User WHERE Mail = "user3@upm.es"""".trimIndent())
            resultSet = query.executeQuery()
            assertEquals(false, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun c_testRegisterUser() {
        try {

            query = connection.prepareStatement("""
                INSERT INTO User VALUES
                (3, "user3", "user3@upm.es", "Password3", "Description3", "", 0)
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("SELECT * FROM User WHERE UserId = 3")
            resultSet = query.executeQuery()
            assertEquals(true, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun d_testCreateUserConfigurationAndGetById() {
        try {

            query = connection.prepareStatement("""
                INSERT INTO UserConfiguration VALUES
                (3,1,1,3)
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("SELECT * FROM UserConfiguration WHERE ConfigId = 3")
            resultSet = query.executeQuery()
            assertEquals(true, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun e_testUpdateUserConfiguration() {
        try {

            query = connection.prepareStatement("UPDATE UserConfiguration SET Theme = 0, Notifications = 0 WHERE ConfigId = 3")
            query.executeUpdate()
            query = connection.prepareStatement("SELECT * FROM UserConfiguration WHERE ConfigId = 3 AND Theme = 0 AND Notifications = 0")
            resultSet = query.executeQuery()
            assertEquals(true, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun f_testUpdateUserInformation() {
        try {

            query = connection.prepareStatement("""UPDATE User SET Name = "a" WHERE UserId = 3""")
            query.executeUpdate()
            query = connection.prepareStatement("""SELECT * FROM User WHERE Name = "a"""")
            resultSet = query.executeQuery()
            assertEquals(true, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun g_resetDB() {
        StaticMethods.resetDB(connection)
    }
}