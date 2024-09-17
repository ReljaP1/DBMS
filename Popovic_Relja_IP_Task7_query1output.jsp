<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Query Result</title>
</head>
    <body>
    <%@page import="individualProjectDBMS.DataHandler"%>
    <%@page import="java.sql.ResultSet"%>
    <%@page import="java.sql.Array"%>
    <%
    // The handler is the one in charge of establishing the connection.
    DataHandler handler = new DataHandler();

    // Get the attribute values passed from the input form.
    String name = request.getParameter("name");
    String address = request.getParameter("address");
    String category_request = request.getParameter("category"); // editing got to here

    /*
     * If the user hasn't filled out all the time, movie name and duration. This is very simple checking.
     */
    if (name.equals("") || address.equals("") || category_request.equals("")) {
        response.sendRedirect("query12input.jsp");
    } else {
        int category = Integer.parseInt(category_request);

                
        // Now perform the query with the data from the form.
        boolean success = handler.InsertNewCustomer(name, address, category);
        if (!success) { // Something went wrong
            %>
                <h2>There was a problem inserting the customer</h2>
            <%
        } else { // Confirm success to the user
            %>
            <h2>The customer:</h2>

            <ul>
                <li>Customer Name: <%=name%></li>
                <li>Customer Address: <%=address%></li>
                <li>Category: <%=category%></li>
            </ul>

            <h2>Was successfully inserted.</h2>
            
            <a href="query12.jsp">See customers within a given category range.</a>
            <%
        }
    }
    %>
    </body>
</html>
