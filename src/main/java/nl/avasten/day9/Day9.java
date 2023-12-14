package nl.avasten.day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/day9")
public class Day9 {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String calculateSteps() {

		long sum = 0;
		List<List<Integer>> sequencelist = mapInput();

		for (List<Integer> l : sequencelist) {
			System.out.println("Original list: " + l);
			long nextval = l.getLast() + calcAdditional(calcDif(l));
			sum += nextval;
		}

		return "Sequences: " + sum;
	}

	public static long calcAdditional(List<List<Integer>> input) {
		System.out.println("Input: " + input);
		long lastStep = input.getLast().getLast();
		for (int i = input.size() - 2; i >= 0; i--) {
			System.out.println("Next val: " + (input.get(i).getLast() + lastStep));
			lastStep = input.get(i).getLast() + lastStep;
		}
		return lastStep;
	}

	public static List<List<Integer>> calcDif(List<Integer> input) {

		List<List<Integer>> diffList = new ArrayList<>();

		List<Integer> diff = new ArrayList<>();
		if (input.size() <= 1) {
			System.out.println("TOO SMALL");
		} else {
			for (int i = 1; i < input.size(); i++) {
				int cur = input.get(i);
				int prev = input.get(i - 1);
				diff.add(cur - prev);
			}
		}

		if (!isAllZeros(diff)) {
			diffList.add(diff);
			diffList.addAll(calcDif(diff));
		}

		return diffList;
	}

	public static boolean isAllZeros(List<Integer> list) {
		for (int value : list) {
			if (value != 0) {
				return false; // If any element is not zero, return false
			}
		}
		return true; // All elements are zero
	}

	public static List<List<Integer>> mapInput() {
		List<List<Integer>> sequences = new ArrayList<>();

		try {
			Scanner scanner = new Scanner(new File("src/main/resources/puzzle9.txt"));

			while (scanner.hasNextLine()) {
				String currentLine = scanner.nextLine();
				String[] s = currentLine.split(" ");
				sequences.add(Arrays.stream(s).map(Integer::valueOf).toList());
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return sequences;

	}
}
