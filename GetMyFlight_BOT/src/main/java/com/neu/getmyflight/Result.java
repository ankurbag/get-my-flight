package com.neu.getmyflight;

import com.neu.getmyflight.request.Metadata;

public class Result {
	/*"result": {
    "source": "agent",
    "resolvedQuery": "flight between 3rd march and 4th april",
    "action": "",
    "actionIncomplete": false,
    "parameters": "2017-03-03/2017-04-04",
    "contexts": [],
    "metadata": {
      "intentId": "bf483453-49bb-4e9f-a74d-f1234237bd11",
      "webhookUsed": "false",
      "webhookForSlotFillingUsed": "false",
      "intentName": "flightDates"
    }*/
	private String parameters;
	private String resolvedQuery;
	private Metadata metadata;
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getResolvedQuery() {
		return resolvedQuery;
	}
	public void setResolvedQuery(String resolvedQuery) {
		this.resolvedQuery = resolvedQuery;
	}
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	
	
}
