product_list = [
    {"name": "Laptop", "price": 999.99, "quantity": 5},
    {"name": "Smartphone", "price": 499.99, "quantity": 10},
    {"name": "Tablet", "price": 299.99, "quantity": 0},
    {"name": "Smartwatch", "price": 199.99, "quantity": 3}
]


# Calculate the total inventory value
def total_value():
    total = 0
    for p in product_list:
        total += p["quantity"] * p["price"]
    return round(total, 2)


# Find the most expensive product
def most_expensive_products():
    max_product_name = ""
    max_product_price = 0
    for p in product_list:
        if p["price"] > max_product_price:
            max_product_price = p["price"]
            max_product_name = p["name"]
    return max_product_name


# Check if a product named "Headphones" is in stock
def is_product_in_stock():
    for p in product_list:
        if p["name"] == "Headphones":
            return True
    return False


# Sort products by price in descending order
def sorted_by_descending_order_price():
    return sorted(product_list, key=lambda p: p["price"], reverse=True)


# Sort products by price in ascending order
def sorted_by_ascending_order_price():
    return sorted(product_list, key=lambda p: p["price"], reverse=False)


# Sort products by price in descending order
def sorted_by_descending_order_quantity():
    return sorted(product_list, key=lambda p: p["quantity"], reverse=True)


# Sort products by price in ascending order
def sorted_by_ascending_order_quantity():
    return sorted(product_list, key=lambda p: p["quantity"], reverse=False)


print("Total inventory value: " + str(total_value()) + "\n")

print("The most expensive product : " + most_expensive_products() + "\n")

print("Is the a product named Headphones is in stock: " + str(is_product_in_stock()) + "\n")

print("Products sorted by price in descending order:")
for product in sorted_by_descending_order_price():
    print(str(product["name"]) + ": " + str(product["price"]))

print("\nProducts sorted by price in ascending order:")
for product in sorted_by_ascending_order_price():
    print(str(product["name"]) + ": " + str(product["price"]))

print("\nProducts sorted by quantity in descending order:")
for product in sorted_by_descending_order_quantity():
    print(str(product["name"]) + ": " + str(product["quantity"]))

print("\nProducts sorted by quantity in ascending order:")
for product in sorted_by_ascending_order_quantity():
    print(str(product["name"]) + ": " + str(product["quantity"]))
