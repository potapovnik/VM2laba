package mypackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainClass {
    public ArrayList<Double> x_In = new ArrayList<>();
    public ArrayList<Double> y_In = new ArrayList<>();
    public double Sleft;
    public double Sright;

    private void InputFromFile(String path) {
        Scanner scan;
        try {
            scan = new Scanner(new File(path)).useDelimiter("\n");
            int i = 0;
            while (scan.hasNext()) {
                String s = scan.next();
                if (i == 0) {
                    Scanner sc = new Scanner(s).useDelimiter(" ");
                    while (sc.hasNext()) {
                        x_In.add(Double.parseDouble(sc.next()));
                    }
                    i++;
                } else if (i == 1) {
                    Scanner sc = new Scanner(s).useDelimiter(" ");
                    while (sc.hasNext()) {
                        y_In.add(Double.parseDouble(sc.next()));
                    }
                    i++;
                } else if (i == 2) {
                    Scanner sc = new Scanner(s).useDelimiter(" ");
                    Sleft = (int) (Double.parseDouble(sc.next()));
                    i++;
                } else if (i == 3) {
                    Scanner sc = new Scanner(s).useDelimiter(" ");
                    Sright = (Double.parseDouble(sc.next()));
                    i++;
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String path="D:\\vm2laba\\src\\source\\input.txt";
        MainClass go=new MainClass();
        go.InputFromFile(path);
        int n=go.x_In.size();
        ArrayList<ArrayList<Double>> h=new ArrayList<>();
        double [][] hi=new double[n][3];

        for (int i=1;i<n-1;i++){    //заполнение матрицы разниц переменных из вектора Х
            hi[i][0]=go.x_In.get(i)-go.x_In.get(i-1);    // [i][0] Ai
            hi[i][1]=2*(go.x_In.get(i+1)-go.x_In.get(i-1));// [i][1] Ci
            hi[i][2]=go.x_In.get(i+1)-go.x_In.get(i);    // [i][2] Bi
        }
        hi[0][0]=1;
        hi[n-1][2]=1;

        double [][] NM=new double[n][2]; // [i][0] Ni   [i][1] Mi
        NM[0][0]=0;
        NM[0][1]=go.Sleft;

        NM[n-1][0]=0;
        NM[n-1][1]=go.Sright;
        double[] Ci=new double[n];
        Ci[n-1]=go.Sright;
        Ci[0]=go.Sleft;
        for (int i=1;i<n-1;i++){ // вычисление Ni и Mi с 1 до N-1
            NM[i][0]=(-(hi[i][2]))/(hi[i][1]+hi[i][0]*NM[i-1][0]);
            NM[i][1]=((6*go.y_In.get(i))-hi[i][0]*NM[i-1][1])/(hi[i][1]+hi[i][0]*NM[i-1][0]);
        }

        for (int i=n-2;i>0;i--){
            Ci[i]=Ci[i+1]*NM[i][0]+NM[i][1];
        }
        double[] Hi=new double[n];
        for (int i=1;i<n;i++){
            Hi[i]=go.x_In.get(i)-go.x_In.get(i-1);
        }
        double [] di=new double[n]; // создание массива коэффицентов di и заполнение его
        for (int i=1;i<n;i++){
            di[i]=(Ci[i]-Ci[i-1])/Hi[i];
        }

        double[] bi=new double[n];
        for (int i=1;i<n;i++){
            bi[i]=(Hi[i]/2)*Ci[i]-((Hi[i]*Hi[i])/6)*di[i]+(go.y_In.get(i)-go.y_In.get(i-1))/Hi[i];
        }
        for (int i=1;i<n;i++){
            System.out.println("a"+i+"="+go.y_In.get(i)+"   b"+i+"="+bi[i]+"   c"+i+"="+Ci[i]+"   d"+i+"="+di[i]);
            //System.out.println("ai="+go.y_In.get(i)+"-di="+bi[i]+"*"+Hi[i]+"+Ci="+Ci[i]+"/2*"+(Hi[i]*Hi[i])+"di="+di[i]+"/6*"+(Hi[i]*Hi[i]*Hi[i]));
        }
        System.out.println("С делением на коэф.  т.е. Ci/2 di/6 и знаками");
        for (int i=1;i<n;i++){
            System.out.println("a"+i+"="+go.y_In.get(i)+"   b"+i+"="+(-bi[i])+"   c"+i+"="+Ci[i]/2+"   d"+i+"="+(-di[i]/6));
            //System.out.println("ai="+go.y_In.get(i)+"-di="+bi[i]+"*"+Hi[i]+"+Ci="+Ci[i]+"/2*"+(Hi[i]*Hi[i])+"di="+di[i]+"/6*"+(Hi[i]*Hi[i]*Hi[i]));
        }

    }

}
