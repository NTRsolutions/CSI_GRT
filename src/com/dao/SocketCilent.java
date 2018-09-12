package com.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketCilent {
	public static Socket builder(String host,int port) throws UnknownHostException, IOException{
		Socket socket = new Socket(host, port);
		return socket;
		
	}
	public static InputStream getCilentInputStream(String host,int port) throws UnknownHostException, IOException{
		InputStream is = SocketCilent.builder(host, port).getInputStream();
		return is;
	}
	public static OutputStream getCilentOutputStream(String host,int port) throws UnknownHostException, IOException{
		OutputStream os = SocketCilent.builder(host, port).getOutputStream();
		return os;
		
	}
	public static ObjectInputStream getCilentObjectInputStream(String host,int port) throws UnknownHostException, IOException{
		InputStream is = SocketCilent.builder(host, port).getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		return ois;
		
	}
	public static ObjectOutputStream getCilentObjectOutputStream(String host,int port) throws UnknownHostException, IOException{
		OutputStream os = SocketCilent.builder(host, port).getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		return oos;
		
	}
	public static DataInputStream getCilentDataInputStream(String host,int port) throws UnknownHostException, IOException{
		InputStream is = SocketCilent.builder(host, port).getInputStream();
		DataInputStream dis = new DataInputStream(is);
		return dis;
		
	}
	public static DataOutputStream getCilentDataOutputStream(String host,int port) throws UnknownHostException, IOException{
		OutputStream os = SocketCilent.builder(host, port).getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		return dos;
		
	}
}
