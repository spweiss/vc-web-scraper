import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BainCapital {

	private URL url;
	private HttpURLConnection connection;
	private String html;

	/**
	 * Initializes the connection to the desired URL.
	 * 
	 * @throws IOException
	 *             When the desired URL is malformed or does not exist or when there
	 *             is an input error while reading the page.
	 */
	public void initialize() {
		try {
			url = new URL("https://www.baincapitalventures.com/portfolio/?status=active");
			URLConnection urlConnection = url.openConnection();
			connection = (HttpURLConnection) urlConnection;
		} catch (IOException e) {
			System.out.println("Tried to create a URL object with an invalid URL\n" + e);
		}
		html = "";
		try {
			Scanner in = new Scanner(connection.getInputStream());
			while (in.hasNextLine()) {
				html += in.nextLine();
			}
			in.close();
		} catch (IOException e) {
			System.out.println("Input error when reading the page\n" + e);
		}
		System.out.println("-- Scrape Complete -- \n");
	}

	public void parseSite() {
		String regex = "<a href=\"\\S*\" target=\"_blank\">";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String result = m.group();
			System.out.println(result.substring(9, result.length() - 18));
		}
	}

	public void parseName() {
		String regex = "<h4>[\\s\\w]*</h4>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String result = m.group();
			System.out.println(result.substring(4, result.length() - 5));
		}
	}

	public void parseDescription() {
		String regex = "<p>^\\r*&hellip;</p>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String result = m.group();
			System.out.println(result.substring(3, result.length() - 4));
		}
	}
	
	public void parseActive() {
		String regex = "<li class=\"\\S{1,50}  \\S{1,50} \\S{1,50} \\D{1,50}\">";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String result = m.group();
			if (result.contains("active")) {
				System.out.println("active");
			} else {
				System.out.println("exited");
			}
		}
	}
	
	public void parseStage() {
		String regex = "<li class=\"\\S{1,50}  \\S{1,50} \\S{1,50} \\D{1,50}\">";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String result = m.group();
			if (result.contains("growth")) {
				System.out.println("growth");
			} else {
				System.out.println("early-stage");
			}
		}
	}
	
	public void parseIndustry() {
		String regex = "<li class=\"\\S{1,50}  \\S{1,50} \\S{1,50} \\D{1,50}\">";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String[] a = m.group().split(" ");
			System.out.println(a[5]);
		}
	}

	public static void main(String[] args) {
		System.out.println("-- Scraping Bain Capital Ventures -- \n");
		BainCapital m = new BainCapital();
		m.initialize();
		// m.parseSite();
		// m.parseName();
		// m.parseDescription();
		// m.parseActive();
		// m.parseStage();
		// m.parseIndustry();
	}

}