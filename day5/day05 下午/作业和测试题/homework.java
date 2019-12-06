import java.util.Scanner;

public class homework{
    public static void main(String[]args){
        //1 循环打印输入的月份的天数。 【使用continue实现】要有判断输入的月份是否错误的语句
        //采集输入
        // Scanner input = new Scanner(System.in);
        // short year = 0;
        // byte month = 0;
        // byte day = 0;
        // while(true){
        //     System.out.println();
        //     System.out.print("Please enter a year: ");
        //     year = input.nextShort();
        //     System.out.print("Please enter a month: ");
        //     month = input.nextByte();
        //     if(month>12||month<1){
        //         System.out.println("Error: Please enter a correct month.");
        //         continue;
        //     }
        //     System.out.print("Please enter a day: ");
        //     day = input.nextByte();
            
        //     System.out.println("The date you entered is "+month+" "+day+", "+year);
        // }

        // 随机猜数游戏   随机生成一个1——100的整数 有十次机会 
        // Variable initialize
        // Scanner input = new Scanner(System.in);
        // byte gameNum = 0;
        // byte inputNum = 0;
        // byte tryNum = 0;

        // System.out.println("Geuss the randomly generated number(1~100).");
        // // creat a random number which is between 1 to 100.
        // gameNum = (byte)(Math.random()*100+1);
        // // Collect the user input and deal with it. He has ten times to try.
        // while(tryNum<11){
        //     tryNum++;
        //     System.out.print("Please enter your number: ");
        //     inputNum = input.nextByte();
        //     if(inputNum==gameNum){
        //         break;
        //     }
        // }
        // if(1==tryNum){
        //     System.out.println("You are a genius!");
        // }else if(tryNum>1&&tryNum<4){
        //     System.out.println("You are so smart, and catching up with me!");
        // }else if(tryNum>4&&tryNum<10){
        //     System.out.println("Just so so!");
        // }else if(10 == tryNum){
        //     System.out.println("You finally got it right!");
        // }else{
        //     System.out.println("I don't know what to say about you!");
        // }
        // System.out.println("The generated number is "+ gameNum);


        //输出100以内的所有素数(只能被1和自己整除的数)，每行显示5个；并求和
        // int count = 0;
        // int displayCount = 0;
        // int sum = 0;
        // for(int i = 3;i<=100;i++){
        //     count = 0;
        //     for(int j = 1;j<=100;j++){
        //         if(i%j==0){
        //             count++;
        //         }
        //     }
        //     if(2==count){
        //         System.out.print(i+" ");
        //         displayCount++;
        //         sum += i;

        //         if(displayCount%5==0){
        //             System.out.println();
        //         }
        //     }
            
        // }
        // System.out.println("\nSum = "+sum);


        //  中国有句俗语叫“三天打鱼两天晒网”。如果从1990年1月1日起开始执行“三天打鱼两天晒网”。如何判断在以后的某一天中是“打鱼”还是“晒网”？
        // 采集输入的日期
        // 计算从起始日开始到输入日期有多少天
        // 根据公式判断 打渔还是晒网 

        //2.随机生成10个整数(1_100的范围)保存到数组，并倒序打印以及求平均值、求最大值和最大值的下标、并查找里面是否有55
        // int arry[] = new int[10];
        // int sum = 0;
        // for(int i = 0;i<arry.length;i++){
        //     arry[i] = (int)(Math.random()*100+1);
        // }
        // //Reverse 
        // for(int i = 0;i<arry.length;i++){
        //     System.out.print(arry[i]+" ");
        // }
        // System.out.println();
        // for(int i = 0;i<arry.length;i++){
        //     System.out.print(arry[arry.length-i-1]+" ");
        // }
        // System.out.println();
        // // average
        // for(int i = 0;i<arry.length;i++){
        //     sum += arry[i];
        // }
        // System.out.println("Average is "+((double)sum/10));
        // // Maxmum
        // int max = arry[0];
        // int index = 0;
        // for(int i = 0;i<arry.length-1;i++){
        //     if(arry[i]<arry[i+1]){
        //         max = arry[i+1];
        //         index = i+1;
        //     }
        // }
        // System.out.println("The max number is "+max+",it's index is "+index);

        // // If there is num 55?
        // Boolean isThere55 = false;
        // for(int i = 0;i<arry.length;i++){
        //     if(arry[i]==55){
        //         System.out.println("This number is 55.");
        //         isThere55 = true;
        //         break;
        //     }
        // }
        // System.out.println("Is there number 55? "+ isThere55);


        //最佳评委
        // double score[] = {9.3,7.8,8.8,9.2,9.9,6.5,8.4,8.4};
        // double sum = 0;
        // double average = 0.0;
        // double difference[] = new double[8];

        // // TODO: Generating random decimals.
        // // for(int i = 0;i<score.length;i++){
        // //     score[i] = (double)(Math.random()*10+1);
        // // }

        // for(int i=0;i<score.length;i++){
        //     sum += score[i];
        // }
        // average = sum/score.length;
        // System.out.println("Average is : " + average);

        // //calculate the difference of every judge.
        // for(int i = 0;i<score.length;i++){
        //     difference[i] = Math.abs(score[i]-average);
        // }

        // //Finding the best judges.
        // double minDifferences = 0.0;
        // byte judge[] = new byte[10];
        // byte count = 0;
        // for(int i = 0;i<difference.length-1;i++){
        //     if(difference[i]>difference[i+1]){
        //         minDifferences = difference[i+1];
        //         if(count>0){//The minDifference has been changed, we must clear the record data.
        //             count = 0;
        //         }
        //         judge[count] = (byte)(i+1);
        //         count++;
        //     }else if(difference[i]==difference[i+1]){//Record the index that has the same value with minDifference
        //         judge[count] = (byte)(i+1);
        //         count++;
        //     }
        // }
        // System.out.println("The most close difference is "+minDifferences);
        // System.out.println("Thers're "+count+" best judges.");
        // for(int i=0;i<count;i++){
        //     System.out.println("He is:"+judge[i]);
        //     System.out.println("He's score is "+score[judge[i]]);
        // }


    }
}