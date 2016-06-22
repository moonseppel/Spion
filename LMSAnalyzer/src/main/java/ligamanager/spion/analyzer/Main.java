package ligamanager.spion.analyzer;

import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;

import ligamanager.spion.analyzer.hibernate.DbInitializer;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;

import javax.persistence.criteria.CriteriaBuilder;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		int result = innerMain(args);
		System.exit(result);
	}

	public static int innerMain(String[] args) {
		try {


			for (int i = 0; i < args.length; i++) {
				String arg = args[i];
				LOGGER.debug("Input parameter no. " + i + ": " + arg);
			}

			if(parametersAreIncomplete(args)) {
				System.out.println("Number Parameters must be 1 oder 4.");
				printUsage();
				return -1;
			}

			if(isInitDB(args)) {
				DbInitializer.initialize();
				return -1;
			}

			Optional<GameReaderParameters> params = readParameters(args);
			if(!params.isPresent()) {
				System.out.println("Unable to parse parameters.");
				printUsage();
				return -1;
			}

			GameReader reader = new GameReader(params.get());
			return reader.readGames();

		} catch (Throwable t) {
			LOGGER.error("Application execution failed. Message: " + t.getMessage(), t);
			return -1;
		}
	}

	private static Optional<GameReaderParameters> readParameters(String[] args) {
		GameReaderParameters ret = new GameReaderParameters();
		int firstSeason = -1;
		int lastSeason = -1;
		int firstGame = -1;
		int lastGame = -1;

		Optional<Range<Integer>> seasonRange = parseValueOrRangeParameter(args[0]);
		if(seasonRange.isPresent()) {
			ret.firstSeason = seasonRange.get().getMinimum();
			ret.lastSeason = seasonRange.get().getMaximum();
		} else {
			return Optional.empty();
		}

		Optional<Range<Integer>> gameRange = parseValueOrRangeParameter(args[1]);
		if(gameRange.isPresent()) {
			ret.firstGameNumber = gameRange.get().getMinimum();
			ret.lastGameNumber = gameRange.get().getMaximum();
		} else {
			return Optional.empty();
		}

		ret.user = args[2];
		ret.password = args[3];

		return Optional.of(ret);
	}

	public static Optional<Range<Integer>> parseValueOrRangeParameter(String valueOrRange) {
		boolean conversionSuccess = false;
		Integer startOfRange = -1;
		Integer endOfRange = -1;

		try {
			if (valueOrRange.contains("-")) {

				StringTokenizer tokenizer = new StringTokenizer(valueOrRange, "-");

				if(tokenizer.hasMoreTokens()) {
					startOfRange = Integer.parseInt(tokenizer.nextToken());

					if(tokenizer.hasMoreTokens()) {
						endOfRange = Integer.parseInt(tokenizer.nextToken());

						conversionSuccess = true;
						LOGGER.trace("Parsed range.");
					}
				}

			} else {

				startOfRange = Integer.parseInt(valueOrRange);
				endOfRange = startOfRange;

				conversionSuccess = true;
				LOGGER.trace("Parsed value.");
			}

		} catch (NumberFormatException ex) {
			LOGGER.error("Can't parse input parameter from \"" + valueOrRange + "\". Message: " + ex.getMessage());
			conversionSuccess = false;
		} catch (NoSuchElementException ex) {
			LOGGER.error("Can't parse input parameter from \"" + valueOrRange + "\". Message: " + ex.getMessage());
			conversionSuccess = false;
		}

		if(conversionSuccess) {
			Range<Integer> range = Range.between(startOfRange, endOfRange);
			return Optional.of(range);
		} else {
			return Optional.empty();
		}
	}

	private static void printUsage() {
		System.out.println();
		System.out.println();
		System.out.println("Syntax: java -jar lmsanalyzer.jar [seasons] [numberOfGames] [LM username] [LM password]");
		System.out.println("OR: java -jar lmsanalyzer.jar initdb");
		System.out.println("[seasons] maybe a range (\"10-12\") or a single season (\"1\").");
		System.out.println("[numberOfGames] is the number of games to retrieve for each season, always starting with game 1.");
		System.out.println("\"initdb\" initilizes a fresh db for running this application.");
		System.out.println("");
	}

	private static boolean parametersAreIncomplete(String[] args) {
		return !(args.length == 4 || args.length == 1);
	}

	private static boolean isInitDB(String[] args) {
		return args.length == 1 && args[0].equalsIgnoreCase("initDB");
	}

}
