
package seleniumTests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Tester {
	final static String OATH_TOKEN = "6e2fb96471f4efce93a8a557b4502013a461016a";
	final static String GistURL = "https://api.github.com";

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

	public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();

		for (Map.Entry<String, String> entry : params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			result.append("&");
		}

		String resultString = result.toString();
		return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
	}

	public static void CreatGistTest() throws IOException, ParseException {
		final String endPoint = "/gists";
		HttpURLConnection connection = CreateConnection(GistURL + endPoint, "POST");
		SetAuthenticationHeader(connection, OATH_TOKEN);

		JSONObject object = new JSONObject();
		object.put("description", "This is a java test for Gist.");
		object.put("public", new Boolean(false));

		JSONObject file1 = new JSONObject();
		JSONObject file1Content = new JSONObject();
		file1Content.put("content", "This is the java JSON test for Gist API.");
		file1.put("file1.txt", file1Content);
		object.put("files", file1);

		System.out.println(object.toString());

		connection.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.writeBytes(object.toString());
		out.flush();
		out.close();

		int status = connection.getResponseCode();
		System.out.println("Status code  " + status);

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		System.out.println(content.toString());

		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		response = (JSONObject) parser.parse(content.toString());
		System.out.println(response.get("url"));
		connection.disconnect();

	}

	public static void main(String[] args) throws IOException, ParseException {
		CreatGistTest();
	}
}
