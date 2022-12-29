package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private ArrayList<ClientHandler> clientsArrayList;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clientsArrayList) {
        try {
            this.socket = socket;
            this.clientsArrayList = clientsArrayList;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override

    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                for (ClientHandler clientHandler : clientsArrayList) {
                    clientHandler.writer.println(message);
                }
            }


        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                socket.close();

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
