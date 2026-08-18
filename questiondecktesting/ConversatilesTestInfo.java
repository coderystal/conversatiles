package questiondecktesting;

public class ConversatilesTestInfo {
	static ConversatilesStepDefinitions conversatileSteps;
	public static void main(String[] args) throws InterruptedException {
		conversatileSteps = new ConversatilesStepDefinitions();
		conversatileSteps.launchConversatiles(conversatileSteps.getLocalSiteUrl());
		

		conversatileSteps.customizeDeckByAdvanced("likes");
		testInfoTwice();
		
		conversatileSteps.customizeDeckByPreset("dislikes");
		testInfoTwice();

		conversatileSteps.customizeDeckByAdvanced("goals");
		testInfoTwice();		
		
		conversatileSteps.customizeDeckByPreset("experience");
		testInfoTwice();

		conversatileSteps.customizeDeckByAdvanced("fantasy");
		testInfoTwice();	
		
		conversatileSteps.customizeDeckByPreset("identity");
		testInfoTwice();
		
		conversatileSteps.customizeDeckByAdvanced("tendencies");
		testInfoTwice();	
		
		conversatileSteps.customizeDeckByPreset("people");
		testInfoTwice();
		
		conversatileSteps.customizeDeckByPreset("react!");
		testInfoTwice();
		
		conversatileSteps.customizeDeckByAdvanced("worldview");
		testInfoTwice();		
		
		conversatileSteps.customizeDeckByPreset("complete");
		
		for (int i = 0; i < 20; i++) {
			conversatileSteps.selectRandomAdvancedDeck();
			testInfoTwice();
		}

		conversatileSteps.close();
	}
	
	private static void testInfoTwice() {
		conversatileSteps.clickCardOrDraw();
		conversatileSteps.clickInfoCompareQuestionTextAndResume();
		conversatileSteps.clickCardOrDraw();
		conversatileSteps.clickInfoCompareQuestionTextAndResume();
	}
}