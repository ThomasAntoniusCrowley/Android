import java.net.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    /**
     * Class holding all methods for server functionality using thread-pool architecture.
     * Handles client send/receive requests and threading.
     * <p>
     * Edit MAXCLIENTS to change size of thread pool.
     */
    private Socket connection;
    private int ID;
    private static int clientCount;
    private DatabaseHandler db;
    public static final int MAXCLIENTS = 10;

    public Server(Socket connection, int i) {
        this.connection = connection;
        this.ID = i;

    }

    public void ReceiveBill(BufferedReader in) {
        /**
         * Skeleton code for receiving order details from client app. Next step is to handle writing to database
         * file - it's ready to receive an order.
         */
        System.out.println("Preparing to receive Bill");
        try //Receives order information from client
        {
            //BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            int tableNo = in.read();
            System.out.println(String.format("Table number: %d", tableNo));

            int size = (in.read());
            System.out.println("Order size: " + size);

            String[] items = new String[size];
            System.out.print("Reading order data...");
            for (int i = 0; i < size; i++)
            {
                items[i] = in.readLine();
                System.out.println("Accepting item: " + items[i]);
            }
            System.out.print("OK!\n");

            System.out.print("Appending to order...");
            db = new DatabaseHandler();
            db.addItemsToOrder(items, tableNo);
            System.out.print("OK!\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReceiveTest() {
        try //Receives order information from client
        {
            System.out.println("TESTING");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            int size = in.read();

            System.out.println(size);

            String[] items = new String[size];
            for (int i = 0; i < size; i++)
            {
                items[i] = in.readLine();
                System.out.println(items[i]);
            }

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            out.write("OK");

            System.out.println("Read order data.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Disconnect() {
        /**
         * Handles client disconnection. May have to update to handle sudden disconnection as opposed to controlled.
         */

        try //Disconnects client
        {
            connection.close();
            System.out.println("Client disconnected.");
            clientCount -= 1;
        } catch (IOException f) {
            f.printStackTrace();
        }
    }

    public void HandleClient() {
        /**
         * Method runs on each thread in run(), always ready to handle any new client requests.
         */

        try //Handles client send/receive requests
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String request = in.readLine();
            System.out.println(String.format("RECEIVED REQUEST: %s FROM CLIENT", request));
            if (request.equals("SENDING_BILL")) {
                ReceiveBill(in);
            }
            if (request.equals("SENDING_TEST")) {
                ReceiveTest();
            }
            if (request.equals("SEND_MENU")){
                SendMenu();
            }
        } catch (Exception e) {
            Disconnect();
            Thread.currentThread().stop();
        }
    }

    public void SendMenu() {
        /**
         * Runs whenever client connects and sends them most recent menu.
         */
        System.out.println("Sending Menu");
        db = new DatabaseHandler();
        String[][] menu = db.returnMenuAsArray();
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

            out.write(menu.length);
            out.write(menu[0].length);

            for (int i = 0; i < menu.length; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    out.write(menu[i][j] + "\n");
                }
            }
            out.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /**
         * Code for handling new clients where necessary using thread-pool architecture and the Executor interface.
         * Uses clientCount and MAXCLIENTS to ensure the thread pool remains controlled by rejecting clients once
         * limit has been reached.
         */

        //Crreate a GUI popup
        CentralGUI centralGUI = new CentralGUI("192.168.43.224", "19999");

        int port = 19999;
        clientCount = 0;
        Executor executor = Executors.newFixedThreadPool(MAXCLIENTS);

        try //Create server socket on specified port
        {
            ServerSocket socket = new ServerSocket(port);
            System.out.println("Server ready!");
            while (clientCount <= MAXCLIENTS) //Create loop to instantiate threads for new clients
            {
                Socket connection = socket.accept();
                System.out.println("Client accepted");

                Runnable runnable = new Server(connection, ++clientCount);
                executor.execute(runnable);
                System.out.println("Thread started");

            }
            System.out.println("Client limit reached");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        /**
         * Code that runs on each thread. Sends new clients a menu file before waiting for a request from them.
         */

        while (true) {
            HandleClient();
        }
    }
}