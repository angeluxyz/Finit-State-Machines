
public class TextAnalysis {

	public String state = "initial";

	public static void main(String ...args) {
		new TextAnalysis().analyzeText("Remove 		empty space from this text * + =");
	}

	public void analyzeText(String text) {
		int index = 0;
		String response = "";

		while(index < text.length()) {
			switch(state) {
			case "initial":
				// Text found
				if(!Character.isWhitespace(text.charAt(index))) {
					state = "collect";
				} else {
					// Ignore character
					index++;
				}
				break;
			case "collect":
				// Text not found
				if(Character.isWhitespace(text.charAt(index))) {
					state = "initial";
				} else {
					// Collect character
					response += text.charAt(index);
					index++;
				}
				break;
			}
		}
		
		// END: Print
		System.out.println(response);
	}
}
