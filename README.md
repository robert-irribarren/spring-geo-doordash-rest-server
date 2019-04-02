# Doordash Rest Server

My shot at making a doordash server with spring boot

### Powered by
- GeoSpatial MySQL
- REST
- Spring
- JPA

### DB Design
<p align="center"><img src="https://github.com/robert-irribarren/spring-geo-doordash-rest-server/blob/master/db_design.PNG?raw=true"/></p>

-Customer --> CustomerAddress(Assoc. mapping) --> Address

-Merchant --> Address (mapped by fk Merchant.address_id)
