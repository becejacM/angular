package org.levi9.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "verification_token")
public class VerificationToken {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "verification_token_id")
	private Long id;

	@Column(name = "verification_token_string")
	private String token;

	@OneToOne
	@JoinColumn(name = "id")
	private User user;

	@Temporal(TemporalType.DATE)
	@Column(name = "verification_token_expiry_date")
	private Date expiryDate;

	@Column(name = "verification_token_purpose")
	private String purpose;

	public VerificationToken(User user, Integer minutes, String purpose) {
		this.expiryDate = calculateExpiryDate(minutes);
		this.user = user;
		this.token = UUID.randomUUID().toString();
		this.purpose = purpose;
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	public Boolean isOwner(Long userId) {
		return user.getId().longValue() == userId.longValue();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof VerificationToken))
			return false;

		VerificationToken vt = (VerificationToken) o;

		if (user != null ? !user.equals(vt.user) : vt.user != null)
			return false;
		if (token != null ? !token.equals(vt.token) : vt.token != null)
			return false;
		if (purpose != null ? !purpose.equals(vt.purpose) : vt.purpose != null)
			return false;

		return true;
	}

	public VerificationToken() {
		super();
	}

	@Override
	public int hashCode() {
		int result = 0;
		result = 31 * result + (user != null ? user.hashCode() : 0);
		result = 31 * result + (token != null ? token.hashCode() : 0);
		result = 31 * result + (purpose != null ? purpose.hashCode() : 0);
		return result;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "VerificationToken [id=" + id + ", token=" + token + ", user=" + user + ", expiryDate=" + expiryDate
				+ ", purpose=" + purpose + "]";
	}

	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

}
