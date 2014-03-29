package edu.cmu.deiis.cpe;

import org.apache.uima.UIMAFramework;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionProcessingEngine;
import org.apache.uima.collection.EntityProcessStatus;
import org.apache.uima.collection.StatusCallbackListener;
import org.apache.uima.collection.metadata.CpeDescription;
import org.apache.uima.util.XMLInputSource;

class CPE {
	public static void main(String[] args) throws Exception {
		
		//get CPE file descriptor
		CpeDescription cpeDesc = UIMAFramework.getXMLParser().
		parseCpeDescription(new XMLInputSource(
				"/Users/IBAGNOG/Documents/workspace/hw3-139547/src/main/resources/hw3-139547-cpe.xml"
		));
		
		//produce CPE
		CollectionProcessingEngine mCPE = UIMAFramework.produceCollectionProcessingEngine(cpeDesc);
		
		//Create and register a Status Callback Listener
		mCPE.addStatusCallbackListener(new StatusCallbackListener() {

			@Override
			public void initializationComplete() {
				// TODO Auto-generated method stub

			}

			@Override
			public void batchProcessComplete() {
				// TODO Auto-generated method stub

			}

			@Override
			public void collectionProcessComplete() {
				// TODO Auto-generated method stub

			}

			@Override
			public void paused() {
				// TODO Auto-generated method stub

			}

			@Override
			public void resumed() {
				// TODO Auto-generated method stub

			}

			@Override
			public void aborted() {
				// TODO Auto-generated method stub

			}

			@Override
			public void entityProcessComplete(CAS aCas,
					EntityProcessStatus aStatus) {
				// TODO Auto-generated method stub

			}

		});
		//Start Processing
		mCPE.process();
	}

}