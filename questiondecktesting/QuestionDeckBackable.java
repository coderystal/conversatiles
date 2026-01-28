package questiondecktesting;

import java.util.Random;

public class QuestionDeckBackable {
	public static void main(String[] args) {
		ConversatilesStepDefinitions conversatileSteps = new ConversatilesStepDefinitions();		

		//check back 10x *********************************************************************************BACK
		int totFlipped = 0;
		int backable = 0;
		for (int backRounds = 0; backRounds < 10; backRounds++) {			
			//flip random amount of cards
	    	int toFlip = (new Random()).nextInt(50);
	    	System.out.println("flipping " + toFlip);
			for (int flipped = 0; flipped < toFlip; flipped++) {
				conversatileSteps.clickCardOrDraw();
				totFlipped++;
				backable = totFlipped == 1 ? 0 : backable+1;
				conversatileSteps.validateBackButtonEnabledStatus(backable != 0);
			}
			

	    	int toBack = (new Random()).nextInt(totFlipped + 1);
	    	System.out.println("backing " + toBack);
			for (int backed = 0; backed < toBack; backed++) {
				//click back
				conversatileSteps.clickBack();
				backable--;
				conversatileSteps.validateBackButtonEnabledStatus(backable != 0);
				if (backable == 0) {
			    	System.out.println("actually backed " + (backed+1));
					conversatileSteps.validateBackButtonEnabledStatus(false);
					break;
				}
			}
		}

    	System.out.println("backing remaining " + backable);
		for (int backed = 0; backed < backable; backed++) {
			//click back
			conversatileSteps.validateBackButtonEnabledStatus(true);
			conversatileSteps.clickBack();
		}
		conversatileSteps.validateBackButtonEnabledStatus(false);
		
		conversatileSteps.close();
	}
}
