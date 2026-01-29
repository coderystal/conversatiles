package questiondecktesting;

public class QuestionDeckFindCardNumber {
	
	public static void main(String[] args) throws InterruptedException {
		ConversatilesStepDefinitions conversatileSteps = new ConversatilesStepDefinitions();
		
		//store complete deck *********************************************************************************DECK COMPLETE
		conversatileSteps.clickNumCardsAndStoreDeck();
		
		//find card # *****************************************************************************************FIND CARD #
        conversatileSteps.testFind();

		conversatileSteps.close();
	}
}
