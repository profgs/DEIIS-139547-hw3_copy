/**
 * @author Gabriela Gongora
 *
 */

package edu.cmu.deiis.annotators;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;

import edu.cmu.deiis.types.*;

import java.util.*;

public class NGramAnnotator extends JCasAnnotator_ImplBase{
	String annotatorID="NGramAnnotator";
	//For future application might want to turn this N into a parameter.
	//Integer[] possibleNs={1,2,3};
	
	
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		//Annotation's index from JCas pipeline 
		AnnotationIndex<Annotation> an_toks = aJCas.getAnnotationIndex(Token.type);
		//Iterators for annotations
		FSIterator<Annotation> iter_toks = an_toks.iterator();
		ArrayList<Annotation> tokens = new ArrayList<Annotation>();
		FSArray tok_elems;
		NGram ngram;
		Token tok;
		int beg_a=0, end_a=0;
		String type_;
		 System.out.println("=================== NGRAM ANNOTATOR ======================");
			
				
		while(iter_toks.hasNext()){
			tokens.add(iter_toks.next());
		}
		
		
		//N=1
		for(int i=0; i<tokens.size(); i++) {
			tok_elems = new FSArray(aJCas, 1);
			tok_elems.set(0, tokens.get(i));
			tok=(Token)tokens.get(i);
			type_=tok.getToken_type();
			beg_a=tok.getBegin();
			end_a=tok.getEnd();
			ngram = buildNGram(tok_elems,type_, aJCas, 1,beg_a,end_a,tok.getLine_doc());
			ngram.addToIndexes();
		}
		
		//N=2
		int L=0;
		int cur_i=0;
		int checker;
		int prevChecker;
		for(int i=0; i<tokens.size(); i++) {
		
			if((cur_i+1)<tokens.size()) {
				
				tok=(Token)tokens.get(cur_i);
				prevChecker=tok.getLine_doc();
				cur_i++;
				tok=(Token)tokens.get(cur_i);
				checker=tok.getLine_doc();
					
				if(prevChecker==checker){
					tok_elems= new FSArray(aJCas, 2);
					tok_elems.set(0, tokens.get(cur_i-1));
					tok_elems.set(1, tokens.get(cur_i));
					tok=(Token)tokens.get(cur_i);
					type_=tok.getToken_type();
					beg_a=tokens.get(cur_i-1).getBegin();
					end_a=tokens.get(cur_i).getEnd();
					ngram = buildNGram(tok_elems,type_, aJCas, 2,beg_a,end_a,tok.getLine_doc());
					ngram.addToIndexes();
				}else{
					L=L+1;
				}
			}
		}
		
		//N=3
		L=0;
		cur_i=1;
		
		for(int i=0; i<tokens.size(); i++) {
				
					if((cur_i+1)<tokens.size()) {
						
						tok=(Token)tokens.get(cur_i-1);
						prevChecker=tok.getLine_doc();
						cur_i++;
						//cur_i++;
						tok=(Token)tokens.get(cur_i);
						checker=tok.getLine_doc();
							
						if(prevChecker==checker){
							tok_elems= new FSArray(aJCas, 3);

							tok_elems.set(0, tokens.get(cur_i-2));
							tok_elems.set(0, tokens.get(cur_i-1));
							tok_elems.set(1, tokens.get(cur_i));
							tok=(Token)tokens.get(cur_i);
							type_=tok.getToken_type();
							beg_a=tokens.get(cur_i-2).getBegin();
							end_a=tokens.get(cur_i).getEnd();
							ngram = buildNGram(tok_elems,type_, aJCas, 3,beg_a,end_a,tok.getLine_doc());
							ngram.addToIndexes();
						}else{
							L=L+1;
						}
					}
				}
		System.out.println("");

	}

	public NGram buildNGram(FSArray elems, String type_grams, JCas aJCas, int size_N, int beg_a, int end_a,int line_doc) {
		NGram ngram = new NGram(aJCas);
		ngram.setElements(elems);
		ngram.setElementType(type_grams);
		ngram.setBegin(beg_a);
		ngram.setEnd(end_a);
		ngram.setLine_doc(line_doc);
		ngram.setCasProcessorId(this.annotatorID);
		ngram.setConfidence(1.0);
		
		return ngram;
	}
}

