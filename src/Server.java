import java.net.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server implements Runnable
{
    /**
     * Class holding all methods for server functionality using thread-pool architecture.
     * Handles client send/receive requests and threading.
     *
     * Edit MAXCLIENTS to change size of thread pool.
     */
    private Socket connection;
    private int ID;
    private static int clientCount;
    public static final int MAXCLIENTS = 10;
    
    public Server(Socket connection, int i)
    {
        this.connection = connection;
        this.ID = i;
    }

    public void ReceiveOrder()
    {
        /**
         * Skeleton code for receiving order details from client app. Will update to plug in client app.
         */

        try //Receives order information from client
        {
            ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
            File file = (File) ois.readObject();
            System.out.println("Read image data.");
            FileOutputStream fos = new FileOutputStream("<Path to orders file>", true);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    CODE DEPRECATED
//    public void SendToClient()
//    {
//        try //Handles request and sends appropriate data
//        {
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

    public void Disconnect()
    {
        /**
         * Handles client disconnection. May have to update to handle sudden disconnection as opposed to controlled.
         */

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
        /**
         * Method runs on each thread in run(), always ready to handle any new client requests.
         */

        try //Handles client send/receive requests
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String request = in.readLine();
            if (request.equals("SENDING_ORDER"))
            {
                ReceiveOrder();
            }
        }
        catch (Exception e)
        {
            Disconnect();
            Thread.currentThread().stop();
        }
    }

    public void SendMenuFile()
    {
        /**
         * Runs whenever client connects and sends them most recent menu file. Menu file in progress.
         */

        File menuFile = new File("<Menu directory>");

        try //Sends menu file as object to client
        {
            ObjectOutputStream outList = new ObjectOutputStream(connection.getOutputStream());
            outList.writeObject(menuFile);
            outList.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

//    POTENTIALLY USEFUL CODE
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
        /**
         * Code for handling new clients where necessary using thread-pool architecture and the Executor interface.
         * Uses clientCount and MAXCLIENTS to ensure the thread pool remains controlled by rejecting clients once
         * limit has been reached.
         */

        int port = 19999;
        clientCount = 0;
        Executor executor = Executors.newFixedThreadPool(MAXCLIENTS);

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
        /**
         * Code that runs on each thread. Sends new clients a menu file before waiting for a request from them.
         */

        SendMenuFile();
        while (true)
        {
            HandleClient();
        }
    }
}