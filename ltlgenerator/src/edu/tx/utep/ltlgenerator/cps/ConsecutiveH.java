package edu.tx.utep.ltlgenerator.cps;

import java.util.ArrayList;
import java.util.List;

public class ConsecutiveH extends CompositeProposition {

	// private static String cpFormula = "(p1 ^ !p2 ^ !p3 ^ !p4 ^ X(p2 ^ !p3 ^ !p4 ^ X(p3 ^ !p4 ^ Xp4)))";

	@Override
	public String generateLTLString(String letter, int count) {
		StringBuilder formula = new StringBuilder();
		StringBuilder intermediateString = new StringBuilder();

		for (int i = count; i > 0; i--) {
			if (i == count) {
				formula.append(AND).append(NEXT).append(letter).append(i);
			} else {
				intermediateString.setLength(0);
				intermediateString.append(OPEN_P).append(letter).append(i).append(AND).append(generateProposition(CPT_NOT_AND, letter, i + 1, count));
				formula.insert(0, intermediateString).append(CLOSE_P);

				if (i > 1) {
					formula.insert(0, AND + NEXT);
				}
			}
		}

		return formula.toString();
	}


	@Override
	public List<String> generateLTLArray(String letter, int count) {
		List<String> output = new ArrayList<String>();
		String closingParentheses = "";

		for (int n = 1; n < count; n++) {
			output.add(OPEN_P + generateProposition(CPT_NOT_AND, letter, n, count, false));
			output.add(AND);
			output.add(NEXT);

			if (n + 1 == count) {
				output.add(OPEN_P + letter + ++n);
			    closingParentheses += CLOSE_P;
				n--;
			}
		}

		for (int n = 1; n < count; n++) {
			closingParentheses += CLOSE_P;
		}

		output.add(closingParentheses);

		return output;
	}
}
