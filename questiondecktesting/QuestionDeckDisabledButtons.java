package questiondecktesting;
import java.util.Random;

public class QuestionDeckDisabledButtons {
	public static void main(String[] args) {
		ConversatilesStepDefinitions conversatileSteps = new ConversatilesStepDefinitions();
		
		
		//check reset 10x *********************************************************************************RESET
		for (int resetNum = 0; resetNum < 10; resetNum++) {
			conversatileSteps.validateButtonEnabledValuesAfterReset();
			//flip random amount of cards
	    	int toFlip = resetNum == 0 ? conversatileSteps.getTotQs() : (new Random()).nextInt(50);
			for (int flipped = 0; flipped < toFlip; flipped++) {
				conversatileSteps.clickCardOrDraw();

				conversatileSteps.validateButtonEnabledValuesAfterFlip(flipped);
			}

			conversatileSteps.clickReset();
		}
		
		conversatileSteps.close();
	}
}