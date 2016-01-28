package com.gcit.training.lms.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.lms.entity.Book;

public class BookDAO extends AbstractDAO{

	public BookDAO(Connection con)
	{
		super(con);
	}
public void create(Book a) throws SQLException {
		
		save("insert into tbl_book (title, pubId) values (?,?) ",new Object[]{a.getTitle(),a.getPublisher().getPublisherId() });

	}

	public void update(Book a) throws SQLException {

		save("update tbl_book set title = ? where bookId = ?",new Object[]{a.getTitle(),a.getBookId()});
	}

	public void delete(Book a) throws SQLException {

		save("delete from tbl_book where bookId = ?",new Object[]{a.getBookId()});

	}

	public Book readOne(int bookId) throws SQLException {

		List<Book> list = (List<Book>) read("select * from tbl_book where bookId = ?",new Object[] { bookId });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

	public List<Book> readAll() throws SQLException {

		return (List<Book>) read("select * from tbl_book", null);
	}
	
	public List<Book> readByTitle(String searchString) throws SQLException {

		String qString = "%" + searchString + "%";
		return (List<Book>)read("select * from tbl_book where title like ?", new Object[]{qString});
		
	}

	@Override
	protected List<Book> processResult(ResultSet rs) throws SQLException {
		List<Book> aList = new ArrayList<Book>();
		while (rs.next()) {
			Book a = new Book();
			a.setBookId(rs.getInt("bookId"));
			a.setTitle(rs.getString("title"));
			a.setPublisher(new PublisherDAO(this.getConnection()).readOne(rs.getInt("pubId")));
			//a.setAuthors(new BookAuthorDAO().readByBook(rs.getInt("bookId")));
			aList.add(a);
		}

		return aList;
	}
}
