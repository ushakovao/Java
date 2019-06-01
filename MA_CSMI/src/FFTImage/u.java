package FFTImage;

/**
 * Created by ushakova on 15/03/16.
 */
public class u {

    public static double mod(double x, double n){
        double res=x;
        if(x>=0){
            while(res>n){
                res=res-n;
            }
        }else{
            while(res<0){
                res=res+n;
            }
        }
        return res;
    }


    public static void o (Object object){
        System.out.println(object);
    }

    public static void oo(Object ob){
        String message=ob.toString()+"     ";
        for (int i=2;i<=2;i++){
            StackTraceElement str=Thread.currentThread().getStackTrace()[i];
            message+=str.toString();
        }

        System.out.println(message);
    }


}
