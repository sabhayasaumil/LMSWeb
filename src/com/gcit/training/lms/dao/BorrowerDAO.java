package com.gcit.training.lms.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.lms.entity.Borrower;

public class BorrowerDAO extends AbstractDAO{

	public BorrowerDAO(Connection con)
	{
		super(con);
	}
	
	public void create(Borrower a) throws SQLException {
		save("insert into tbl_borrower (name, address, phone) values (?, ?, ?)",new Object[]{a.getName(),a.getAddress(), a.getPhoneNo()});
		}

	public void update(Borrower a) throws SQLException {
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?", new Object[]{a.getName(), a.getAddress(), a.getPhoneNo(), a.getCardNo()});
	}

	public void updateName(Borrower a) throws SQLException {
		save("update tbl_borrower set name = ? where cardNo = ?", new Object[]{a.getName(), a.getCardNo()});
	}
	
	public void updateAddress(Borrower a) throws SQLException {
		save("update tbl_borrower set  address = ? where cardNo = ?", new Object[]{a.getAddress(), a.getCardNo()});
	}
	
	public void updatePhoneNo(Borrower a) throws SQLException {
		save("update tbl_borrower set phone = ? where cardNo = ?", new Object[]{a.getPhoneNo(), a.getCardNo()});
	}
	
	public void delete(Borrower a) throws SQLException {
		save("delete from tbl_borrower where cardNo = ?",new Object[]{a.getCardNo()});
	}

	public Borrower readOne(int cardNo) throws SQLException {

		List<Borrower> list= (List<Borrower>) read("select * from tbl_borrower where cardNo = ?",new Object[]{cardNo});
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

	public List<Borrower> readAll() throws SQLException {

		return (List<Borrower>)read("select * from tbl_borrower",null);

	}
	
	public List<Borrower> readByName(String searchString) throws SQLException {

		String qString = "%" + searchString + "%";
		return (List<Borrower>)read("select * from tbl_borrower name like ?",new Object[]{qString});
	}
	
	public List<Borrower> readByAddress(String searchString) throws SQLException {

		String qString = "%" + searchString + "%";
		return (List<Borrower>)read("select * from tbl_borrower address like ?",new Object[]{qString});
	}
	
	public List<Borrower> readByPhone(String searchString) throws SQLException {

		String qString = "%" + searchString + "%";
		return (List<Borrower>)read("select * from tbl_borrower phone like ?",new Object[]{qString});
	}

	@Override
	protected List<Borrower> processResult(ResultSet rs) throws SQLException {
		List<Borrower> aList = new ArrayList<Borrower>();
		while(rs.next()) {
			Borrower a = new Borrower();
			a.setName(rs.getString("name"));
			a.setAddress(rs.getString("address"));
			a.setPhoneNo(rs.getString("phone"));
			aList.add(a);
		}


		return aList;
	}
}
