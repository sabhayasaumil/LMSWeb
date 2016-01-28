<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="include.html"%>
<%@ page import="com.gcit.training.lms.service.AdministrativeService"%>
<%@ page import="com.gcit.training.lms.service.ConnectionUtil"%>
<%@ page import="com.gcit.training.lms.entity.Author"%>
<%@ page import="com.gcit.training.lms.dao.AuthorDAO"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	AdministrativeService adminService = new AdministrativeService();
	
	List<Author> authors = null;
	
	Integer totalPage,currentPage = 1, pageSize = 10,startPage = 1, endPage = 1, i =0;
	
	String searchString = null,searchForm = "",link = "searchAuthor?";
	
	if(request.getAttribute("authors")!=null)
	{
		authors = (List<Author>)request.getAttribute("authors");
		if(request.getAttribute("currentPage") != null)
			currentPage = (Integer)request.getAttribute("currentPage");
		
	}
	else
	{
		authors = adminService.getAllAuthors(0, 10);
		searchString = null;
		link = "pageAuthor?";
	}
	
	if(request.getAttribute("totalPage")!=null)
	{
		totalPage = (Integer)request.getAttribute("totalPage");
	}
	else
	{
		int count = adminService.getAllAuthorsCount();
		totalPage = count/pageSize;
			if(count%pageSize !=0)
				totalPage++;
	}
	if(request.getAttribute("searchString")!=null)
		{
			searchString = (String)request.getAttribute("searchString");
			link = link + "searchString="+searchString;	
			searchForm = "value = '" + searchString + "'";
		}
	else
	{
		link = "pageAuthor?";
	}
	link = link + "&pageNo=";
	
	if(totalPage < 5)
	{
		endPage = totalPage;
	}
	else
	{
		if(currentPage > 3)
		{
			startPage = currentPage-2;
			
			
		}
		if(startPage+5 > totalPage)
		{
			endPage = totalPage;
		}
		else
		{
			endPage = totalPage + 2;
		}
	}
	
	
%>

<div class="page-header">
	<h1>List of Authors in LMS Application</h1>
	${result }
</div>
<form action="searchAuthor" method="get">
<div class="input-group">
  <span class="input-group-addon" id="basic-addon1">Search</span>
  <input type="text" class="form-control" <%=searchForm%> placeholder="Username" aria-describedby="basic-addon1" name="searchString" >
</div>
<button type="submit" class="btn btn-sm btn-primary">Search!</button>
</form>
<% 
 	if(totalPage > 1)
 		{ %>
<nav>
 <ul class="pagination">
 	
 	
 	<% 
 		if(currentPage > 1)
 		{
 	%>
 	<li>
      <a href="<%=link%><%=currentPage-1%>" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    <%
 		}
    %>
    
		<% 
			for(i = startPage; i<currentPage; i++)
		{ 
		%>
    		<li><a href="<%=link+i%>"><%=i%></a></li>
    	<%
		}
    	%>
    		<li><a><%=currentPage%></a></li>
    	<% 
			for(i = currentPage+1; i<=endPage; i++)
		{ 
		%>
    		<li><a href="<%=link+i%>"><%=i%></a></li>
    	<%
		}
    	%>	
    
    <% 
 		if(currentPage <totalPage)
 		{
 	%>
    <li>
      <a href="<%=link+(currentPage+1)%>" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
    <%
 		}
    %>
  </ul>
</nav>
<%
 		}
%>
<div class="row">
	<div class="col-md-6">
		<table class="table">
			<thead>
				<tr>
					<th>#</th>
					<th>Author Name</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (Author author : authors) {
			
				%>
				<tr>
					<td><%=author.getAuthorId()%></td>
					<td><%=author.getAuthorName()%></td>
					<td><button type="button" class="btn btn-sm btn-primary">Edit Author</button>
					<td><button type="button" class="btn btn-sm btn-danger" onclick="javascript:location.href='deleteAuthor?authorId=<%=author.getAuthorId()%>';">Delete Author</button>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
</div>