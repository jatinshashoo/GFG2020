//https://leetcode.com/problems/find-median-from-data-stream/discuss/506772/Java-solution-using-2-heaps.
package company.intuit;

import java.util.PriorityQueue;

class FindMedianInStream {

    /**
     * initialize your data structure here.
     */
    //create 2 heap,one is a Max-heap(descending) and another is Min-heap(ascending).
    //where the numbers less than the middle are put in the descending one while the numbers greater than the middle are put in the ascending one
    //to make it balance,0<=descending.size()<=ascending.size()+1;
    //so in the end,the middle one will be the peek of descending one or the mean of the descending.peek() and ascending.peak()
    PriorityQueue<Integer> descending = new PriorityQueue<>((a, b) -> b - a);
    PriorityQueue<Integer> ascending = new PriorityQueue<>((a, b) -> a - b);

    public FindMedianInStream() {
    }

    public void addNum(int num) {
        if (descending.size() == 0 || num <= descending.peek())
            descending.add(num);
        else if (ascending.size() == 0 || num >= ascending.peek())
            ascending.add(num);
        else
            //put the num in an arbitrary heap
            descending.add(num);
        balance();
    }

    private void balance() {
        if (descending.size() > ascending.size() + 1)
            ascending.add(descending.poll());
        else if (descending.size() < ascending.size())
            descending.add(ascending.poll());
    }

    public double findMedian() {
        if (descending.size() == ascending.size() + 1)
            return descending.peek();
        else
            return (double) (descending.peek() + ascending.peek()) / 2;
    }

    public static void main(String[] args) {
        FindMedianInStream obj = new FindMedianInStream();
        obj.addNum(5);
        obj.addNum(15);
        obj.addNum(1);
        obj.addNum(3);
        obj.addNum(2);
        obj.addNum(8);
        System.out.println(obj.findMedian());
        obj.addNum(7);
        obj.addNum(9);
        obj.addNum(10);
        obj.addNum(6);
        obj.addNum(11);
        obj.addNum(4);
        System.out.println(obj.findMedian());
    }
}