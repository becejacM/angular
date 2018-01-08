package org.levi9.DTO;

public class ChangePasswordDto {
	private String oldPassword;
	private String newPassword;
	private String repeatedNewPassword;

	@Override
	public String toString() {
		return "ChangePasswordDto [oldPassword=" + oldPassword + ", newPassword=" + newPassword
				+ ", repeatedNewPassword=" + repeatedNewPassword + ", userId=" + userId + "]";
	}

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatedNewPassword() {
		return repeatedNewPassword;
	}

	public void setRepeatedNewPassword(String repeatedNewPassword) {
		this.repeatedNewPassword = repeatedNewPassword;
	}

	public ChangePasswordDto(String oldpassword, String newPassword, String repeatedNewPassword, Long userId) {
		super();
		this.userId = userId;
		this.oldPassword = oldpassword;
		this.newPassword = newPassword;
		this.repeatedNewPassword = repeatedNewPassword;
	}

	public ChangePasswordDto() {
		super();
	}

}
