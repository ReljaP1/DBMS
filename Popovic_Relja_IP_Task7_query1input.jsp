<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add New Customer</title>
    </head>
    <body>
        <h2>Add New Customer</h2>
        <!--
            Form for collecting user input for the new movie_night record.
            Upon form submission, add_movie.jsp file will be invoked.
        -->
        <form action="query1output.jsp">
            <!-- The form organized in an HTML table for better clarity. -->
            <table border=1>
                <tr>
                    <th colspan="2">Enter new customer:</th>
                </tr>
                <tr>
                    <td>Customer name:</td>
                    <td><div style="text-align: center;">
                    <input type=text name=name>
                    </div></td>
                </tr>
                <tr>
                    <td>Customer address:</td>
                    <td><div style="text-align: center;">
                    <input type=text name=address>
                    </div></td>
                </tr>
                <tr>
                    <td>Customer category (1-10):</td>
                    <td><div style="text-align: center;">
                    <input type=text name=category>
                    </div></td>
                </tr>
                <tr>
                    <td><div style="text-align: center;">
                    <input type=reset value=Clear>
                    </div></td>
                    <td><div style="text-align: center;">
                    <input type=submit value=Insert>
                    </div></td>
                </tr>
            </table>
        </form>
    </body>
</html>
