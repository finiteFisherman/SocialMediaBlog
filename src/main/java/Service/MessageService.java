package Service;
import Model.Message;
import DAO.MessageDAO;

import java.util.List;

/*
 The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
 persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
 SQL. Programming tasks like checking that the input is valid, conducting additional security checks, or saving the
 actions undertaken by the API to a logging file.
 */
public class MessageService {
    public MessageDAO messageDAO;

    //constructor for MessageService which creates a messageDAO
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    //constructor for MessageDAO mock in test cases
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // get all messages
    public List<Message> getAllMsg() {
        return messageDAO.getAllMsg();
    }

    // get all messages by messageID
    public Message getMsgByMsgID(int msgID) {
        return messageDAO.getMsgByMsgID(msgID);
    }

    // delete message by messageID
    public boolean delMsgById(int msgID) {
        return messageDAO.delMsgById(msgID);
    }

    //get messages by user ID
    public List<Message> getMessagesByUserId(int userID) {
        return messageDAO.getMessagesByUserId(userID);
    }

    //method to check if the account exists
    //private boolean accountExists(int userID){
    //return false;
    // }
    // create message check if null, empty, text.length > 255
    public Message createMessage(Message message) {

        //if message is null or length is greater than 255 chars
        if (message.getMessage_text() == null || message.getMessage_text().isEmpty() ||
                message.getMessage_text().length() > 255) {
            //throw new IllegalArgumentException("Invalid message or user");
            return null;
        } else {
            // pass case
            //message.setTime_posted_epoch(System.currentTimeMillis());
            return messageDAO.createMessage(message);
        }
    }

    /*
    public Message updateMsg(String messageText, int messageId){
        // cases where message is not updated


        if(messageText.isEmpty() || (messageText.length() > 255) || messageText == null ){
            return null;
        }
        return messageDAO.updateMsg(messageText, messageId);
    }

     */
    public Message updateMsg(String message_text, int message_id){
        return message_text.length() > 0 ? messageDAO.updateMsg(message_text, message_id) : null;
    };


}






