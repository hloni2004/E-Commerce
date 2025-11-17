# Product Image Upload Implementation

## Overview
This document describes the implementation of product image upload functionality using LOB (Large Object) storage in the database. Images are stored directly in the MySQL database as LONGBLOB and can be uploaded/retrieved through REST API endpoints.

---

## Backend Implementation

### 1. Database Changes

**Product Entity Updates:**
- Added `imageData` field with `@Lob` annotation (LONGBLOB column type)
- Added `imageType` field to store MIME type (image/jpeg, image/png, etc.)
- Kept `imageUrl` for backward compatibility

```java
@Lob
@Column(name = "image_data", columnDefinition = "LONGBLOB")
private byte[] imageData;

@Column(name = "image_type")
private String imageType;
```

### 2. REST API Endpoints

**ProductController New Endpoints:**

#### Upload Product with Image
```
POST /api/products/upload
Content-Type: multipart/form-data

Parameters:
- name: string (required)
- description: string (required)
- price: double (required)
- stockQuantity: integer (required)
- categoryId: string (required)
- image: file (optional) - Image file (JPG, PNG, GIF)
```

#### Update Product Image
```
PUT /api/products/{id}/image
Content-Type: multipart/form-data

Parameters:
- image: file (required) - New image file
```

#### Get Product Image
```
GET /api/products/{id}/image
Returns: Image bytes with appropriate Content-Type header
```

### 3. Sample Data Generation

**DataInitializer Component:**
- Automatically creates 5 categories on application startup
- Generates 15 sample products with placeholder images
- Each product gets a unique colored placeholder image with product name
- Images are generated programmatically using Java AWT

**Generated Categories:**
1. Men's Clothing
2. Women's Clothing
3. Accessories
4. Footwear
5. Sports & Outdoor

**Sample Products (3 per category):**
- Men's: Classic Denim Jacket, Cotton Polo Shirt, Slim Fit Chinos
- Women's: Floral Summer Dress, Elegant Blouse, High-Waist Jeans
- Accessories: Leather Belt, Designer Sunglasses, Leather Wallet
- Footwear: Running Sneakers, Casual Loafers, High-Top Sneakers
- Sports: Performance T-Shirt, Running Shorts, Windbreaker Jacket

### 4. Image Generation

The `DataInitializer` creates placeholder images programmatically:
- 400x400 pixel JPEG images
- Colored background unique to each product
- Product name text centered
- Gradient overlay for visual appeal
- White border decoration

---

## Frontend Implementation

### 1. Admin Dashboard Updates

**Image Upload Form:**
- File input with drag-and-drop support
- Image preview before upload
- File type validation (image/* only)
- File size validation (max 5MB)
- Clear/remove image button

**Features:**
- Upload new product images during creation
- Update existing product images
- Display images from database via API endpoint
- Fallback to placeholder if image fails to load

### 2. Product Display

**ProductCard Component:**
- Displays images using endpoint: `GET /api/products/{id}/image`
- Fallback to placeholder on error
- Responsive image sizing with aspect ratio
- Currency display in ZAR (R)

### 3. API Integration

**Creating Product with Image:**
```javascript
const formData = new FormData();
formData.append('name', 'Product Name');
formData.append('description', 'Description');
formData.append('price', '999.99');
formData.append('stockQuantity', '50');
formData.append('categoryId', categoryId);
formData.append('image', imageFile);

await api.post('/products/upload', formData, {
  headers: { 'Content-Type': 'multipart/form-data' }
});
```

**Updating Product Image:**
```javascript
const formData = new FormData();
formData.append('image', imageFile);

await api.put(`/products/${productId}/image`, formData, {
  headers: { 'Content-Type': 'multipart/form-data' }
});
```

**Displaying Product Image:**
```jsx
<img 
  src={`http://localhost:8181/api/products/${productId}/image`}
  alt={productName}
  onError={(e) => {
    e.target.src = 'fallback-placeholder-url';
  }}
/>
```

---

## Testing the Implementation

### 1. Start the Backend
```bash
cd OnlineClothingStore
mvn spring-boot:run
```

On startup, you should see:
```
✓ Admin user created successfully
✓ 5 categories created
✓ 15 sample products created with images
✓ Sample data initialized successfully
```

### 2. Start the Frontend
```bash
cd stylesphere-studio
npm run dev
```

### 3. Test Admin Image Upload

1. Navigate to `http://localhost:5173/login`
2. Login with admin credentials:
   - Username: `admin`
   - Password: `Admin@123`
3. You'll be redirected to Admin Dashboard
4. Click "Add Product" button
5. Fill in product details
6. Click file input or drag image to upload area
7. See image preview
8. Select category and submit
9. Product should appear in list with image

### 4. Test Product Display

1. Navigate to Products page
2. All 15 sample products should display with generated images
3. Each product shows unique colored placeholder
4. Product names are visible in the images
5. Currency displayed as ZAR (R)

### 5. Test Image Update

1. In Admin Dashboard, click Edit on any product
2. See current image preview
3. Upload new image file
4. Save changes
5. Image should update immediately

---

## Technical Details

### Database Storage

**Advantages:**
- Single source of truth
- Transactional consistency
- No file system management
- Backup includes images
- Access control via database

**Considerations:**
- Larger database size
- Network transfer for each image request
- Not ideal for very large images (recommend max 5MB)

### Image Format Support

Supported formats:
- JPEG/JPG
- PNG
- GIF
- WebP (if browser supports)

### Performance Optimization Ideas

For production, consider:
1. Add image caching headers
2. Implement CDN for image delivery
3. Generate thumbnails for listings
4. Compress images before storage
5. Add lazy loading on frontend

---

## File Changes Summary

### Backend Files Modified/Created:
1. `Product.java` - Added imageData and imageType fields
2. `ProductController.java` - Added upload, update image, get image endpoints
3. `DataInitializer.java` - NEW: Generates sample data with images
4. `AdminInitializer.java` - Added @Order(1) annotation

### Frontend Files Modified:
1. `AdminDashboard.tsx` - Added image upload form with preview
2. `ProductCard.tsx` - Updated to display images from API
3. `FRONTEND_PROMPT.md` - Documented image upload endpoints

---

## Admin Credentials

**Default Admin Account:**
- Username: `admin`
- Password: `Admin@123`
- Email: `admin@stylehub.com`
- Role: `ADMIN`

This account is created automatically on first application startup.

---

## Next Steps

Potential enhancements:
1. Add image compression before upload
2. Support multiple product images
3. Add image cropping/editing tools
4. Implement image zoom on product details
5. Add image quality settings
6. Create admin panel to manage categories
7. Add bulk product import with images
8. Implement image CDN integration

---

## Troubleshooting

**Images not displaying:**
- Check backend is running on port 8181
- Verify image was uploaded successfully
- Check browser console for CORS errors
- Ensure product has imageData in database

**Upload fails:**
- Check file size < 5MB
- Verify file is valid image format
- Check category exists in database
- View backend logs for errors

**Sample data not created:**
- Check database is empty before first run
- Verify MySQL connection is working
- Check console logs for initialization errors
- Ensure DataInitializer is enabled
