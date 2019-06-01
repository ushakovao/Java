package common;


public class Tester {

    int count=0;
    long startingTime =System.currentTimeMillis();

    public void asserTrue(boolean bool){
        if (bool) count++;
        else throw new RuntimeException("une erreur dans les testes");
    }

    public long duration(){
        return System.currentTimeMillis()-this.startingTime;
    }

    @Override
    public String toString() {
        return "Tester{" +
                "count=" + count +
                ",duration=" + this.duration() +
                '}';
    }
}