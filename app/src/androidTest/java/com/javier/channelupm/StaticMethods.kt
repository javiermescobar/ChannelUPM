package com.javier.channelupm

import java.lang.Exception
import java.sql.*
import java.util.*

class StaticMethods {

    companion object {

        private val INIT_DB_STRINGS = mutableListOf<String>()
        private val INIT_DB_INSERT_STRINGS = mutableListOf<String>()
        private val RESET_DB_STRINGS = mutableListOf<String>()

        fun initDb(conn: Connection) {

            INIT_DB_STRINGS.add("""
                CREATE TABLE IF NOT EXISTS User(
                        UserId int NOT NULL PRIMARY KEY,
                        Name varchar(30) NOT NULL,
                        Mail varchar(30) NOT NULL,
                        Password varchar(20) NOT NULL,
                        Description varchar(200),
                        AvatarImage varchar(200),
                        Administrator int NOT NULL
                    );
            """.trimIndent())

            INIT_DB_STRINGS.add("""
                CREATE TABLE IF NOT EXISTS UserConfiguration(
                        ConfigId int NOT NULL PRIMARY KEY,
                        Theme int NOT NULL,
                        Notifications int NOT NULL,
                        UserId int NOT NULL, 
                        FOREIGN KEY (UserId) REFERENCES User(UserId)
                    );
            """.trimIndent())

            INIT_DB_STRINGS.add("""
                CREATE TABLE IF NOT EXISTS GroupChat(
                        GroupChatId int NOT NULL PRIMARY KEY,
                        AvatarImage varchar(1000),
                        Description varchar(200),
                        GroupName varchar(30) NOT NULL
                    );
            """.trimIndent())

            INIT_DB_STRINGS.add("""
                CREATE TABLE IF NOT EXISTS PrivateMessage(
                        MessageId int NOT NULL PRIMARY KEY,
                        Text varchar(1000) NOT NULL,
                        SendDate varchar(30) NOT NULL,
                        SenderId int NOT NULL,
                        ReceiverId int NOT NULL,
                        FOREIGN KEY (SenderId) REFERENCES User(UserId),
                        FOREIGN KEY (ReceiverId) REFERENCES User(UserId)
                    );
            """.trimIndent())

            INIT_DB_STRINGS.add("""
                CREATE TABLE IF NOT EXISTS GroupMessage(
                        GroupMessageId int NOT NULL PRIMARY KEY,
                        Text varchar(1000) NOT NULL,
                        SendDate varchar(30) NOT NULL,
                        GroupChatId int NOT NULL,
                        SenderId int NOT NULL,
                        FOREIGN KEY (GroupChatId) REFERENCES GroupChat(GroupChatId),
                        FOREIGN KEY (SenderId) REFERENCES User(UserId)
                    );
            """.trimIndent())

            INIT_DB_STRINGS.add("""
                CREATE TABLE IF NOT EXISTS Category(
                        CategoryId int NOT NULL PRIMARY KEY,
                        CategoryName varchar(30) NOT NULL UNIQUE
                        );
            """.trimIndent())

            INIT_DB_STRINGS.add("""
                CREATE TABLE IF NOT EXISTS News(
                        NewsItemId int NOT NULL PRIMARY KEY,
                        Title varchar(30) NOT NULL,
                        Description varchar(200) NOT NULL,
                        SendDate varchar(30) NOT NULL,
                        UserId int NOT NULL,
                        CategoryId int NOT NULL,
                        FOREIGN KEY (UserId) REFERENCES User(UserId),
                        FOREIGN KEY (CategoryId) REFERENCES Category(CategoryId)
                    );
            """.trimIndent())

            INIT_DB_STRINGS.add("""
                CREATE TABLE IF NOT EXISTS SaveUser(
                        UserId int NOT NULL,
                        ContactId int NOT NULL,
                        FOREIGN KEY (UserId) REFERENCES User(UserId),
                        FOREIGN KEY (ContactId)REFERENCES User(UserId)
                    );
            """.trimIndent())

            INIT_DB_STRINGS.add("""
                CREATE TABLE IF NOT EXISTS UserInGroup(
                        Administrator int NOT NULL,
                        UserId int NOT NULL,
                        GroupChatId int NOT NULL,
                        FOREIGN KEY (UserId) REFERENCES User(UserId),
                        FOREIGN KEY (GroupChatId) REFERENCES GroupChat(GroupChatId)
                    );
            """.trimIndent())

            INIT_DB_STRINGS.add("""
                CREATE TABLE IF NOT EXISTS UserInterested(
                        UserId int NOT NULL,
                        CategoryId int NOT NULL,
                        FOREIGN KEY (UserId) REFERENCES User(UserId),
                        FOREIGN KEY (CategoryId) REFERENCES Category(CategoryId)
                    );
            """.trimIndent())

            INIT_DB_INSERT_STRINGS.add("""
                INSERT INTO User (UserId, Name, Mail, Password, Description, AvatarImage, Administrator)
                    VALUES
                    (1, "User1", "user1@upm.es", "Password1", "Description1", "", 0),
                    (2, "User2", "user2@upm.es", "Password2", "Description2", "", 1);
            """.trimIndent())

            INIT_DB_INSERT_STRINGS.add("""
                INSERT INTO UserConfiguration (ConfigId, Theme, Notifications, UserId)
                    VALUES
                    (1, 1, 1, 1),
                    (2,0,0,2);
            """.trimIndent())

            try {

                var query: PreparedStatement

                INIT_DB_STRINGS.forEach {
                    query = conn.prepareStatement(it)
                    query.executeUpdate()
                }

                query = conn.prepareStatement("DELETE FROM User WHERE UserId > 0")
                query.executeUpdate()
                query = conn.prepareStatement("DELETE FROM UserConfiguration WHERE ConfigId > 0")
                query.executeUpdate()


                INIT_DB_INSERT_STRINGS.forEach {
                    query = conn.prepareStatement(it)
                    query.executeUpdate()
                }

            } catch (sqlE: SQLException) {
                sqlE.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun resetDB(conn: Connection) {
            try {
                RESET_DB_STRINGS.add("DROP TABLE UserInterested;")
                RESET_DB_STRINGS.add("DROP TABLE UserInGroup")
                RESET_DB_STRINGS.add("DROP TABLE SaveUser;")
                RESET_DB_STRINGS.add("DROP TABLE GroupMessage;")
                RESET_DB_STRINGS.add("DROP TABLE News;")
                RESET_DB_STRINGS.add("DROP TABLE PrivateMessage;")
                RESET_DB_STRINGS.add("DROP TABLE GroupChat;")
                RESET_DB_STRINGS.add("DROP TABLE Category;")
                RESET_DB_STRINGS.add("DROP TABLE UserConfiguration;")
                RESET_DB_STRINGS.add("DROP TABLE User;  ")

                var query: PreparedStatement
                RESET_DB_STRINGS.forEach {
                    query = conn.prepareStatement(it)
                    query.executeUpdate()
                }

            } catch (sqlE: SQLException) {
                sqlE.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}