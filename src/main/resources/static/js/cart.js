// const CART_KEY = "shopping_cart";

// // Lấy giỏ hàng từ localStorage
// function getCart() {
//     return JSON.parse(localStorage.getItem(CART_KEY)) || [];
// }

// // Lưu giỏ hàng vào localStorage
// function saveCart(cart) {
//     localStorage.setItem(CART_KEY, JSON.stringify(cart));
// }

// // Thêm sản phẩm vào giỏ hàng
// function addToCart(button) {
//     let productId = button.getAttribute("data-id");
//     let name = button.getAttribute("data-name");
//     let price = parseFloat(button.getAttribute("data-price"));
//     let image = button.getAttribute("data-image");

//     console.log("Thêm sản phẩm:", { productId, name, price, image });

//     let cart = getCart();
//     let existingItem = cart.find(item => item.productId === productId);

//     if (existingItem) {
//         existingItem.quantity += 1;
//     } else {
//         cart.push({ productId, name, price, quantity: 1, image });
//     }

//     saveCart(cart);
//     alert("Đã thêm vào giỏ hàng!");
// }

// // Xóa sản phẩm khỏi giỏ hàng
// function removeFromCart(index) {
//     let cart = getCart();
//     cart.splice(index, 1);
//     saveCart(cart);
//     displayCart();
// }

// // Cập nhật số lượng sản phẩm
// function updateQuantity(index, quantity) {
//     let cart = getCart();
//     cart[index].quantity = parseInt(quantity);
//     saveCart(cart);
//     displayCart();
// }

// // Hiển thị giỏ hàng
// function displayCart() {
//     let cart = getCart();
//     let cartTable = document.getElementById("cart-items");
//     cartTable.innerHTML = "";

//     cart.forEach((item, index) => {
//         cartTable.innerHTML += `
//             <tr>
//                 <td><img src="/images/${item.image}" width="50"></td>
//                 <td>${item.name}</td>
//                 <td>${item.price.toLocaleString()} VND</td>
//                 <td>
//                     <input type="number" value="${item.quantity}" min="1"
//                            onchange="updateQuantity(${index}, this.value)">
//                 </td>
//                 <td>${(item.price * item.quantity).toLocaleString()} VND</td>
//                 <td><button onclick="removeFromCart(${index})">Xóa</button></td>
//             </tr>
//         `;
//     });

//     document.getElementById("cart-total").innerText =
//         cart.reduce((sum, item) => sum + item.price * item.quantity, 0).toLocaleString() + " VND";
// }

// // Xử lý thanh toán (Gửi giỏ hàng đến Spring Boot)
// function checkout() {
//     let cart = getCart();
//     if (cart.length === 0) {
//         alert("Giỏ hàng trống!");
//         return;
//     }

//     fetch("/api/orders", {
//         method: "POST",
//         headers: { "Content-Type": "application/json" },
//         body: JSON.stringify({ items: cart })
//     }).then(response => {
//         if (response.ok) {
//             alert("Đặt hàng thành công!");
//             localStorage.removeItem(CART_KEY);
//             displayCart();
//         } else {
//             alert("Lỗi khi đặt hàng!");
//         }
//     });
// }

// // Hiển thị giỏ hàng khi tải trang
// document.addEventListener("DOMContentLoaded", displayCart);
