package tk.azertyfun.dasmtc.assembler.arguments;

import tk.azertyfun.dasmtc.assembler.exceptions.ParsingException;
import tk.azertyfun.dasmtc.assembler.sourceManagement.Line;

import java.util.LinkedList;

public class ArgumentLiteral extends Argument {

	public ArgumentLiteral(String argument, Line line, LinkedList<String> labels) throws ParsingException {

		boolean isLabel = false;
		for(String label : labels) {
			if(label.equalsIgnoreCase(argument)) {
				isLabel = true;
				break;
			}
		}

		if(isLabel)
			value = new Value(argument);
		else
			value = new Value(Parser.parseNumber(argument, line));
	}

	public ArgumentLiteral(char character) {
		value = new Value(character);
	}

	@Override
	public char getValue() {
		if(value.literal == 0xFFFF || value.literal <= 0x1E) { //We can optimize to a literal
			return (char) (value.literal + 0x21);
		} else {
			return 0x1F;
		}
	}

	@Override
	public boolean hasNextWordValue() {
		if(value.literal == 0xFFFF || value.literal <= 0x1E) { //We can optimize to a literal
			return false;
		} else {
			return true;
		}
	}

	@Override
	public char getNextWordValue() {
		return value.literal;
	}
}
