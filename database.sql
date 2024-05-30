CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL,
    address VARCHAR(100) DEFAULT '',
    password VARCHAR(100) NOT NULL DEFAULT '',
    create_at DATETIME,
    update_at DATETIME,
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATE,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0
);
CREATE TABLE tokens(
    id int PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) UNIQUE NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    expiration_date DATETIME,
    revoked TINYINT(1) NOT NULL,
    expired TINYINT(1) NOT NULL,
    user_id INT,
    FOREIGN KEY(user_id) REFERENCES users(id)
);
CREATE TABLE social_accounts(
    
);
CREATE TABLE blogs(
	id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) DEFAULT '' NOT NULL,
    content LONGTEXT DEFAULT '' NOT NULL,
    create_at DATETIME,
    update_at DATETIME,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE customers(
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL,
    email VARCHAR(100) DEFAULT '',
    address VARCHAR(100) DEFAULT '',
    create_at DATETIME,
    update_at DATETIME,
);

CREATE TABLE orders_in_counter(
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id int,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    address VARCHAR(200) NOT NULL,
    note VARCHAR(100) DEFAULT '',
    purchase_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_money FLOAT CHECK(total_money >= 0)
);

CREATE TABLE orders_in_counter_details (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_counter_id INT,
    FOREIGN KEY (order_counter_id) REFERENCES orders_in_counter(id),
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products (id),
    price FLOAT CHECK(price >= 0),
    number_of_products INT CHECK(number_of_products > 0),
    total_money FLOAT CHECK(total_money >= 0)
);