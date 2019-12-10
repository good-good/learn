import java.util.Scanner;

public class CalculateConstellation {
	public static void main(String[] args) {
		Koradji koradji = new Koradji();
		Scanner scanner = new Scanner(System.in);
		int month = 0;
		int day = 0;
		do {
			System.out.println("请输入你的生日-月(0退出): ");
			month = scanner.nextInt();
			if (0 == month)
				break;
			System.out.println("请输入你的生日-日(0退出): ");
			day = scanner.nextInt();
			if (0 == day)
				break;
			System.out.println(koradji.CalculateConstellation(2019, month, day));
			System.out.println("Calculated: " + koradji.count);
		} while (true);
		System.out.println("Exit.");
	}

}

class Koradji {
	String constellation;
	int count = 0;

	public String CalculateConstellation(int year, int month, int day) {
		count++;
		System.out.println(year + " " + month + " " + day);
		switch (month) {
		case 1: {
			constellation = day <= 19 ? "摩羯座" : "水瓶座";
		}
			break;
		case 2: {
			constellation = day <= 18 ? "水瓶座" : "双鱼座";
		}
			break;
		case 3: {
			constellation = day <= 20 ? "双鱼座" : "白羊座";
		}
			break;
		case 4: {
			constellation = day <= 19 ? "白羊座" : "金牛座";
		}
			break;
		case 5: {
			constellation = day <= 20 ? "金牛" : "双子座";
		}
			break;
		case 6: {
			constellation = day <= 20 ? "双子座" : "巨蟹座";
		}
			break;
		case 7: {
			constellation = day <= 22 ? "巨蟹座" : "狮子座";
		}
			break;
		case 8: {
			constellation = day <= 22 ? "狮子座" : "处女座";
		}
			break;
		case 9: {
			constellation = day <= 22 ? "处女座" : "天秤座";
		}
			break;
		case 10: {
			constellation = day <= 22 ? "天秤座" : "天蝎座";
		}
			break;
		case 11: {
			constellation = day <= 21 ? "天羯座" : "射手座";
		}
			break;
		case 12: {
			constellation = day <= 21 ? "射手座" : "摩羯座";
		}
			break;
		default:
			break;
		}
		return constellation;
	}
}