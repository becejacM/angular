package org.levi9.ServiceTests;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.levi9.exception.MyNotFoundException;
import org.levi9.model.Comment;
import org.levi9.model.Component;
import org.levi9.model.ComponentType;
import org.levi9.model.Manufacturer;
import org.levi9.model.User;
import org.levi9.repository.CommentRepository;
import org.levi9.repository.UserRepository;
import org.levi9.service.impl.CommentServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CommentServiceTest {

	@Mock
	private CommentRepository commentRepository;

	@Mock
	private UserRepository UserRepository;
	
	@Mock
	private org.levi9.repository.ComponentRepository ComponentRepository;

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@InjectMocks
	private CommentServiceImpl commentService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreatePass() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1"), new Manufacturer(),
				new Integer(32), new Double(33),true);
		componentSet.add(component);
		final User User = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		
		final Comment comment = new Comment("milana", User);
		when(UserRepository.findOne(any(Long.class))).thenReturn(User);
		when(commentRepository.save(any(Comment.class))).thenReturn(comment);
		when(ComponentRepository.findOne(any(Long.class))).thenReturn(component);
		when(ComponentRepository.save(any(Component.class))).thenReturn(component);

		final Comment savedComment = commentService.create(comment, 1L, 1L);

		verify(UserRepository).findOne(any(Long.class));
		verify(commentRepository).save(any(Comment.class));
		verify(ComponentRepository).findOne(any(Long.class));
		assertTrue(savedComment.getText().equals("milana"));
	}
	
	@Test
	public void testCreateUserNull() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName",true, "1"), new Manufacturer(),
				new Integer(32), new Double(33),true);
		componentSet.add(component);
		final User User = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		
		final Comment comment = new Comment("milana", User);
		when(UserRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		commentService.create(comment,null, 1L);
	}
	
	@Test
	public void testCreateCommentNull() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1"), new Manufacturer(),
				new Integer(32), new Double(33),true);
		componentSet.add(component);
		final User User = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		
		final Comment comment = new Comment("milana", User);
		when(UserRepository.findOne(any(Long.class))).thenReturn(User);
		when(commentRepository.save(any(Comment.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		commentService.create(comment,1L, null);
	}
	
	@Test
	public void testCreateComponentNull() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1"), new Manufacturer(),
				new Integer(32), new Double(33),true);
		componentSet.add(component);
		final User User = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		
		final Comment comment = new Comment("milana", User);
		when(UserRepository.findOne(any(Long.class))).thenReturn(User);
		when(commentRepository.save(any(Comment.class))).thenReturn(comment);
		when(ComponentRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		commentService.create(comment, 1L, 1L);
	}
	
	
	
	@Test
	public void testGetAllByComponentPass() {
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "n"), new Manufacturer(),
				new Integer(32), new Double(33),true);
		final User User = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		final Comment comment = new Comment("milana", User);
		component.getComments().add(comment);
		when(ComponentRepository.findOne(any(Long.class))).thenReturn(component);
		Collection<Comment> comments = commentService.getAllByComponent(component.getId());
		
		verify(ComponentRepository).findOne(any(Long.class));
		assertTrue(comments.size()>0);
	}
	
	@Test
	public void testGetAllByComponentNull() {
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "n"), new Manufacturer(),
				new Integer(32), new Double(33),true);
		final User User = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		final Comment comment = new Comment("milana", User);
		component.getComments().add(comment);
		when(ComponentRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		commentService.getAllByComponent(component.getId());
	}
	
	@Test
	public void testDeletePass() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1"), new Manufacturer(),
				new Integer(32), new Double(33),true);
		componentSet.add(component);
		final User User = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		
		final Comment comment = new Comment("milana", User);
		when(commentRepository.findOne(any(Long.class))).thenReturn(comment);
		when(ComponentRepository.findOne(any(Long.class))).thenReturn(component);
		when(ComponentRepository.save(any(Component.class))).thenReturn(component);
		commentService.delete(comment.getId(), component.getId());

		verify(commentRepository).findOne(any(Long.class));
		verify(ComponentRepository).findOne(any(Long.class));
	}
	
	@Test
	public void testDeleteCommentNull() {
		when(commentRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		commentService.delete(1l, 2l);
	}
	
	@Test
	public void testDeleteComponentNull() {
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1"), new Manufacturer(),
				new Integer(32), new Double(33),true);
		final User User = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		
		final Comment comment = new Comment("milana", User);
		when(commentRepository.findOne(any(Long.class))).thenReturn(comment);
		when(ComponentRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		commentService.delete(comment.getId(), component.getId());

	}
}
