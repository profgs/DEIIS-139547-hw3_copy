/**
 * @author Gabriela Gongora
 *
 */

 
package edu.cmu.deiis.annotators;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.types.*;

import java.util.*;



public class EvaluationAnnotator extends JCasAnnotator_ImplBase{
	String annotatorID="EvaluationAnnotator";
	
	
	//Receiving a specific N to calculate precision@N
	Integer Nval=null; 
	
	@Override
	//Get context in order to extract parameters for N 
	public void initialize(UimaContext aContext) throws ResourceInitializationException{
		super.initialize(aContext);
		
		//get Config. parameter value N.
		this.Nval = (Integer)aContext.getConfigParameterValue("N");
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		AnswerScore anw_scr = null;
		AnnotationIndex<Annotation> annot_ans_scr = aJCas.getAnnotationIndex(AnswerScore.type);
		//AnnotationIndex<Annotation> annot_ans = aJCas.getAnnotationIndex(Answer.type);
		//FSIterator<Annotation> iterate_ans = annot_ans.iterator();
		FSIterator<Annotation> iterate_ans_scr = annot_ans_scr.iterator();
		ArrayList<AnswerScore> top_N = new ArrayList<AnswerScore>();
		int numCorrect=0;
		int j=0;
		Precision prec =null;
		

        System.out.println("=================== EVALUATION ANNOTATOR ======================");
		while(iterate_ans_scr.hasNext()) {
			anw_scr = (AnswerScore)iterate_ans_scr.next();
			top_N.add(anw_scr);
			//System.out.println(top_N.get(j).toString());
			j++;
		}
		
		if(j<this.Nval){
			System.out.println("Cannot calculate presicion@N, insufficient answers!");
		}else{

			//Sort according to score in AnswerScore
			Collections.sort(top_N, new Comparator<AnswerScore>() {
				@Override
				public int compare(AnswerScore ans1, AnswerScore ans2) {
					return ans1.getScore() < ans2.getScore() ? -1 : 1;
				}
			});
			//Check how many answers are correct
			for(int i=0; i<this.Nval; i++){
				if(top_N.get(i).getAnswer().getIsCorrect())
					numCorrect++;
			}
			
			//Added for hw3!
			prec = new Precision(aJCas);
    		prec.addToIndexes();
    		prec.setCasProcessorId(annotatorID);	
    		prec.setConfidence(1.0);	
    		
			System.out.printf("Precision@N (N=%d): %.4f\n", this.Nval, (double)numCorrect/this.Nval);
		}

        System.out.println("============================= THE END ======================");
	}
}

