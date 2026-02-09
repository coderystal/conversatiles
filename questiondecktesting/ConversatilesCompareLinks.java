package questiondecktesting;

public class ConversatilesCompareLinks {
	public static void main(String[] args) throws InterruptedException {
		compareLinks("likes", false);
		compareLinks("dislikes", true);
		compareLinks("experience", false);
		compareLinks("goals", true);
		
		compareLinks("fantasy", false);
		compareLinks("identity", true);
		compareLinks("people", false);
		compareLinks("tendencies", true);
		
		compareLinks("worldview", false);
		compareLinks("react!", true);
		compareLinks("complete", false);
	}
	
	private static void compareLinks(String deck, boolean checkLocalDirectLinks) {
		System.out.println("---------------" + deck + " launching urls for " + (checkLocalDirectLinks ? "local" : "published") + 
				" and clicking through " + (checkLocalDirectLinks ? "published" : "local"));

		ConversatilesStepDefinitions conversatileSteps = new ConversatilesStepDefinitions();
		
		String directUrl = (checkLocalDirectLinks) ? conversatileSteps.getLocalSiteUrl() : conversatileSteps.getPublishedSiteUrl();
		String historyUrl = (checkLocalDirectLinks) ? conversatileSteps.getPublishedSiteUrl() : conversatileSteps.getLocalSiteUrl();
	    
		//open first link to all likes questions and store in history set
		int qnum = 1;
		do {
			conversatileSteps.launchConversatiles(conversatileSteps.getUrlToQuestion(directUrl, deck, qnum));
			conversatileSteps.updateHistorySet();
			qnum++;
		} while (qnum <= conversatileSteps.getTotDeckQs());
		conversatileSteps.printHistorySet();
		
		//open other link, select likes, click through all, and compare history table to history set (saved in java from first)
		conversatileSteps.launchConversatiles(historyUrl);
		if (checkLocalDirectLinks)
			conversatileSteps.selectDeck(deck);
		else
			conversatileSteps.customizeDeckByPreset(deck);
		qnum = 0;
		do {
			conversatileSteps.clickCardOrDraw();
			qnum++;
		} while (qnum <= conversatileSteps.getTotDeckQs());
		conversatileSteps.clickNumViewedAndValidateHistoryWithoutNumbers();
		conversatileSteps.close();
	}
}