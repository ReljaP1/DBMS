<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Display Customers in a range</title>
    </head>
    <body>
        <h2>Display Customers in a range</h2>
        <!--
            Form for collecting user input for the new movie_night record.
            Upon form submission, add_movie.jsp file will be invoked.
        -->
        <form action="query12output.jsp">
            <!-- The form organized in an HTML table for better clarity. -->
            <table border=1>
                <tr>
                    <th colspan="2">Enter lower and upper bounds:</th>
                </tr>
                <tr>
                    <td>Lower bound:</td>
                    <td><div style="text-align: center;">
                    <input type=text name=lower_bound>
                    </div></td>
                </tr>
                <tr>
                    <td>Upper bound:</td>
                    <td><div style="text-align: center;">
                    <input type=text name=upper_bound>
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
