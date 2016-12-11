package com.neu.getmyflight.response;

import java.util.List;

public class Response {
	private String speech;
	private String displayText;
	private String source;
	private TravelOutput data;
	private List<String> contextOut;
	public TravelOutput getData() {
		return data;
	}
	public void setData(TravelOutput data) {
		this.data = data;
	}
	public String getSpeech() {
		return speech;
	}
	public void setSpeech(String speech) {
		this.speech = speech;
	}
	public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<String> getContextOut() {
		return contextOut;
	}
	public void setContextOut(List<String> contextOut) {
		this.contextOut = contextOut;
	}
	
	
}
