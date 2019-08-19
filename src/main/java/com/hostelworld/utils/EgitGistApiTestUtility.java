package com.hostelworld.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;

public class EgitGistApiTestUtility {

	public static void main(String[] args) throws IOException {

	
	GitHubClient client = new GitHubClient().setOAuth2Token("6e2fb96471f4efce93a8a557b4502013a461016a");
			
	Gist gist = new Gist().setDescription("Prints a string to standard out");
	GistFile file = new GistFile().setContent("System.out.println(\"Hey World\");");
	gist.setFiles(Collections.singletonMap("Hi.java", file));
	gist = new GistService(client).createGist(gist);//Create
	String gistId=gist.getId();
	System.out.println("Gist created : id is ="+gistId);
	Map<String, GistFile> output=gist.getFiles();
	GistFile opfile = new GistFile();
	for (String key : output.keySet())
    {    	
        System.out.println(key + "=" + output.get(key));
        opfile=output.get(key);
    }
	
	String outputFile =opfile.getContent();
	System.out.println("Gist created : description is ="+gist.getUrl() +"*******************"+outputFile);//get content
	new GistService(client).deleteGist(gistId);//delete 
	
	}

}
