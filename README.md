# Gallery

![Some Image](http://cobweb.cs.uga.edu/~mec/cs1302/cs1302-gallery.jpg)

This document contains the description for the `cs1302-gallery` project
assigned to the students in the Fall 2019 CSCI 1302 classes
at the University of Georgia.

## Description

Your goal is to implement a GUI application in Java using JavaFX 8 that displays a 
gallery of images based on the results of a search query to the 
[iTunes Search API](https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/#searching).
This will require you to lookup things in Javadoc and apply your knowledge of
things like inheritance, polymorphism, and interfaces. The functional
and non-functional requirements for this project are outlined later in this 
document. Here is an example of what your program might look like:

![screenshot1](https://i.imgur.com/eUh0NbF.png)

Here are links to an animated version: 
[gifv](https://i.imgur.com/tdRDXM1.gifv), 
[gif](https://i.imgur.com/tdRDXM1.gif).

Part of software development is being given a goal but not necessarily being 
given instruction on all of the details needed to accomplish that goal. For example,
even though working with things like images, threads, JSON, and the iTunes Search API 
haven't been covered in class, you are going to need to lookup how to do these things 
in order to complete this project.

This project is also designed to help you better understand the usefulness of good
class design. While you can technically write your entire JavaFX-based
GUI application entirely in the `start` method, this will make your code messy, 
hard to read, possibly redundant, and likely more prone to errors.
Before you write any code, you should plan out your application's scene graph
(i.e., the containment hierarchy), and design custom components as needed.
If you find that you are writing a lot of code related to a specific component
(e.g., setting styling, adding event handlers, etc.), then it's probably 
a good idea to make a custom version of that component in order to reduce
clutter. 

<hr/>

[![License: CC BY-NC-ND 4.0](https://img.shields.io/badge/License-CC%20BY--NC--ND%204.0-lightgrey.svg)](http://creativecommons.org/licenses/by-nc-nd/4.0/)

<small>
Copyright &copy; Michael E. Cotterell and the University of Georgia.
This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/">Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License</a> to students and the public.
The content and opinions expressed on this Web page do not necessarily reflect the views of nor are they endorsed by the University of Georgia or the University System of Georgia.
</small>
