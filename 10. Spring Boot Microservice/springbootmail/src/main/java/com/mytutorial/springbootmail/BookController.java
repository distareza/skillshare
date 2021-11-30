package com.mytutorial.springbootmail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mytutorial.springbootmail.model.Book;

@RestController
public class BookController {

	private static Map<Integer, Book> bookstore = new HashMap<>();
	static {
		Book book1 = new Book(123, "Alice in Wonderland", "Lewis Carrol");
		bookstore.put(book1.getId(), book1);
		Book book2 = new Book(456, "Anna Karenina", "Leo tolstoy");
		bookstore.put(book2.getId(), book2);
	}
	
	@RequestMapping(value = "/")
	public String welcome() {
		return "<h1>Welcome to bookstore!</h1>";
	}
	
	@RequestMapping(value = "/book")
	public ResponseEntity<Object> getBook(@RequestParam("bookId") Integer bookId) {
		System.out.println("Retrieving book....");
		return new ResponseEntity<>(bookstore.get(bookId), HttpStatus.OK);
	}
	
}
