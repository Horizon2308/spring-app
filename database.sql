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