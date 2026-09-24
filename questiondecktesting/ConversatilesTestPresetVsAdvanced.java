package questiondecktesting;

public class ConversatilesTestPresetVsAdvanced {
	public static void main(String[] args) throws InterruptedException {
		ConversatilesStepDefinitions conversatileSteps = new ConversatilesStepDefinitions();
		conversatileSteps.launchConversatiles(conversatileSteps.getLocalSiteUrl());
		
		conversatileSteps.comparePresetVsAdvanced();
		

		conversatileSteps.close();
	}
}
