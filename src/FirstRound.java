import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstRound {

	private URL url;
	private HttpURLConnection connection;
	private String html = "";

	/**
	 * Initializes the connection to the desired URL.
	 * 
	 * @throws IOException
	 *             When the desired URL is malformed or does not exist or when there
	 *             is an input error while reading the page.
	 */
	public void initialize() {
		try {
			url = new URL("http://firstround.com/companies/");
			URLConnection urlConnection = url.openConnection();
			connection = (HttpURLConnection) urlConnection;
		} catch (IOException e) {
			System.out.println("Tried to create a URL object with an invalid URL\n" + e);
		}
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
		String regex = "<a class=\"status-link\" href=\"\\S*\" target=\"_blank\"";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String result = m.group();
			System.out.println(result.substring(29, result.length() - 17));
		}
	}

	public void parseName() {
		String regex = "<p><strong>[^<^>]*</strong>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			m.find();
			m.find();
			String result = m.group();
			System.out.println(result.substring(11, result.length() - 9));
		}
	}

	public static void main(String[] args) {
		System.out.println("-- Scraping First Round Capital -- \n");
		FirstRound m = new FirstRound();
		m.initialize();
		// m.parseSite();
		m.parseName();
	}

}