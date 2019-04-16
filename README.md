# Doordash Rest Server
## Version 0.0.7
My shot at making a doordash server with spring boot

### Powered by
- GeoSpatial MySQL
- REST
- Spring
- JPA

### DB Design
<p align="center"><img src="https://github.com/robert-irribarren/spring-geo-doordash-rest-server/blob/master/db_design.PNG?raw=true"/></p>

### With Real Doordash Data
# (scraper included)
<p align="center"><img src="https://github.com/robert-irribarren/spring-geo-doordash-rest-server/blob/master/mcdata.PNG?raw=true"/></p>

### Setup

In windows setup the environment variable

Set DD_GOOGLE_PLACES_API_KEY='YOUR_API_KEY'

In ubuntu/mac

EXPORT DD_GOOGLE_PLACES_API_KEY=YOUR_API_KEY

In the Main class (DoordashServerApplication)
You can set the seed latitude,longitudes and SEED_MODE=true to
populate your database with restaurants nearby.