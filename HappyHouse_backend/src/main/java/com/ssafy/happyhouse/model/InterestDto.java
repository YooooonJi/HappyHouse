package com.ssafy.happyhouse.model;

public class InterestDto {
	private int interestSeq;
	private String userId;
	private String interestName;
	private String interestAddress;
	private int interestFloor;
	private double interestArea;
	private String interestDeposit;
	private String interestMonthlyRent;;
	private String interestDealAmount;
	private int interestBuildYear;
//	private boolean isTrade;
	
	public int getInterestSeq() {
		return interestSeq;
	}
	public void setInterestSeq(int interestSeq) {
		this.interestSeq = interestSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getInterestName() {
		return interestName;
	}
	public void setInterestName(String interestName) {
		this.interestName = interestName;
	}
	public String getInterestAddress() {
		return interestAddress;
	}
	public void setInterestAddress(String interestAddress) {
		this.interestAddress = interestAddress;
	}
	public int getInterestFloor() {
		return interestFloor;
	}
	public void setInterestFloor(int interestFloor) {
		this.interestFloor = interestFloor;
	}
	public double getInterestArea() {
		return interestArea;
	}
	public void setInterestArea(double interestArea) {
		this.interestArea = interestArea;
	}
	public String getInterestDeposit() {
		return interestDeposit;
	}
	public void setInterestDeposit(String interestDeposit) {
		this.interestDeposit = interestDeposit;
	}
	public String getInterestMonthlyRent() {
		return interestMonthlyRent;
	}
	public void setInterestMonthlyRent(String interestMonthlyRent) {
		this.interestMonthlyRent = interestMonthlyRent;
	}
	public String getInterestDealAmount() {
		return interestDealAmount;
	}
	public void setInterestDealAmount(String interestDealAmount) {
		this.interestDealAmount = interestDealAmount;
	}
	public int getInterestBuildYear() {
		return interestBuildYear;
	}
	public void setInterestBuildYear(int interestBuildYear) {
		this.interestBuildYear = interestBuildYear;
	}
//	public boolean getIsTrade() {
//		return isTrade;
//	}
//	public void setIsTrade(boolean isTrade) {
//		this.isTrade = isTrade;
//	}
	@Override
	public String toString() {
		return "InterestDto [interestSeq=" + interestSeq + ", userId=" + userId + ", interestName=" + interestName
				+ ", interestAddress=" + interestAddress + ", interestFloor=" + interestFloor + ", interestArea="
				+ interestArea + ", interestDeposit=" + interestDeposit + ", interestMonthlyRent=" + interestMonthlyRent
				+ ", interestDealAmount=" + interestDealAmount + "]";
	}
	
	
}
