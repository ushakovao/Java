package common;

public class uu {

    public static void o (Object object){
        System.out.println(object);
    }

    public static void oo(Object ob){

        String message;
        if(ob!=null) message=ob.toString()+"     ";
        else message="null     ";
        for (int i=2;i<=2;i++){
            StackTraceElement str=Thread.currentThread().getStackTrace()[i];
            message+=str.toString();
        }

        System.out.println(message);
    }


}