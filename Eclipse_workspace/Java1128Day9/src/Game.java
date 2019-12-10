
public class Game {
	int fingerNum;

	public static void main(String[] args) {
		Game game = new Game();
		Tom tom = new Tom();
		for (int i = 0; i < 5; i++) {
			tom.scoreResult(game.fingerNum() == tom.fingerNum());
			System.out.println("********************");
		}
	}

	public int fingerNum() {
		fingerNum = (int) (Math.random() * 10) % 3;
		System.out.println("Computer:\t" + convert(fingerNum));
		return fingerNum;
	}

	public String convert(int num) {
		String result = null;
		switch (num) {
		case 0:
			result = "Rock ";
			break;
		case 1:
			result = "Scissors ";
			break;
		case 2:
			result = "Paper ";
			break;
		default:
			result = "Nothing! ";
			break;
		}
		return result;
	}
}

class Tom {
	String name = "Tom";
	boolean list[];
	int count;

	public Tom() {
		list = new boolean[5];
		count = 0;
	}

	public int fingerNum() {
		int temp = (int) (Math.random() * 10) % 3;
		System.out.print("Tom:\t\t" + convert(temp));
		return temp;
	}

	public void scoreResult(boolean bResult) {
		list[count++] = bResult;
		System.out.println(bResult ? "\tWin" : "\tLose");
	}

	public void showInfo() {
		System.out.println(name + " played " + count + " times: ");
		for (int i = 0; i < count; i++) {
			System.out.println(list[i]);
		}
	}

	public String convert(int num) {
		String result = null;
		switch (num) {
		case 0:
			result = "Rock ";
			break;
		case 1:
			result = "Scissors ";
			break;
		case 2:
			result = "Paper ";
			break;
		default:
			result = "Nothing! ";
			break;
		}
		return result;
	}
}
