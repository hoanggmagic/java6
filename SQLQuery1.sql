INSERT INTO Accounts (username, password, fullname, email, photo, activated, admin) VALUES
('ha', '123', 'Đặng Thị Hà', 'ha@gmail.xom', 'user1.jpg', 1, 0),
('hoangg', '123', 'Khương Đình Hoàng ', 'hoang@gnnmail.com', 'user2.jpg', 1, 0),
('linh', '123', 'Nguyễn Thị Thùy Linh', 'linh.com', 'admin.jpg', 1, 1),
('guest', '123', 'Khách Hàng', 'guest@example.com', 'guest.jpg', 1, 0);

INSERT INTO Categories (id, name) VALUES
('C001', 'Nam'),
('C002', 'Nữ'),
('C003', 'Trẻ em'),
('C004', 'Phụ kiện');

-- Chèn dữ liệu vào bảng Orders (Giả sử Username đã tồn tại trong bảng Accounts)
INSERT INTO Orders (Username, Address, Createdate) VALUES
('ha', '123 Đường ABC, TP. HCM', '2024-03-17'),
('hoangg', '456 Đường XYZ, Hà Nội', '2024-03-16'),
('linh', '789 Đường DEF, Đà Nẵng', '2024-03-15'),
('guest', 'Chưa có địa chỉ', '2024-03-14');

INSERT INTO Products (name, image, price, Createdate, available, Categoryid) VALUES
('Áo thun nam', 'product1.jpg', 500000, '2024-03-17', 1, 'C001'),
('Quần jeans nữ', 'product2.jpg', 300000, '2024-03-16', 1, 'C002'),
('Giày trẻ em', 'product3.jpg', 150000, '2024-03-15', 1, 'C003'),
('Mũ thời trang', 'product4.jpg', 200000, '2024-03-14', 1, 'C004');

INSERT INTO Orderdetails (Orderid, Productid, Price, Quantity) VALUES
(1, 1, 500000, 2),
(1, 2, 300000, 1),
(2, 3, 150000, 3),
(3, 1, 500000, 1),
(3, 4, 200000, 2),
(4, 2, 300000, 2);

