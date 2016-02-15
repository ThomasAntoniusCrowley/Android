import java.net.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server implements Runnable
{
    private Socket connection;
    private int ID;
    private static int clientCount;
    public static final int MAXCLIENTS = 10;
    
    public Server(Socket connection, int i)
    {
        this.connection = connection;
        this.ID = i;
    }

    public void ReceiveFromClient()
    {
        try //Receives file information and image data from client
        {

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void SendToClient()
    {
        try //Handles request and sends appropriate data
        {

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void Disconnect()
    {
        try //Disconnects client
        {
            connection.close();
            System.out.println("Client disconnected.");
            clientCount -= 1;
        }
        catch (IOException f)
        {
            f.printStackTrace();
        }
    }

    public void HandleClient()
    {
        try //Handles client send/receive requests
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String request = in.readLine();
            if (request.equals("<Client request message>"))
            {
                //Response
            }
        }
        catch (Exception e)
        {
            Disconnect();
            Thread.currentThread().stop();
        }
    }

//    public void LogRequestInfo(String request)
//    {
//        try
//        {
//            DateFormat dateFormat = new SimpleDateFormat(("dd/MM/yyyy:HH.mm.ss"));
//            PrintWriter printer = new PrintWriter(new FileWriter("log.txt", true));
//            Date date = new Date();
//            printer.printf(dateFormat.format(date) + ":" + connection.getInetAddress() + ":" + request + "%n");
//            printer.close();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args)
    {
        int port = 19999;
        clientCount = 0;
        Executor executor = Executors.newFixedThreadPool(10);

        try //Create server socket on specified port
        {
            ServerSocket socket = new ServerSocket(port);
            while (clientCount <= MAXCLIENTS) //Create loop to instantiate threads for new clients
            {
                Socket connection = socket.accept();
                System.out.println("Client accepted.");
                Runnable runnable = new Server(connection, ++clientCount);
                executor.execute(runnable);
                System.out.println("Thread started.");
            }
            System.out.println("Client limit reached.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void run() //This code will run on each thread
    {
        //Loop to accept clients here
    }
}