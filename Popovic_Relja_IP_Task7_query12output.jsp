<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
        <title>Display customers in range</title>
    </head>
    <body>
        <%@page import="individualProjectDBMS.DataHandler"%>
        <%@page import="java.sql.ResultSet"%>
       	<%@page import="java.sql.Array"%>
	
        <%
        // The handler is the one in charge of establishing the connection.
        DataHandler handler = new DataHandler();

        // Get the attribute values passed from the input form.
        String lower_bound_string = request.getParameter("lower_bound");
        String upper_bound_string = request.getParameter("upper_bound");
        
        //if (lower_bound_string.equals("") || upper_bound_string.equals("")) {
        //    response.sendRedirect("query12input.jsp");
        //}
        
        int lower_bound = Integer.parseInt(lower_bound_string);
        int upper_bound = Integer.parseInt(upper_bound_string);
        final ResultSet customers = handler.GetCustomersByCategoryRange(lower_bound, upper_bound);
        
        %>
        <!-- The table for displaying all the movie records -->
        <table cellspacing="2" cellpadding="2" border="1">
            <tr> <!-- The table headers row -->
              <td align="center">
                <h4>Name</h4>
              </td>
              <td align="center">
                <h4>address</h4>
              </td>
              <td align="center">
                <h4>category</h4>
              </td>

            </tr>
            <%
               while(customers.next()) { // For each movie_night record returned...
                   // Extract the attribute values for every row returned
                   final String name = customers.getString("name");
                   final String address = customers.getString("address");
                   final String category = customers.getString("category");
                   
                   out.println("<tr>"); // Start printing out the new table row
                   out.println( // Print each attribute value
                        "<td align=\"center\">" + name +
                        "</td><td align=\"center\"> " + address +
                        "</td><td align=\"center\"> " + category + "</td>");
                   out.println("</tr>");
               }
               %>
          </table>
    </body>
</html>
