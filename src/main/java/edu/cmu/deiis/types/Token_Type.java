
/* First created by JCasGen Wed Sep 11 13:44:28 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;

import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Thu Mar 27 20:55:00 CST 2014
 * @generated */
public class Token_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Token_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Token_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Token(addr, Token_Type.this);
  			   Token_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Token(addr, Token_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = Token.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.deiis.types.Token");



  /** @generated */
  final Feature casFeat_token_type;
  /** @generated */
  final int     casFeatCode_token_type;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getToken_type(int addr) {
        if (featOkTst && casFeat_token_type == null)
      jcas.throwFeatMissing("token_type", "edu.cmu.deiis.types.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_token_type);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setToken_type(int addr, String v) {
        if (featOkTst && casFeat_token_type == null)
      jcas.throwFeatMissing("token_type", "edu.cmu.deiis.types.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_token_type, v);}
    
  
 
  /** @generated */
  final Feature casFeat_line_doc;
  /** @generated */
  final int     casFeatCode_line_doc;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getLine_doc(int addr) {
        if (featOkTst && casFeat_line_doc == null)
      jcas.throwFeatMissing("line_doc", "edu.cmu.deiis.types.Token");
    return ll_cas.ll_getIntValue(addr, casFeatCode_line_doc);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLine_doc(int addr, int v) {
        if (featOkTst && casFeat_line_doc == null)
      jcas.throwFeatMissing("line_doc", "edu.cmu.deiis.types.Token");
    ll_cas.ll_setIntValue(addr, casFeatCode_line_doc, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Token_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_token_type = jcas.getRequiredFeatureDE(casType, "token_type", "uima.cas.String", featOkTst);
    casFeatCode_token_type  = (null == casFeat_token_type) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_token_type).getCode();

 
    casFeat_line_doc = jcas.getRequiredFeatureDE(casType, "line_doc", "uima.cas.Integer", featOkTst);
    casFeatCode_line_doc  = (null == casFeat_line_doc) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_line_doc).getCode();

  }
}



    