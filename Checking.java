
public class Checking extends Account {
    
    private static String accountType = "checking";
    
    Checking(double initialDeposit){
        this.setBalance(initialDeposit);
        this.checkInterest(0);

    }
    
    @Override
    public String toString(){
        return 
               "-> Account Type: " + accountType + " Account\n" +
               "-> Account Number: " + this.getAccountNum() + "\n" +
               "-> Balance: " + this.getBalance() + "\n" +
               "-> Interest rate: " + this.getInterest() + "%" + "\n";           
                
    }
    
}
