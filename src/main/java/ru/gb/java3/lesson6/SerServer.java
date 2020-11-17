package ru.gb.java3.lesson6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SerServer {

    private static final int PORT = 32000;
    private static final int TIMEOUT = 1000;
    private static boolean connected = false;
    private static Socket client;
    private static final Logger LOGGER = LogManager.getLogger(SerServer.class);

    public static void main(String[] args) {
        connect();
        if (client != null) {
            getObject();
        }
        else LOGGER.error("Not connected");
    }

    private static void getObject() {
        try (InputStream input = client.getInputStream();
             ObjectInputStream objInput = new ObjectInputStream(input)) {
            SerObject obj = (SerObject) objInput.readObject();
            LOGGER.info(String.format("Received object: %s",obj.info()));
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void connect() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            LOGGER.info(String.format("Starting server on port %d",PORT));
            server.setSoTimeout(TIMEOUT);
            while (!connected) {
                try {
                    client = server.accept();
                    connected = true;
                    LOGGER.info(String.format("Connected client from %s",client.getInetAddress()));
                } catch (SocketTimeoutException e) {
                    //continue;
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
