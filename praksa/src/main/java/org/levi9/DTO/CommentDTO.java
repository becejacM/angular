package org.levi9.DTO;

import javax.validation.constraints.NotNull;

public class CommentDTO {

	@NotNull
	private String commentText;

	@NotNull
	private Long idUser;

	@NotNull
	private Long idComponent;

	public CommentDTO() {
	}

	public CommentDTO(String commentText, Long idUser, Long idComponent) {
		super();
		this.commentText = commentText;
		this.idUser = idUser;
		this.idComponent = idComponent;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdComponent() {
		return idComponent;
	}

	public void setIdComponent(Long idComponent) {
		this.idComponent = idComponent;
	}

}
