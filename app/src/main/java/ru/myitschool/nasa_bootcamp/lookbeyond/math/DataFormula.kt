package ru.myitschool.nasa_bootcamp.lookbeyond.math

//Изучала с помощью сайта http://astro.if.ufrgs.br/trigesf/position.htmlъ

/*
Declination and Right ascension
Склонение и прямое восхождение. Вторая экваториальная система координат.

ВЫЧИСЛЯЕМ Ra и Dec // В презентации напишу пояснение на русском
Пример (солнце) :
The position of the Sun is computed just like the position of any other planet, but since the Sun always is moving in the ecliptic,
 and since the eccentricity of the orbit is quite small,
a few simplifications can be made. Therefore, a separate presentation for the Sun is given.
Of course, we're here really computing the position of the Earth in its orbit around the Sun,
but since we're viewing the sky from an Earth-centered perspective, we'll pretend that the Sun is in orbit around the Earth instead.

First, compute the eccentric anomaly E from the mean anomaly M and from the eccentricity e (E and M in degrees):

    E = M + e*(180/pi) * sin(M) * ( 1.0 + e * cos(M) )
or (if E and M are expressed in radians):
    E = M + e * sin(M) * ( 1.0 + e * cos(M) )

Note that the formulae for computing E are not exact; h
owever they're accurate enough here.

Then compute the Sun's distance r and its true anomaly v from:

    xv = r * cos(v) = cos(E) - e
    yv = r * sin(v) = sqrt(1.0 - e*e) * sin(E)

    v = atan2( yv, xv )
    r = sqrt( xv*xv + yv*yv )


Now, compute the Sun's true longitude:

    lonsun = v + w
Convert lonsun,r to ecliptic rectangular geocentric coordinates xs,ys:

    xs = r * cos(lonsun)
    ys = r * sin(lonsun)
(since the Sun always is in the ecliptic plane, zs is of course zero).
 xs,ys is the Sun's position in a coordinate system in the plane of the ecliptic. To convert this to equatorial, rectangular, geocentric coordinates, compute:

    xe = xs
    ye = ys * cos(ecl)
    ze = ys * sin(ecl)
Finally, compute the Sun's Right Ascension (RA) and Declination (Dec):

    RA  = atan2( ye, xe )
    Dec = atan2( ze, sqrt(xe*xe+ye*ye) )


    N = longitude of the ascending node
    i = inclination to the ecliptic (plane of the Earth's orbit)
    w = argument of perihelion
    a = semi-major axis, or mean distance from Sun
    e = eccentricity (0=circle, 0-1=ellipse, 1=parabola)
    M = mean anomaly (0 at perihelion; increases uniformly with time)

Orbital elements of the Sun:

N = 0.0
i = 0.0
w = 282.9404 + 4.70935E-5 * d
a = 1.000000  (AU)
e = 0.016709 - 1.151E-9 * d
M = 356.0470 + 0.9856002585 * d
Orbital elements of the Moon:

N = 125.1228 - 0.0529538083 * d
i = 5.1454
w = 318.0634 + 0.1643573223 * d
a = 60.2666  (Earth radii)
e = 0.054900
M = 115.3654 + 13.0649929509 * d
Orbital elements of Mercury:

N =  48.3313 + 3.24587E-5 * d
i = 7.0047 + 5.00E-8 * d
w =  29.1241 + 1.01444E-5 * d
a = 0.387098  (AU)
e = 0.205635 + 5.59E-10 * d
M = 168.6562 + 4.0923344368 * d
Orbital elements of Venus:

N =  76.6799 + 2.46590E-5 * d
i = 3.3946 + 2.75E-8 * d
w =  54.8910 + 1.38374E-5 * d
a = 0.723330  (AU)
e = 0.006773 - 1.302E-9 * d
M =  48.0052 + 1.6021302244 * d
Orbital elements of Mars:

N =  49.5574 + 2.11081E-5 * d
i = 1.8497 - 1.78E-8 * d
w = 286.5016 + 2.92961E-5 * d
a = 1.523688  (AU)
e = 0.093405 + 2.516E-9 * d
M =  18.6021 + 0.5240207766 * d
Orbital elements of Jupiter:

N = 100.4542 + 2.76854E-5 * d
i = 1.3030 - 1.557E-7 * d
w = 273.8777 + 1.64505E-5 * d
a = 5.20256  (AU)
e = 0.048498 + 4.469E-9 * d
M =  19.8950 + 0.0830853001 * d
Orbital elements of Saturn:

N = 113.6634 + 2.38980E-5 * d
i = 2.4886 - 1.081E-7 * d
w = 339.3939 + 2.97661E-5 * d
a = 9.55475  (AU)
e = 0.055546 - 9.499E-9 * d
M = 316.9670 + 0.0334442282 * d
Orbital elements of Uranus:

N =  74.0005 + 1.3978E-5 * d
i = 0.7733 + 1.9E-8 * d
w =  96.6612 + 3.0565E-5 * d
a = 19.18171 - 1.55E-8 * d  (AU)
e = 0.047318 + 7.45E-9 * d
M = 142.5905 + 0.011725806 * d
Orbital elements of Neptune:

N = 131.7806 + 3.0173E-5 * d
i = 1.7700 - 2.55E-7 * d
w = 272.8461 - 6.027E-6 * d
a = 30.05826 + 3.313E-8 * d  (AU)
e = 0.008606 + 2.15E-9 * d
M = 260.2471 + 0.005995147 * d
 */