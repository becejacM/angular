package org.levi9.service;

import java.util.Collection;

import org.levi9.model.Comment;

/**
 * @author m.becejac
 *
 */
public interface CommentService {

	Comment create(Comment comment, Long idUser, Long idComp);

	Collection<Comment> getAllByComponent(Long idComponent);

	Comment delete(Long id, Long compId);
}
