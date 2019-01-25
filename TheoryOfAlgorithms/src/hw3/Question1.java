package hw3;

public class Question1 {
	public static void main(String[] args) {
		
		int[] a = new int[100000];
		
		for(int i=0;i<a.length;i++) {
			a[i] = (int) (Math.random()*202-101); //Loads the array with random integers between -100 to 100
		}
		
		long t1a = System.nanoTime();
		System.out.println("Question 1, a Maximum Subarray: "+n2(a));
		long t1b = System.nanoTime();
		System.out.println("RunningTime: "+(t1b-t1a)+ " nano-seconds");
		
		long t2a = System.nanoTime(); 
		System.out.println("Question 1, b Maximum Subarray: "+nlogn(a,0,99999));
		long t2b = System.nanoTime();
		System.out.println("RunningTime: "+(t2b-t2a)+ " nano-seconds");
		
		long t3a = System.nanoTime();
		System.out.println("Question 1, c Maximum Subarray: "+n(a));
		long t3b = System.nanoTime();
		System.out.println("RunningTime: "+(t3b-t3a)+ " nano-seconds");
		
	}
	
	public static int n2(int[]a) {
		int maxSum = 0;
		
		//System.out.println(t1);
		for(int i=0;i<a.length;i++) {
			int thisSum = 0;
			for(int j=i;j<a.length;j++) {
				thisSum+=a[j];
				if(thisSum>maxSum){
					maxSum = thisSum;
				}
			}
		}
		return maxSum;
	}
	
	public static int nlogn(int[]a,int l,int r) {
	
		if(l == r) //Base case
			return a[0];
		
		int m = (l+r)/2;
		
		return Math.max(Math.max(nlogn(a,l,m),nlogn(a,m+1,r)),nlognCross(a,l,m,r));
	}
	
	static int nlognCross(int a[], int l, int m, int r) { // Include elements on left of mid. 
		int sum = 0; 
		int left_sum = Integer.MIN_VALUE; 
		
		//LEFT
		for (int i=m; i>=l;i--){ 
			sum = sum + a[i]; 
			if (sum > left_sum) 
				left_sum = sum; 
		} 
		
		//RIGHT
		sum = 0; 
		int right_sum = Integer.MIN_VALUE; 
		
		for (int i=m+1;i<=r;i++) { 
			sum = sum+a[i]; 
			if (sum>right_sum) 
				right_sum = sum; 
		} 
		
		//SUM
		return left_sum + right_sum; 
	} 
	
	public static int n(int[]a) {
		int maxSum = 0;
		int thisSum = 0;
		
		for(int j=0;j<a.length;j++) {
			thisSum+=a[j];
			
			if(thisSum>maxSum)
				maxSum = thisSum;
			else if(thisSum<0) 
				thisSum = 0;
		}
		return maxSum;
	}
}
