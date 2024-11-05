# The function to find and return the missing number.
def find_missing_number(arr):
    l = len(arr)
    expected_sum = (l + 2) * (l + 1) // 2
    current_sum = sum(arr)
    missing_number = expected_sum - current_sum
    return missing_number


array = [3, 7, 1, 2, 6, 4]
print("The missing number is: " + str(find_missing_number(array)))
