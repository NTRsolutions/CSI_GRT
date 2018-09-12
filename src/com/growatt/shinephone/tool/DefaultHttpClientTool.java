package com.growatt.shinephone.tool;

import org.apache.http.impl.client.DefaultHttpClient;

public class DefaultHttpClientTool 
{
	private static DefaultHttpClient client = new DefaultHttpClient();
	
	public static DefaultHttpClient getInstance()
	{
		return client;
	}

}
