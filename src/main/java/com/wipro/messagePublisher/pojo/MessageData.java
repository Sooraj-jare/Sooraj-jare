package com.wipro.messagePublisher.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7900202169712368963L;
	public String deliveringAgent;
	public String seller;
	public String safekeepingAccount;
	public String placeOfSettlement;
	public String placeOfSafekeepingAccount;
	
	@Override
	public String toString() {
		return "MessageData [deliveringAgent=" + deliveringAgent + ", seller=" + seller + ", safekeepingAccount="
				+ safekeepingAccount + ", placeOfSettlement=" + placeOfSettlement + ", placeOfSafekeepingAccount="
				+ placeOfSafekeepingAccount + "]";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDeliveringAgent() {
		return deliveringAgent;
	}

	public String getSeller() {
		return seller;
	}

	public String getSafekeepingAccount() {
		return safekeepingAccount;
	}

	public String getPlaceOfSettlement() {
		return placeOfSettlement;
	}

	public String getPlaceOfSafekeepingAccount() {
		return placeOfSafekeepingAccount;
	}
	
	
}
