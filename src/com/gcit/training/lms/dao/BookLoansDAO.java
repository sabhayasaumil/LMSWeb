package com.gcit.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.lms.entity.Loans;

public class BookLoansDAO extends AbstractDAO {

	public BookLoansDAO(Connection con)
	{
		super(con);
	}
	
	public void create(Loans a) throws SQLException {
		save("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) values (?,?,?,?,?)",
				new Object[] { a.getBook().getBookId(),
						a.getBranch().getBranchId(), a.getCard().getCardNo(),
						a.getDateOut(), a.getDueDate() });
	}

	public void update(Loans a) throws SQLException {
		save("update tbl_book_loan set dateIn = ? where bookId = ? AND branchId = ? AND cardNo = ? AND dateOut = ?",
				new Object[] { a.getDateIn(), a.getBook().getBookId(),
						a.getBranch().getBranchId(), a.getCard().getCardNo(),
						a.getDateOut() });
	}

	public void updateDueDate(Loans a) throws SQLException {
		save("update tbl_book_loan set dueDate = ? where bookId = ? AND branchId = ? AND cardNo = ? AND dateOut = ?",
				new Object[] { a.getDueDate(), a.getBook().getBookId(),
						a.getBranch().getBranchId(), a.getCard().getCardNo(),
						a.getDateOut() });
	}

	public void delete(Loans a) throws SQLException {
		save("delete from tbl_book_loans  where bookId = ? AND branchId = ? AND cardNo = ? AND dateOut = ?",
				new Object[] { a.getBook().getBookId(),
						a.getBranch().getBranchId(), a.getCard().getCardNo(),
						a.getDateOut() });

	}

	public void deleteByBookId(int bookId) throws SQLException {
		save("delete from tbl_book_loans where bookId = ?",
				new Object[] { bookId });
	}

	public void deleteByBranchId(int branchId) throws SQLException {
		save("delete from tbl_book_loans where branchId = ?",
				new Object[] { branchId });
	}

	public void deleteByCardNo(int cardNo) throws SQLException {
		save("delete from tbl_book_loans where cardNo = ?",
				new Object[] { cardNo });
	}

	public Loans readOne(int bookId, int branchId, int cardNo)
			throws SQLException {
		List<Loans> list = (List<Loans>) read(
				"select * from tbl_book_loans bookId = ? AND branchId = ? AND cardNo = ? SORT BY dateOut DESC",
				new Object[] { bookId, branchId, cardNo });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<Loans> readDue() throws SQLException {
		return (List<Loans>) read(
				"select * from tbl_book_loans where dueDate = cast(now() as date)",
				null);
	}

	public List<Loans> readAll() throws SQLException {
		return (List<Loans>) read("select * from tbl_book_loans", null);
	}

	public List<Loans> readByCardNo(int CardNo) throws SQLException {
		return (List<Loans>) read(
				"select * from tbl_book_loans where cardNo = ?",
				new Object[] { CardNo });
	}

	public List<Loans> readByBranchId(int branchId) throws SQLException {
		return (List<Loans>) read(
				"select * from tbl_book_loans where branchId = ?",
				new Object[] { branchId });
	}

	public List<Loans> readByBookId(int BookId) throws SQLException {
		return (List<Loans>) read(
				"select * from tbl_book_loans where bookId = ?",
				new Object[] { BookId });
	}

	public List<Loans> readByDateCut(Timestamp DateOut) throws SQLException {
		return (List<Loans>) read(
				"select * from tbl_book_loans where cast(dateOut as Date) = cast(? as Date)",
				new Object[] { DateOut });
	}

	public List<Loans> readByDueDate(Timestamp DueDate) throws SQLException {
		return (List<Loans>) read(
				"select * from tbl_book_loans where cast(DueDate as Date) = cast(? as Date)",
				new Object[] { DueDate });
	}

	public List<Loans> readByDateIn(Timestamp DateIn) throws SQLException {
		return (List<Loans>) read(
				"select * from tbl_book_loans where cast(dateIn as Date) = cast(? as Date)",
				new Object[] { DateIn });
	}

	@Override
	protected List<Loans> processResult(ResultSet rs) throws SQLException {
		List<Loans> aList = new ArrayList<Loans>();
		while (rs.next()) {
			Loans a = new Loans();
			a.setBook(new BookDAO(this.getConnection()).readOne(rs.getInt("bookId")));
			a.setCard(new BorrowerDAO(this.getConnection()).readOne(rs.getInt("cardNo")));
			a.setBranch(new BranchDAO(this.getConnection()).readOne(rs.getInt("branchId")));
			a.setDateOut(rs.getTimestamp("dateOut"));
			a.setDueDate(rs.getTimestamp("dueDate"));
			a.setDateIn(rs.getTimestamp("dateIn"));
			aList.add(a);
		}
		
		
		return aList;

	}
}
