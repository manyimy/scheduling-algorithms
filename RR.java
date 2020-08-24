import java.util.*;

public class RR{
  public static void main(String[] args) {
    Process p[];  // process array
    int current = 0;   // current time
    int numProcess;
    int quant = 0;

    Scanner input = new Scanner(System.in);
    // get total number of processes from user
    do {
      System.out.print("Enter total number of processes: ");
      numProcess = input.nextInt();
      if(numProcess < 3 || numProcess > 10) {
        System.out.println("Enter number of processes in range 3 to 10 only.");
      }
    }while(numProcess < 3 || numProcess > 10);

    System.out.print("Enter time quantum: ");
    quant = input.nextInt();

    // create new Process object
    p = new Process[numProcess];
    // order of process waiting to be executed
    ArrayList<Integer> order = new ArrayList<>();
    ArrayList<Integer> time = new ArrayList<>();

    // get burst and arrival time
    // add into process array
    for (int i = 0; i < numProcess; i++) {
      System.out.print("Enter  burst  time for P" + i + ": ");
      int bt = input.nextInt();
      System.out.print("Enter arrival time for P" + i + ": ");
      int at = input.nextInt();
      p[i] = new Process(bt, at);
    }
    // get total burst time
    // purpose: to end the loop/process later
    int totalBT = 0;
    for(int i = 0; i < numProcess; i++) {
      totalBT += p[i].getBurst();
    }

    int now = -1;
    int seq = 0;
    boolean cont = true;
    //int now = -1; // to get the process to be processed
    // while current time is less than or equal to total burst time
    do {
      for(int i = 0; i < numProcess; i++) {
        for(int j = 0; j < numProcess; j++) {
          if(p[j].getArrival() <= current && !order.contains(j)) {
            order.add(j);
            System.out.println("Added " + j + " to order in line 52");
          }
        }
        if(now != -1) { // add now to order list if not equal to -1
          order.add(now);
        }
        System.out.println(order);
        if(!order.isEmpty()) {  // order list is not empty
          now = order.get(seq++);
          if(p[now].getTempBurst() > quant) {   // burst time left more than quantum time
            p[now].setTempBurst(p[now].getTempBurst() - quant);
            current += quant;
            time.add(current);
          }
          else {  // burst time left less than or equal to quantum time
            current += p[now].getTempBurst();
            time.add(current);
            p[now].setTempBurst(0);
            p[now].setCompleteTime(current);
            now = -1;
          }
        }
        else {  // empty order list
          current++;
        }
      }

      // check if all completed
      for(int i = 0; i < numProcess; i++) {
        if(p[i].getTempBurst() != 0) {
          cont = true;
          break;
        }
        cont = false;
      }
    }while(cont);

    int totalTAT = 0;
    int totalWT = 0;
    System.out.printf("%s %s %s %s %s %s\n", "Process", "Arrival", "Burst", "Finish", "Turnaround", "Waiting");
    for(int i = 0; i < numProcess; i++) {
      System.out.printf("%7s %7s %5s %6s %10s %7s\n", "P"+i, p[i].getArrival(), p[i].getBurst(), p[i].getComplete(),p[i].getTurnaround(), p[i].getWaiting());
      totalTAT += p[i].getTurnaround();
      totalWT += p[i].getWaiting();
    }
    System.out.printf("\nAverage Turnaround Time : %.3f \n", ((double) totalTAT / numProcess));
    System.out.printf("Average Waiting Time : %.3f \n", ((double) totalWT / numProcess));

    System.out.println();
    for (Integer o : order) {
      System.out.print("    P" + o + "\t");
    }
    System.out.println();
    System.out.print("0\t");
    for (Integer t : time) {
      System.out.print(t + "\t");
    }
    System.out.println();
  }
}

class Process {
  private int burstTime;
  private int tempBurst;
  private int arrivalTime;
  private int waitingTime;
  private int completeTime;
  private int turnaroundTime;

  public Process(int burstTime, int arrivalTime){
    this.burstTime = burstTime;
    tempBurst = burstTime;
    this.arrivalTime = arrivalTime;
  }

  public int getBurst() { return burstTime; }
  public int getTempBurst() { return tempBurst; }
  public int getArrival() { return arrivalTime; }
  public int getWaiting() {
    waitingTime = turnaroundTime - burstTime;
    return waitingTime;
  }
  public int getComplete() { return completeTime; }
  public int getTurnaround() {
    turnaroundTime = completeTime - arrivalTime;
    return turnaroundTime;
  }

  public void setTempBurst(int tempBurst) {this.tempBurst = tempBurst;}
  public void setCompleteTime(int completeTime) { this.completeTime = completeTime; }
  public String toString() {
    return "Burst   Time: " + burstTime +
            "\nArrival Time: " + arrivalTime;
  }
}
