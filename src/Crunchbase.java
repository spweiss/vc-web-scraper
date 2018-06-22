import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crunchbase {

	private URL url;
	private HttpURLConnection connection;
	private String html = "";
	private ArrayList<String> list = new ArrayList<String>();

	/**
	 * Initializes the connection to the desired URL.
	 * 
	 * @throws IOException
	 *             When the desired URL is malformed or does not exist or when there
	 *             is an input error while reading the page.
	 */
	public void initialize() {
		try {
			File f = new File("/Users/spencerweiss/Documents/nets150workspace/vc-web-scraper/src/FirstRoundCompanies.txt");
			BufferedReader br = new BufferedReader(new FileReader(f));
			while (br.ready()) {
				String temp = br.readLine().toLowerCase();
				String[] a = temp.split("\\s");
				String result = a[0];
				for (int i = 1; i < a.length; i++) {
					result += "-" + a[i];
				}
				System.out.println(result);
				list.add(result);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("File not found or was not in the expected format\n" + e);
		}
		for (String name : list) {
			try {
				url = new URL("https://www.crunchbase.com/organization/" + name);
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
			parseFunding(html, name);
		}
		System.out.println("-- Scrape Complete -- \n");
	}

	public void parseName() {
		String regex = "<div class=\"flex cb-overflow-ellipsis identifier-label\">[^<^>^-]*</div>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String result = m.group();
			System.out.println(result.substring(57, result.length() - 7).trim());
		}
	}

	public void parseFunding(String html, String name) {
		String regex = "<a class=\"cb-link component--field-formatter field-type-money ng-star-inserted\" href=\"/search/funding_rounds/field/organizations/funding_total/" + name + "\">[^<^>]*</a>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String result = m.group();
			System.out.println(result.substring(70, result.length() - 5));
		}
	}

	public static void main(String[] args) {
		System.out.println("-- Scraping Crunchbase FirstRound Investments -- \n");
		Crunchbase m = new Crunchbase();
		m.initialize();
	}

}