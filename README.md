# Doordash Rest Server

My shot at making a doordash server with spring boot

### Powered by
- GeoSpatial MySQL
- REST
- Spring
- JPA

### DB Design

-Customer --> CustomerAddress(Assoc. mapping) --> Address
-Merchant --> Address (mapped by fk Merchant.address_id)