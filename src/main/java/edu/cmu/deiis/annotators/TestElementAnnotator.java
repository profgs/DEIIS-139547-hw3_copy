/**
 * @author Gabriela Gongora
 *
 */

package edu.cmu.deiis.annotators;


import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.*;

public class TestElementAnnotator extends JCasAnnotator_ImplBase  {
	String annotatorID="TestElementAnnotator";

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		//get incoming text
		String inText = aJCas.getDocumentText();
		//split lines
		String[] lines = inText.split("\n");
		
		Question quest;
		Answer anw;
		int l_offset=0;
		

        System.out.println("=================== TEST ELEMENT ANNOTATOR ======================");
		//Do line by line parsing, separating Question from Answer.
		for(int numL=0; numL < lines.length; numL++){
			String eachline = lines[numL];
			if(eachline.startsWith("Q ")){
				quest=new Question(aJCas);
				quest.setBegin(l_offset+2);
				quest.setEnd(l_offset + eachline.length());
				quest.setConfidence(1.0);
				quest.setCasProcessorId(this.annotatorID);
				quest.addToIndexes();
			}
			if(eachline.startsWith("A ")){
				anw = new Answer(aJCas);
				anw.setBegin(l_offset+4);
				anw.setEnd(l_offset + eachline.length());
				
				String initIsCorrect = eachline.substring(2, 3);
				
				anw.setIsCorrect(initIsCorrect.equals("1"));
				
				anw.setCasProcessorId(this.annotatorID);

				anw.setConfidence(1.0);
				anw.addToIndexes();
			}
			l_offset+= eachline.length() + 1;

	    }
		//System.out.println("=======================================================");
        System.out.println("");

		
	}

}
