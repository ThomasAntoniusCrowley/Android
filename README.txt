README - MONGOOSE GROUP PROJECT

Execution starts at the main( ) method of the "server" class. This then launches a
server which listens for connections from the android app instances, it also launches
a tabbed GUI which displays order info and other info panes.

Execution relies on a mySQL database called "restaurant" with several tables detailed 
in the wiki. We chose not to embed this database so that multiple instances of the 
server side class can all connect to a single database, without fear of falling out
of sync. Early versions which used SQLite would not have been able to do this.

To use our software with this database, load the included outputDatabase.db database
into mySQL as "restaurant", our code also assumes that the user will use the default
mySQL logins, but these can be changed if needed.

There is an associated client application which runs on android. It's through this 
app that the waiters use to order food items to the various tables, the centralGUI 
then handles payment and billing for these along with the other functionality asked
for in the backlog.
