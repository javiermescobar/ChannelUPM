package com.javier.channelupm


import org.junit.Assert.assertEquals
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.sql.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CategoryApiTest {

    private var connection = DriverManager.getConnection(
        "jdbc:mysql://10.0.2.2:3306/ChannelUPMDB",
        "root", "root")
    private lateinit var query: PreparedStatement
    private lateinit var resultSet: ResultSet

    @Test
    fun a_initDB() {
        StaticMethods.initDb(connection)
    }

    @Test
    fun b_testGetCategories() {
        query = connection.prepareStatement("""
            INSERT INTO Category(CategoryId, CategoryName) 
                VALUES
                (1, "Primero"),
                (2, "Segundo"),
                (3, "Tercero");
        """.trimIndent())
        query.executeUpdate()

        query = connection.prepareStatement("SELECT * FROM Category")
        resultSet = query.executeQuery()
        resultSet.last()
        assertEquals(3, resultSet.row)
    }

}