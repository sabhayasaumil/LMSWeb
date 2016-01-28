package com.gcit.training.lms.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.training.lms.entity.Author;
import com.gcit.training.lms.service.AdministrativeService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({"/addAuthor", "/deleteAuthor", "/pageAuthor", "/searchAuthor"})
public class AdminServlet extends HttpServlet {
	AdministrativeService adminService = new AdministrativeService();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),  request.getRequestURI().length());
		switch (reqUrl) {
		case "/deleteAuthor":
			try {
				deleteAuthor(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/pageAuthor":
			try {
				readAllAuthors(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/searchAuthor":
			try {
				searchAuthor(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	private void readAllAuthors(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewauthor.jsp");
		Integer pageNo = 1;
		if(request.getParameter("pageNo")!=null)
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		Integer pageSize = 10;
		try {
			List<Author> authors = adminService.getAllAuthors(pageNo, 10);
			request.setAttribute("authors", authors);
			int count = adminService.getAllAuthorsCount();
			int totalPage = count / pageSize;
			
			if(count % pageSize !=0)
				totalPage++;
			
			request.setAttribute("currentPage", pageNo);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("authors", authors);

			
			
			
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
			rd.forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteAuthor(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewauthor.jsp");
		Integer authorId = Integer.parseInt(request.getParameter("authorId"));
		
		try {
			adminService.deleteAuthor(authorId);
			request.setAttribute("result", "Author Deleted Sucessfully.");
			rd.forward(request, response);
		} catch (ServletException e) {
			request.setAttribute("result", "Author Delete Failed.");
			e.printStackTrace();
			rd.forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),  request.getRequestURI().length());
		switch (reqUrl) {
		case "/addAuthor":
			addAuthor(request, response);
			break;
		

		default:
			break;
		}
		
	}

	private void searchAuthor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewauthor.jsp");
		String searchString = request.getParameter("searchString");
		Integer pageNo = 1;
		Integer pageSize = 10;
		if(request.getParameter("pageNo")!=null)
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		try {
			List<Author> authors = adminService.searchAuthors(searchString, pageNo,pageSize);
			int count = adminService.searchAuthorsCount(searchString);
			int totalPage = count / pageSize;
			
			if(count % pageSize !=0)
				totalPage++;
			
			request.setAttribute("currentPage", pageNo);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("authors", authors);
			request.setAttribute("searchString", searchString);
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
			rd.forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void addAuthor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");
		String authorName = request.getParameter("authorName");
		if (authorName.length() < 1 || authorName.length() > 40) {
			rd = getServletContext().getRequestDispatcher("/addauthor.jsp");
			request.setAttribute("result", "Author Name cannot be more than 45 chars!");
			rd.forward(request, response);
		}
		Author author = new Author();
		author.setAuthorName(authorName);
		try {
			adminService.addAuthor(author);
			request.setAttribute("result", "Author Added Sucessfully!");
			rd.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			rd = getServletContext().getRequestDispatcher("/addauthor.jsp");
			request.setAttribute("result", "Author Add Failed!");
			rd.forward(request, response);
		}
	}

}
