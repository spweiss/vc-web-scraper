import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CBInsightsAI100 {

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
			url = new URL("https://www.cbinsights.com/research/artificial-intelligence-top-startups/");
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
		String regex = "<td><a href=\"[^\"]*\">[^<^>]*</a></td>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String[] a = m.group().split(">");
			System.out.println(a[1].substring(9, a[1].length() - 1));
		}
	}

	public void parseName() {
		String regex = "<td><a href=\"[^\"]*\">[^<^>]*</a></td>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String[] a = m.group().split(">");
			System.out.println(a[2].substring(0, a[2].length() - 3));
		}
	}

	public void parseFunding() {
		String regex = "<td>[^<^>^,^A-Z]*</td>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String result = m.group();
			System.out.println(result.substring(4, result.length() - 5));
		}
	}

	public static void main(String[] args) {
		System.out.println("-- Scraping CB Insights AI 100 -- \n");
		CBInsightsAI100 m = new CBInsightsAI100();
		m.initialize();
		// m.parseSite();
		// m.parseName();
		m.parseFunding();
	}

}