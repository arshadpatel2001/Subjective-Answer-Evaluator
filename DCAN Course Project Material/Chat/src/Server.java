// Java implementation of Server side
// It contains two classes : Server and ClientHandler
import java.io.*;
import java.util.*;
import java.net.*;

// Server class
public class Server
{

    // Vector to store active clients
    static Vector<ClientHandler> ar = new Vector<>();
    // counter for clients

    public static void main(String[] args) throws IOException
    {
        System.out.println("Server started successfully\nWaiting for clients to join...");//printing server started confirmation message.
        // sendMessage thread for server
        Thread message = new Thread(new Runnable()
        {
            @Override
            public void run() {
                Scanner scn=new Scanner(System.in);//to scan from server
                while (true) {//true loop for infinite iterations
                    // read the message to deliver.
                    String msg = scn.nextLine();
                    //tokenizer to split the string into message and recipient
                    StringTokenizer str = new StringTokenizer(msg, "#");//using # as delimiter
                    String MsgToSend = str.nextToken();//Message
                    try {
                        String recipient = str.nextToken();//recipient
                        // search for the recipient in the connected devices list.
                        // ar is the vector storing client of active users
                        for (ClientHandler mc : Server.ar) {//iterating over ar vector
                            // if the recipient is found and is loggedin, write on its
                            // output stream
                            if (mc.name.equals(recipient) && mc.isloggedin == true) {
                                mc.dos.writeUTF("Server : " + MsgToSend);//writing on the output stream using UTF-8 encoding
                                break;//break the search loop if the recipient is found.
                            }
                        }
                    } catch (NoSuchElementException | IOException e) {
                        for (ClientHandler Clients : Server.ar)
                        {
                            if (Clients.isloggedin==true)
                            {
                                try {
                                    Clients.dos.writeUTF("Server : "+msg);//writing on the output stream of all the clients
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();//printing the stack for errors
                                }
                            }
                        }
                    }
                }
            }
        });

        message.start();//starting the message thread

        // server is listening on port 1234
        ServerSocket ss = new ServerSocket(1234);

        Socket s;

        // running infinite loop for getting
        // client request
        while (true) {
                // Accept the incoming request
                s = ss.accept();
                //confirmation message if request is accepted
                System.out.println("New client request received : " + s);
                // obtain input and output streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                //Welcome message for clients
                dos.writeUTF("Server is Online...");
                //Asking client's name
                dos.writeUTF("Enter your NAME");
                String nickname = dis.readUTF();//storing the name in nickname
                System.out.println("Adding "+nickname+" to active client list");//printing message on server side
                System.out.println("Creating a new handler for "+nickname+"....");//printing message on server side

                // Create a new handler object for handling this request.
                ClientHandler mtch = new ClientHandler(s,nickname, dis, dos);

                // Create a new Thread with this object.
                Thread t = new Thread(mtch);

                //printing message on server side
                System.out.println("Adding "+nickname+" to active client list");

                // add this client to active clients list i.e. in ar vector
                ar.add(mtch);

                //Sending message to other clients which are loggedin
                for (ClientHandler Clients : Server.ar)
                {
                    // output stream
                    if (Clients.name!=mtch.name && Clients.isloggedin==true)
                    {
                        Clients.dos.writeUTF(mtch.name + " just hopped in to the server...");
                    }
                }

                // start the thread.
                t.start();

                // Successfully connected to the server message for client
                dos.writeUTF("Connected to the server...");
        }
    }
}

// ClientHandler class
class ClientHandler implements Runnable
{
    final String name;//to store client name
    final DataInputStream dis;//client's input stream
    final DataOutputStream dos;//client's output stream
    Socket s;//client's socket
    boolean isloggedin;//boolean to store login/logout information

    // constructor
    public ClientHandler(Socket s, String name,
                         DataInputStream dis, DataOutputStream dos) throws IOException {
        this.dis = dis;//assigning values
        this.dos = dos;//assigning values
        this.name = name;//assigning values
        this.s = s;//assigning values
        this.isloggedin=true;//assigning values
        //printing the menu.
        dos.writeUTF("***Connected to the server***");//printing the menu.
        dos.writeUTF("Options:");//printing the menu.
        dos.writeUTF("Type the message to be sent the server");//printing the menu.
        dos.writeUTF("Use #client_name at end to send to a specific client");//printing the menu.
        dos.writeUTF("Use #everyone at end to send everyone");//printing the menu.
        dos.writeUTF("Use 'list' to check online clients");//printing the menu.
        dos.writeUTF("->logout : to logout from the chat");//printing the menu.
        dos.writeUTF("->login  : to join the chat again");//printing the menu.
        dos.writeUTF("->exit   : to leave the server");//printing the menu.
        //printing the menu.
    }

    @Override
    public void run() {

        String received;//variable to store received messages
        while (true)
        {
            try
            {
                // receive the string from input stream
                received = dis.readUTF();

                //logout condition
                if(received.equals("logout")){
                    this.dos.writeUTF("You are offline");//printing message
                    this.isloggedin=false;//logging out
                    for (ClientHandler mc : Server.ar)
                    {
                        //sending message to other clients
                        if (this.name!=mc.name && mc.isloggedin==true){
                            mc.dos.writeUTF(this.name+" went offline");
                        }
                    }
                    continue;
                }
                //login condition
                else if(received.equals("login")){
                    this.dos.writeUTF("You are online");//printing message
                    this.isloggedin=true;//logging in
                    for (ClientHandler mc : Server.ar)
                    {
                        //sending message to other clients
                        if (this.name!=mc.name && mc.isloggedin==true){
                            mc.dos.writeUTF(this.name+" is online");
                        }
                    }
                    continue;
                }
                //exit condition
                else if (received.equals("exit")){
                    this.dos.writeUTF("You left the Server");//printing message
                    System.out.println(this.name+" Left The Server");//printing message on server side
                    //sending message to other clients
                    for (ClientHandler mc : Server.ar)
                    {
                        if (this.name!=mc.name && mc.isloggedin==true){
                            mc.dos.writeUTF(this.name+" Left The Server");
                        }
                    }
                    this.isloggedin=false;//logging out
                    this.s.close();//closing the socket connection
                    break;//terminating the infinite loop
                }
                //online clients list condition
                else if (received.equals("list")){
                    this.dos.writeUTF("Online :");//printing message
                    for (ClientHandler mc : Server.ar)
                    {
                        //searching and printing online clients
                        if (this.name!=mc.name && mc.isloggedin==true){
                            this.dos.writeUTF(" "+mc.name+" ");
                        }
                    }
                }

                // break the string into message and recipient part
                StringTokenizer st = new StringTokenizer(received, "#");//using # as delimiter
                String MsgToSend = st.nextToken();//message
                try {
                    String recipient = st.nextToken();//recipient
                    System.out.println(received);//printing message on server side
                    //everyone condition
                    if(recipient.equals("everyone")){
                        for (ClientHandler mc : Server.ar)
                        {
                            //if recipent is everyone send to everyone who are logged in
                            if (this.name!=mc.name && mc.isloggedin==true){
                                mc.dos.writeUTF(this.name+" : "+MsgToSend);
                            }
                        }
                    }
                    //single specific client condition
                    else {
                        for (ClientHandler mc : Server.ar) {
                            //seraching and sending the message to client
                            if (mc.name.equals(recipient) && mc.isloggedin == true) {
                                mc.dos.writeUTF(this.name + " : " + MsgToSend);
                                break;
                            }
                        }
                    }
                } catch (NoSuchElementException e) {
                    System.out.println(this.name+" : "+received);//printing the message on server side
                }
            } catch (IOException e) {
                try {
                    Server.ar.remove(this);//deleting the client from ar vector of clients
                    try {
                        this.s.close();//closing the socket connection
                        this.dis.close();//closing the input stream
                        this.dos.close();//closing the output stream
                        this.isloggedin=false;//logging out
                    } catch (IOException ioException) {
                        ioException.printStackTrace();//printing stack for errors
                    }
                }catch (ConcurrentModificationException z){
                    System.out.println("No clients joined");//printing no clients connected message
                }
            }
        }
    }
}
