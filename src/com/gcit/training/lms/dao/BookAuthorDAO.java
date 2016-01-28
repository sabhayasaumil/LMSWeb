package com.gcit.training.lms.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.lms.entity.Author;
import com.gcit.training.lms.entity.Book;

public class BookAuthorDAO extends AbstractDAO{
	
	public BookAuthorDAO(Connection con)
	{
		super(con);
	}
	
public void create(int bookId, int authorId) throws SQLException {
		
		save("insert into tbl_book_author (bookId, authorId) values (?,?) ",new Object[]{bookId, authorId});

	}

public void delete(int bookId, int authorId) throws SQLException {

		save("delete from tbl_book_author where bookId = ? AND authorId = ?",new Object[]{bookId, authorId});

	}

	public List<Author> readByBook(int bookId) throws SQLException {

		List<Integer> list = (List<Integer>) read("select authorId as data from tbl_book_authors where bookId = ?",new Object[] { bookId });
		if(list.size() == -1)
			return null;
		AuthorDAO author = new AuthorDAO(this.getConnection());
		List<Author> authors = new ArrayList<Author>();
			for(int i:list)
				authors.add(author.readOne(i));
			return authors;
	}
	
	public List<Book> readByAuthor(int authorId) throws SQLException {

		List<Integer> list = (List<Integer>) read("select bookId as data from tbl_book_authors where authorId = ?",new Object[] { authorId });
		if(list.size() == -1)
			return null;
		BookDAO book = new BookDAO(this.getConnection());
		List<Book> books = new ArrayList<Book>();
			for(int i:list)
				books.add(book.readOne(i));
			
		return books;

	}
	

	public List<Book> readAll() throws SQLException {

		return (List<Book>) read("select * from tbl_book", null);
	}
	
	public List<Book> readByTitle(String searchString) throws SQLException {

		String qString = "%" + searchString + "%";
		return (List<Book>)read("select * from tbl_book where title like ?", new Object[]{qString});
		
	}

	@Override
	protected List<Integer> processResult(ResultSet rs) throws SQLException {
		List<Integer> aList = new ArrayList<Integer>();
		while (rs.next()) {
			aList.add(rs.getInt("data"));
		}


		return aList;
	}
}
