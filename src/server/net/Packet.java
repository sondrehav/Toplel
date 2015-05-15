package server.net;

public class Packet {

    // General messages from server to client (0 -> 31):
    public static final byte SV_SERVER_STOP = 0;
    public static final byte SV_SERVER_CAP_CHANGE = 1;
    public static final byte SV_MESSAGE = 2;
    public static final byte SV_CL_MESSAGE = 3;

    // Requests from server to client (32 -> 95):
    public static final byte REQ_VERSION = 32;
    public static final byte REQ_FILE_TABLE = 33;

    // Response from server to client (96 -> 127):
    public static final byte RES_WRONG_VERSION = 96;
    public static final byte RES_ACCEPTED = 97;
    public static final byte RES_SERVER_FULL = 98;
    public static final byte RES_USERNAME_OK = 99;
    public static final byte RES_USERNAME_TAKEN = 100;
    public static final byte RES_USERNAME_INVALID = 101;
    public static final byte RES_NEED_USERNAME_FOR_OPERATION = 102;
    public static final byte RES_NULL_MSG = 103;

    // General messages from client to server (-128 -> -97):
    public static final byte CL_DISCONNECT = -128;
    public static final byte CL_MESSAGE = -127;

    // Request from client to server (-96 -> -33):
    public static final byte REQ_SET_USERNAME = -96;

    // Response from client to server (-32 -> -1):
    public static final byte RES_VERSION = -32;
    public static final byte RES_FILE_TABLE = -31; // Send files not in dir.

    //*************************************************

    // Values
    public static final byte VERSION = 0;

}
