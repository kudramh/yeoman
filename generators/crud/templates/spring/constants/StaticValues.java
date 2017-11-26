package com.citigroup.nga.crud.moneymovement.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:bootstrap.yml")
@ConfigurationProperties(prefix = "moneymovement")
@Component
public class StaticValues {
	  private String debit;
	  private String credit;
	  private String cancelDebit;
	  private String cancelCredit;
	  
	/**
	 * @return the debit
	 */
	public String getDebit() {
		return debit;
	}
	/**
	 * @param debit the debit to set
	 */
	public void setDebit(String debit) {
		this.debit = debit;
	}
	/**
	 * @return the credit
	 */
	public String getCredit() {
		return credit;
	}
	/**
	 * @param credit the credit to set
	 */
	public void setCredit(String credit) {
		this.credit = credit;
	}
	/**
	 * @return the cancelDebit
	 */
	public String getCancelDebit() {
		return cancelDebit;
	}
	/**
	 * @param cancelDebit the cancelDebit to set
	 */
	public void setCancelDebit(String cancelDebit) {
		this.cancelDebit = cancelDebit;
	}
	/**
	 * @return the cancelCredit
	 */
	public String getCancelCredit() {
		return cancelCredit;
	}
	/**
	 * @param cancelCredit the cancelCredit to set
	 */
	public void setCancelCredit(String cancelCredit) {
		this.cancelCredit = cancelCredit;
	}
	  
	  
}
