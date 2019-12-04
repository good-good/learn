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
        // 至少有一次，所以应该用 do-while
        // Scanner input = new Scanner(System.in);
        // int num1 = 10;
        // int num2 = 5;
        // byte choice = 1;
        // do{
        //     System.out.println("-------------Calculator---------------");
        //     System.out.println("1. addition");
        //     System.out.println("2. subtraction");
        //     System.out.println("3. multiplication");
        //     System.out.println("4. division");
        //     System.out.println("0. exit");
        //     System.out.print("Please enter your choice: ");
        //     choice = input.nextByte();
        //     // System.out.print("Please input number 1 :");
        //     // num1 = input.nextInt();
        //     // System.out.print("Please input number 2 :");
        //     // num2 = input.nextInt();
            
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
        // }while(choice!=0);
        


        // 3 输出小写的a-z以及大写的Z—A //for
        // for(int i = 'a';i<='z';i++){
        //     System.out.println((char)i);
        // }
        // for(int i = 'Z';i>='A';i--){
        //     System.out.println((char)i);
        // }


        // 4 求出1-1/2+1/3-1/4…..1/100的和 [动脑]
        // double sum = 0.0;
        // double temp = 0.0;
        // for(int i = 0;i<100;i++){
        //     temp = Math.pow(-1,i)/(i+1);
        //     sum += temp;
        // }
        // System.out.println(sum);



        // 5.输入年月日，判断该日是当年的第几天 
        // Scanner input = new Scanner(System.in);
        // System.out.println("Please input a particular date. ");
        // System.out.println("Please input a particular year: ");
        // short year = input.nextShort();
        // System.out.println("Please input a particular month: ");
        // byte month = input.nextByte();
        // System.out.println("Please input a particular day: ");
        // byte day = input.nextByte();
        // short index = 0;

        // for(int j = 1;j<= month;j++){
        //     System.out.println("index "+j);
        //     switch(j){
        //         case 1:{
        //             index += day;
        //         }break;
        //         case 2:
        //         case 4:
        //         case 6:
        //         case 8:
        //         case 9:
        //         case 11:{
        //             index+=31;
        //         }break;
        //         case 3:{
        //             if(((year%4==0)&&(year%100!=0))||(year%400==0)){//leap year
        //                 index+=29;
        //             }else{//common year
        //                 index+=28;
        //             }
        //         }break;
        //         case 5:
        //         case 7:
        //         case 10:
        //         case 12:{
        //             index+=30;
        //         }break;

        //     }
        // }
        // System.out.println("Today is the "+index+" day of the year.");

        //6.打印九九乘法表
        // for(int i = 1;i<=9;i++){
        //     for(int j = 1;j<=i;j++){
        //         System.out.print(j+" * "+i+" = "+(i*j)+"\t");
        //         if(j==i){
        //             System.out.print('\n');
        //             continue;
        //         }
        //     }
        // }


        //7.打印等腰三角形
        // //采集一个奇数
        // Scanner input = new Scanner(System.in);
        // System.out.print("Please input a integer: ");
        // int count = input.nextInt();
        // //除以 2 计算出最多空格数
        // int blankCountMax = count/2;
        // //输出
        // for(int i = 0;i<count;i++){
        //     for(int j = 0;j<count-i-1;j++){
        //         System.out.print(' ');
        //     }
        //     for(int k = 0;k<(count-blankCountMax*2)+i*2;k++){
        //         System.out.print('*');
        //     }
        //     System.out.print('\n'); 
        // }

        //打印空心菱形
        // Scanner input = new Scanner(System.in);
        // System.out.print("Please input a integer: ");
        // int totalLevel = input.nextInt();
        // for(int i = 0;i<totalLevel;i++){
        //     for(int k = 0;k<totalLevel-i-1;k++){
        //         System.out.print(' ');
        //     }
        //     for(int j = 0;j<=i*2;j++){
        //         if(((0==j)||(i*2==j))){
        //             System.out.print('*');
        //         }else{
        //             System.out.print(' ');
        //         }
                
        //     }
        //     System.out.println();
        // } 
        // for(int i = 0;i<totalLevel-1;i++){
        //     for(int k = 0;k<i+1;k++){
        //         System.out.print(' ');
        //     }
        //     for(int j = 1;j<=(totalLevel-i-1)*2-1;j++){
        //         if((1==j)||((totalLevel-i-1)*2-1==j)){
        //             System.out.print('*');
        //         }else{
        //             System.out.print(' ');
        //         }
                
        //     }
        //     System.out.println();
        // }


        // 求1+（1+2）+（1+2+3）+（1+2+3+4）+...+(1+2+3+..+100)的结果
        // int sum = 0;
        // int num = 0;
        // Scanner input = new Scanner(System.in);
        // System.out.print("Please input a integer: ");
        // int totalLevel = input.nextInt();
        // for(int i = 1;i<=totalLevel;i++){
        //     num = 0;
        //     for(int j = 1;j<=i;j++){
        //         num += j;
        //     }
        //     sum += num;
        // }
        // System.out.println("Sum is: "+ sum);
    }
}