package org.levi9.service.impl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.levi9.exception.MyNotFoundException;
import org.levi9.model.Comment;
import org.levi9.model.Component;
import org.levi9.model.User;
import org.levi9.repository.CommentRepository;
import org.levi9.repository.ComponentRepository;
import org.levi9.repository.UserRepository;
import org.levi9.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author m.becejac
 *
 */
@Service
public class CommentServiceImpl implements CommentService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ComponentRepository componentRepository;

	/**
	 * Function adds comment in database
	 * 
	 * @param -
	 *            comment which is going to be created
	 * @param idUser
	 *            - who created the comment
	 * @param -
	 *            idComp which is going to get a comment
	 * @return comment which is created
	 */
	@Override
	public Comment create(Comment comment, Long idUser, Long idComp) {
		try {
			User user = userRepository.findOne(idUser);
			if (user == null) {
				throw new MyNotFoundException("User not found");
			}
			comment.setUserId(user);
			Comment comm = commentRepository.save(comment);
			if (comm == null) {
				throw new MyNotFoundException("Comment not properly saved");
			}
			Component component = componentRepository.findOne(idComp);
			if (component == null) {
				throw new MyNotFoundException("Component not found");
			}
			component.getComments().add(comm);
			componentRepository.save(component);
			return comm;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Function gets all comments of one component
	 * 
	 * @param idComponent
	 *            - if of component, whose comments will be found
	 * @return created comment
	 */
	@Override
	public Collection<Comment> getAllByComponent(Long idComponent) {
		try {
			Component component = componentRepository.findOne(idComponent);
			if (component == null) {
				throw new MyNotFoundException("Component not found");
			}
			Collection<Comment> comments = component.getComments();
			return comments;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Function deletes comment, which id is send
	 * 
	 * @param id
	 *            - id component, whose comments will be found
	 * @return Collection of comments
	 */
	@Override
	@Transactional
	public Comment delete(Long id, Long compId) {
		try {
			Comment comment = commentRepository.findOne(id);
			if (comment == null) {
				throw new MyNotFoundException("Comment not found");
			}
			Component component = componentRepository.findOne(compId);
			if (component == null) {
				throw new MyNotFoundException("Component not found");
			}

			for (Comment c : component.getComments()) {
				if (c.getId() == comment.getId()) {
					component.getComments().remove(c);
					break;
				}
			}
			commentRepository.delete(comment);
			componentRepository.save(component);
			return comment;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

}
