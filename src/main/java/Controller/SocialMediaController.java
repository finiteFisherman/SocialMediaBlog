package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;
//import java.sql.Connection;
import java.sql.SQLException;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

public class SocialMediaController {
    //private Connection connection;
    private AccountService accountService;
    private MessageService messageService;

    // constructors for tests
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /*
   Method defines the structure of the Javalin Library API. Javalin methods will use handler methods
   to manipulate the Context object, which is a special object provided by Javalin which contains information about
   HTTP requests and can generate responses.
   Start API method to define endpoints
   */
    public Javalin startAPI(){
        Javalin app = Javalin.create();
        app.post("/register", this::postRegister);
        app.post("/login", this::postLogin);
        app.post("/messages", this::postMessage);
        app.get("/messages", this::getAllMessage);
        app.get("/messages/{message_id}", this::getAllMessageMessID);
        app.delete("/messages/{message_id}", this:: delMessageMessID);
        app.patch("/messages/{message_id}", this:: patchMessageMessID);
        app.get("/accounts/{account_id}/messages", this:: getAllMessageAccID);
        //app.start(8080); dont need already setup in tests
        return app;
    }

    /*
     Handler to register a new account.
     The Jackson ObjectMapper will automatically convert the JSON of the POST request into an Account object.
     JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postRegister(Context ctx) throws JsonProcessingException, SQLException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if (addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        }
        else {
            ctx.status(400);
        }
    }
    //Handler to create a new login
    private void postLogin(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedLogin = accountService.login(account);
        if (addedLogin != null) {
            ctx.json(mapper.writeValueAsString(addedLogin));
            ctx.status(200);
        }
        else {
            ctx.status(401);
        }

    }
    //Handler to create a new message
    private void postMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        if (addedMessage != null) {
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }
    // handler to get all messages
    private void getAllMessage(Context ctx) {
        List<Message> message = messageService.getAllMsg();
        ctx.json(message);
        ctx.status(200);
    }
    // handler to get all message by message_id
    private void getAllMessageMessID(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //Message message = mapper.readValue(ctx.body(), Message.class);
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message messByID = messageService.getMsgByMsgID(messageID);
        System.out.println(messByID);
        if (messByID == null) {
            ctx.status(200);
        }
        else {
            ctx.json(mapper.writeValueAsString(messByID));
            ctx.status(200);
        }
    }

    // handler to delete message by message_id
    private void delMessageMessID(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message messByID = messageService.getMsgByMsgID(messageID);
        boolean deleted = messageService.delMsgById(messageID);
            if (messByID == null) {
            ctx.status(200);
            }
        else {
        ctx.json(mapper.writeValueAsString(messByID));
        ctx.status(200);
        }
    }

    // handler to update (patch) message by message ID
    /*
    private void patchMessageMessID(Context ctx) throws JsonProcessingException, SQLException {

        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int messID = Integer.parseInt(ctx.pathParam("message_id"));
        String newMessageText = ctx.body();
        Message updatedMess = messageService.updateMsg(newMessageText, messID);
        System.out.println(updatedMess);
        if (updatedMess != null){
            ctx.json(mapper.writeValueAsString(updatedMess));
            ctx.status(200);
        }
        else{
        ctx.status(400);
        }

    }
    */
    private void patchMessageMessID(Context context) throws JsonProcessingException, SQLException {
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = messageService.updateMsg(
                mapper.readTree(context.body()).get("message_text").asText(),
                Integer.parseInt(context.pathParam("message_id" ))
        );
        if(newMessage != null ) context.status(200).json(mapper.writeValueAsString(newMessage)) ;
        else context.status(400);


    }

    // handler to get all messages by account id
    private void getAllMessageAccID(Context ctx) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        //Message message = mapper.readValue(ctx.body(), Message.class);
        int accountID = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUserId(accountID);
        System.out.println(messages);
        //if (messages == null) {
            //ctx.status(400);
        //} else {
        ctx.json(mapper.writeValueAsString(messages));
        ctx.status(200);
        //}
    }

}



//OLD
//public class SocialMediaController {

    //In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
    //suite must receive a Javalin object from this method.
    //@return a Javalin app object which defines the behavior of the Javalin controller.

   // public Javalin startAPI() {
    //Javalin app = Javalin.create();
    //app.get("example-endpoint", this::exampleHandler);

   // return app;
   // }


    //This is an example handler for an example endpoint.
    //@param context The Javalin Context object manages information about both the HTTP request and response.

   // private void exampleHandler(Context context) {

   //context.json("sample text");
    //}



