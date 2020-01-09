Jester
======

Jester is an experimental (a.k.a unfinished) API test tool. It outputs your actions as Java statements using REST Assured that can then be refactored into reusable tests.
Jester can GET, edit or import and POST JSON responses, assert on the responses or DELETE from API endpoints.



Building Jester
===============

Clone or download the source code and either:

Open the 'Jester' folder in NetBeans or your favourite IDE, and build it.

Build a package from the command line with:

    mvn package 


Running Jester
==============

Once built, either double-click 'Jester-0.1.jar' in the 'target' folder or start from the command line with:

    java -jar target/Jester-0.1.jar
    



Testing Jester
============
Jester can GET, POST, DELETE, assert on responses and all of these operations generate Java statements using either Junit4 or Junit5.


JSON editing
------------
You can insert, delete and modify JSON elements but this functionality has no error checking at present (coming soon!)


POST
----
Either import some JSON data from 'File -> Import' or create it by inserting keys and values into the empty obejct (this may be a little unstable, with minimal error checking).
Then click the 'POST' icon. There is an option in 'Settings' to only parse GET responses.


GET
---
Enter a URI into the text field and click 'GET'. The treeview will (or should) be updated with a graphical representation of the response. You can now edit this and POST.


DELETE
------
Sends a simple DELETE request.


ASSERT
------
Right-click on a value in the tree and assert on it.


Cookies & Headers
-----------------
Cookies and custom headers can be included in the requests by adding them to the text boxes as a comma seperated list of key=value pairs -  e.g.

    SESSIONID=1234, DEGUG=true



Query Parameters
----------------
Add query parameters to the request by adding them to the URI - e.g.

    q=test&type=debug


In the right hand frame, Java statements are generated. These should be runnable from an IDE, etc. after a little cleanup.



