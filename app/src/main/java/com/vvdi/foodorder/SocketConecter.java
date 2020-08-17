package com.vvdi.foodorder;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import static com.vvdi.foodorder.MainActivity.FlagConnect;
import static com.vvdi.foodorder.MainActivity.sPref;

public class SocketConecter
{
    private static Socket socket;
    static SocketAddress socketAddress;
    private  Context context;

    private static OnReceiveData onReceiveData;

    public SocketConecter(OnReceiveData _onReceiveData) {
        int port = sPref.getInt("port", 2020);
        String ip  = sPref.getString("ip_address", "7.235.127.80");

        //int port = sPref.getInt("port", 9090);
        //String ip  = sPref.getString("ip_address", "62.33.74.81");

        socketAddress  = new InetSocketAddress(ip, port);
        socket = new Socket();
        this.onReceiveData = _onReceiveData;
    }

    public static void Close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void ConnectCheck () {
        try {
            socket = new Socket();
            socket.connect(socketAddress,3000);

            onReceiveData.Connected();
            FlagConnect = false;
        }

        catch (IOException e) {
            //Log.d("TAG", "Неудалось подключиться "+ e.getMessage());
        }
    }

    public static void SendData(String data) {
       final byte[] dataBt = data.getBytes();

        if(dataBt!=null) {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try {
                        socket.getOutputStream().write(dataBt);
                        socket.getOutputStream().flush();
                    }
                    catch (IOException e) {
                        Log.d("TAG", "SendData: "+ e.getMessage());
                    }
                }
            }).start();
               }
        }

    public static void Recv() {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try {
                       InputStream inputStream = socket.getInputStream();

                       BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                       String text;
                       while ((text = reader.readLine()) != null) {
                            onReceiveData.RecvData(text);
                       }
                    }
                    catch (IOException e) {
                        Log.d("TAG", "SendData: "+ e.getMessage());
                    }
                }
            }).start();
    }


    public interface OnReceiveData
    {
        public void RecvData(String text);
        public void Connected();
    }
}