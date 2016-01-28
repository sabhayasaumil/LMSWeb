package com.gcit.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.lms.entity.Copies;

public class CopiesDAO extends AbstractDAO {

	public CopiesDAO(Connection con)
	{
		super(con);
	}
	
	public void create(Copies a) throws SQLException {
		save("insert into tbl_copies (bookId, branchId, noOfCopies) values (?, ? , ?)",
				new Object[] { a.getBook().getBookId(),
						a.getBranch().getBranchId(), a.getNoOfCopies() });
	}

	public void update(Copies a) throws SQLException {
		save("update tbl_copies set noOfCopies = ? where bookId = ? AND branchId = ?",
				new Object[] { a.getNoOfCopies(), a.getBook().getBookId(),
						a.getBranch().getBranchAddress() });
	}

	public void delete(Copies a) throws SQLException {

		save("delete from tbl_copies where bookId = ? AND branchId = ?",
				new Object[] { a.getBook().getBookId(),
						a.getBranch().getBranchAddress() });

	}

	public Copies readOne(int bookId, int branchId) throws SQLException {
		List<Copies> list = (List<Copies>) read(
				"select * from tbl_copies bookId = ? AND branchId = ?",
				new Object[] { bookId, branchId });
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

	public List<Copies> readAll() throws SQLException {
		return (List<Copies>) read("select * from tbl_copies", null);
	}

	public List<Copies> readByBook(int bookId) throws SQLException {
		return (List<Copies>) read("select * from tbl_copies where bookId = ?",
				new Object[] { bookId });
	}

	public List<Copies> readByBranch(int branchId) throws SQLException {
		return (List<Copies>) read(
				"select * from tbl_copies where branchId = ?",
				new Object[] { branchId });
	}

	@Override
	protected List<Copies> processResult(ResultSet rs) throws SQLException {
		List<Copies> aList = new ArrayList<Copies>();
		while (rs.next()) {
			Copies a = new Copies();
			a.setBook(new BookDAO(this.getConnection()).readOne(rs.getInt("bookId")));
			a.setBranch(new BranchDAO(this.getConnection()).readOne(rs.getInt("branchId")));
			a.setNoOfCopies(rs.getInt("noOfCopies"));
			aList.add(a);
		}

		return aList;
	}
}
