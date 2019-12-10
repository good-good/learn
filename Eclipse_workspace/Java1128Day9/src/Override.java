
public class Override {
	public static void main(String[] args) {
		Override override = new Override();
		System.out.println(override.max(12, 34));
		
		int[] arr = {1,3,9,5,4,10,2};
		String[] arrString = {"aaaa","bbbbbb","ccc","d","ee"};
		override.sort(arr);
		for(int i = 0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
		override.sort(arrString);
		for(int i = 0;i<arrString.length;i++){
			System.out.println(arrString[i]);
		}
	}
	public double max(double a, double b) {
		return a > b ? a : b;
	}

	public double max(double a, double b, double c) {
		return max(max(a, b), c);
	}

	public int max(int a, int b) {
		return a > b ? a : b;
	}
	
	public void sort(int[] arr){
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length-1-i; j++) {
				if (arr[j]>arr[j+1]) {
					arr[j] = arr[j]+arr[j+1];
					arr[j+1] = arr[j]-arr[j+1];
					arr[j] = arr[j]-arr[j+1];
				}
			}
		}
	}
	public void sort(String[] arr){
		String temp;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length-1-i; j++) {
				if (arr[j].length()>arr[j+1].length()) {
					temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
				}
			}
		}
	}
}
