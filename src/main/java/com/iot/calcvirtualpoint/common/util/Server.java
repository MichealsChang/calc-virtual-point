package com.iot.calcvirtualpoint.common.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket ss = null;

    Socket s = null;

    DataInputStream inStream = null;

    DataOutputStream outStream = null;
    
    public Server() {
        try {
            ss = new ServerSocket(8188);
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    void waitForClient() {
        try {

            s = ss.accept();

            inStream = new DataInputStream(s.getInputStream());

            outStream = new DataOutputStream(s.getOutputStream());

            outStream.writeUTF("1");

            s.setSoTimeout(3000);

            waitData();

        }

        catch (Exception e) {

            System.out.println(e.toString());

        }

    }



    void waitData() {

        while (true) {

            try {

                String str = inStream.readUTF();

                System.out.println("Server accept: " + str);

                int nu = Integer.parseInt(str) + 1;

                if (nu > 20) {

                    System.out.println("Send end!");

                    break;

                }

                else {

                    str = Integer.toString(nu);

                    outStream.writeUTF(str);

                }

            }

            catch (Exception e) {

                System.out.println(e.toString());

                break;

            }

        }

    }



    public static void main(String[] args) {

        Server socketServer1 = new Server();

        socketServer1.waitForClient();

    }

}
