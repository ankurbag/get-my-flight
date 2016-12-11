package com.neu.getmyflight.request;

import java.io.Serializable;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neu.getmyflight.Flight;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelDateRequest  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2003939474974837256L;
	
	private HashMap<String, String> opaqueData;
	
	public TravelDateRequest(){
		
	}
	private TravelDateResult result;

	public HashMap<String, String> getOpaqueData() {
		return opaqueData;
	}
	public void setOpaqueData(HashMap<String, String> opaqueData) {
		this.opaqueData = opaqueData;
	}
	public TravelDateResult getResult() {
		return result;
	}
	public void setResult(TravelDateResult result) {
		this.result = result;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	/*{
		  "id": "7ca774c6-55ca-4c0e-987f-122a1ddd3d60",
		  "timestamp": "2016-12-09T04:27:16.312Z",
		  "result": {
		    "source": "agent",
		    "resolvedQuery": "tickets from boston to california",
		    "action": "",
		    "actionIncomplete": false,
		    "parameters": {
		      "destination": "California",
		      "source": "Boston"
		    },
		    "contexts": [],
		    "metadata": {
		      "intentId": "545e1d10-7f34-43d7-a3c8-f82ef0392445",
		      "webhookUsed": "false",
		      "webhookForSlotFillingUsed": "false",
		      "intentName": "sourceDestination"
		    },
		    "fulfillment": {
		      "speech": "",
		      "messages": [
		        {
		          "type": 0,
		          "speech": ""
		        }
		      ]
		    },
		    "score": 0.93
		  },
		  "status": {
		    "code": 200,
		    "errorType": "success"
		  },
		  "sessionId": "0e4d873e-7537-44fe-8aa0-0db634bcc74b"
		}*/
	
}
