package bh.app.chronomicon.model.entities;

import bh.app.chronomicon.model.enums.Rank;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_core_user_information")
public class CoreUserInformationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String fullName;
	@Enumerated(EnumType.STRING)
	private Rank rank;
	private String service_name;
	@Column(unique = true)
	private String emailAddress;
	@Column(unique = true)
	private String saram;
	private String phoneNumber;
	
	public CoreUserInformationEntity(String fullName, Rank rank, String service_name, String emailAddress, String saram, String phoneNumber) {
		this.fullName = fullName;
		this.rank = rank;
		this.service_name = service_name;
		this.emailAddress = emailAddress;
		this.saram = saram;
		this.phoneNumber = phoneNumber;
	}
	
	public CoreUserInformationEntity(String id, String fullName, Rank rank, String service_name, String emailAddress, String saram, String phoneNumber) {
		this.id = id;
		this.fullName = fullName;
		this.rank = rank;
		this.service_name = service_name;
		this.emailAddress = emailAddress;
		this.saram = saram;
		this.phoneNumber = phoneNumber;
	}
	
	public CoreUserInformationEntity() {
	}
	
	public String getId() {
		return id;
	}
	
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	public String getService_name() {
		return service_name;
	}
	
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getSaram() {
		return saram;
	}
	
	public void setSaram(String saram) {
		this.saram = saram;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
