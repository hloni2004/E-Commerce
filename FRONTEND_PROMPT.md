# E-Commerce Online Clothing Store - Frontend Development Guide

## Project Overview
Create a modern, responsive frontend for an Online Clothing Store e-commerce application. The backend is a Spring Boot REST API running on `http://localhost:8181` with comprehensive endpoints for managing users, products, orders, payments, and more.

---

## Backend API Information

### Base URL
```
http://localhost:8181
```

### Available REST API Endpoints

#### 1. **User Management** (`/api/users`)
- `POST /api/users` - Create new user (register)
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users` - Get all users
- `GET /api/users/username/{username}` - Find user by username
- `GET /api/users/email/{email}` - Find user by email
- `GET /api/users/role/{role}` - Get users by role (CUSTOMER/ADMIN)
- `GET /api/users/exists/username/{username}` - Check if username exists
- `GET /api/users/exists/email/{email}` - Check if email exists
- `DELETE /api/users/{id}` - Delete user

**User Object:**
```json
{
  "userId": "string",
  "username": "string",
  "email": "string",
  "password": "string",
  "phoneNumber": "string",
  "role": "CUSTOMER or ADMIN",
  "createdAt": "2024-11-14T10:30:00",
  "updatedAt": "2024-11-14T10:30:00"
}
```

---

#### 2. **Product Management** (`/api/products`)
- `POST /api/products` - Create new product
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products` - Get all products
- `GET /api/products/search/{name}` - Search products by name
- `GET /api/products/category/{categoryId}` - Get products by category
- `GET /api/products/price?min={min}&max={max}` - Get products in price range
- `GET /api/products/stock/low?threshold={threshold}` - Get low stock products
- `GET /api/products/stock/available?threshold={threshold}` - Get available products
- `DELETE /api/products/{id}` - Delete product

**Product Object:**
```json
{
  "productId": "string",
  "name": "string",
  "description": "string",
  "price": 99.99,
  "stockQuantity": 100,
  "imageUrl": "string",
  "category": {
    "categoryId": "string",
    "name": "string",
    "description": "string"
  }
}
```

---

#### 3. **Category Management** (`/api/categories`)
- `POST /api/categories` - Create category
- `GET /api/categories/{id}` - Get category by ID
- `GET /api/categories` - Get all categories
- `GET /api/categories/name/{name}` - Get category by name
- `GET /api/categories/search/{name}` - Search categories
- `GET /api/categories/ordered` - Get all categories ordered by name
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

**Category Object:**
```json
{
  "categoryId": "string",
  "name": "string",
  "description": "string"
}
```

---

#### 4. **Cart Management** (`/api/carts`)
- `POST /api/carts` - Create cart
- `GET /api/carts/{id}` - Get cart by ID
- `GET /api/carts` - Get all carts
- `GET /api/carts/user/{userId}` - Get user's cart
- `GET /api/carts/exists/user/{userId}` - Check if user has cart
- `PUT /api/carts/{id}` - Update cart
- `DELETE /api/carts/{id}` - Delete cart

**Cart Object:**
```json
{
  "cartId": "string",
  "user": { "userId": "string" },
  "createdAt": "2024-11-14T10:30:00",
  "updatedAt": "2024-11-14T10:30:00"
}
```

---

#### 5. **Cart Item Management** (`/api/cart-items`)
- `POST /api/cart-items` - Add item to cart
- `GET /api/cart-items/{id}` - Get cart item by ID
- `GET /api/cart-items` - Get all cart items
- `GET /api/cart-items/cart/{cartId}` - Get items in cart
- `GET /api/cart-items/product/{productId}` - Get cart items by product
- `GET /api/cart-items/cart/{cartId}/product/{productId}` - Get specific cart item
- `PUT /api/cart-items/{id}` - Update cart item quantity
- `DELETE /api/cart-items/{id}` - Remove item from cart
- `DELETE /api/cart-items/cart/{cartId}` - Clear cart

**CartItem Object:**
```json
{
  "cartItemId": "string",
  "cart": { "cartId": "string" },
  "product": { "productId": "string" },
  "quantity": 2,
  "price": 99.99
}
```

---

#### 6. **Order Management** (`/api/orders`)
- `POST /api/orders` - Create order (checkout)
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders` - Get all orders
- `GET /api/orders/user/{userId}` - Get user's orders
- `GET /api/orders/status/{status}` - Get orders by status
- `GET /api/orders/date-range?start={start}&end={end}` - Get orders in date range
- `GET /api/orders/user/{userId}/status/{status}` - Get user orders by status
- `GET /api/orders/user/{userId}/recent` - Get user's recent orders
- `GET /api/orders/count/status/{status}` - Count orders by status
- `DELETE /api/orders/{id}` - Cancel order

**Order Object:**
```json
{
  "orderId": "string",
  "user": { "userId": "string" },
  "orderDate": "2024-11-14T10:30:00",
  "status": "PENDING/PAID/SHIPPED/DELIVERED/CANCELED",
  "totalPrice": 299.99,
  "shippingAddress": {
    "addressId": "string",
    "street": "string",
    "city": "string",
    "province": "string",
    "postalCode": "string",
    "country": "string"
  }
}
```

---

#### 7. **Order Item Management** (`/api/order-items`)
- `GET /api/order-items/{id}` - Get order item by ID
- `GET /api/order-items/order/{orderId}` - Get items in order
- `GET /api/order-items/product/{productId}` - Get order items by product

**OrderItem Object:**
```json
{
  "orderItemId": "string",
  "order": { "orderId": "string" },
  "product": { "productId": "string" },
  "quantity": 2,
  "price": 99.99
}
```

---

#### 8. **Payment Management** (`/api/payments`)
- `POST /api/payments` - Process payment
- `GET /api/payments/{id}` - Get payment by ID
- `GET /api/payments/order/{orderId}` - Get payments for order
- `GET /api/payments/status/{status}` - Get payments by status
- `GET /api/payments/method/{method}` - Get payments by method
- `GET /api/payments/date-range?start={start}&end={end}` - Get payments in date range

**Payment Object:**
```json
{
  "paymentId": "string",
  "order": { "orderId": "string" },
  "paymentMethod": "CREDIT_CARD/DEBIT_CARD/PAYPAL/CASH",
  "amount": 299.99,
  "status": "PENDING/COMPLETED/FAILED/REFUNDED",
  "paymentDate": "2024-11-14T10:30:00",
  "transactionId": "string"
}
```

---

#### 9. **Shipment Management** (`/api/shipments`)
- `POST /api/shipments` - Create shipment
- `GET /api/shipments/{id}` - Get shipment by ID
- `GET /api/shipments/order/{orderId}` - Get shipment for order
- `GET /api/shipments/status/{status}` - Get shipments by status
- `GET /api/shipments/tracking/{trackingNumber}` - Track shipment
- `GET /api/shipments/carrier/{carrier}` - Get shipments by carrier

**Shipment Object:**
```json
{
  "shipmentId": "string",
  "order": { "orderId": "string" },
  "carrier": "FedEx/UPS/DHL",
  "trackingNumber": "string",
  "status": "PREPARING/SHIPPED/IN_TRANSIT/DELIVERED",
  "shippedDate": "2024-11-14T10:30:00",
  "deliveryDate": "2024-11-20T14:00:00"
}
```

---

#### 10. **Address Management** (`/api/addresses`)
- `POST /api/addresses` - Create address
- `GET /api/addresses/{id}` - Get address by ID
- `GET /api/addresses/user/{userId}` - Get user's addresses
- `GET /api/addresses/city/{city}` - Get addresses by city
- `PUT /api/addresses/{id}` - Update address
- `DELETE /api/addresses/{id}` - Delete address

**Address Object:**
```json
{
  "addressId": "string",
  "user": { "userId": "string" },
  "street": "123 Main Street",
  "city": "Cape Town",
  "province": "Western Cape",
  "postalCode": "8001",
  "country": "South Africa"
}
```

---

#### 11. **Coupon Management** (`/api/coupons`)
- `POST /api/coupons` - Create coupon
- `GET /api/coupons/code/{code}` - Get coupon by code
- `GET /api/coupons/active` - Get active coupons
- `GET /api/coupons/exists/code/{code}` - Check if coupon exists

**Coupon Object:**
```json
{
  "couponId": "string",
  "code": "SAVE20",
  "discountPercentage": 20.0,
  "expiryDate": "2024-12-31T23:59:59",
  "isActive": true
}
```

---

#### 12. **Audit Log Management** (`/api/audit-logs`)
- `GET /api/audit-logs/user/{userId}` - Get user's activity logs
- `GET /api/audit-logs/action/{action}` - Get logs by action type

---

## Frontend Requirements

### Technology Stack Recommendations
- **Framework**: React.js with TypeScript / Vue.js / Next.js
- **Styling**: Tailwind CSS / Material-UI / Bootstrap 5
- **State Management**: Redux Toolkit / Zustand / Context API
- **HTTP Client**: Axios / Fetch API
- **Routing**: React Router / Vue Router / Next.js Router
- **Form Handling**: React Hook Form / Formik
- **Authentication**: JWT tokens stored in localStorage/sessionStorage

---

### Required Pages/Features

#### 1. **Public Pages**
- **Home Page**: Featured products, categories, hero banner
- **Product Listing**: Grid/list view, filters (category, price, stock), search, pagination
- **Product Detail**: Images, description, price, stock status, add to cart, reviews
- **Login/Register**: Authentication forms with validation
- **About Us**: Store information
- **Contact Us**: Contact form

#### 2. **Customer Pages** (Authenticated)
- **My Profile**: View/edit user information, change password
- **My Addresses**: CRUD operations for shipping addresses
- **Shopping Cart**: View items, update quantities, remove items, apply coupons, checkout
- **Checkout**: Select shipping address, payment method, order review
- **My Orders**: Order history, filter by status, view details
- **Order Details**: Items, payment status, shipment tracking
- **Order Tracking**: Real-time shipment status

#### 3. **Admin Dashboard** (Admin Role)
- **Dashboard Overview**: Stats (total sales, orders, products, users)
- **Product Management**: CRUD operations, stock management, category assignment
- **Category Management**: CRUD operations
- **Order Management**: View all orders, update status, cancel orders
- **User Management**: View users, manage roles, delete users
- **Payment Management**: View payments, process refunds
- **Shipment Management**: Create shipments, update tracking
- **Coupon Management**: Create/manage discount coupons
- **Audit Logs**: View system activity logs

---

### Key Features to Implement

#### Authentication & Authorization
- Register new users (default role: CUSTOMER)
- Login with username/email and password
- Store JWT token in localStorage
- Protected routes for authenticated users
- Role-based access (CUSTOMER vs ADMIN)
- Auto-redirect on unauthorized access
- Logout functionality

#### Shopping Experience
- Browse products by category
- Search products by name
- Filter by price range, stock availability
- Sort by price (low-high, high-low), name, newest
- Add products to cart
- Update cart item quantities
- Remove items from cart
- View cart total
- Apply discount coupons
- Real-time stock validation

#### Checkout Process
1. Review cart items
2. Select/add shipping address
3. Choose payment method
4. Review order summary (items, subtotal, discount, total)
5. Place order
6. Display order confirmation with order ID

#### Order Management
- View order history (newest first)
- Filter orders by status
- View detailed order information
- Track shipment status
- Cancel pending orders
- Download invoice (optional)

#### Admin Features
- Dashboard with analytics (charts recommended)
- Manage products (CRUD, stock updates, category assignment)
- Manage categories
- View and update order statuses
- Assign shipments to orders
- View payment history
- Create/manage coupons
- View audit logs for security

---

### UI/UX Guidelines

#### Design Principles
- **Responsive**: Mobile-first design, works on all screen sizes
- **Intuitive Navigation**: Clear menu structure, breadcrumbs
- **Fast Loading**: Optimize images, lazy loading, skeleton screens
- **Accessibility**: ARIA labels, keyboard navigation, color contrast
- **Feedback**: Loading states, success/error messages, confirmation dialogs
- **Consistency**: Uniform styling, spacing, component patterns

#### Color Scheme
- Primary: Clothing brand colors (suggest: #3B82F6 blue)
- Secondary: Accent color (suggest: #10B981 green)
- Error: Red (#EF4444)
- Warning: Yellow (#F59E0B)
- Success: Green (#10B981)
- Neutral: Grays for text and backgrounds

#### Components to Create
- Navigation bar (with cart icon showing item count)
- Footer with links
- Product card (image, name, price, add to cart button)
- Cart item component
- Order summary card
- Address form
- Payment method selector
- Order status badge (color-coded)
- Search bar with autocomplete
- Filter sidebar
- Pagination controls
- Loading spinners/skeletons
- Toast notifications
- Modal dialogs
- Breadcrumb navigation

---

### API Integration Guidelines

#### Making Requests
```javascript
// Example: Fetch all products
const fetchProducts = async () => {
  try {
    const response = await axios.get('http://localhost:8181/api/products');
    return response.data;
  } catch (error) {
    console.error('Error fetching products:', error);
    throw error;
  }
};

// Example: Create order
const createOrder = async (orderData) => {
  try {
    const response = await axios.post('http://localhost:8181/api/orders', orderData, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    return response.data;
  } catch (error) {
    console.error('Error creating order:', error);
    throw error;
  }
};
```

#### Error Handling
- Display user-friendly error messages
- Handle network errors gracefully
- Show validation errors from backend
- Implement retry logic for failed requests
- Log errors for debugging

#### CORS Configuration
Backend has `@CrossOrigin(origins = "*")` enabled, so no CORS issues expected.

---

### Data Flow Examples

#### User Registration Flow
1. User fills registration form
2. Validate email format, password strength
3. Check if username/email exists: `GET /api/users/exists/username/{username}`
4. Create user: `POST /api/users` with user data
5. Auto-login or redirect to login page

#### Add to Cart Flow
1. User clicks "Add to Cart" on product
2. Check if user has cart: `GET /api/carts/user/{userId}`
3. If no cart, create one: `POST /api/carts`
4. Check if product already in cart: `GET /api/cart-items/cart/{cartId}/product/{productId}`
5. If exists, update quantity: `PUT /api/cart-items/{id}`
6. If not, add new item: `POST /api/cart-items`
7. Update cart item count in navbar

#### Checkout Flow
1. Display cart items: `GET /api/cart-items/cart/{cartId}`
2. User selects shipping address: `GET /api/addresses/user/{userId}`
3. User enters payment details
4. Create order: `POST /api/orders` with items and address
5. Process payment: `POST /api/payments`
6. Clear cart: `DELETE /api/cart-items/cart/{cartId}`
7. Show order confirmation

#### Admin Product Management Flow
1. Display products: `GET /api/products`
2. Add product: `POST /api/products` (select category from dropdown)
3. Edit product: `PUT /api/products/{id}`
4. Delete product: `DELETE /api/products/{id}` (with confirmation)
5. Low stock alert: `GET /api/products/stock/low?threshold=10`

---

### State Management Structure

```javascript
// Suggested Redux/Zustand store structure
{
  auth: {
    user: { userId, username, email, role },
    isAuthenticated: boolean,
    token: string
  },
  products: {
    items: [],
    loading: boolean,
    error: string,
    filters: { category, minPrice, maxPrice, search }
  },
  cart: {
    cartId: string,
    items: [],
    totalItems: number,
    totalPrice: number
  },
  orders: {
    items: [],
    loading: boolean,
    selectedOrder: {}
  },
  categories: {
    items: []
  },
  addresses: {
    items: []
  }
}
```

---

### Performance Optimization
- Implement pagination for product lists (20-30 items per page)
- Lazy load images with placeholders
- Debounce search input (300ms)
- Cache frequently accessed data (categories)
- Optimize bundle size with code splitting
- Use React.memo/useMemo for expensive computations
- Implement infinite scroll for product listing (optional)

---

### Testing Recommendations
- Unit tests for components (Jest + React Testing Library)
- Integration tests for user flows (checkout, login)
- E2E tests for critical paths (Cypress/Playwright)
- API mock tests during development
- Test responsive design on multiple devices

---

### Deployment Considerations
- Environment variables for API URL
- Build optimization for production
- Static asset hosting (Vercel, Netlify, AWS S3)
- Configure CORS if deploying to different domain
- Set up CI/CD pipeline
- Monitor frontend performance (Lighthouse, Web Vitals)

---

## Getting Started Steps

1. **Setup Project**
   - Initialize project with chosen framework
   - Install dependencies (axios, router, state management, UI library)
   - Configure TypeScript (recommended)
   - Set up folder structure (components, pages, services, utils, store)

2. **Configure API Service**
   - Create axios instance with base URL
   - Implement API service layer for all endpoints
   - Add request/response interceptors for auth tokens
   - Create error handling utility

3. **Implement Authentication**
   - Create login/register pages
   - Implement auth state management
   - Set up protected routes
   - Add token storage and auto-logout

4. **Build Core Features**
   - Start with product listing and detail pages
   - Implement cart functionality
   - Create checkout flow
   - Add order management
   - Build admin dashboard

5. **Polish & Test**
   - Add loading states and error handling
   - Implement responsive design
   - Test all user flows
   - Fix bugs and optimize performance

---

## Example API Usage Scenarios

### Scenario 1: Display Product Catalog
```
GET /api/products → Display all products
GET /api/categories → Show category filter options
GET /api/products/category/{categoryId} → Filter by category
GET /api/products/search/shirt → Search products
GET /api/products/price?min=50&max=200 → Filter by price
```

### Scenario 2: Complete Purchase
```
POST /api/users → Register user
GET /api/carts/user/{userId} → Get user's cart
POST /api/cart-items → Add products to cart
GET /api/addresses/user/{userId} → Get shipping addresses
POST /api/orders → Create order
POST /api/payments → Process payment
POST /api/shipments → Create shipment (admin)
```

### Scenario 3: Track Order
```
GET /api/orders/user/{userId} → Get user's orders
GET /api/orders/{orderId} → Get order details
GET /api/payments/order/{orderId} → Check payment status
GET /api/shipments/order/{orderId} → Get shipment info
```

---

## Additional Notes

- **Backend Port**: `8181`
- **Database**: MySQL on port `3306` (database: `OnlineShopping`)
- **Order Statuses**: PENDING, PAID, SHIPPED, DELIVERED, CANCELED
- **Payment Statuses**: PENDING, COMPLETED, FAILED, REFUNDED
- **Shipment Statuses**: PREPARING, SHIPPED, IN_TRANSIT, DELIVERED
- **User Roles**: CUSTOMER, ADMIN
- **All IDs**: String type (UUID recommended)
- **Dates**: ISO 8601 format (e.g., "2024-11-14T10:30:00")

---

## Success Criteria

✅ Users can browse and search products  
✅ Users can register and login  
✅ Users can add products to cart and checkout  
✅ Users can view order history and track shipments  
✅ Admins can manage products, categories, orders  
✅ Responsive design works on mobile, tablet, desktop  
✅ Proper error handling and user feedback  
✅ Secure authentication with role-based access  
✅ Fast page loads and smooth interactions  
✅ Clean, maintainable code structure  

---

**Ready to build an amazing e-commerce frontend!** 🚀
