package RERSlearner;

import com.google.common.collect.ImmutableSet;
import de.learnlib.api.SUL;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by rick on 17/03/2017.
 */
public class RERSExperiment {
    /**
     * Example of how to learn a Mealy machine model from one of the compliled RERS programs.
     * @param args
     * @throws IOException
     */
    public static void main(String [] args) throws IOException {
        int pno = Integer.parseInt(args[0]);
    	
    	// Load the System Under Learning (SUL)
        SUL<String,String> sul = new ProcessSUL(args[1]+"ltl17c/Problem"+pno+".out");
        
        // the input alphabet
        int iAlphSz[] = {5,10,15,10,15,15,10,15,20};
		String[] iAlph = new String[iAlphSz[pno-1]];
		for(int i = 0; i < iAlphSz[pno-1]; i++) iAlph[i] = (i+1) + "";
        
        //Collection<String> inputAlphabet = ImmutableSet.of("1","2","3","4","5");
        Collection<String> inputAlphabet = ImmutableSet.copyOf(iAlph);

        try {
            // runControlledExperiment for detailed statistics, runSimpleExperiment for just the result
        	BasicLearner.runControlledExperiment(args[1]+"opteclipse/Problem"+pno+"/", sul, BasicLearner.LearningMethod.TTT, BasicLearner.TestingMethod.RandomWalk, inputAlphabet);
            //BasicLearner.runControlledExperiment(sul, BasicLearner.LearningMethod.TTT, BasicLearner.TestingMethod.RandomWalk, inputAlphabet);
            //BasicLearner.runControlledExperiment(sul, BasicLearner.LearningMethod.LStar, BasicLearner.TestingMethod.WMethod, inputAlphabet);
        } finally {
            if (sul instanceof AutoCloseable) {
                try {
                    ((AutoCloseable) sul).close();
                } catch (Exception exception) {
                    // should not happen
                }
            }
        }
    }
}