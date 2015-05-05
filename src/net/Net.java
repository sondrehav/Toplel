package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

import static utils.helpers.Display.display;

public class Net {

    static Socket socket;
    static InputStream socketInput = null;
    static OutputStream socketOutput = null;

    static final String SERVER = "localhost";
    static final int PORT = 8001;

    static final Scanner in = new Scanner(System.in);

    static ServerListener serverListener;

    static boolean running = false;

    public static void main(String[] args){
        try{
            socket = new Socket(SERVER, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        display("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
        try{
            socketOutput = socket.getOutputStream();
            socketInput = socket.getInputStream();
        } catch (IOException e) {
            display("Could not open streams.");
        }
        new Thread(serverListener = new ServerListener(socketInput)).start();
        running = true;
        while(running){
            String command = in.nextLine();
            executeCommand(command);
        }
    }

    public static void executeCommand(String command){
        switch (command) {
            case "stop":
                sendMsg("stop");
                serverListener.stop();
                running = false;
                stop();
                break;
            case "server.stop":
                display("Server stopped!");
                serverListener.stop();
                running = false;
                stop();
                break;
            default:
                sendMsg(command);
        }
    }

    public static void sendMsg(String msg){
        try{
            socketOutput.write(msg.getBytes(Charset.forName("UTF-8")));
            socketOutput.flush();
        } catch (IOException e) {
            display("Could not send command '" + msg + "'.");
        }
    }

    public static void stop(){
        try{
            if(socketInput != null) socketInput.close();
            if(socketOutput != null) socketOutput.close();
            if(socket != null) socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static class ServerListener implements Runnable {

        InputStream inputStream = null;

        public ServerListener(InputStream inputStream){
            this.inputStream = inputStream;
        }

        public void stop(){
            running = false;
        }

        boolean running = false;

        @Override
        public void run() {
            running = true;
            System.out.println("HELLO");
            while (running){
                try{
                    if(inputStream.available()>0){
                        byte[] bytes = new byte[inputStream.available()];
                        inputStream.read(bytes);
                        String str = new String(bytes, "UTF-8");
                        display("INPUT: "+str);
                        executeCommand(str);
                    }
                } catch(IOException e){
                    e.printStackTrace();
                    running = false;
                    Net.stop();
                }
            }
        }
    }

}
