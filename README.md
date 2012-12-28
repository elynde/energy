![](https://raw.github.com/elynde/energy/master/logo.png)

Simple Android app for logging your energy level throughout the day.

After installing (and restarting the phone), a screen will come up every hour asking what your energy level is.
![](https://raw.github.com/elynde/energy/master/log.png)

You can then view a graph of your daily energy level or of your average energy level per hour.
![](https://raw.github.com/elynde/energy/master/graph.png)

There a bunch of me-specific hacks in the code you might want to remove like the fact that I discard data between 1am - 8am (I'm rarely awake then so the data is mostly noise).

Project is set up to be built with IntelliJ.

Uses [jjoe64's GraphView library](https://github.com/jjoe64/GraphView) for graphing.
