package questiondecktesting;

import java.util.Random;

public class QuestionDeckFlipAndReset {
	public static void main(String[] args) {
		ConversatilesStepDefinitions conversatileSteps = new ConversatilesStepDefinitions();
	    
		for (int i = 0; i < conversatileSteps.getTotQs(); i++) {
			//click card or draw button *************************************************************************CARD AND DRAW NEW CARD
			conversatileSteps.clickCardOrDraw();

			//check m/n button counts
			conversatileSteps.validateNumViewedCards(i+1);
			conversatileSteps.validateNumTotalCards();
			
			//store q from card
			conversatileSteps.updateHistorySet();
			
			//check deck history *********************************************************************************DECK HISTORY
			conversatileSteps.clickNumViewedAndValidateHistory();
		}

		//check complete deck *********************************************************************************COMPLETE DECK
		conversatileSteps.validateHistoryAfterViewedAll_nums_and_strings();
		
		//check reset 10x *********************************************************************************RESET
		for (int resetNum = 0; resetNum < 10; resetNum++) {
	    	conversatileSteps.clickReset();
		     
			//flip random amount of cards
	    	int toFlip = (new Random()).nextInt(50);
			for (int flipped = 0; flipped < toFlip; flipped++) {
				//click card or draw button
				conversatileSteps.clickCardOrDraw();

				//check m/n button counts
				conversatileSteps.validateNumViewedCards(flipped+1);
				conversatileSteps.validateNumTotalCards();

				//store q from card
				conversatileSteps.updateHistorySet();
				
				//check deck history
				conversatileSteps.clickNumViewedAndValidateHistory();
			}
		}
		
		conversatileSteps.close();
	}
}
