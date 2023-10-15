package com.javier.channelupm

import org.junit.Assert.assertEquals
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.sql.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LoginApiTest {

    private var connection = DriverManager.getConnection(
        "jdbc:mysql://10.0.2.2:3306/ChannelUPMDB",
        "root", "root")
    private lateinit var query: PreparedStatement
    private lateinit var resultSet: ResultSet

    @Test
    fun a_testLoginUserSuccess() {
        try {

            query = connection.prepareStatement("""SELECT * FROM User WHERE Mail = "user1@upm.es" AND Password = "Password1" """)
            resultSet = query.executeQuery()
            assertEquals(true, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun b_testLoginUserSuccess() {
        try {

            query = connection.prepareStatement("""SELECT * FROM User WHERE Mail = "user1@upm.es" AND Password = "aaa" """)
            resultSet = query.executeQuery()
            assertEquals(false, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun c_testGetUserById() {
        try {

            query = connection.prepareStatement("SELECT * FROM User WHERE UserId = 1")
            resultSet = query.executeQuery()
            assertEquals(true, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {

        }
    }
}