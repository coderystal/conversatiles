package questiondecktesting;

public class ConversatilesTestInfo {
	static ConversatilesStepDefinitions conversatileSteps;
	public static void main(String[] args) throws InterruptedException {
		conversatileSteps = new ConversatilesStepDefinitions();
		conversatileSteps.launchConversatiles(conversatileSteps.getLocalSiteUrl());
		

		conversatileSteps.customizeDeckByAdvanced("likes");
		testInfoTwice();
		
		conversatileSteps.customizeDeckByPreset("dislikes", true);
		testInfoTwice();

		conversatileSteps.customizeDeckByAdvanced("goals");
		testInfoTwice();		
		
		conversatileSteps.customizeDeckByPreset("experience", true);
		testInfoTwice();

		conversatileSteps.customizeDeckByAdvanced("fantasy");
		testInfoTwice();	
		
		conversatileSteps.customizeDeckByPreset("identity", true);
		testInfoTwice();
		
		conversatileSteps.customizeDeckByAdvanced("tendencies");
		testInfoTwice();	
		
		conversatileSteps.customizeDeckByPreset("people", true);
		testInfoTwice();
		
		conversatileSteps.customizeDeckByPreset("react!", true);
		testInfoTwice();
		
		conversatileSteps.customizeDeckByAdvanced("worldview");
		testInfoTwice();		
		
		conversatileSteps.customizeDeckByPreset("complete", true);
		
		for (int i = 0; i < 20; i++) {
			System.out.println(i);
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