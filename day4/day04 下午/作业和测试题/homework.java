import java.util.Scanner;
public class homework{
    public static void main(String[]args){

        // //1 
        // int count = 0;
        // for(int i = 1;i<=100;i++){
        //     if(i % 5 != 0){
        //         count++;
        //         System.out.print(i+" ");
        //         if(count % 5 ==0){
        //             System.out.print('\n');
        //         }
        //     }
        // }


        
        // 2 
        // Scanner input = new Scanner(System.in);
        // int num1 = 0;
        // int num2 = 0;
        // byte choice = 1;
        // while(choice!=0){
        //     System.out.println("-------------Calculator---------------");
        //     System.out.println("1. addition");
        //     System.out.println("2. subtraction");
        //     System.out.println("3. multiplication");
        //     System.out.println("4. division");
        //     System.out.println("0. exit");
        //     System.out.print("Please enter your choice: ");
        //     choice = input.nextByte();
        //     System.out.print("Please input number 1 :");
        //     num1 = input.nextInt();
        //     System.out.print("Please input number 2 :");
        //     num2 = input.nextInt();
            
        //     switch(choice){
        //         case 1:{
        //             System.out.println(num1+" + "+num2+" = " + (num1+num2));
        //         }break;
        //         case 2:{
        //             System.out.println(num1+" - "+num2+" = " + (num1-num2));
        //         }break;
        //         case 3:{
        //             System.out.println(num1+" * "+num2+" = " + (num1*num2));
        //         }break;
        //         case 4:{
        //             System.out.println(num1+" / "+num2+" = " + ((double)num1/num2));
        //         }break;
        //         case 0:{
        //             System.out.println("Exited.");
        //         }break;
        //         default:{
        //             System.out.println("Input error.");
        //         }break;
        //     }
        // }
        


        // 3 输出小写的a-z以及大写的Z—A //for
        // for(int i = 'a';i<='z';i++){
        //     System.out.println((char)i);
        // }
        // for(int i = 'Z';i>='A';i--){
        //     System.out.println((char)i);
        // }


        // 4 求出1-1/2+1/3-1/4…..1/100的和 [动脑]

        // 5.输入年月日，判断该日是当年的第几天 
        Scanner input = new Scanner(System.in);
        System.out.println("Please input a particular date. ");
        System.out.println("Please input a particular year: ");
        short year = input.nextShort();
        System.out.println("Please input a particular month: ");
        byte month = input.nextByte();
        System.out.println("Please input a particular day: ");
        byte day = input.nextByte();
        short index = 0;

        for(int j = 1;j<= month;j++){
            System.out.println("index "+j);
            switch(j){
                case 1:{
                    index += day;
                    System.out.println("month 1");
                    if(month==1)break;
                }break;
                case 2:
                case 4:
                case 6:
                case 8:
                case 9:
                case 11:{
                    index+=31;
                    System.out.println("month 2");
                    if(month==2)break;
                }break;
                case 3:{
                    if(((year%4==0)&&(year%100!=0))||(year%400==0)){//leap year
                        index+=29;
                    }else{//common year
                        index+=28;
                    }
                    System.out.println("month 3");
                    if(month==3)break;
                }break;

                case 5:
                case 7:
                case 10:
                case 12:{
                    index+=30;
                    System.out.println("month 5");
                    if(month==5)break;
                }break;

            }
        }
        System.out.println("Today is the "+index+" day of the year.");
    }
}