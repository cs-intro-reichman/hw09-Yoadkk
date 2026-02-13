import java.util.HashMap;
import java.util.Random;

public class LanguageModel {

    // The map of this model.
    // Maps windows to lists of charachter data objects.
    HashMap<String, List> CharDataMap;
    
    // The window length used in this model.
    int windowLength;
    
    // The random number generator used by this model. 
	private Random randomGenerator;

    /** Constructs a language model with the given window length and a given
     *  seed value. Generating texts from this model multiple times with the 
     *  same seed value will produce the same random texts. Good for debugging. */
    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        randomGenerator = new Random(seed);
        CharDataMap = new HashMap<String, List>();
    }

    /** Constructs a language model with the given window length.
     * Generating texts from this model multiple times will produce
     * different random texts. Good for production. */
    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        randomGenerator = new Random();
        CharDataMap = new HashMap<String, List>();
    }

    /** Builds a language model from the text in the given file (the corpus). */
	public void train(String fileName) {



        In in = new In(fileName);
        String fullString = in.readAll();

        
        
        for (int i = 0; i + windowLength < fullString.length(); i++) {
            String window = fullString.substring(i, i + windowLength);
            char next = fullString.charAt(i + windowLength);

            List trainList = CharDataMap.get(window);
            if (trainList == null) {
                trainList = new List();
                CharDataMap.put(window, trainList);
            }

            trainList.update(next);
        }

        for (String key : CharDataMap.keySet()) {
            calculateProbabilities(CharDataMap.get(key));
        }

        


	

}




    // Computes and sets the probabilities (p and cp fields) of all the
	// characters in the given list. */
	void calculateProbabilities(List probs) {				
		// I have the list size - with each iteration i have to divide number of appearences from num of size
        int index = 0;
        double cp = 0;
        while (index != probs.getSize()){
                
                double p = (double)probs.get(index).count;
                probs.get(index).p = p/probs.instanceCount();

                cp += p;
                probs.get(index).cp = cp/probs.instanceCount();

                index++;
                probs.listIterator(index);

        }

        

	}

    // Returns a random character from the given probabilities list.
	char getRandomChar(List probs) {
        int index = 0;
		double r = randomGenerator.nextDouble();
        //if empty list
        if(probs == null || probs.getSize() == 0){
            return '2';
        }
        //if works
        while (index != probs.getSize()){
            if (r <= probs.get(index).cp){
                return probs.get(index).chr;
            }
            else {
                index++;
                probs.listIterator(index);
            }
        }
        // if fails
		return '1';
	}

    /**
	 * Generates a random text, based on the probabilities that were learned during training. 
	 * @param initialText - text to start with. If initialText's last substring of size numberOfLetters
	 * doesn't appear as a key in Map, we generate no text and return only the initial text. 
	 * @param numberOfLetters - the size of text to generate
	 * @return the generated text
	 */
	public String generate(String initialText, int textLength) {
        
        if (textLength <= initialText.length()) {
        return initialText.substring(0, textLength);
        }


        String genText = initialText;

        //cant create window
        if (windowLength > initialText.length()){
            return initialText;
        }

       
        while(textLength + initialText.length() > genText.length()){
            
            String window = genText.substring(genText.length() - windowLength);
            List probs = CharDataMap.get(window);
            if (probs==null){
                return genText;
            }
            genText += this.getRandomChar(probs);
            

        }
        
        return genText;
	}

    /** Returns a string representing the map of this language model. */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (String key : CharDataMap.keySet()) {
			List keyProbs = CharDataMap.get(key);
			str.append(key + " : " + keyProbs + "\n");
		}
		return str.toString();
	}

    public static void main(String[] args) {
        int windowLength = Integer.parseInt(args[0]);
        String initialText = args[1];
        int generatedTextLength = Integer.parseInt(args[2]);
        boolean randomGeneration = args[3].equals("random");
        String filename = args[4];

        //Create the languageModel
        LanguageModel lm;
        if (randomGeneration){
            lm = new LanguageModel(windowLength);

        }
        else {
            lm = new LanguageModel(windowLength, 20);
        }


        //Trains the model
        lm.train(filename);

        // Generates text and prints
        System.out.println(lm.generate(initialText, generatedTextLength));
        
    }
}
