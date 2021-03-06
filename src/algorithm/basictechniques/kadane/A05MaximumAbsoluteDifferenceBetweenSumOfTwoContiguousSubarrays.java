package algorithm.basictechniques.kadane;

//#REVISIT
//https://www.geeksforgeeks.org/maximum-absolute-difference-between-sum-of-two-contiguous-sub-arrays/
class A05MaximumAbsoluteDifferenceBetweenSumOfTwoContiguousSubarrays {
    public static void main(String[] args) {
        int a[] = {-2, -3, 4, -1, -2, 1, 5, -3};
        int n = a.length;
        System.out.print(findMaxAbsDiff(a, n));
    }

    // Find leftMax, leftMin, rightMax, rightMin by Kadane Algo
    // For each index : check max of abs diff between leftMax(i) and rightMin(i + 1) , leftMin(i) and rightMax(i + 1)
    public static int findMaxAbsDiff(int arr[], int n) {
        // ARRAYS OF MAXSOFARS NOT CURRMAXS IN KADANE : leftMax[] rightMax[] leftMin[] rightMin[]
        // INVERTARR : REPLACE + WITH - : THEN GET MAX_SO_FAR_ARR FROM KADANE AND AGAIN APPLY INVERSION TO MAX_SO_FAR_ARR TO GET MIN_SO_FAR_ARR
        // CAVEAT IS DONG THE SAME FOR NEGATIVE SCENARIOS AS WELL : Math.abs(leftMin[i] - rightMax[i + 1]));
        int leftMax[] = new int[n];
        maxLeftSubArraySum(arr, n, leftMax);

        int rightMax[] = new int[n];
        maxRightSubArraySum(arr, n - 1, rightMax);

        int invertArr[] = new int[n];
        for (int i = 0; i < n; i++)
            invertArr[i] = -arr[i];

        int leftMin[] = new int[n];
        maxLeftSubArraySum(invertArr, n, leftMin);
        for (int i = 0; i < n; i++)
            leftMin[i] = -leftMin[i];

        int rightMin[] = new int[n];
        maxRightSubArraySum(invertArr, n - 1, rightMin);
        for (int i = 0; i < n; i++)
            rightMin[i] = -rightMin[i];

        int result = -2147483648;
        for (int i = 0; i < n - 1; i++) {
            //Find maximum Difference between leftMin and rightMax OR between leftMax and rightMin
            int absValue = Math.max(Math.abs(leftMax[i] - rightMin[i + 1]),
                    Math.abs(leftMin[i] - rightMax[i + 1]));
            if (absValue > result)
                result = absValue;
        }

        return result;
    }

    // Returns array of max_so_far, not curr_max
    static int maxLeftSubArraySum(int a[], int size,
                                  int sum[]) {
        int max_so_far = a[0];
        int curr_max = a[0];
        sum[0] = max_so_far;

        for (int i = 1; i < size; i++) {
            curr_max = Math.max(a[i], curr_max + a[i]);
            max_so_far = Math.max(max_so_far, curr_max);
            sum[i] = max_so_far;
        }

        return max_so_far;
    }

    // Returns array of max_so_far, not curr_max
    static int maxRightSubArraySum(int a[], int n, int sum[]) {
        int max_so_far = a[n];
        int curr_max = a[n];
        sum[n] = max_so_far;

        for (int i = n - 1; i >= 0; i--) {
            curr_max = Math.max(a[i], curr_max + a[i]);
            max_so_far = Math.max(max_so_far, curr_max);
            sum[i] = max_so_far;
        }

        return max_so_far;
    }
}