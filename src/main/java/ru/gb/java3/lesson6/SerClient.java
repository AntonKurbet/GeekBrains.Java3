package ru.gb.java3.lesson6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SerClient {
    private static final String IP_ADDR = "127.0.0.1";
    private static final int PORT = 32000;
    private static final Logger LOGGER = LogManager.getLogger(SerServer.class);

    private static Socket connect() {
        try {
            return new Socket(IP_ADDR, PORT);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    private static void sendObject(Socket socket) throws IOException {
        SerObject obj = new SerObject(1,"test",8.369f);
        OutputStream out = socket.getOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(obj);
        LOGGER.info("Sent object: " + obj.info());
    }

    public static void main(String[] args) {
        try {
            Socket socket = connect();
            if (socket != null) {
                LOGGER.info("Connected to " + socket.getInetAddress() + ":" + socket.getPort());
                sendObject(socket);
            }
            else LOGGER.error("Not connected");
        } catch (IOException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }

    }

}
