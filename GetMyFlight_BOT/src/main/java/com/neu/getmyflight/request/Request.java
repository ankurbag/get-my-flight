package com.neu.getmyflight.request;

import java.io.Serializable;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neu.getmyflight.Flight;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2003939474974837256L;
	
	private HashMap<String, String> opaqueData;
	
	public Request(){
		
	}
	private Result result;

	public HashMap<String, String> getOpaqueData() {
		return opaqueData;
	}
	public void setOpaqueData(HashMap<String, String> opaqueData) {
		this.opaqueData = opaqueData;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
