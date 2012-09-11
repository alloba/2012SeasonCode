NOTES ON 2012Season CODE

ON UNITS OF MEASURE:

LENGTH
--------
the standard unit for length in this code is inches. Therefore
    to represent feet you must convert to inches. There are no classes for this
    conversion as that it will take to long.

TIME
-------
the standard unit for time is seconds unless otherwise stated. The metric system
    is used for the conversion of time (i.e.: s --> ms )

COORDINATE SYSTEM
-----------------
the robot coordinate sytsem uses the center of the robot as the origin at ALL 
TIMES! Distance is calculated as the forward distance to travel and the angle is 
given off of the y-axis. Angles are given in radians to make arc length computations 
easier. 
                     \ +rad | -rad /
                      \     |     /
                       \    |    /
                        \   |   /
                      ---\--|--/--
                      |   \ | /  |
                      |    \|/   |
                      |          |
                      ------------
