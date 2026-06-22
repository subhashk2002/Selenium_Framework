# Test Cases Specifications & Implementation Roadmap

## Test Cases Overview

### Total Test Cases: 30
- UI Tests: 15 tests
- API Tests: 15 tests

---

## UI TEST CASES (15 Tests)

### Module 1: Authentication (3 Tests)

#### TC-001: Valid User Login
```
Test Class: LoginTests
Method: testValidLogin()
Category: Authentication
Priority: P0 (Critical)

Preconditions:
  - Browser is open
  - User is on Login page
  - Valid credentials available in test data

Test Steps:
  1. Navigate to login page
  2. Enter valid username
  3. Enter valid password
  4. Click Login button
  5. Wait for page redirect
  6. Verify landing on Dashboard/Home page
  7. Verify user name displayed in header
  8. Verify welcome message is visible

Expected Results:
  - User successfully logs in
  - Redirected to home/dashboard page
  - User information displayed
  - No error messages
  - Session created successfully

Test Data:
  - Username: validuser@test.com
  - Password: Test@1234
  - Expected Landing Page: /dashboard

Assertions:
  - Assert current URL contains "dashboard"
  - Assert user name visible
  - Assert welcome message visible
  - Assert no error messages present

Dependencies:
  - LoginPage
  - HomePage
  - AuthActions

Expected Duration: 10 seconds
Retry Count: 2
```

#### TC-002: Invalid User Login
```
Test Class: LoginTests
Method: testInvalidLogin()
Category: Authentication
Priority: P1 (High)

Preconditions:
  - Browser is open
  - User is on Login page
  - Invalid credentials provided

Test Steps:
  1. Navigate to login page
  2. Enter invalid username
  3. Enter invalid password
  4. Click Login button
  5. Wait for error message
  6. Verify error message displayed

Expected Results:
  - Login fails
  - Error message displayed: "Invalid username or password"
  - User remains on login page
  - No session created

Test Data:
  - Username: invaliduser@test.com
  - Password: wrongpass123
  - Expected Error: "Invalid username or password"

Assertions:
  - Assert error message text matches expected
  - Assert current URL still on login page
  - Assert user not logged in

Dependencies:
  - LoginPage
  - AuthActions

Expected Duration: 8 seconds
Retry Count: 1
```

#### TC-003: Password Reset Workflow
```
Test Class: ForgotPasswordTests
Method: testPasswordReset()
Category: Authentication
Priority: P1 (High)

Preconditions:
  - Browser is open
  - User is on Login page
  - User account exists with valid email

Test Steps:
  1. Click "Forgot Password?" link
  2. Enter registered email address
  3. Click "Send Reset Link" button
  4. Verify success message
  5. Check email (mock) for reset link
  6. Click reset link in email
  7. Enter new password
  8. Confirm new password
  9. Click "Reset Password" button
  10. Verify password reset success message
  11. Login with new password

Expected Results:
  - Password reset email sent
  - Reset link valid
  - Password successfully changed
  - Can login with new password
  - Old password no longer works

Test Data:
  - Email: validuser@test.com
  - New Password: NewPass@2024
  - Confirm Password: NewPass@2024

Assertions:
  - Assert success message shown
  - Assert email received (in mock)
  - Assert new password accepted
  - Assert old password rejected

Dependencies:
  - LoginPage
  - ForgotPasswordPage
  - AuthActions

Expected Duration: 20 seconds
Retry Count: 1
```

### Module 2: Product Management (4 Tests)

#### TC-004: Search for Product
```
Test Class: ProductSearchTests
Method: testSearchProduct()
Category: Product Management
Priority: P0 (Critical)

Preconditions:
  - User is logged in
  - User is on Product page
  - At least one product exists with search keyword

Test Steps:
  1. Navigate to Products page
  2. Click search box
  3. Enter search keyword: "laptop"
  4. Press Enter or Click Search button
  5. Wait for results to load
  6. Verify search results displayed
  7. Verify all results contain keyword

Expected Results:
  - Search results displayed
  - All results contain search keyword
  - Result count > 0
  - Product names visible
  - Product prices visible

Test Data:
  - Search Keyword: "laptop"
  - Expected Min Results: 1

Assertions:
  - Assert result count > 0
  - Assert all results contain keyword
  - Assert result list loaded
  - Assert product information displayed

Dependencies:
  - SearchPage
  - ProductListPage
  - SearchActions

Expected Duration: 8 seconds
Retry Count: 1
```

#### TC-005: Filter Products by Category
```
Test Class: ProductFilterTests
Method: testFilterProducts()
Category: Product Management
Priority: P1 (High)

Preconditions:
  - User is logged in
  - User is on Products page
  - Multiple product categories available

Test Steps:
  1. Navigate to Products page
  2. Identify filter section
  3. Click on category filter
  4. Select "Electronics" category
  5. Wait for results to update
  6. Verify filtered results displayed
  7. Verify all products are in selected category

Expected Results:
  - Products filtered by category
  - Only Electronics products shown
  - Filter selection persisted
  - Result count accurate

Test Data:
  - Category: "Electronics"
  - Expected Product Count: > 0

Assertions:
  - Assert filter applied
  - Assert all results in category
  - Assert result list not empty

Dependencies:
  - ProductListPage
  - ProductActions

Expected Duration: 8 seconds
Retry Count: 1
```

#### TC-006: Sort Products by Price (Ascending)
```
Test Class: ProductFilterTests
Method: testSortProducts()
Category: Product Management
Priority: P2 (Medium)

Preconditions:
  - User is logged in
  - User is on Products page
  - Multiple products available

Test Steps:
  1. Navigate to Products page
  2. Identify sort dropdown
  3. Click sort dropdown
  4. Select "Price: Low to High"
  5. Wait for results to reorder
  6. Verify products sorted by price ascending
  7. Compare prices of first and last product

Expected Results:
  - Products sorted by ascending price
  - First product price <= Last product price
  - All products visible with prices
  - Sorting applied correctly

Test Data:
  - Sort Type: "Price: Low to High"

Assertions:
  - Assert first product price <= last product price
  - Assert all products displayed
  - Assert prices in ascending order

Dependencies:
  - ProductListPage
  - ProductActions

Expected Duration: 8 seconds
Retry Count: 1
```

#### TC-007: View Product Details
```
Test Class: ProductDetailTests
Method: testProductDetail()
Category: Product Management
Priority: P1 (High)

Preconditions:
  - User is logged in
  - User is on Products page
  - At least one product available

Test Steps:
  1. Navigate to Products page
  2. Click on product name/image
  3. Wait for product detail page to load
  4. Verify product name displayed
  5. Verify product description displayed
  6. Verify product price displayed
  7. Verify product images displayed
  8. Verify Add to Cart button available
  9. Verify reviews section visible

Expected Results:
  - Product detail page loaded
  - All product information displayed
  - Images loaded correctly
  - Add to Cart button clickable
  - Reviews visible

Test Data:
  - Product ID: 1 (from test data)

Assertions:
  - Assert product name matches
  - Assert price displayed
  - Assert description visible
  - Assert images loaded
  - Assert Add to Cart button enabled

Dependencies:
  - ProductDetailPage
  - ProductActions

Expected Duration: 10 seconds
Retry Count: 1
```

### Module 3: Shopping Cart (4 Tests)

#### TC-008: Add Product to Cart
```
Test Class: CartTests
Method: testAddToCart()
Category: Shopping Cart
Priority: P0 (Critical)

Preconditions:
  - User is logged in
  - User is viewing product details
  - Product is in stock

Test Steps:
  1. View product details
  2. Enter quantity (e.g., 2)
  3. Click "Add to Cart" button
  4. Wait for confirmation
  5. Verify success message
  6. Navigate to cart
  7. Verify product in cart
  8. Verify quantity correct
  9. Verify price calculation correct

Expected Results:
  - Product added to cart
  - Cart count incremented
  - Correct quantity shown
  - Price calculated correctly
  - Success message displayed

Test Data:
  - Product: "Laptop"
  - Quantity: 2
  - Expected Price: 1200.00 (per item)
  - Expected Total: 2400.00

Assertions:
  - Assert product in cart
  - Assert quantity = 2
  - Assert total price = 2400.00
  - Assert cart count incremented

Dependencies:
  - ProductDetailPage
  - CartPage
  - CartActions

Expected Duration: 8 seconds
Retry Count: 1
```

#### TC-009: Remove Product from Cart
```
Test Class: CartTests
Method: testRemoveFromCart()
Category: Shopping Cart
Priority: P1 (High)

Preconditions:
  - User is logged in
  - User has items in cart
  - Cart page accessible

Test Steps:
  1. Navigate to Cart page
  2. Identify product to remove
  3. Click Remove button for product
  4. Confirm removal if prompted
  5. Wait for cart update
  6. Verify product removed
  7. Verify cart total recalculated
  8. Verify cart count decremented

Expected Results:
  - Product removed from cart
  - Cart updated
  - Total price recalculated
  - Empty cart message shown (if no items left)

Test Data:
  - Product to Remove: "Laptop"

Assertions:
  - Assert product not in cart
  - Assert cart count decremented
  - Assert total updated

Dependencies:
  - CartPage
  - CartActions

Expected Duration: 8 seconds
Retry Count: 1
```

#### TC-010: Update Product Quantity in Cart
```
Test Class: CartOperationsTests
Method: testUpdateQuantity()
Category: Shopping Cart
Priority: P1 (High)

Preconditions:
  - User is logged in
  - User has items in cart
  - Quantity can be increased/decreased

Test Steps:
  1. Navigate to Cart page
  2. Identify product to update
  3. Click quantity increment button 3 times
  4. Wait for cart update
  5. Verify new quantity displayed
  6. Verify total price updated
  7. Click quantity decrement button 1 time
  8. Verify quantity decreased
  9. Verify total recalculated

Expected Results:
  - Quantity updated correctly
  - Total price recalculated
  - Cart reflects changes
  - Quantity bounds respected (min 1, max in stock)

Test Data:
  - Initial Quantity: 2
  - Increment to: 5
  - Decrement to: 4
  - Expected Total: (depends on product price)

Assertions:
  - Assert quantity = 4
  - Assert total price updated
  - Assert cart consistent

Dependencies:
  - CartPage
  - CartActions

Expected Duration: 8 seconds
Retry Count: 1
```

#### TC-011: Checkout Process
```
Test Class: CheckoutTests
Method: testCheckoutFlow()
Category: Shopping Cart
Priority: P0 (Critical)

Preconditions:
  - User is logged in
  - User has items in cart
  - Checkout page accessible

Test Steps:
  1. Navigate to Cart page
  2. Verify cart totals
  3. Click "Proceed to Checkout" button
  4. Verify shipping address form
  5. Enter/confirm shipping address
  6. Enter shipping method preference
  7. Click "Continue to Payment"
  8. Verify payment information form
  9. Enter card details (test card)
  10. Click "Place Order"
  11. Verify order confirmation page
  12. Verify order number displayed
  13. Verify order total matches

Expected Results:
  - Checkout completed successfully
  - Order created
  - Order number displayed
  - Confirmation email triggered
  - Order visible in order history

Test Data:
  - Shipping Address: "123 Test St, Test City"
  - Shipping Method: "Standard (5-7 days)"
  - Card Number: 4111111111111111
  - Expiry: 12/25
  - CVV: 123

Assertions:
  - Assert order created
  - Assert order number generated
  - Assert order total correct
  - Assert confirmation page displayed

Dependencies:
  - CartPage
  - CheckoutPage
  - CartActions

Expected Duration: 15 seconds
Retry Count: 1
```

### Module 4: User Profile (2 Tests)

#### TC-012: Update User Profile
```
Test Class: ProfileTests
Method: testUpdateProfile()
Category: User Profile
Priority: P1 (High)

Preconditions:
  - User is logged in
  - User is on Profile page
  - Profile editable

Test Steps:
  1. Navigate to Profile page
  2. Click "Edit Profile" button
  3. Update First Name
  4. Update Last Name
  5. Update Phone Number
  6. Upload profile picture (optional)
  7. Click "Save Changes" button
  8. Wait for confirmation
  9. Verify success message
  10. Verify changes persisted
  11. Log out and log back in
  12. Verify changes still present

Expected Results:
  - Profile updated successfully
  - Changes persisted in database
  - Success message displayed
  - No validation errors

Test Data:
  - First Name: "John"
  - Last Name: "Updated"
  - Phone: "+1234567890"
  - Picture: test_image.jpg

Assertions:
  - Assert first name updated
  - Assert last name updated
  - Assert phone updated
  - Assert success message shown

Dependencies:
  - ProfilePage
  - CommonActions

Expected Duration: 10 seconds
Retry Count: 1
```

#### TC-013: Change Password
```
Test Class: SettingsTests
Method: testChangePassword()
Category: User Profile
Priority: P1 (High)

Preconditions:
  - User is logged in
  - User is on Settings page
  - Old password known

Test Steps:
  1. Navigate to Settings page
  2. Click "Change Password" section
  3. Enter current password
  4. Enter new password
  5. Confirm new password
  6. Click "Update Password" button
  7. Verify success message
  8. Log out
  9. Try login with old password (should fail)
  10. Login with new password (should succeed)

Expected Results:
  - Password changed successfully
  - Old password no longer works
  - New password works
  - Session maintained after change
  - No error messages

Test Data:
  - Current Password: Test@1234
  - New Password: NewTest@5678
  - Confirm Password: NewTest@5678

Assertions:
  - Assert success message
  - Assert old password rejected
  - Assert new password accepted

Dependencies:
  - ProfilePage
  - AuthActions

Expected Duration: 12 seconds
Retry Count: 1
```

### Module 5: End-to-End Tests (2 Tests)

#### TC-014: End-to-End Purchase (Login to Checkout)
```
Test Class: E2ELoginToCheckoutTests
Method: testEndToEndPurchase()
Category: Integration
Priority: P0 (Critical)

Preconditions:
  - Browser is open
  - Product inventory available
  - Application is responsive

Test Steps:
  1. Navigate to application
  2. Login with valid credentials
  3. Search for product: "laptop"
  4. Click on first product
  5. Enter quantity: 1
  6. Add to cart
  7. Navigate to cart
  8. Verify product and total
  9. Click checkout
  10. Enter shipping address
  11. Select shipping method
  12. Enter payment details
  13. Place order
  14. Verify order confirmation
  15. Verify order in order history

Expected Results:
  - Complete purchase workflow successful
  - Order created with correct details
  - Order visible in user profile
  - No errors during process

Test Data:
  - Username: testuser@test.com
  - Password: Test@1234
  - Product: "laptop"
  - Quantity: 1
  - Shipping: "123 Test St"
  - Card: 4111111111111111

Assertions:
  - Assert order created
  - Assert order total correct
  - Assert confirmation received
  - Assert order in history

Dependencies:
  - LoginPage
  - ProductListPage
  - CartPage
  - CheckoutPage
  - AuthActions
  - CartActions

Expected Duration: 30 seconds
Retry Count: 1
```

#### TC-015: Guest Checkout
```
Test Class: FullUserJourneyTests
Method: testGuestCheckout()
Category: Integration
Priority: P1 (High)

Preconditions:
  - Browser is open
  - Product available
  - Guest checkout enabled

Test Steps:
  1. Navigate to application
  2. Search for product without logging in
  3. Click on product
  4. Add to cart
  5. Proceed to checkout
  6. Click "Continue as Guest" or skip login
  7. Enter guest email: guest@test.com
  8. Enter shipping address
  9. Select shipping method
  10. Enter payment details
  11. Place order
  12. Verify order confirmation
  13. Verify confirmation email triggered

Expected Results:
  - Guest can complete purchase
  - Order created without account
  - Confirmation email sent
  - Order details provided

Test Data:
  - Product: "laptop"
  - Quantity: 1
  - Guest Email: guest@test.com
  - Shipping: "456 Guest Ave"
  - Card: 4111111111111111

Assertions:
  - Assert order created for guest
  - Assert confirmation email triggered
  - Assert order total correct

Dependencies:
  - ProductListPage
  - CartPage
  - CheckoutPage
  - CartActions

Expected Duration: 25 seconds
Retry Count: 1
```

---

## API TEST CASES (15 Tests)

### Module 1: Authentication API (3 Tests)

#### TC-016: Login API - Valid Credentials
```
Test Class: LoginAPITests
Method: testLoginAPI()
Category: API - Authentication
Priority: P0 (Critical)

Endpoint: POST /api/v1/auth/login
Request Method: POST
Content-Type: application/json

Request Body:
{
  "username": "validuser@test.com",
  "password": "Test@1234"
}

Expected Response:
{
  "statusCode": 200,
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIs...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
    "userId": 1,
    "username": "validuser@test.com",
    "email": "validuser@test.com"
  },
  "message": "Login successful"
}

Test Steps:
  1. Create request with valid credentials
  2. Send POST request to /api/v1/auth/login
  3. Verify status code = 200
  4. Verify response contains token
  5. Verify refreshToken present
  6. Verify userId in response
  7. Verify username matches

Expected Results:
  - Status code: 200 OK
  - Auth tokens generated
  - User information returned
  - Response time < 2 seconds
  - No errors in response

Assertions:
  - Assert statusCode == 200
  - Assert token not empty
  - Assert userId == 1
  - Assert response time < 2000ms

Dependencies:
  - RestAssured
  - APIHelper
  - AuthTokenManager

Expected Duration: 2 seconds
Retry Count: 1
```

#### TC-017: Token Refresh API
```
Test Class: TokenRefreshAPITests
Method: testTokenRefresh()
Category: API - Authentication
Priority: P1 (High)

Endpoint: POST /api/v1/auth/refresh
Request Method: POST
Content-Type: application/json

Request Body:
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIs..."
}

Expected Response:
{
  "statusCode": 200,
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIs...",
    "expiresIn": 3600
  },
  "message": "Token refreshed successfully"
}

Test Steps:
  1. Call login API to get refresh token
  2. Extract refresh token from response
  3. Wait for original token to expire (or simulate expiry)
  4. Send refresh token to /api/v1/auth/refresh
  5. Verify new token generated
  6. Verify new token is different from old
  7. Verify new token valid for API calls

Expected Results:
  - Status code: 200
  - New token generated
  - New token different from old
  - Expiry time provided

Assertions:
  - Assert statusCode == 200
  - Assert newToken != oldToken
  - Assert expiresIn > 0

Dependencies:
  - RestAssured
  - AuthTokenManager
  - LoginAPITests (dependency)

Expected Duration: 3 seconds
Retry Count: 1
```

#### TC-018: Logout API
```
Test Class: LogoutAPITests
Method: testLogoutAPI()
Category: API - Authentication
Priority: P1 (High)

Endpoint: POST /api/v1/auth/logout
Request Method: POST
Headers:
  - Authorization: Bearer {token}

Expected Response:
{
  "statusCode": 200,
  "success": true,
  "message": "Logout successful"
}

Test Steps:
  1. Login to get valid token
  2. Extract token from login response
  3. Send logout request with token
  4. Verify status code = 200
  5. Verify token invalidated
  6. Try to use old token (should fail)
  7. Verify 401 error on token use

Expected Results:
  - Status code: 200
  - Session invalidated
  - Token no longer usable
  - User logged out

Assertions:
  - Assert statusCode == 200
  - Assert token invalidated
  - Assert subsequent API call with token returns 401

Dependencies:
  - RestAssured
  - LoginAPITests (dependency)

Expected Duration: 2 seconds
Retry Count: 1
```

### Module 2: User API (3 Tests)

#### TC-019: Get User Profile API
```
Test Class: UserAPITests
Method: testGetUserProfile()
Category: API - User
Priority: P1 (High)

Endpoint: GET /api/v1/user/{userId}
Request Method: GET
Headers:
  - Authorization: Bearer {token}

Path Parameters:
  - userId: 1

Expected Response:
{
  "statusCode": 200,
  "success": true,
  "data": {
    "userId": 1,
    "username": "validuser@test.com",
    "email": "validuser@test.com",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+1234567890",
    "profilePicture": "url/to/picture",
    "createdAt": "2024-01-01T10:00:00Z",
    "updatedAt": "2024-06-22T10:00:00Z"
  }
}

Test Steps:
  1. Get valid auth token
  2. Send GET request to /api/v1/user/1
  3. Include token in Authorization header
  4. Verify status code = 200
  5. Verify response contains user data
  6. Verify user ID matches request
  7. Verify all required fields present

Expected Results:
  - Status code: 200
  - User profile returned
  - All user information present
  - Response time < 1 second

Assertions:
  - Assert statusCode == 200
  - Assert userId == 1
  - Assert email == "validuser@test.com"
  - Assert firstName present

Dependencies:
  - RestAssured
  - LoginAPITests (for token)

Expected Duration: 1 second
Retry Count: 1
```

#### TC-020: Update User Profile API
```
Test Class: UserProfileAPITests
Method: testUpdateUserAPI()
Category: API - User
Priority: P1 (High)

Endpoint: PUT /api/v1/user/{userId}
Request Method: PUT
Headers:
  - Authorization: Bearer {token}
  - Content-Type: application/json

Request Body:
{
  "firstName": "Jane",
  "lastName": "Updated",
  "phone": "+9876543210"
}

Expected Response:
{
  "statusCode": 200,
  "success": true,
  "data": {
    "userId": 1,
    "firstName": "Jane",
    "lastName": "Updated",
    "phone": "+9876543210"
  },
  "message": "User updated successfully"
}

Test Steps:
  1. Get valid auth token
  2. Prepare update request body
  3. Send PUT request to /api/v1/user/1
  4. Include updated data
  5. Verify status code = 200
  6. Verify response contains updated fields
  7. Get user profile again to verify persistence
  8. Verify new values in retrieved profile

Expected Results:
  - Status code: 200
  - User data updated
  - Changes persisted in database
  - Verification GET returns updated data

Assertions:
  - Assert statusCode == 200
  - Assert firstName == "Jane"
  - Assert phone updated
  - Assert verification GET returns new values

Dependencies:
  - RestAssured
  - LoginAPITests
  - testGetUserProfile (for verification)

Expected Duration: 2 seconds
Retry Count: 1
```

#### TC-021: Delete User API
```
Test Class: UserDeletionAPITests
Method: testDeleteUser()
Category: API - User
Priority: P2 (Medium)

Endpoint: DELETE /api/v1/user/{userId}
Request Method: DELETE
Headers:
  - Authorization: Bearer {token}

Expected Response:
{
  "statusCode": 200,
  "success": true,
  "message": "User deleted successfully"
}

Test Steps:
  1. Create new test user via API
  2. Get user ID
  3. Get auth token for user
  4. Send DELETE request to /api/v1/user/{userId}
  5. Verify status code = 200
  6. Try to retrieve deleted user (should return 404)
  7. Verify 404 error
  8. Verify token no longer valid

Expected Results:
  - Status code: 200
  - User deleted from system
  - User data no longer retrievable
  - 404 on subsequent GET request

Assertions:
  - Assert delete statusCode == 200
  - Assert subsequent GET returns 404
  - Assert user not in system

Dependencies:
  - RestAssured
  - User creation API

Expected Duration: 3 seconds
Retry Count: 1
```

### Module 3: Product API (3 Tests)

#### TC-022: Get Products List API
```
Test Class: ProductAPITests
Method: testGetProducts()
Category: API - Product
Priority: P1 (High)

Endpoint: GET /api/v1/products
Request Method: GET
Query Parameters:
  - page: 1
  - limit: 20
  - sort: name (optional)

Expected Response:
{
  "statusCode": 200,
  "success": true,
  "data": {
    "products": [
      {
        "productId": 1,
        "name": "Laptop",
        "category": "Electronics",
        "price": 999.99,
        "stock": 50,
        "description": "...",
        "rating": 4.5,
        "reviews": 120
      },
      ...
    ],
    "totalCount": 100,
    "page": 1,
    "limit": 20,
    "totalPages": 5
  }
}

Test Steps:
  1. Send GET request to /api/v1/products
  2. Include pagination parameters
  3. Verify status code = 200
  4. Verify products array returned
  5. Verify pagination info correct
  6. Verify each product has required fields
  7. Verify product count <= limit
  8. Verify total count > 0

Expected Results:
  - Status code: 200
  - Product list returned
  - Pagination working
  - Product data complete
  - Response time < 2 seconds

Assertions:
  - Assert statusCode == 200
  - Assert products.length <= 20
  - Assert totalCount > 0
  - Assert each product has productId

Dependencies:
  - RestAssured
  - APIHelper

Expected Duration: 2 seconds
Retry Count: 1
```

#### TC-023: Search Products API
```
Test Class: ProductSearchAPITests
Method: testSearchProductAPI()
Category: API - Product
Priority: P1 (High)

Endpoint: GET /api/v1/products/search
Request Method: GET
Query Parameters:
  - keyword: "laptop"
  - category: "Electronics" (optional)
  - minPrice: 500 (optional)
  - maxPrice: 1500 (optional)

Expected Response:
{
  "statusCode": 200,
  "success": true,
  "data": {
    "products": [
      {
        "productId": 1,
        "name": "Laptop Pro",
        "category": "Electronics",
        "price": 1299.99,
        ...
      },
      ...
    ],
    "resultCount": 10
  }
}

Test Steps:
  1. Send GET request with search keyword
  2. Include optional filters
  3. Verify status code = 200
  4. Verify products returned
  5. Verify all products match keyword
  6. Verify filtering applied correctly
  7. Verify result count accurate

Expected Results:
  - Status code: 200
  - Search results returned
  - All results match search criteria
  - Filters applied correctly
  - Result count accurate

Assertions:
  - Assert statusCode == 200
  - Assert resultCount > 0
  - Assert all products contain keyword
  - Assert filters respected

Dependencies:
  - RestAssured
  - APIHelper

Expected Duration: 2 seconds
Retry Count: 1
```

#### TC-024: Get Product by ID API
```
Test Class: ProductListAPITests
Method: testGetProductById()
Category: API - Product
Priority: P1 (High)

Endpoint: GET /api/v1/products/{productId}
Request Method: GET
Path Parameters:
  - productId: 1

Expected Response:
{
  "statusCode": 200,
  "success": true,
  "data": {
    "productId": 1,
    "name": "Laptop",
    "category": "Electronics",
    "price": 999.99,
    "stock": 50,
    "description": "High-performance laptop...",
    "images": ["url1", "url2"],
    "rating": 4.5,
    "reviews": [
      {
        "reviewId": 1,
        "userId": 2,
        "rating": 5,
        "comment": "Great product!"
      }
    ]
  }
}

Test Steps:
  1. Send GET request to /api/v1/products/1
  2. Verify status code = 200
  3. Verify product ID matches
  4. Verify all fields present
  5. Verify reviews included
  6. Verify images array present
  7. Verify rating calculated

Expected Results:
  - Status code: 200
  - Complete product details returned
  - Reviews included
  - Images present
  - Rating shown

Assertions:
  - Assert statusCode == 200
  - Assert productId == 1
  - Assert name == "Laptop"
  - Assert reviews array present

Dependencies:
  - RestAssured
  - APIHelper

Expected Duration: 1 second
Retry Count: 1
```

### Module 4: Order API (3 Tests)

#### TC-025: Create Order API
```
Test Class: OrderAPITests
Method: testCreateOrder()
Category: API - Order
Priority: P0 (Critical)

Endpoint: POST /api/v1/orders
Request Method: POST
Headers:
  - Authorization: Bearer {token}
  - Content-Type: application/json

Request Body:
{
  "userId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2,
      "price": 999.99
    }
  ],
  "shippingAddress": "123 Test St, Test City",
  "shippingMethod": "standard",
  "paymentMethod": "credit_card",
  "cardDetails": {
    "cardNumber": "4111111111111111",
    "expiryMonth": 12,
    "expiryYear": 2025,
    "cvv": "123"
  }
}

Expected Response:
{
  "statusCode": 201,
  "success": true,
  "data": {
    "orderId": 1001,
    "userId": 1,
    "orderDate": "2024-06-22T10:00:00Z",
    "status": "CONFIRMED",
    "totalAmount": 1999.98,
    "items": [...],
    "shippingAddress": "123 Test St, Test City",
    "trackingNumber": "TRACK123456"
  }
}

Test Steps:
  1. Get valid auth token
  2. Prepare order request with items
  3. Send POST request to /api/v1/orders
  4. Verify status code = 201
  5. Verify order ID generated
  6. Verify order status = CONFIRMED
  7. Verify total amount calculated
  8. Verify tracking number generated
  9. Verify order stored in database

Expected Results:
  - Status code: 201 Created
  - Order created successfully
  - Order ID assigned
  - Tracking number generated
  - Order retrievable via GET

Assertions:
  - Assert statusCode == 201
  - Assert orderId generated
  - Assert status == "CONFIRMED"
  - Assert totalAmount == 1999.98

Dependencies:
  - RestAssured
  - LoginAPITests (for token)
  - Product API (for products)

Expected Duration: 2 seconds
Retry Count: 1
```

#### TC-026: Get Order Status API
```
Test Class: OrderStatusAPITests
Method: testGetOrderStatus()
Category: API - Order
Priority: P1 (High)

Endpoint: GET /api/v1/orders/{orderId}
Request Method: GET
Headers:
  - Authorization: Bearer {token}

Path Parameters:
  - orderId: 1001

Expected Response:
{
  "statusCode": 200,
  "success": true,
  "data": {
    "orderId": 1001,
    "userId": 1,
    "status": "CONFIRMED",
    "orderDate": "2024-06-22T10:00:00Z",
    "totalAmount": 1999.98,
    "items": [...],
    "trackingNumber": "TRACK123456",
    "estimatedDelivery": "2024-06-27",
    "shippingUpdates": [
      {
        "timestamp": "2024-06-22T12:00:00Z",
        "status": "SHIPPED",
        "message": "Package shipped"
      }
    ]
  }
}

Test Steps:
  1. Get valid auth token
  2. Create order via API
  3. Extract orderId from response
  4. Send GET request to /api/v1/orders/{orderId}
  5. Verify status code = 200
  6. Verify order details returned
  7. Verify shipping updates present
  8. Verify estimated delivery date

Expected Results:
  - Status code: 200
  - Order details retrieved
  - Current status shown
  - Shipping updates visible
  - Delivery estimate provided

Assertions:
  - Assert statusCode == 200
  - Assert orderId matches request
  - Assert status in valid values
  - Assert trackingNumber present

Dependencies:
  - RestAssured
  - testCreateOrder (dependency)

Expected Duration: 1 second
Retry Count: 1
```

#### TC-027: Cancel Order API
```
Test Class: OrderCancellationAPITests
Method: testCancelOrder()
Category: API - Order
Priority: P1 (High)

Endpoint: PUT /api/v1/orders/{orderId}/cancel
Request Method: PUT
Headers:
  - Authorization: Bearer {token}

Request Body:
{
  "reason": "Changed my mind"
}

Expected Response:
{
  "statusCode": 200,
  "success": true,
  "data": {
    "orderId": 1001,
    "status": "CANCELLED",
    "cancellationDate": "2024-06-22T11:00:00Z",
    "reason": "Changed my mind",
    "refundAmount": 1999.98
  },
  "message": "Order cancelled successfully"
}

Test Steps:
  1. Get valid auth token
  2. Create new order
  3. Extract orderId
  4. Send PUT request to cancel order
  5. Include cancellation reason
  6. Verify status code = 200
  7. Verify order status = CANCELLED
  8. Verify refund amount calculated
  9. Verify refund processing initiated

Expected Results:
  - Status code: 200
  - Order cancelled
  - Status updated to CANCELLED
  - Refund calculated
  - Refund initiated
  - Order not modifiable after cancellation

Assertions:
  - Assert statusCode == 200
  - Assert status == "CANCELLED"
  - Assert refundAmount == originalAmount
  - Assert cancellationDate present

Dependencies:
  - RestAssured
  - testCreateOrder (dependency)

Expected Duration: 2 seconds
Retry Count: 1
```

### Module 5: Cross-API Flow Tests (3 Tests)

#### TC-028: User Creation and Login Flow
```
Test Class: APIChainingTests
Method: testCreateUserAndLogin()
Category: API - Integration
Priority: P1 (High)

Workflow:
  1. Create new user via POST /api/v1/user/register
  2. Verify user created (statusCode 201)
  3. Login with created user credentials
  4. Verify login successful (statusCode 200)
  5. Verify token received
  6. Use token to get user profile
  7. Verify profile matches created data

Test Data:
  - Email: newuser_{{timestamp}}@test.com
  - Password: TempPass@123
  - FirstName: John
  - LastName: Doe

Expected Results:
  - User created successfully
  - Login successful
  - Token received
  - Profile retrievable
  - All data consistent

Assertions:
  - Assert user creation statusCode == 201
  - Assert login statusCode == 200
  - Assert token present
  - Assert profile matches creation data

Dependencies:
  - RestAssured
  - Multiple API calls chained

Expected Duration: 3 seconds
Retry Count: 1
```

#### TC-029: Complete Order Flow (Create to Status Check)
```
Test Class: CrossAPIFlowTests
Method: testCompleteOrderFlow()
Category: API - Integration
Priority: P0 (Critical)

Workflow:
  1. Login and get token
  2. Get product list and select product
  3. Create order with selected product
  4. Verify order created
  5. Get order status immediately
  6. Verify order is CONFIRMED
  7. Wait 5 seconds
  8. Get order status again
  9. Verify status updated to SHIPPED (if applicable)
  10. Verify tracking number updated

Test Data:
  - Product ID: 1
  - Quantity: 2
  - Address: 123 Test St

Expected Results:
  - Complete workflow successful
  - Status progression correct
  - Tracking information updated
  - All API calls consistent

Assertions:
  - Assert order created
  - Assert status transitions valid
  - Assert tracking updated
  - All response times acceptable

Dependencies:
  - RestAssured
  - Login, Product, Order APIs

Expected Duration: 10 seconds
Retry Count: 1
```

#### TC-030: Update and Retrieve User Workflow
```
Test Class: CompleteOrderFlowTests
Method: testUpdateAndRetrieveUser()
Category: API - Integration
Priority: P1 (High)

Workflow:
  1. Login and get token
  2. Get current user profile
  3. Update user profile (change name, phone)
  4. Verify update successful
  5. Immediately get user profile again
  6. Verify updated data returned
  7. Wait 2 seconds
  8. Get user profile again
  9. Verify data still updated
  10. Verify no data loss or reversion

Test Data:
  - New FirstName: UpdatedJohn
  - New Phone: +1111111111
  - Email: Should remain same

Expected Results:
  - Profile updated correctly
  - Changes immediately reflected
  - Changes persist
  - No data loss
  - All calls successful

Assertions:
  - Assert update statusCode == 200
  - Assert first GET returns old data before update
  - Assert subsequent GETs return updated data
  - Assert email unchanged

Dependencies:
  - RestAssured
  - Login and User APIs

Expected Duration: 5 seconds
Retry Count: 1
```

---

## Implementation Roadmap

### Phase 1: Foundation (Week 1-2)
```
Tasks:
  1. Setup Maven project structure
     - Create pom.xml with all dependencies
     - Create directory structure
     - Configure testng.xml
  
  2. Implement Configuration Layer
     - ConfigManager (Singleton)
     - PropertyReader
     - EnvironmentConfig (Enum)
     - FrameworkConstants
  
  3. Implement Driver Management Layer
     - DriverManager (ThreadLocal)
     - DriverFactory
     - CapabilityFactory
     - Browser capabilities classes
  
  4. Implement Wait Strategies
     - WaitHelper
     - CustomWait
     - WaitFactory

Deliverables:
  - Working project structure
  - Configurable driver setup
  - Thread-safe driver management
  - Explicit wait implementations

Estimated Hours: 40
```

### Phase 2: Base Classes & POM (Week 2-3)
```
Tasks:
  1. Implement Base Classes
     - BasePage
     - BaseTest
     - BaseActions
     - BaseAPI
  
  2. Create Page Object Model
     - LoginPage
     - RegisterPage
     - ProductListPage
     - ProductDetailPage
     - CartPage
     - CheckoutPage
     - ProfilePage
     - SearchPage
     - CommonPage
  
  3. Implement Action Classes
     - AuthActions
     - ProductActions
     - CartActions
     - SearchActions
     - CommonActions

Deliverables:
  - All page objects created
  - All action classes implemented
  - Reusable methods available
  - POM structure established

Estimated Hours: 50
```

### Phase 3: Data & Utilities (Week 3)
```
Tasks:
  1. Implement Data Handling
     - DataReader interface
     - JSONDataReader
     - ExcelDataReader
     - CSVDataReader
     - TestDataBuilder
  
  2. Implement Utility Classes
     - FileUtils
     - RandomDataGenerator
     - DateTimeUtils
     - ScreenshotUtils
     - EncryptionUtils
     - EmailValidator
  
  3. Create Test Data Files
     - JSON test data
     - Excel test data
     - CSV test data

Deliverables:
  - Data-driven testing capability
  - Utility functions available
  - Test data prepared
  - Multiple data source support

Estimated Hours: 30
```

### Phase 4: API Layer (Week 4)
```
Tasks:
  1. Implement API Layer
     - APIClient
     - APIHelper
     - AuthTokenManager
     - RequestSpecBuilder
     - ResponseSpecBuilder
  
  2. Create API Models
     - Request DTOs
     - Response DTOs
     - Test data models
  
  3. Create API Endpoints
     - AuthEndpoints
     - UserEndpoints
     - ProductEndpoints
     - OrderEndpoints

Deliverables:
  - Full API testing framework
  - All models created
  - All endpoints defined
  - Token management working

Estimated Hours: 35
```

### Phase 5: AI Integration (Week 4-5)
```
Tasks:
  1. Implement Ollama Integration
     - OllamaAIHelper
     - OllamaRequest/Response models
  
  2. Implement Self-Healing
     - SelfHealingHelper
     - Element locator recovery
  
  3. Implement AI Utilities
     - TestDataGeneratorAI
     - FailureAnalyzerAI

Deliverables:
  - Ollama integration working
  - Self-healing capabilities
  - AI test data generation
  - Failure analysis capability

Estimated Hours: 25
```

### Phase 6: Listeners & Retry (Week 5)
```
Tasks:
  1. Implement Listeners
     - TestListeners
     - RetryAnalyzer
     - ScreenshotListener
  
  2. Implement Retry Mechanism
     - RetryPolicy
     - Flaky test handling

Deliverables:
  - Test execution hooks working
  - Retry mechanism functioning
  - Screenshots captured
  - Automatic failure handling

Estimated Hours: 15
```

### Phase 7: Reporting (Week 5)
```
Tasks:
  1. Implement Reporting
     - ReportManager
     - AllureReportHelper
     - ExtentReportHelper
  
  2. Configure Reporting
     - Allure configuration
     - Report generation setup

Deliverables:
  - Reports generated
  - Allure integration working
  - Detailed test reports
  - Screenshots attached

Estimated Hours: 15
```

### Phase 8: UI Tests (Week 6)
```
Tasks:
  1. Create UI Test Cases
     - Auth tests (3 tests)
     - Product tests (4 tests)
     - Cart tests (4 tests)
     - User tests (2 tests)
     - E2E tests (2 tests)
  
  2. Execute & Debug
     - Run tests locally
     - Fix failures
     - Verify stability

Deliverables:
  - 15 UI tests created
  - All tests passing
  - Cross-browser compatibility verified

Estimated Hours: 40
```

### Phase 9: API Tests (Week 6-7)
```
Tasks:
  1. Create API Test Cases
     - Auth API tests (3 tests)
     - User API tests (3 tests)
     - Product API tests (3 tests)
     - Order API tests (3 tests)
     - Flow tests (3 tests)
  
  2. Execute & Debug
     - Run tests locally
     - Fix failures
     - Verify consistency

Deliverables:
  - 15 API tests created
  - All tests passing
  - Data consistency verified

Estimated Hours: 35
```

### Phase 10: CI/CD Integration (Week 7)
```
Tasks:
  1. Setup CI/CD
     - Jenkins configuration (if applicable)
     - GitHub Actions workflow
     - Parallel execution setup
  
  2. Performance Tuning
     - Optimize test execution
     - Parallel test configuration
     - Report optimization

Deliverables:
  - CI/CD pipeline working
  - Tests running in parallel
  - Reports accessible
  - Automated execution

Estimated Hours: 20
```

### Phase 11: Documentation (Week 7)
```
Tasks:
  1. Create Documentation
     - Setup guide
     - Architecture documentation
     - API documentation
     - Troubleshooting guide
  
  2. Create Best Practices
     - Contributing guidelines
     - Code standards
     - Maintenance procedures

Deliverables:
  - Complete documentation
  - Quick start guide
  - Architecture diagrams
  - Troubleshooting guide

Estimated Hours: 15
```

---

## Summary

**Total Estimated Hours: 320 hours (8 weeks)**

**Breakdown:**
- Foundation: 40 hours
- Base Classes & POM: 50 hours
- Data & Utilities: 30 hours
- API Layer: 35 hours
- AI Integration: 25 hours
- Listeners & Retry: 15 hours
- Reporting: 15 hours
- UI Tests: 40 hours
- API Tests: 35 hours
- CI/CD Integration: 20 hours
- Documentation: 15 hours

**Key Milestones:**
1. Week 2 - Framework foundation complete
2. Week 3 - All base classes and POM done
3. Week 4 - API layer complete
4. Week 5 - AI integration and reporting done
5. Week 6 - All tests created and passing
6. Week 7 - CI/CD setup and documentation complete

---

**Status**: Ready for Implementation
**Framework Version**: 1.0.0
**Created**: 2026-06-22

