
package com.hostelworld.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpAPITestUtility {
	final static String GistURL = "https://api.github.com";
	static HttpAPITestUtility HttpAPITestUtility = new HttpAPITestUtility();

	public static HttpURLConnection CreateConnection(String targetURL, String requestType) throws IOException {
		URL url = new URL(targetURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(requestType);
		connection.setRequestProperty("Content-Type", "application/json");
		return connection;
	}

	public static void SetAuthenticationHeader(HttpURLConnection connection, String token) {
		connection.setRequestProperty("Authorization", "token " + token);
	}

	@SuppressWarnings("unchecked")
	public String getJsonObject(String description, Boolean isPublic, String filename, String fileContent) {
		JSONObject object = new JSONObject();
		object.put("description", description);
		object.put("public", new Boolean(isPublic));

		JSONObject file1 = new JSONObject();
		JSONObject file1Content = new JSONObject();
		file1Content.put("content", fileContent);
		file1.put(filename + ".txt", file1Content);
		object.put("files", file1);

		return object.toString();
	}

	public HttpURLConnection sendAPIRequest(String AUTH_TOKEN, String requestType, String contentToSend)
			throws IOException {
		HttpURLConnection connection = null;
		String contents = null;
		if (requestType.equals("create")) {
			final String endPoint = "/gists";
			connection = CreateConnection(GistURL + endPoint, "POST");
			SetAuthenticationHeader(connection, AUTH_TOKEN.toString());
			contents = contentToSend;

			connection.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(contents);
			out.flush();
			out.close();
			// 201
		} else if (requestType.equals("getcontent")) {
			final String endPoint = "/gists";
			connection = CreateConnection(GistURL + endPoint, "GET");
			SetAuthenticationHeader(connection, AUTH_TOKEN.toString());
			// 200
		} else if (requestType.equals("delete")) {
			final String endPoint = "/gists/" + contentToSend;
			connection = CreateConnection(GistURL + endPoint, "DELETE");
			SetAuthenticationHeader(connection, AUTH_TOKEN.toString());
			// 204
		}

		return connection;
	}

	public String parseResponse(HttpURLConnection connection, String parameter) throws ParseException, IOException {
		String value = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		try {
			JSONObject response = new JSONObject();
			JSONParser parser = new JSONParser();
			response = (JSONObject) parser.parse(content.toString());
			value = (response.get(parameter)).toString();
		} catch (Exception e) {
			value = content.toString();
		}
		return value;
	}
}
