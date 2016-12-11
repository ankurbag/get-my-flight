package com.neu.getmyflight.request;

public class Metadata {
	private String intentName;
	private String intendId;
	public String getIntentName() {
		return intentName;
	}
	public void setIntentName(String intentName) {
		this.intentName = intentName;
	}
	public String getIntendId() {
		return intendId;
	}
	public void setIntendId(String intendId) {
		this.intendId = intendId;
	}
	
	/*"metadata": {
    "intentId": "bf483453-49bb-4e9f-a74d-f1234237bd11",
    "webhookUsed": "false",
    "webhookForSlotFillingUsed": "false",
    "intentName": "flightDates"
  }*/
}
