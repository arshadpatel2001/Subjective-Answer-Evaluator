// Java implementation for multithreaded chat client
//import statements
import java.io.*;
import java.net.*;
import java.util.Scanner;

//client class
public class Client
{
    final static int ServerPort = 1234;//port number

    public static void main(String args[]) throws UnknownHostException, IOException
    {
        //scanner to read input
        Scanner scn = new Scanner(System.in);
        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");
        // establish the connection
        Socket s = new Socket(ip, ServerPort);
        // obtaining input and out streams
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        // sendMessage thread
        Thread sendMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (true) {//infinite loop

                    // read the message to deliver.
                    String msg = scn.nextLine();

                    try {
                        // write on the output stream
                        dos.writeUTF(msg);
                        //exit condition
                        if(msg.equals("exit")){
                            System.exit(0);//terminating the program
                        }
                    } catch (IOException e) {
                    }
                }
            }
        });

        // readMessage thread
        Thread readMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (true) {//infinite loop
                    try {
                        // read the message sent to this client
                        String msg = dis.readUTF();
                        //name condition
                        if (msg=="Enter your NAME"){
                            String NICKNAME = scn.nextLine();
                            dos.writeUTF(NICKNAME);
                        }
                        //general message condition
                        else {
                            System.out.println(msg);
                        }
                    } catch (IOException e) {
                    }
                }
            }
        });

        sendMessage.start();//starting sendMessage thread
        readMessage.start();//starting readMessage thread

    }
}
