
public class Employee {
	String name;
	char gender;
	int age;
	String profession;
	double salary;

	public static void main(String[] args) {
		Employee employee = new Employee("大黄", '男', 19);
		Employee employee2 = new Employee("小黄", '女', 9, "经理", 9000);
		Employee employee3 = new Employee("职员", 5000);
		employee.showInfo();
		employee2.showInfo();
		employee3.showInfo();
	}

	public Employee(String name, char gender, int age, String profession, double salary) {
		this(name, gender, age);
		this.profession = profession;
		this.salary = salary;
	}

	public Employee(String name, char gender, int age) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	public Employee(String profession, double salary) {
		this.profession = profession;
		this.salary = salary;
	}

	public void showInfo() {
		System.out.println(name + " " + age + " " + gender + " " + profession + " " + salary);
	}
}
