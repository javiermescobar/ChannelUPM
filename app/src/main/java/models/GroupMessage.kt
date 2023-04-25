package models

data class GroupMessage(
    val GroupMessageId: Int,
    val Text: String,
    val SendDate: String,
    val GroupChatId: Int,
    val SenderId: Int
)

/*
GropuMessageId int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  Text varchar(1000) NOT NULL,
  SendDate varchar(30) NOT NULL,
  GroupChatId int NOT NULL,
  SenderId int NOT NULL,
  FOREIGN KEY (GroupChatId) REFERENCES GroupChat(GroupChatId),
  FOREIGN KEY (SenderId) REFERENCES User(UserId)
 */