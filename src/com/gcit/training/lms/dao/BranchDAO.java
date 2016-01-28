package com.gcit.training.lms.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.training.lms.entity.Branch;


public class BranchDAO extends AbstractDAO{

	public BranchDAO(Connection con)
	{
		super(con);
	}
	
	public void create(Branch a) throws SQLException {
		save("insert into tbl_library_branch (branchName, branchAddress) values (? , ?)",new Object[]{a.getBranchName(),a.getBranchAddress()});
		
	}

	public void update(Branch a) throws SQLException {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",new Object[]{a.getBranchName(),a.getBranchAddress(),a.getBranchId()});
	}
	
	public void updateName(Branch a) throws SQLException {
		save("update tbl_library_branch set branchName = ? where branchId = ?",new Object[]{a.getBranchName(),a.getBranchId()});
	}
	
	public void updateAddress(Branch a) throws SQLException {
		save("update tbl_library_branch set  branchAddress = ? where branchId = ?",new Object[]{a.getBranchAddress(),a.getBranchId()});
	}

	public void delete(Branch a) throws SQLException {
		save("delete from tbl_library_branch where branchId = ?",new Object[]{a.getBranchId()});
	}

	public Branch readOne(int branchId) throws SQLException {

		List<Branch> list = (List<Branch>)read("select * from tbl_library_branch where branchId = ?",new Object[]{branchId});
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<Branch> readAll() throws SQLException {

		return (List<Branch>)read("select * from tbl_library_branch",null);

	}
	
	public List<Branch> readByName(String searchString) throws SQLException {

		String qString = "%" + searchString + "%";
		return (List<Branch>)read("select * from tbl_library_branch where branchName like ?",new Object[]{qString});

	}
	
	public List<Branch> readByAddress(String searchString) throws SQLException {
		String qString = "%" + searchString + "%";

		return (List<Branch>)read("select * from tbl_library_branch where branchAddress like ?",new Object[]{qString});

	}

	@Override
	protected List<Branch> processResult(ResultSet rs) throws SQLException {
		List<Branch> aList = new ArrayList<Branch>();
		while(rs.next()) {
			Branch a = new Branch();
			a.setBranchId(rs.getInt("branchId"));
			a.setBranchName(rs.getString("branchName"));
			a.setBranchAddress(rs.getString("branchAddress"));
			aList.add(a);
		}

		return aList;
	}
	
}
