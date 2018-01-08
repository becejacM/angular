package org.levi9.controller;

import java.util.Collection;
import javax.validation.Valid;
import org.levi9.DTO.CommentDTO;
import org.levi9.exception.MyNotFoundException;
import org.levi9.exception.MyValidationFormException;
import org.levi9.model.Comment;
import org.levi9.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * @author m.becejac
 *
 */
@RestController
@RequestMapping("/api/comments")
@CrossOrigin
@Api(value = "comment", description = "Comments of components in application")
public class CommentController {

	@Autowired
	private CommentService commentService;

	/**
	 * This method adds a new comment
	 * 
	 * @param commentDTO
	 *            - which should be added
	 * @return comment which is added
	 */
	@PostMapping()
	public ResponseEntity<Comment> createComment(@RequestBody @Valid CommentDTO commentDTO, BindingResult result) {
		if (result.hasErrors()) {
			throw new MyValidationFormException("Creating comment isn't possible parameters aren't valid");
		}
		Comment comment = new Comment();
		comment.setText(commentDTO.getCommentText());
		Comment created = commentService.create(comment, commentDTO.getIdUser(), commentDTO.getIdComponent());
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	/**
	 * Function deletes comment, which id is send
	 * 
	 * @param id
	 *            of comment which is going to be deleted
	 * @return comment comment
	 * @throws MyNotFoundException
	 *             when comment is not found
	 */
	@DeleteMapping
	private ResponseEntity<Comment> deleteCommentt(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "compId", required = true) Long compId) throws MyNotFoundException {
		Comment comment = commentService.delete(id, compId);
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}

	/**
	 * Function gets all comments of one component
	 * 
	 * @param id
	 *            component, whose comments will be found
	 * @return Collection of comments
	 */
	@GetMapping(value = "/get-all-by-component/{id:.+}")
	private ResponseEntity<Collection<Comment>> getAllByComponent(@PathVariable Long id) {
		return new ResponseEntity<>(commentService.getAllByComponent(id), HttpStatus.OK);
	}
}
