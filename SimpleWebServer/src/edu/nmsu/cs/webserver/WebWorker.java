package edu.nmsu.cs.webserver;

/**
 * Web worker: an object of this class executes in its own new thread to receive and respond to a
 * single HTTP request. After the constructor the object executes on its "run" method, and leaves
 * when it is done.
 *
 * One WebWorker object is only responsible for one client connection. This code uses Java threads
 * to parallelize the handling of clients: each WebWorker runs in its own thread. This means that
 * you can essentially just think about what is happening on one client at a time, ignoring the fact
 * that the entirety of the webserver execution might be handling other clients, too.
 *
 * This WebWorker class (i.e., an object of this class) is where all the client interaction is done.
 * The "run()" method is the beginning -- think of it as the "main()" for a client interaction. It
 * does three things in a row, invoking three methods in this class: it reads the incoming HTTP
 * request; it writes out an HTTP header to begin its response, and then it writes out some HTML
 * content for the response content. HTTP requests and responses are just lines of text (in a very
 * particular format).
 * 
 * @author Jon Cook, Ph.D.
 *
 **/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;
import java.nio.file.Files;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

public class WebWorker implements Runnable
{

	private Socket socket;

	/**
	 * Constructor: must have a valid open socket
	 **/
	public WebWorker(Socket s)
	{
		socket = s;
	}

	/**
	 * Worker thread starting point. Each worker handles just one HTTP request and then returns, which
	 * destroys the thread. This method assumes that whoever created the worker created it with a
	 * valid open socket object.
	 **/
	public void run()
	{
		String fileType = null;
		
		String htmlPage;
		System.err.println("Handling connection...");
		try
		{
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			htmlPage = readHTTPRequest(is);
			File file = new File(htmlPage);
			
			if (htmlPage.endsWith("html")) {
				fileType="html";
				writeHTTPHeader(os, file, "text/html");
			}
			else if (htmlPage.endsWith("jpg")) {
				fileType="jpg";
				writeHTTPHeader(os, file, "image/jpg");
			}
			else if (htmlPage.endsWith("png")) {
				fileType="png";
				writeHTTPHeader(os, file, "image/png");
			}
			else if (htmlPage.endsWith("gif")) {
				fileType="gif";
				writeHTTPHeader(os, file, "image/gif");
			}
			
			writeContent(os, fileType, file);
		
			os.flush();
			socket.close();
		}
		catch (Exception e)
		{
			System.err.println("Output error: " + e);
		}
		System.err.println("Done handling connection.");
		return;
	}

	/**
	 * Read the HTTP request header.
	 **/
	private String readHTTPRequest(InputStream is)
	{
		String line;
		String parsedLine = null;
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		
		// parse the line and return it as a string
		while (true) {
			try {
				while (!r.ready())
					Thread.sleep(1);
				line = r.readLine();
				
				if (line.contains("GET")) {
					parsedLine = line.substring(5);
					if (parsedLine.contains(" "))
						parsedLine = parsedLine.substring(0, parsedLine.indexOf(" "));
					System.out.println("\n*" + parsedLine + "*\n");
				}
				
				System.err.println("Request line: (" + line + ")");
				if (line.length() == 0)
					break;
			}
			catch (Exception e) {
				System.err.println("Request error: " + e);
				break;
			}
		}
		
		return parsedLine;
	}

	/**
	 * Write the HTTP header lines to the client network connection.
	 * 
	 * @param os
	 *          is the OutputStream object to write to
	 * @param contentType
	 *          is the string MIME content type (e.g. "text/html")
	 **/
	private void writeHTTPHeader(OutputStream os, File file, String contentType) throws Exception
	{
		Date d = new Date();
		DateFormat df = DateFormat.getDateTimeInstance();
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		if (file.exists())
			os.write("HTTP/1.1 200 OK\n".getBytes());
		else
			os.write("HTTP/1.1 404 Not Found\n".getBytes());
		
		os.write("Date: ".getBytes());
		os.write((df.format(d)).getBytes());
		os.write("\n".getBytes());
		os.write("Server: Eli's very own server\n".getBytes());
		// os.write("Last-Modified: Wed, 08 Jan 2003 23:11:55 GMT\n".getBytes());
		// os.write("Content-Length: 438\n".getBytes());
		os.write("Connection: close\n".getBytes());
		os.write("Content-Type: ".getBytes());
		os.write(contentType.getBytes());
		os.write("\n\n".getBytes()); // HTTP header ends with 2 newlines
		return;
	}

	/**
	 * Write the data content to the client network connection. This MUST be done after the HTTP
	 * header has been written out.
	 * 
	 * @param os
	 *          is the OutputStream object to write to
	 **/
	private void writeContent(OutputStream os, String fileType, File file) throws Exception
	{
		
		// if working with an html file, go through and print each line individually
		if (fileType=="html") {
			if (file.exists() && !file.isDirectory()) {
				BufferedReader input = new BufferedReader(new FileReader(file));
				try {
					String readLine;
					while ((readLine = input.readLine()) != null) {
						os.write(readLine.getBytes());
					}	
					input.close();
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		
		// if working with an image or gif
		else if (fileType=="jpg" || fileType=="png" || fileType=="gif") {
			byte[] imageToBytes = Files.readAllBytes(file.toPath());
			os.write(imageToBytes);
		}
		
		// 404 not found
		else {
			os.write("<html><head></head><body>\n".getBytes());
			os.write("<h3>404 Not Found</h3>\n".getBytes());
			os.write("</body></html>\n".getBytes());
		}
		
	}

} // end class
