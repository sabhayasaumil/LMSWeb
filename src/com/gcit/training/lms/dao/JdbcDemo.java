package com.gcit.training.lms.dao;

import java.util.List;

import com.gcit.training.lms.entity.Loans;

public class JdbcDemo {

	public static void main(String[] args) {

		try {

			/*
			 * List<Book> list = new BookDAO().readByTitle("Lost"); for(Book
			 * l:list) { System.out.println(l.getTitle());
			 * 
			 * }
			 */

			
			List<Loans> list = new BookLoansDAO(null).readAll();

			for (Loans l : list) {
				System.out.println(l.getBook().getTitle() + "\t"
						+ l.getBranch().getBranchName() + "\t"
						+ l.getCard().getCardNo() + "\t" + l.getDateIn() + "\t"
						+ l.getDateOut() + "\t" + l.getDueDate());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
