package com.gcit.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.lms.entity.Author;

public class AuthorDAO extends AbstractDAO {
	
	public AuthorDAO(Connection con)
	{
		super(con);
	}

	public void create(Author a) throws SQLException {
		save("insert into tbl_author (authorName) values (?)",
				new Object[] { a.getAuthorName() });
	}

	public void update(Author a) throws SQLException {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { a.getAuthorName(), a.getAuthorId() });
	}

	public void delete(Author a) throws SQLException {
		save("delete from tbl_author where authorId = ?",
				new Object[] { a.getAuthorId() });
	}

	public Author readOne(int authorId) throws SQLException {
		List<Author> list = (List<Author>) read("select * from tbl_author where authorId = ?",new Object[] { authorId });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<Author> readAll(int PageNo, int PageSize) throws SQLException {
		
		if(PageNo == 0)
			PageNo++;
		Integer PageOffset = (PageNo - 1) * PageSize;
		return (List<Author>) read("select * from tbl_author LIMIT ? OFFSET ?", new Object[]{PageSize, PageOffset});
	}
	public int readAllCount() throws SQLException {
		return  count("select count(*) as count from tbl_author", null);
	}

	public List<Author> readByName(String searchString, int PageNo, int PageSize) throws SQLException {
		String qString = "%" + searchString + "%";
		
		Integer PageOffset = (PageNo - 1) * PageSize;
		return (List<Author>) read("select * from tbl_author where authorName like ? LIMIT ? OFFSET ?",new Object[] { qString, PageSize, PageOffset });
	}
	
	public int readByNameCount(String searchString) throws SQLException {
		String qString = "%" + searchString + "%";
		return count("select count(*) as count from tbl_author where authorName like ?",new Object[] { qString});
	}

	@Override
	protected List<Author> processResult(ResultSet rs) throws SQLException {
		List<Author> aList = new ArrayList<Author>();
		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			a.setBooks(new BookAuthorDAO(this.getConnection()).readByAuthor(rs.getInt("authorId")));
			aList.add(a);
		}

		return aList;
	}
}
