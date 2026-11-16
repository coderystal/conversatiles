package questiondecktesting;

public class ConversatilesTestShare {
	static ConversatilesStepDefinitions conversatileSteps;
	public static void main(String[] args) throws Exception {
		
		//don't think I can interact with the navigator.share popup
		//i think copy to clipboard knows that I'm automated and won't let me copy??? at least not consistently
		
		//first need to comment out navigator.share block
		
		conversatileSteps = new ConversatilesStepDefinitions();
		conversatileSteps.launchConversatiles(conversatileSteps.getLocalSiteUrl());
		

		conversatileSteps.customizeDeckByAdvanced("likes");
		testShare();
		
		conversatileSteps.customizeDeckByPreset("dislikes", true);
		testShare();

		conversatileSteps.customizeDeckByAdvanced("goals");
		testShare();		
		
		conversatileSteps.customizeDeckByPreset("experience", true);
		testShare();

		conversatileSteps.customizeDeckByAdvanced("fantasy");
		testShare();	
		
		conversatileSteps.customizeDeckByPreset("identity", true);
		testShare();
		
		conversatileSteps.customizeDeckByAdvanced("tendencies");
		testShare();	
		
		conversatileSteps.customizeDeckByPreset("people", true);
		testShare();
		
		conversatileSteps.customizeDeckByPreset("react!", true);
		testShare();
		
		conversatileSteps.customizeDeckByAdvanced("worldview");
		testShare();		
		
		conversatileSteps.customizeDeckByPreset("complete", true);
		

		conversatileSteps.close();
		conversatileSteps = new ConversatilesStepDefinitions();
		conversatileSteps.launchConversatiles(conversatileSteps.getLocalSiteUrl());
		
		for (int i = 0; i < 20; i++) {
			System.out.println(i);
			conversatileSteps.selectRandomAdvancedDeck();
			testShare();
		}

		conversatileSteps.close();
	}
	
	private static void testShare() throws Exception {
		conversatileSteps.clickCardOrDraw();
		//actually just checks text, copying link is complicated
		conversatileSteps.clickShareCompareCopyAndCopiedLinkAndComeBack();
	}
}