package com.neu.getmyflight.response;

public class TravelOutput {
	private String carrier;
	private String dateOfTravel;
	private String dateOfPriceFall;
	private String actualPrice;
	private String predictedPrice;
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getDateOfTravel() {
		return dateOfTravel;
	}
	public void setDateOfTravel(String dateOfTravel) {
		this.dateOfTravel = dateOfTravel;
	}
	public String getDateOfPriceFall() {
		return dateOfPriceFall;
	}
	public void setDateOfPriceFall(String dateOfPriceFall) {
		this.dateOfPriceFall = dateOfPriceFall;
	}
	public String getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(String actualPrice) {
		this.actualPrice = actualPrice;
	}
	public String getPredictedPrice() {
		return predictedPrice;
	}
	public void setPredictedPrice(String predictedPrice) {
		this.predictedPrice = predictedPrice;
	}
	
	
}
