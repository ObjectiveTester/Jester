Jester
======

Jester is an experimental (a.k.a unfinished) API test tool. It outputs your actions as Java statements that can then be refactored into reusable tests using REST Assured.
So far the tool can GET, edit and POST JSON responses, or DELETE from API endpoints.



Building Jester
===============

Clone or download the source code and either:

Open the 'Jester' folder in NetBeans or your favourite IDE, and build it.

Build a package from the command line with:

    mvn package 


Running Jester
==============

Once built, either double-click 'Jester-0.01.jar' in the 'target' folder or start from the command line with:

    java -jar target/Jester-0.01.jar
    



Testing Jester
============
Functionality is limited to POST, GET and ASSERT for now:

POST
----
Either import some JSON data from 'File -> Import' or create it by inserting keys and values into the empty obejct (this may be a little unstable, with minimal error checking).
Then click the 'POST' icon. There is an option in 'Settings' to only parse GET responses.


GET
---
Enter a URI into the text field and click 'GET'. The treeview will (or should) be updated with a graphical representation of the response. You can now edit this and POST.

ASSERT
------
Right-click on a value in the tree and assert on it.


DELETE
------
Sends a simple DELETE request.

In the right hand frame, Java statements are generated. These should be runnable from an IDE, etc. after a little cleanup.
