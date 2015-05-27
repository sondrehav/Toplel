package main;

import org.json.JSONException;
import org.json.JSONObject;
import loaders.SimpleFileReader;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static server.net.Packet.*;

public class Client {

    public static void main(String[] args){

        Scanner sysin = new Scanner(System.in);
        PrintStream sysout = System.out;

        Client c = new Client(sysin, sysout);

//        Scanner scanner = new Scanner(new FileInputStream(new File("res/lol")));


    }

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private Listener listener;

    private int serverFrameCap;

    boolean running = false;

    String username = null;

    Scanner in;
    PrintStream out;

    public Client(Scanner input, PrintStream out){
        this.in = input;
        this.out = out;
        while(true){
            parseCommand(in.nextLine());
        }
    }

    public void start(String address, int port){
        out.println("Client connecting.");
        try{
            socket = new Socket(address, port);
            new Thread(listener = new Listener()).start();
        } catch (ConnectException e){
            out.println("\u001B[31m"+"Not a valid pair of port and host."+"\u001B[0m");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning(){
        return running;
    }

    private void parseCommand(String command){
        Matcher matcher = Pattern.compile("^(\\w+)\\((.*)\\)$").matcher(command);
        try {
            if (matcher.find()) {
                String commandName = matcher.group(1);
                String[] args = matcher.group(2).split("\\s*,\\s*(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
                switch (commandName){
                    case "connect":{
                        if(isRunning()){
                            out.println("\u001B[31m"+"Already running. Stop the client first."+"\u001B[0m");
                            return;
                        }
                        String address = null;
                        int port = -1;
                        Matcher mat2 = Pattern.compile("^\\\"(.*)\\\"$").matcher(args[0]);
                        if(!mat2.find()){
                            out.println("\u001B[31m"+"Address argument not formatted correctly. Include quotes."+"\u001B[0m");
                            return;
                        }
                        if(args.length==1){
                            try{
                                JSONObject object = new JSONObject(SimpleFileReader.read(mat2.group(1)));
                                address = object.getString("host");
                                port = object.getInt("port");
                                if(object.has("name")){
                                    this.temp_name_holding_var = object.getString("name");
                                }
                            } catch (IOException e) {
                                out.println("\u001B[31m"+"No settings file."+"\u001B[0m");
                                return;
                            } catch (JSONException e) {
                                out.println("\u001B[31m"+e.getLocalizedMessage()+"\u001B[0m");
                            }
                        } else if(args.length==2) {
                            address = mat2.group(1);
                            port = Integer.parseInt(args[1]);
                        } else {
                            out.println("\u001B[31m"+"Not correct arguments."+"\u001B[0m");
                            return;
                        }
                        start(address, port);
                        break;}
                    case "disconnect":{
                        if(!isRunning()){
                            out.println("\u001B[31m"+"Client is not currently running."+"\u001B[0m");
                            return;
                        }
                        send(new byte[]{CL_DISCONNECT});
                        this.closeConnection();
                        out.println("Client disconnecting.");
                        break;}
                    case "exit":{
                        if(isRunning()){
                            send(new byte[]{CL_DISCONNECT});
                            closeConnection();
                        }
                        out.println("Exiting client application.");
                        System.exit(0);
                        return;}
                    case "set_name":{
                        Matcher mat3 = Pattern.compile("^\\\"(.*)\\\"$").matcher(args[0]);
                        if(!mat3.find()){
                            out.println("\u001B[31m"+"Name argument not formatted correctly. Include quotes."+"\u001B[0m");
                            return;
                        }
                        String name = mat3.group(1);
                        byte[] bytes = ByteBuffer.allocate(1 + name.length())
                                .put(REQ_SET_USERNAME)
                                .put(name.getBytes(Charset.forName("UTF-8")))
                                .array();
                        send(bytes);
                        temp_name_holding_var = name;
                        break;}
                    case "msg":{
                        Matcher mat4 = Pattern.compile("^\\\"(.*)\\\"$").matcher(args[0]);
                        System.out.println(args[0]);
                        if(!mat4.find()){
                            out.println("\u001B[31m"+"Message argument not formatted correctly. Include quotes."+"\u001B[0m");
                            return;
                        }
                        String message = mat4.group(1);
                        byte[] bytes = ByteBuffer.allocate(1 + message.length())
                                .put(CL_MESSAGE)
                                .put(message.getBytes(Charset.forName("UTF-8")))
                                .array();
                        send(bytes);
                        break;}
                    case "debug":
                        if(args[0].toLowerCase().contentEquals("true"))
                            setDebug(true);
                        else if(args[0].toLowerCase().contentEquals("false"))
                            setDebug(false);
                        else out.println("\u001B[31m"+"Input either 'true' or 'false'."+"\u001B[0m");
                        break;
                    default:
                        out.println("\u001B[31m"+"Unrecognized command."+"\u001B[0m");
                }
            }
        } catch (NumberFormatException e) {
            out.println("\u001B[31m"+"Some of the arguments is formatted wrong."+"\u001B[0m");
        } catch (ArrayIndexOutOfBoundsException e) {
            out.println("\u001B[31m"+"You did not provide enough arguments."+"\u001B[0m");
        }
    }
    private String temp_name_holding_var = null;

    private class Listener implements Runnable {
        Listener(){
            try{
                inputStream = new DataInputStream(socket.getInputStream());
                outputStream = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            running = true;
            while(isRunning()) {
                try{
                    byte packetType = inputStream.readByte();
                    if(debug)
                        out.println("\u001B[34mIN:  "+packetType+"\u001B[0m");
                    switch (packetType) {
                        case REQ_VERSION:
                            serverFrameCap = inputStream.readInt();
                            send(new byte[]{RES_VERSION, VERSION});
                            break;
                        case RES_WRONG_VERSION:
                            out.println("\u001B[31m"+"Client rejected: wrong version."+"\u001B[0m");
                            closeConnection();
                            break;
                        case RES_SERVER_FULL:
                            out.println("\u001B[31m"+"Client rejected: full server."+"\u001B[0m");
                            closeConnection();
                            break;
                        case SV_SERVER_STOP:
                            out.println("Server stopping.");
                            closeConnection();
                            break;
                        case RES_ACCEPTED:
                            out.println("Client accepted.");
                            if(temp_name_holding_var!=null){
                                byte[] bytes = ByteBuffer.allocate(1 + temp_name_holding_var.length())
                                        .put(REQ_SET_USERNAME)
                                        .put(temp_name_holding_var.getBytes(Charset.forName("UTF-8")))
                                        .array();
                                send(bytes);
                            }
                            break;
                        case SV_SERVER_CAP_CHANGE:
                            serverFrameCap = inputStream.readInt();
                            out.println("New server fps cap: " + serverFrameCap);
                            break;
                        case RES_USERNAME_OK:
                            username = temp_name_holding_var;
                            temp_name_holding_var=null;
                            out.println("New username: '"+ username+"'.");
                            break;
                        case RES_USERNAME_TAKEN:
                            out.println("\u001B[31m"+"Username already taken: '" + temp_name_holding_var + "'."+"\u001B[0m");
                            temp_name_holding_var = null;
                            break;
                        case RES_USERNAME_INVALID:
                            out.println("\u001B[31m"+"Username invalid: '" + temp_name_holding_var + "'."+"\u001B[0m");
                            temp_name_holding_var = null;
                            break;
                        case RES_NEED_USERNAME_FOR_OPERATION: // TODO: This is not useful feedback to the user...
                            out.println("\u001B[31m"+"You need a username for operation "+inputStream.readByte()+"."+"\u001B[0m");
                            break;
                        case REQ_FILE_TABLE:{
                            byte[] b_path = new byte[inputStream.available()];
                            inputStream.read(b_path);
                            String path = "res/" + new String(b_path);
                            out.println("Requesting file table: "+path);
                            ArrayList<String> files = new ArrayList<>();
                            Stack<File> remaining = new Stack<>();
                            remaining.add(new File(path));
                            while(!remaining.isEmpty()){
                                File f = remaining.pop();
                                if(f.isDirectory()){
                                    for(File e : f.listFiles()){
                                        remaining.add(e);
                                    }
                                } else if(f.isFile()) {
                                    files.add(f.getPath());
                                }
                            }
                            int totLength = 1;
                            for(String s : files){
                                totLength += s.length() + 2;
                            }
                            ByteBuffer v = ByteBuffer.allocate(totLength)
                                    .put(RES_FILE_TABLE);
                            for(String s : files){
                                short length = (short)s.length();
                                byte[] bytes = s.getBytes(Charset.forName("UTF-8"));
                                v.putShort(length);
                                v.put(bytes);
                            }
                            send(v.array());
                            break;}
                        case SV_CL_MESSAGE:{
                            byte username_length = inputStream.readByte();
                            byte[] b_user = new byte[username_length];
                            inputStream.read(b_user);
                            String user = new String(b_user);
                            byte[] b_msg = new byte[inputStream.available()];
                            inputStream.readFully(b_msg);
                            String msg = new String(b_msg);
                            out.println("'"+user+"': '"+msg+"'");
                            break;}
                        case SV_MESSAGE:{
                            byte[] bytes = new byte[inputStream.available()];
                            inputStream.read(bytes);
                            String msg = new String(bytes);
                            out.println("Server: '"+msg+"'");
                            break;
                        }
                        case RES_NULL_MSG:
                            out.println("You can't send a 'null'-message.");
                            break;
                        default:
                            out.println("\u001B[31m"+"Not a valid packet type: " + packetType+"\u001B[0m");
                    }
                    if(isRunning())
                        inputStream.skip(inputStream.available());
                } catch (SocketException | EOFException e){
//                    out.println("\u001B[31m"+e.getLocalizedMessage()+"\u001B[0m");
                    // TODO: Should I catch EOF?
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    public void send(byte[] input){
        if(debug)
            out.println("\u001B[34mOUT: "+input[0]+"\u001B[0m");
        try{
            outputStream.write(input);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(){
//        try{
//            Thread.sleep(10L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        this.username = null;
        try{
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = false;
    }

    private boolean debug = false;
    public void setDebug(boolean input){
        out.println("Debug is set to " + input);
        debug = input;
    }

}
