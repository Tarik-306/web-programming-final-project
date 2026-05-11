-- E-Ticaret MVC Projesi - Fotoğraf Sorunu Düzeltilmiş Tek SQL Dosyası
-- Bu dosya schema.sql + UTF-8 ve görsel düzeltmelerini birlikte içerir.
-- phpMyAdmin üzerinden doğrudan içe aktarabilirsiniz.

CREATE DATABASE IF NOT EXISTS ecommerce_mvc CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ecommerce_mvc;

SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(120) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  phone VARCHAR(30),
  address TEXT,
  role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE categories (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  is_active BOOLEAN DEFAULT TRUE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INT NOT NULL,
  name VARCHAR(150) NOT NULL,
  description TEXT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL DEFAULT 0,
  image_url VARCHAR(500),
  is_active BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_products_categories FOREIGN KEY(category_id) REFERENCES categories(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  total_amount DECIMAL(10,2) NOT NULL,
  status VARCHAR(30) NOT NULL DEFAULT 'Beklemede',
  CONSTRAINT fk_orders_users FOREIGN KEY(user_id) REFERENCES users(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE order_items (
  id INT AUTO_INCREMENT PRIMARY KEY,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL(10,2) NOT NULL,
  subtotal DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_items_orders FOREIGN KEY(order_id) REFERENCES orders(id),
  CONSTRAINT fk_items_products FOREIGN KEY(product_id) REFERENCES products(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO users(full_name,email,password,phone,address,role) VALUES
('Admin Kullanıcı','admin@site.com','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '5550000000','Admin adres','ADMIN');
-- Admin şifresi: admin

INSERT INTO categories(name,description,is_active) VALUES
('Telefon','Akıllı telefon ürünleri',1),
('Bilgisayar','Dizüstü ve masaüstü bilgisayarlar',1),
('Aksesuar','Teknolojik aksesuarlar',1),
('Kitap','Kitap ürünleri',1),
('Giyim','Giyim ürünleri',1);

INSERT INTO products(category_id,name,description,price,stock,image_url,is_active) VALUES
(1,'Akıllı Telefon X','Günlük kullanım için uygun akıllı telefon.',12500,10,'telefon.png',1),
(2,'Laptop Pro','Okul ve iş için güçlü dizüstü bilgisayar.',24500,6,'laptop.png',1),
(3,'Kablosuz Kulaklık','Bluetooth destekli kulaklık.',1200,20,'kulaklik.png',1),
(4,'Java Programlama Kitabı','Servlet JSP ve JDBC konularını anlatan kitap.',450,15,'kitap.png',1),
(5,'Spor Tişört','Rahat günlük tişört.',350,30,'giyim.png',1);


-- Not: Ürün görselleri /product-image servleti üzerinden güvenli şekilde gösterilir.
