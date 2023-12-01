package nl.avasten;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/day1")
public class Day1 {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String calculateSum() {

		int sum = 0;
		try {
			Scanner scanner = new Scanner(new File("src/main/resources/puzzel1.txt"));

			while (scanner.hasNextLine()) {
				String currentLine = scanner.nextLine();

				String replaced = replaceNumbers(currentLine);

				int curSum = findReplacedSum(replaced);
				System.out.println("Line: " + currentLine + " ,replaced: " + replaced + " , sum: " + curSum);
				sum += curSum;
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return String.valueOf(sum);
	}

	public static int findReplacedSum(String currentLine) {

		String first = extractFirstNumber(currentLine);
		String reversed = reverseUsingStringBuilder(currentLine);
		String last = extractFirstNumber(reversed);
		String localsum = first + last;
		return Integer.parseInt(localsum);
	}

	public static String extractFirstNumber(String input) {
		Pattern pattern = Pattern.compile("\\d");
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			return matcher.group();
		} else {
			return null;
		}
	}

	public static String reverseUsingStringBuilder(String original) {
		return new StringBuilder(original).reverse().toString();
	}

	public static String replaceNumbers(String input) {
		input = input.replaceAll("one", "o1e");
		input = input.replaceAll("two", "t2o");
		input = input.replaceAll("three", "t3e");
		input = input.replaceAll("four", "f4r");
		input = input.replaceAll("five", "f5e");
		input = input.replaceAll("six", "s6x");
		input = input.replaceAll("seven", "s7n");
		input = input.replaceAll("eight", "e8t");
		input = input.replaceAll("nine", "n9e");
		return input;
	}
}
