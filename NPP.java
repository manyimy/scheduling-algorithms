import java.util.*;

public class NPP {
  public static void main(String[] args) {
    Process p[];  // process array
    int current = 0;   // current time
    int numProcess;
    double avgTAT = 0.00;
    double avgWT = 0.00;

    Scanner input = new Scanner(System.in);
    // get total number of processes from user
    do {
      System.out.print("Enter total number of processes: ");
      numProcess = input.nextInt();
      if(numProcess < 3 || numProcess > 10) {
        System.out.println("Enter number of processes in range 3 to 10 only.");
      }
    }while(numProcess < 3 || numProcess > 10);

    // create new Process object
    p = new Process[numProcess];
    ArrayList<Integer> order = new ArrayList<>();

    // get burst and arrival time
    // add into process array
    for (int i = 0; i < numProcess; i++) {
      System.out.print("Enter  burst  time for P" + i + ": ");
      int bt = input.nextInt();
      System.out.print("Enter arrival time for P" + i + ": ");
      int at = input.nextInt();
      System.out.print("Enter   priority   for P" + i + ": ");
      int pr = input.nextInt();
      p[i] = new Process(bt, at, pr);
    }
    int totalBT = 0;
    for(int i = 0; i < numProcess; i++) {
      totalBT += p[i].getBurst();
    }

    while (current <= totalBT) {
      int minPriority = 99999999;
      int now = -1;
      for(int i = 0; i < numProcess; i++) {
        if(p[i].getArrival() <= current && !p[i].getCompleted() && p[i].getPriority() < minPriority) {
          minPriority = p[i].getPriority();
          now = i;
        }
      }
      System.out.println("now: " + now);
      if(now != -1) {
        System.out.println("current: " + current);
        p[now].setCompleteTime(p[now].getBurst()+current);
        System.out.println("p[now].getBurst()+current : " + (p[now].getBurst()+current));
        current = p[now].getComplete();
        p[now].setCompleted(true);
        order.add(now);
      }
      else{
        current++;
      }
    }

    int totalTAT = 0;
    int totalWT = 0;
    System.out.printf("%s %s %s %s %s %s\n", "Process", "Arrival", "Burst", "Priority", "Turnaround", "Waiting");
    for(int i = 0; i < numProcess; i++) {
      System.out.printf("%7s %7s %5s %8s %10s %7s\n", "P"+i, p[i].getArrival(), p[i].getBurst(), p[i].getPriority(), p[i].getTurnaround(), p[i].getWaiting());
      totalTAT += p[i].getTurnaround();
      totalWT += p[i].getWaiting();
    }
    System.out.println("\nAverage Turnaround Time : " + ((double)totalTAT / numProcess));
    System.out.println("Average Waiting Time : " + ((double)totalWT / numProcess) + "\n");

    System.out.println();
    for (Integer o : order) {
      System.out.print("    P" + o + "\t");
    }
    System.out.println();
    System.out.print("0\t");
    for (Integer o : order) {
      System.out.print(p[o].getComplete() + "\t");
    }
    System.out.println();
  }
}

class Process {
  private int burstTime;
  private int arrivalTime;
  private int priority;
  private int waitingTime;
  private int completeTime;
  private int turnaroundTime;
  private boolean completed;

  public Process(int burstTime, int arrivalTime, int priority){
    this.burstTime = burstTime;
    this.arrivalTime = arrivalTime;
    this.priority = priority;
    completed = false;
  }

  public int getBurst() { return burstTime; }
  public int getArrival() { return arrivalTime; }
  public int getPriority() { return priority; }
  public int getWaiting() {
    waitingTime = turnaroundTime - burstTime;
    return waitingTime;
  }
  public int getComplete() { return completeTime; }
  public int getTurnaround() {
    turnaroundTime = completeTime - arrivalTime;
    return turnaroundTime;
  }
  public boolean getCompleted() { return completed; }

  public void setCompleteTime(int completeTime) { this.completeTime = completeTime; }
  public void setCompleted(boolean completed) { this.completed = completed; }
  public String toString() {
    return "Burst   Time: " + burstTime +
            "\nArrival Time: " + arrivalTime +
            "\nPriority    : " + priority;
  }
}
