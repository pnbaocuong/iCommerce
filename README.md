# iCommerce
Simple online shopping application to sell products
# System Design
# 1. Requirements
...
# 2. High-level design
At a high-level, we need some following services to handle the requirements
![image](https://user-images.githubusercontent.com/6725026/128833341-352f709d-0be4-4b67-b4e8-64fd9a01514d.png)
- **Product Service**: manages our products with CRUD operations. This service also provides the ability to allow user could filter, sort and search for products based on dynamic criteria.
- **Audit Service**: use to records all customers activities (filtering, sorting, viewing product detail).
- **Shopping Cart Service**: manages customers shopping carts with CRUD operations.
- **Order Service**: manages customer orders with CRUD operations.
- **Authentication Service**: authenticates customers, use spring security integrates with LDAP otherwise we can integrates with 3rd party identity platform like Facebook, Google...
- **API Gateway**: Route requests to multiple services using a single endpoint. This service allows us to expose multiple services on a single endpoint and route to the appropriate service based on the request.
# 3. Detailed design
### 3.1. Detail architecture
![image](https://user-images.githubusercontent.com/6725026/128842222-6687989c-d9bc-4247-81ec-1e3bbfe38a4d.png)

* Other some design we use CQRS pattern for synchronys ad ansychronus issues

### 3.2 Data model
In this part, we define entity diagram for services
Each serives should not share data connection.
#### Product Service
The Product service stores information about all of our product. The storage requirements for the Product are:
- Long-term storage.
- Read-heavy (it's common for ecommerce application because the traffic from users to view, search, sort product are always much higher than the traffic from administrators to update product's information).
- Structured data: Category, Product, Brand, ProductPriceHistory. 
The relational database is:
![image](https://user-images.githubusercontent.com/6725026/128839754-dff6b2fc-6e7d-4351-b57b-3489a8f61529.png)
![image](https://user-images.githubusercontent.com/6725026/128839793-859890aa-3fb2-4400-bc73-f7b391b69faf.png)
#### Audit Service
The Audit service listens for customer activities from the Product service. The storage requirements for the Audit service are:
- Long-term storage.
- Non-relational data.
- Able to handle massive amount a data (read-heavy as we mentioned in Product section above).

MongoDB is good fit in our scenario. 

We define the data schema like this:
![image](https://user-images.githubusercontent.com/6725026/128840460-9688aa1c-4b00-45d9-9522-f68c7f33c3e0.png)

#### Shopping Cart Service
The Shopping Cart service stores information about shopping cart of the customers. The storage requirements for the Shopping Cart Service are:
- Short-term storage. Each customer will have their own shopping cart and only one shopping cart at the moment. After customer checkout, the shopping cart data will be cleared.
- Need retrieve/lookup shopping cart data quickly and update shopping cart data quickly 
- Support only 1 simple query: query by customer.
![image](https://user-images.githubusercontent.com/6725026/128840880-53634742-2f80-475e-9954-58c0c8e43931.png)
#### Order Service
The Order service stores information about all of our customer orders. 
- Long-term storage.
- Able to handle a high volume of packages, requiring high write throughput 
- Support simple queries. No complex joins or requirements for referential integrity.
![image](https://user-images.githubusercontent.com/6725026/128841317-c3d62b62-8eff-4590-8826-377d73f4b079.png)

Because the order data is simple and not relational, a document-oriented database is appropriate, and MongoDB can achieve high throughput and scale well in terms of volume of traffic or size of data (or both).
#### Authentication Service
We use Spring security integrate with LDAP for authentication
#### API Gateway
All front-end connection call api through api gateway, we use ERUEKA to implement gateway.
![image](https://user-images.githubusercontent.com/6725026/128843869-b6f2e83d-b0dd-4088-8067-8e6b0d595b9b.png)
#### Registry Service
We deploy our system to Kubernetes, so Kubernetes provides Service discovery and load balancing out-of-box.
![image](https://user-images.githubusercontent.com/6725026/128844030-3b084f9f-fcd5-467e-b417-30a5dcf21fb4.png)
### 3.2 Monitoring
In a microservices application, we need to track what's happening across dozens or even hundreds of services. To make sense of what's happening, we must collect telemetry from the application. Telemetry can be divided into logs and metrics.
- **Logs** are text-based records of events that occur while the application is running. 
# 4 Build and run project
- Check out the source code, import maven project to eclipse, build the project
- Open terminal and go to the directory which thesame of file docker-compose.yml the run docker-compose up --build -d to start database and rabitmq 
- Use this postman tools at  https://www.getpostman.com/collections/3b3c9d189a0db9748ae4 to run api

