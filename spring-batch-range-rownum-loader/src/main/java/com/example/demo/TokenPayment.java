package com.example.demo;

public class TokenPayment {
	
	private long tokenRefId;
	private long tokenNumber;
	private String tokenExpMonthDate;
	private long shardSvcId;
	private String tokenSeqNumber;
	private String tokenPanId;
	private String tokenId;
	private short tokenReuseCounter;
	
	public TokenPayment() {
	}

	public TokenPayment(long tokenRefId, long tokenNumber, String tokenExpMonthDate, long shardSvcId,
			String tokenSeqNumber, String tokenPanId, String tokenId, short tokenReuseCounter) {
		
		this.tokenRefId = tokenRefId;
		this.tokenNumber = tokenNumber;
		this.tokenExpMonthDate = tokenExpMonthDate;
		this.shardSvcId = shardSvcId;
		this.tokenSeqNumber = tokenSeqNumber;
		this.tokenPanId = tokenPanId;
		this.tokenId = tokenId;
		this.tokenReuseCounter = tokenReuseCounter;
	}
	
	public long getTokenRefId() {
		return tokenRefId;
	}
	public void setTokenRefId(long tokenRefId) {
		this.tokenRefId = tokenRefId;
	}
	public long getTokenNumber() {
		return tokenNumber;
	}
	public void setTokenNumber(long tokenNumber) {
		this.tokenNumber = tokenNumber;
	}
	public String getTokenExpMonthDate() {
		return tokenExpMonthDate;
	}
	public void setTokenExpMonthDate(String tokenExpMonthDate) {
		this.tokenExpMonthDate = tokenExpMonthDate;
	}
	public long getShardSvcId() {
		return shardSvcId;
	}
	public void setShardSvcId(long shardSvcId) {
		this.shardSvcId = shardSvcId;
	}
	public String getTokenSeqNumber() {
		return tokenSeqNumber;
	}
	public void setTokenSeqNumber(String tokenSeqNumber) {
		this.tokenSeqNumber = tokenSeqNumber;
	}
	public String getTokenPanId() {
		return tokenPanId;
	}
	public void setTokenPanId(String tokenPanId) {
		this.tokenPanId = tokenPanId;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public short getTokenReuseCounter() {
		return tokenReuseCounter;
	}
	public void setTokenReuseCounter(short tokenReuseCounter) {
		this.tokenReuseCounter = tokenReuseCounter;
	}				

}
