package com.example.e4.rcp.todo.service.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;

import com.example.e4.rcp.todo.events.MyEventContants;
import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class MyTodoServiceImpl implements ITodoService {
	
	@Inject
	private IEventBroker broker;

	private static int current = 1;
	private List<Todo> todos;

	public MyTodoServiceImpl() {
		todos = createInitialModel();
	}

	@Override
	public Todo getTodo(long id) {
		Todo todo = findById(id);
		if (todo != null) {
			return todo.copy();
		}
		return null;
	}

	@Override
	public synchronized boolean saveTodo(Todo newTodo) {
		boolean created = false;
		Todo updateTodo = findById(newTodo.getId());
		if (updateTodo == null) {
			created = true;
			updateTodo = new Todo(current++);
			todos.add(updateTodo);
		}
		updateTodo.setSummary(newTodo.getSummary());
		updateTodo.setDescription(newTodo.getDescription());
		updateTodo.setDone(newTodo.isDone());
		updateTodo.setDueDate(newTodo.getDueDate());
		
		if(created){
			broker.post(MyEventContants.TOPIC_TODO_UPDATE, updateTodo);
		} 
		else {
			broker.post(MyEventContants.TOPIC_TODO_NEW, updateTodo);
		}
		return true;
	}

	@Override
	public boolean deleteTodo(long id) {
		Todo deleteTodo = findById(id);
		if (deleteTodo != null) {
			todos.remove(deleteTodo);
			return true;
		}
		return false;
	}

	@Override
	public List<Todo> getTodos() {
		/*try{
			TimeUnit.SECONDS.sleep(1);
		} catch(InterruptedException iEx){
			iEx.printStackTrace();
		}*/
		List<Todo> list = new ArrayList<Todo>();
		for (Todo todo : todos) {
			list.add(todo.copy());
		}
		return list;
	}

	private List<Todo> createInitialModel() {
		List<Todo> list = new ArrayList<Todo>();
		list.add(createTodo("Application model", "Flexible and extensible"));
		list.add(createTodo("DI", "@Inject as programming mode"));
		list.add(createTodo("OSGi", "Services"));
		list.add(createTodo("SWT", "Widgets"));
		list.add(createTodo("JFace", "Especially Viewers!"));
		list.add(createTodo("CSS Styling", "Style your application"));
		list.add(createTodo("Eclipse services", "Selection, model, Part"));
		list.add(createTodo("Renderer", "Different UI toolkit"));
		list.add(createTodo("Compatibility Layer", "Run Eclipse 3.x"));

		return list;

	}

	private Todo createTodo(String summary, String description) {
		return new Todo(current++, summary, description, false, new Date());
	}

	private Todo findById(long id) {
		for (Todo todo : todos) {
			if (id == todo.getId()) {
				return todo;
			}
		}
		return null;
	}

}
