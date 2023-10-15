package com.javier.channelupm

import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runners.MethodSorters
import java.sql.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MessagesApiTest {

    private var connection = DriverManager.getConnection(
        "jdbc:mysql://10.0.2.2:3306/ChannelUPMDB",
        "root", "root")
    private lateinit var query: PreparedStatement
    private lateinit var resultSet: ResultSet

    @Test
    fun a_testSendAndReceivePrivateMessage() {
        try {

            query = connection.prepareStatement("""
                INSERT INTO PrivateMessage (MessageId, Text, SendDate, SenderId, ReceiverId)
                    VALUES
                    (1, "Hola!", "05/05/2023T20:20:00.000", 1, 2),
                    (2, "Hola de vuelta!", "05/05/2023T20:25:00.000", 2, 1);
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("""
                SELECT * FROM PrivateMessage WHERE
                SenderId = 1 AND ReceiverId = 2
                OR SenderId = 2 AND ReceiverId = 1
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
    fun b_testCreateGroupChat() {
        try {

            query = connection.prepareStatement("""
                INSERT INTO GroupChat (GroupChatId, AvatarImage, Description, GroupName)
                    VALUES
                    (1, "", "Group description 1", "Group 1"),
                    (2, "", "Group description 2", "Group 2");
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("SELECT * FROM GroupChat")
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
    fun c_testUpdateGroupChat() {
        try {

            query = connection.prepareStatement("""
                UPDATE GroupChat SET GroupName = "a", Description = "a" WHERE GroupChatId = 2
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("""SELECT * FROM GroupChat WHERE GroupName = "a" AND Description = "a" """)
            resultSet = query.executeQuery()
            assertEquals(true, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun d_testAddUserGroup() {
        try {

            query = connection.prepareStatement("""
                INSERT INTO UserInGroup (Administrator, UserId, GroupChatId)
                    VALUES 
                    (1, 1, 1),
                    (0, 2, 1),
                    (1, 1, 2);
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("SELECT * FROM UserInGroup WHERE GroupChatId = 1")
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(2, resultSet.row)

            query = connection.prepareStatement("SELECT * FROM UserInGroup WHERE GroupChatId = 2")
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(1, resultSet.row)

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun e_testUpdateUserGroup() {
        try {

            query = connection.prepareStatement("UPDATE UserInGroup SET Administrator = 1 WHERE UserId = 2 AND GroupChatId = 1")
            query.executeUpdate()

            query = connection.prepareStatement("SELECT * FROM UserInGroup WHERE Administrator = 1 AND GroupChatId = 1")
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
    fun f_testGetUserGroups() {
        try {

            query = connection.prepareStatement("SELECT * FROM UserInGroup WHERE UserId = 1")
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
    fun g_testSearchGroup() {
        try {

            query = connection.prepareStatement("""SELECT * FROM GroupChat WHERE GroupName LIKE "%Group%" """)
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(1, resultSet.row)

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun h_testGetGroupById() {
        try {

            query = connection.prepareStatement("SELECT * FROM GroupChat WHERE GroupChatId = 1")
            resultSet = query.executeQuery()
            assertEquals(true, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun i_testGetGroupParticipants() {
        try {

            query = connection.prepareStatement("SELECT * FROM UserInGroup WHERE GroupChatId = 1")
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
    fun j_testIsUserInGroupSuccess() {
        try {

            query = connection.prepareStatement("SELECT * FROM UserInGroup WHERE GroupChatId = 1 AND UserId = 1")
            resultSet = query.executeQuery()
            assertEquals(true, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun k_testIsUserInGroupFail() {
        try {

            query = connection.prepareStatement("SELECT * FROM UserInGroup WHERE GroupChatId = 2 AND UserId = 2")
            resultSet = query.executeQuery()
            assertEquals(false, resultSet.next())

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun l_testSendGroupChatMessages() {
        try {

            query = connection.prepareStatement("""
                INSERT INTO GroupMessage (GroupMessageId, Text, SendDate, GroupChatId, SenderId)
                    VALUES
                    (1, "Hola grupo!", "05/05/2023T20:20:00.000", 1, 1),
                    (2, "Soy Vannesa", "30/10/2023T20:21:00.000", 1, 1),
                    (3, "Feliz Juagolin!", "30/10/2023T20:22:00.000", 1, 1);
            """.trimIndent())
            query.executeUpdate()

            query = connection.prepareStatement("SELECT * FROM GroupMessage WHERE GroupChatId = 1")
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
    fun m_testGetGroupChatMessages() {
        try {

            query = connection.prepareStatement("SELECT * FROM GroupMessage WHERE GroupChatId = 1")
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
    fun n_testRemoveUserGroup() {
        try {

            query = connection.prepareStatement("DELETE FROM UserInGroup WHERE UserId = 2 AND GroupChatId = 1")
            query.executeUpdate()

            query = connection.prepareStatement("SELECT * FROM UserInGroup WHERE GroupChatId = 1")
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(1, resultSet.row)

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun o_testRemoveGroup() {
        try {

            query = connection.prepareStatement("DELETE FROM UserInGroup WHERE GroupChatId = 2")
            query.executeUpdate()

            query = connection.prepareStatement("DELETE FROM GroupChat WHERE GroupChatId = 2")
            query.executeUpdate()

            query = connection.prepareStatement("SELECT * FROM GroupChat")
            resultSet = query.executeQuery()
            resultSet.last()
            assertEquals(1, resultSet.row)

        } catch (sqlE: SQLException) {
            sqlE.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}