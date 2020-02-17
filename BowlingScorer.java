public class BowlingScorer {
	public static void main(String[] args) {
		System.out.println("These are a bunch of test cases I thought were worth testing: ");

		int[] validThrows = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		testCalculationFinalScore("All 0s", 0, validThrows);

		validThrows = new int[] {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
		testCalculationFinalScore("All strikes", 300, validThrows);

		validThrows = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10};
		testCalculationFinalScore("10th frame strikes", 30, validThrows);

		validThrows = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 3, 5};
		testCalculationFinalScore("10th frame spare", 15, validThrows);

		validThrows = new int[] {7, 3, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		testCalculationFinalScore("First frame spare", 20, validThrows);

		validThrows = new int[] {10, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		testCalculationFinalScore("First frame strike add 2 additional throws", 30, validThrows);

		validThrows = new int[] {10, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		testCalculationFinalScore("First frame strike add first additional throws", 20, validThrows);

		validThrows = new int[] {10, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		testCalculationFinalScore("First frame strike add second additional throws", 20, validThrows);

		validThrows = new int[] {1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		testCalculationFinalScore("Open frame", 3, validThrows);

		validThrows = new int[] {1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		testCalculationFinalScore("Two open frames", 10, validThrows);
	}

	private static void testCalculationFinalScore(String scenarioName, int expectedScore, int[] scenario) {
		int actualScore = calculateFinalScore(scenario);
		String status = expectedScore == actualScore ? "PASS" : "FAIL";

		System.out.println(scenarioName + " - " + status);
		System.out.println("Expected Score: " + expectedScore);
		System.out.println("Actual Score: " + actualScore);
		System.out.println();
	}

	public static int calculateFinalScore(int[] validThrows) {
		final int CLOSED_FRAME = 10;
		final int FIRST_THROW = 1;
		final int SECOND_THROW = 2;
		final int PREVIOUS_THROW = 1;
		final int SPARE_TENTH_FRAME_BUFFER = 2;
		final int STRIKE_TENTH_FRAME_BUFFER = 3;

		boolean secondFrame = false;
		int score = 0;

		for (int i = 0; i < validThrows.length; i++) {
			score += validThrows[i];
			if (secondFrame) {
				// If a spare is thrown and it is not the 10th frame then apply spare scoring logic
				if (validThrows[i] + validThrows[i-PREVIOUS_THROW] == CLOSED_FRAME && i + SPARE_TENTH_FRAME_BUFFER < validThrows.length) {
					score += validThrows[i+FIRST_THROW];
				}
				secondFrame = false;
			}
			else {
				// If a strike is thrown and it is not the 10th frame then apply strike scoring logic
				if (validThrows[i] == CLOSED_FRAME && i + STRIKE_TENTH_FRAME_BUFFER < validThrows.length) {
					score += validThrows[i+FIRST_THROW] + validThrows[i+SECOND_THROW];
				}
				else {
					secondFrame = true;
				}
			}
		}
		return score;
	}
}