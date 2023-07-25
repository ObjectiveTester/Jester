Jester
======

Jester is an experimental (a.k.a unfinished) API test tool. It outputs your actions as Java statements using REST Assured that can then be refactored into reusable tests.
Jester can GET and POST JSON requests and assert on the responses or DELETE from API endpoints. Additional HTTP verbs will be added later.



Building Jester
===============

Clone or download the source code and either:

Open the 'Jester' folder in NetBeans or your favourite IDE, and build it.

Build a package from the command line with:

    mvn package 


Running Jester
==============

Once built, either double-click 'Jester-0.4.jar' in the 'target' folder or start from the command line with:

    java -jar target/Jester-0.4.jar
    



Testing REST API's
============
Jester can GET, POST, DELETE and assert on values in responses and all of these operations generate Java statements using either Junit4 or Junit5.


POST
----
Create a request body by inserting keys and values into the Request Object (this may be a little unstable, with minimal error checking), or start by copying a response with 'File -> Copy Response into Request', or import some JSON data with 'File -> Import'.
Then click the 'POST' icon.


GET
---
Enter a URI into the text field and click 'GET'. The Response Object treeview will be updated with a graphical representation of the response. You can then add assertions, or copy it (from 'File -> Copy Response into Request') into the Request Object and use it in a POST.


DELETE
------
Sends a simple DELETE request.


ASSERT
------
Right-click on a value in the Response Object tree and assert on it.


JSON editing
------------
You can insert, delete and modify JSON elements with some error checking. Editing data within an array does work, but be careful when creating new array elements. 

Cookies & Headers
-----------------
Cookies and custom headers can be included in the requests by adding them to the text boxes as a comma seperated list of key=value pairs -  e.g.

    Authorization=Bearer abcde..., JSESSIONID=1234...


Query Parameters
----------------
Add query parameters to the request by adding them to the URI - e.g.

    q=test&type=debug


In the right hand frame, Java statements are generated. These should be runnable from an IDE, etc. after a little cleanup.



Authorization
-------------

Jester works with:

- **API Keys** - by including the key as a query string, header or cookie
- **Basic auth** by including the user:passwd as Base64 in a header - e.g. `Authorization=Basic dXNlcjpwYXNzd2Q=`
- **Bearer token** - by including the token in a header - e.g. `Authorization=Bearer abcde....`
- **Cookie auth** - setting cookies works, extracting them isn't implemented yet