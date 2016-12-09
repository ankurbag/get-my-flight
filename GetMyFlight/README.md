#activator-GetMyFlight

This is a play application written in scala to provide the customers with the cheapest flight options.

-----------------------------------------------------------------------
###Instructions :-
-----------------------------------------------------------------------
Clone and run the app(default database is H2):

     $ git clone git@https://github.com/ankurbag/CSYE7200_Scala_Project_Group3/GetMyFlight.git
     $ cd activator-GetMyFlight
     $ ./activator run
    
![alt tag](/public/images/evolutions.png)
    
 Run the all unit test:

     $ ./activator test
    
Run the app using Postgres database:

     $ ./activator 'run   -Dconfig.file=conf/postgres.conf'
    


-----------------------------------------------------------------------
###References :-
-----------------------------------------------------------------------

* [Play 2.5.x](http://www.playframework.com)
* [WebJars](http://www.webjars.org/)
* [Bootstrap](http://getbootstrap.com/css/)
* [Slick](http://slick.typesafe.com/)
