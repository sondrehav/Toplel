package server.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static server.net.Packet.*;

// TODO: Fix if username is too long.

public class Server implements Runnable {

    Scanner in = new Scanner(System.in);

    public static void main(String[] args){

        Server server = new Server();

    }

    private ServerSocket socket;
    private ServerListener serverListener;
    private int maxConnections = 16;

    private boolean running = false;

    private String dirPath = null;
    private ArrayList<File> loadedFiles = new ArrayList<>();

    ArrayList<Client> connections = new ArrayList<>();
    HashMap<String, Client> names = new HashMap<>();

    public Server(){
        while(true){
            parseCommand(in.nextLine());
        }
    }

    public void setDirectory(String path){
        dirPath = "res/"+path;
        loadedFiles.clear();
        Stack<File> remaining = new Stack<>();
        remaining.add(new File(path));
        while(!remaining.isEmpty()){
            File f = remaining.pop();
            if(f.isDirectory()){
                for(File e : f.listFiles()){
                    remaining.add(e);
                }
            } else if(f.isFile()) {
                loadedFiles.add(f);
            }
        }
        reqFileTable(path);
    }

    public void start(int port){
        running = true;
        try{
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        new Thread(serverListener = new ServerListener()).start();
        new Thread(this).start();
        System.out.println("Server running.");
    }

    public boolean isRunning(){
        return running;
    }

    public void stop(){
        running = false;
        for(Client c : connections){
            c.send(new byte[]{SV_SERVER_STOP});
        }
        for(Client c : connections){
            c.disconnect();
        }
        try{
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server stopped.");
    }

    public int serverFrameRateCap = 2;

    private float serverAvgFrameRate = 0f;

    @Override
    public void run() {
        while(isRunning()){
            long time = System.nanoTime();
            loop();
            float elapsed = (float)(System.nanoTime() - time)/1000000f; // Elapsed time on one loop (ms)
            float maxWait = 1000f / (float)serverFrameRateCap;
            long actWait = (long)(maxWait - elapsed);
            if(actWait>0){
                synchronized(this){
                    try{
                        this.wait(actWait);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            elapsed = (float)(System.nanoTime() - time)/1000000f; // time elapsed in ms.
            float weight = 0.5f;
            if(serverAvgFrameRate == 0f) serverAvgFrameRate = 1000f / elapsed;
            else serverAvgFrameRate = weight * 1000f / elapsed + (1f - weight) * serverAvgFrameRate;
        }
    }

    private void loop(){
//        for(Client c : connections){
//            // Recv stuff...
//            try{
//                byte packetType = c.inputStream.readByte();
//                switch (packetType) {
//                    case REQ_DISCONNECT:
//                        if(connections.contains(c)) connections.remove(c);
//                        c.disconnect();
//                        break;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        for(Client c : connections){
//            // Send stuff...
//        }
//        System.out.println("loop");
    }

    private class ServerListener implements Runnable {

        @Override
        public void run() {
            while(isRunning()){
                try{
                    Socket clientSocket = socket.accept();
                    System.out.println("New client: " + clientSocket.getInetAddress());
                    new Client(clientSocket);
                } catch (SocketException e) {
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(2);
                }
            }
        }
    }

    private class Client implements Runnable {

        Socket clientSocket = null;
        DataInputStream inputStream = null;
        DataOutputStream outputStream = null;

        String name = null;

        ArrayList<String> files = new ArrayList<>();

        Client(Socket in) {
            try {
                clientSocket = in;
                inputStream = new DataInputStream(clientSocket.getInputStream());
                outputStream = new DataOutputStream(clientSocket.getOutputStream());
                byte[] b_cap = ByteBuffer.allocate(5).put(REQ_VERSION).putInt(serverFrameRateCap).array();
                send(b_cap);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            try {
                byte packetType = inputStream.readByte();
                switch (packetType){
                    case RES_VERSION:
                        byte version = inputStream.readByte();
                        if(version != VERSION){
                            send(new byte[]{RES_WRONG_VERSION, VERSION});
                        } else if(connections.size()>=maxConnections) {
                            send(new byte[]{RES_SERVER_FULL});
                        } else {
                            connections.add(this);
                            System.out.println("Client accepted.");
                            new Thread(this).start();
                            send(new byte[]{RES_ACCEPTED});
                            if(dirPath!=null) send(new byte[]{REQ_FILE_TABLE});
                            return;
                        }
                        break;
                    default:
                        System.out.println("Not a valid packet number: " + packetType);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client refused: " + clientSocket.getInetAddress());
            disconnect();
        }

        public void uploadFile(File f, byte filesLeft){
            try{
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                byte[] file = new byte[bis.available()];
                bis.read(file);
                // pkt_type(1), path_length(2), file_num(1), path(...), file(...)
                byte[] out = ByteBuffer.allocate(1 + 2 + 1 + f.getPath().length() + file.length)
                        .put(RES_FILE_TABLE)
                        .putShort((short)f.getPath().length())
                        .put(filesLeft)
                        .put(f.getPath().getBytes(Charset.forName("UTF-8")))
                        .put(file).array();
                send(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(byte[] bytes) {
            if(debug){
                if(name!=null)
                    System.out.println("\u001B[34mCL: "+name+"; OUT: "+bytes[0]+"\u001B[0m");
                else
                    System.out.println("\u001B[34mCL: "+clientSocket.getInetAddress()+"; OUT: "+bytes[0]+"\u001B[0m");
            }
            try {
                outputStream.write(bytes);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void disconnect() {
            try{
                inputStream.close();
                outputStream.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(isRunning()){
                try{
                    byte packetType = inputStream.readByte();
                    if(debug){
                        if(name!=null)
                            System.out.println("\u001B[34mCL: "+name+"; IN:  "+packetType+"\u001B[0m");
                        else
                            System.out.println("\u001B[34mCL: "+clientSocket.getInetAddress()+"; IN:  "+packetType+"\u001B[0m");
                    }
                    switch (packetType) {
                        case CL_DISCONNECT:{
                            if (connections.contains(this)) connections.remove(this);
                            System.out.println("Client is disconnecting: " + this.clientSocket.getInetAddress()+", " + name);
                            if(name != null) names.remove(name);
                            this.disconnect();
                            break;}
                        case REQ_SET_USERNAME:{
                            byte[] b_name = new byte[inputStream.available()];
                            inputStream.readFully(b_name);
                            String name = new String(b_name);
//                            System.out.println("NAME: "+name);
                            if(name.matches("^[A-Za-z0-9_ ]{4,}$")&&!name.toLowerCase().contentEquals("null")){
                                if(!names.containsKey(name)){
                                    if(names.containsKey(this.name)) names.remove(this.name);
                                    this.name = name;
                                    names.put(name, this);
                                    send(new byte[]{RES_USERNAME_OK});
                                    System.out.println("Username for client '"+clientSocket.getInetAddress()+"' set to '"+name+"'.");
                                } else {
                                    send(new byte[]{RES_USERNAME_TAKEN});
                                }
                            } else {
                                send(new byte[]{RES_USERNAME_INVALID});
                            }
                            break;}
                        case CL_MESSAGE: {
                            byte[] b_msg = new byte[inputStream.available()];
                            inputStream.readFully(b_msg);
                            String msg = new String(b_msg);
                            if(msg.toLowerCase().contentEquals("null")){
                                send(new byte[]{RES_NULL_MSG});
                                continue;
                            }
                            if(this.name!=null){
                                System.out.println("'"+name+"': '"+msg+"'");
                                // pkt_type(1), name_length(1), name(...), msg(...)
                                byte[] pkt = ByteBuffer.allocate(1 + 1 + this.name.length() + msg.length())
                                        .put(SV_CL_MESSAGE)
                                        .put((byte)this.name.length())
                                        .put(name.getBytes(Charset.forName("UTF-8")))
                                        .put(msg.getBytes(Charset.forName("UTF-8")))
                                        .array();
                                for(Client c : connections){
                                    c.send(pkt);
                                }
                            } else {
                                send(new byte[]{RES_NEED_USERNAME_FOR_OPERATION, CL_MESSAGE});
                            }
                            break;
                        }
                        case RES_FILE_TABLE: {
                            files = new ArrayList<>();
                            while(inputStream.available()>0){
                                byte[] bytes = new byte[inputStream.readShort()];
                                inputStream.read(bytes);
                                files.add(new String(bytes));
                            }
                            System.out.println("Client has files: ");
                            for(String s : files){
                                System.out.println("\t"+s);
                            }
                            if(dirPath!=null){
                                ArrayList<File> missingFiles = new ArrayList<>();
                                for(int i=0;i<loadedFiles.size();i++){
                                    if(!files.contains(loadedFiles.get(i).getPath())){
                                        System.out.println("\t"+loadedFiles.get(i).getPath());
                                        missingFiles.add(loadedFiles.get(i));
                                    }
                                }
                                System.out.println("Client is missing files: ");
                                if(missingFiles.isEmpty()) System.out.println("\tNONE");
                                else{
                                    for(File f : missingFiles) {
                                        System.out.println("\t"+f.getPath());
                                    }
                                }
                            }
                            break;
                        }
                    }
//                    inputStream.skip(inputStream.available()); // Skips the remaining bytes.
                } catch (SocketException e) {
                    disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void parseCommand(String command){
        Matcher matcher = Pattern.compile("^(\\w+)\\((.*)\\)$").matcher(command);
        try{
            if(matcher.find()){
                String commandName = matcher.group(1);
                String[] args = matcher.group(2).replace(" ", "").split(",");
                switch(commandName) {
                    case "start":
                        if(!isRunning()) start(Integer.parseInt(args[0]));
                        else System.err.println("Server already running.");
                        break;
                    case "stop":
                        if(isRunning()) stop();
                        else System.err.println("Server is not currently running.");
                        break;
                    case "max_connections":
                        int con = Integer.parseInt(args[0]);
                        if(connections.size() <= con) maxConnections = con;
                        else System.err.println("You already have more clients than the new max limit.");
                        break;
                    case "req_file_table":
                        reqFileTable(args[0]);
                        break;
                    case "list_clients":
                        if(connections.size()==0) System.out.println("No clients connected.");
                        else for(Client c : connections){
                            if(c.name!=null)System.out.println("\t"+c.name);
                            else System.out.println("\t"+c.clientSocket.getInetAddress());
                        }
                        break;
                    case "exit":
                        if(isRunning()) stop();
                        System.out.println("Exiting server application.");
                        System.exit(0);
                        break;
                    case "set_fps":
                        this.serverFrameRateCap = Integer.parseInt(args[0]);
                        for(Client c : connections){
                            byte[] b_cap = ByteBuffer.allocate(5).put(SV_SERVER_CAP_CHANGE).putInt(serverFrameRateCap).array();
                            c.send(b_cap);
                        }
                        System.out.println("Server fps cap is set to " + this.serverFrameRateCap + ".");
                        break;
                    case "get_fps":
                        System.out.println("FPS cap: " + this.serverFrameRateCap);
                        if(isRunning()) System.out.println("FPS avg: " + this.serverAvgFrameRate);
                        break;
                    case "set_dir":
                        setDirectory(args[0]);
                        break;
                    case "msg": // msg("message...", client, client ...)
                        if(isRunning()){
                            Matcher mat = Pattern.compile("^\\\"(.*)\\\"$").matcher(args[0]);
                            if(mat.find()){
                                String message = mat.group(1);
                                if(args.length==1){
                                    byte[] b_cap = ByteBuffer.allocate(1+message.length()).put(SV_MESSAGE).put(message.getBytes(Charset.forName("UTF-8"))).array();
                                    for(Client c : connections){
                                        c.send(b_cap);
                                    }
                                    System.out.println("Global message sent.");
                                } else if(args.length>=2) {
                                    String[] cl = new String[args.length-1];
                                    for(int i=0;i<cl.length;i++) cl[i] = args[i+1];
                                    for(String c : cl){
                                        Matcher mat2 = Pattern.compile("^\\\"(.*)\\\"$").matcher(c);
                                        if(!mat2.find()){
                                            System.err.println("Name argument(s) not formatted correctly.");
                                            continue;
                                        }
                                        String a_name = mat2.group(1);
                                        if(names.containsKey(a_name)){
                                            byte[] b_cap = ByteBuffer.allocate(1 + message.length() +a_name.length()).put(SV_MESSAGE)
                                                    .put(message.getBytes(Charset.forName("UTF-8")))
                                                    .put(a_name.getBytes(Charset.forName("UTF-8")))
                                                    .array();
                                            names.get(a_name).send(b_cap);
                                            System.out.println("Message sent to " + a_name + ".");
                                        } else {
                                            System.err.println("No clients with the name '" + a_name + "'.");
                                        }
                                    }
                                }
                            } else {
                                System.err.println("Place messages within quotes.");
                            }
                        } else {
                            System.err.println("Server is not currently running.");
                        }
                        break;
                    case "debug":{
                        if(args[0].toLowerCase().contentEquals("true"))
                            setDebug(true);
                        else if(args[0].toLowerCase().contentEquals("false"))
                            setDebug(false);
                        else System.err.println("Input either 'true' or 'false'.");
                        break;}
                    default:
                        System.out.println("'" + commandName + "' not a command.");
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Some of the arguments is formatted wrong.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("You did not provide enough arguments.");
        }
    }

    private void reqFileTable(String inPath){
        if(!isRunning()){
            System.err.println("Server not running.");
            return;
        }
        Matcher mat1 = Pattern.compile("^\\\"(.*)\\\"$").matcher(inPath);
        if(mat1.find()) {
            String path = mat1.group(1);
            File f = new File("res/"+path);
            if(!f.exists()){
                System.err.println("Path '"+path+"' does not exist.");
                return;
            }
            if(!f.isDirectory()){
                System.err.println("Path '"+path+"' is not a directory.");
                return;
            }
            byte[] b_cap = ByteBuffer.allocate(1 + path.length())
                    .put(REQ_FILE_TABLE)
                    .put(path.getBytes(Charset.forName("UTF-8")))
                    .array();
            for(Client c : connections){
                c.send(b_cap);
            }
            System.out.println("Requested file tables.");
        } else {
            System.err.println("Path not correctly formatted.");
        }
    }

    private boolean debug = false;
    public void setDebug(boolean input){
        System.out.println("Debug is set to " + input);
        debug = input;
    }

}
