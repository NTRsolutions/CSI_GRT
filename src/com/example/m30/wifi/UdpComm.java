package com.example.m30.wifi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.util.Log;


public class UdpComm {

	//private static final String TAG = "UdpComm";
	private static final int BUFFER_SIZE = 2048;

	private int mPort = PublicClass.UDP_PORT;
	private DatagramSocket mSocket;
	private DatagramPacket mSendPacket;
	private InetAddress mInetAddress;
	private ReceiveData mReceiveData;
	private UdpCommListener mListener;
	private byte[] mReceivedBuffer = new byte[BUFFER_SIZE];

	public UdpComm() {
		super();
	}

	// Open udp socket
	public boolean open() {

		try {
			mInetAddress = InetAddress.getByName(PublicClass.PEER_ADDRESS);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}

		try {
			mSocket = new DatagramSocket(mPort);
			mSocket.setBroadcast(true);
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		}

		mReceiveData = new ReceiveData();
		mReceiveData.start();
		return true;
	}

	// Close udp socket
	public void close() {
		stopReceive();
		if (mSocket != null) {
			mSocket.close();
		}
	}

	// Send the data
	public boolean send(String text) {
		if (mSocket == null) {
			return false;
		}

		if (text == null) {
			return true;
		}

		mSendPacket = new DatagramPacket(text.getBytes(), text.getBytes().length, mInetAddress, mPort);
		Log.i("msg", text);
		try {
			mSocket.send(mSendPacket);
			return true;
		} catch (IOException e) {
			//e.printStackTrace();
			return false;
		}
	}

	// Stop to receive
	public void stopReceive() {

		if (mReceiveData != null && !mReceiveData.isStoped()) {
			mReceiveData.stop();
		}
	}

	
	// Receive the data
	private class ReceiveData implements Runnable {

		private boolean stop;
		private Thread thread;

		private ReceiveData() {
			thread = new Thread(this);
		}

		@Override
		public void run() {

			while (!stop) {
				try {
					DatagramPacket packetToReceive = new DatagramPacket(mReceivedBuffer, BUFFER_SIZE);
					mSocket.receive(packetToReceive);
					onReceive(mReceivedBuffer, packetToReceive.getLength());
				} catch (SocketTimeoutException e) {
				}catch (IOException e1) {
				}
			}
		}

		void start() {
			thread.start();
		}

		void stop() {
			stop = true;
		}

		boolean isStoped() {
			return stop;
		}
	}
	
	public void setListener(UdpCommListener listener) {
		mListener = listener;
	}

	public interface UdpCommListener {
		public void onReceived(byte[] data, int length);
	}

	public void onReceive(byte[] buffer, int length) {
		if (mListener != null) {
			mListener.onReceived(buffer, length);
		}
	}
}