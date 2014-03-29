/**
 * @author Gabriela Gongora
 *
 */

 
package edu.cmu.deiis.annotators;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import edu.cmu.deiis.types.*;

import java.util.*;


public class AnswerScoringAnnotator extends JCasAnnotator_ImplBase{
	String annotatorID="AnswerScoringAnnotator";
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		AnswerScore answer_score;
		double score=0;
		AnnotationIndex<Annotation> annot_ngrams =aJCas.getAnnotationIndex(NGram.type);
		AnnotationIndex<Annotation> annot_ans = aJCas.getAnnotationIndex(Answer.type);
		FSIterator<Annotation> ngrams_iter =annot_ngrams.iterator();
		FSIterator<Annotation> ans_iter =annot_ans.iterator();
		NGram ngram;

		ArrayList<Integer> line_ngrams =new ArrayList<Integer>();
		ArrayList<Integer> count_ngrams =new ArrayList<Integer>();
		
		ArrayList<Annotation> all_ngrams =new ArrayList<Annotation>();
		ArrayList<Annotation> q_ngrams =new ArrayList<Annotation>();
		ArrayList<Annotation> eachA_ngrams =new ArrayList<Annotation>();
		ArrayList<Annotation> a_ngrams =new ArrayList<Annotation>();
		
		ArrayList<Answer> answers =new ArrayList<Answer>();
		
		        
        System.out.println("=================== ANSWER SCORING ======================");
        
        
        //get all ngrams
        while(ngrams_iter.hasNext()){
        	all_ngrams.add(ngrams_iter.next());
        }
        //get all answers
        while(ans_iter.hasNext()){
			//answer_a = (Answer)ans_iter.next();
			answers.add((Answer)ans_iter.next());
        }

      
        //separate NGrams into Question and Answer types
        for(int i=0;i<all_ngrams.size();i++){
        	ngram= (NGram)all_ngrams.get(i);
        		
        	if(ngram.getElementType().equals("Q")){
        		q_ngrams.add(all_ngrams.get(i));
        	}
        	else if(ngram.getElementType().equals("A")){
        		a_ngrams.add(all_ngrams.get(i));
        		line_ngrams.add(ngram.getLine_doc());
        	}
        }
        
        int count_numNgrams=0;
        int counter=1;
        int countA=0;
        //System.out.println("SIZE LINE GRAMS: "+line_ngrams.size());
    	
        for(int i=0;i<line_ngrams.size();i++){
        	if(line_ngrams.get(i)==counter){
        		count_numNgrams++;
        		eachA_ngrams.add(a_ngrams.get(i));//added
        		//countN++;
        		
        	}else{
        		count_ngrams.add(count_numNgrams);
        		//System.out.println("SIZE: "+eachA_ngrams.size());
        		score=calculateScore(eachA_ngrams,q_ngrams);
        		System.out.println("Answer SCORE: "+score);
        		answer_score = new AnswerScore(aJCas);
        		answer_score.setBegin(answers.get(countA).getBegin());
        		answer_score.setEnd(answers.get(countA).getEnd());
        		answer_score.setScore(score);
        		answer_score.setAnswer(answers.get(countA));
        		answer_score.addToIndexes();
        		answer_score.setCasProcessorId(annotatorID);	
        		answer_score.setConfidence(1.0);	
        		eachA_ngrams.clear();
        		
        		count_numNgrams=1;
        		counter++;
        		countA++;
        	}
        	if(i==(line_ngrams.size()-1)){
        		count_ngrams.add(count_numNgrams);
        		//System.out.println("SIZE: "+eachA_ngrams.size());
        		score=calculateScore(eachA_ngrams,q_ngrams);
        		System.out.println("Answer SCORE: "+score);
        		answer_score = new AnswerScore(aJCas);
        		answer_score.setBegin(answers.get(countA).getBegin());
        		answer_score.setEnd(answers.get(countA).getEnd());
        		answer_score.setScore(score);
        		answer_score.setAnswer(answers.get(countA));
        		answer_score.addToIndexes();
        		answer_score.setCasProcessorId(annotatorID);	
        		answer_score.setConfidence(1.0);	
        		
        		eachA_ngrams.clear();
        		countA++;
        		
        	}	
        }
       // System.out.println("==========================================================");
        System.out.println("");
	}

	//Calculate score according to Jaccard Index
	public double calculateScore(ArrayList<Annotation> answer_ngrams, ArrayList<Annotation> question_ngrams){
		HashSet<NGram> intersected_ngrams = new HashSet<NGram>();
		HashSet<NGram> unique_ngrams = new HashSet<NGram>();
		HashSet<NGram> dummy = new HashSet<NGram>();
        
        
		for(int i=0;i<answer_ngrams.size();i++){        
        	intersected_ngrams.add((NGram)answer_ngrams.get(i));
        }
		for(int i=0;i<question_ngrams.size();i++){        
	        unique_ngrams.add((NGram)question_ngrams.get(i));
		}
		dummy.addAll(intersected_ngrams);
		dummy.addAll(unique_ngrams);
		unique_ngrams.removeAll(intersected_ngrams);
		
		//Union 
		int union = dummy.size();
		int intersection = intersected_ngrams.size();
		
		return (double)intersection/union;
		
	}
}
