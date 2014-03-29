/**
 * 
 */
package edu.cmu.deiis.cpe;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;


import edu.cmu.deiis.types.*;

/**
 * @author Gabriela Gongora
 *
 */
public class CASconsumer extends CasConsumer_ImplBase {

	/* (non-Javadoc)
	 * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
	 */
	@Override
	public void processCas(CAS aCAS) throws ResourceProcessException {
		JCas aJCas = null;
		AnswerScore anwSc = null;
		try{
			aJCas = aCAS.getJCas();
		}catch (CASException e){
			System.out.println("Oops! Cannot get JCas in CAS Consumer");
			throw new ResourceProcessException(e);
		}
		//lets get the precision
		AnnotationIndex<Annotation> anwScAnnot = aJCas.getAnnotationIndex(AnswerScore.type);
		FSIterator<Annotation> anwScIt = anwScAnnot.iterator();
		
		while(anwScIt.hasNext()){
			anwSc =(AnswerScore)anwScIt.next();
			System.out.println("Answer Score - CAS Consumer: "+ anwSc.getScore());
		}
		

	}

}
