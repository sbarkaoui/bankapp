
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
    
    // Instance variables 
    Bank bank = new Bank();
    boolean exit;
    
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.runMenu();
    }
    
    public void runMenu() { 
        printHeader();
        while(!exit){
            printMenu();
            int choice = getInput();
            performAction(choice);
            
        }                                       
    }

    private void printHeader() {
        System.out.println("+------------------------------------+");
        System.out.println("         Welcome to Mr. V's           ");
        System.out.println("         Awesome Bank App             ");
        System.out.println("+------------------------------------+");
    }

    private void printMenu() {
        displayHeader("Menu");
        System.out.println("1) Create a new account");
        System.out.println("2) Make a deposit");
        System.out.println("3) Make a withdrawal");
        System.out.println("4) List account details");
        System.out.println("0) Exit");
    }

    private int getInput() {
        Scanner keyboard = new Scanner(System.in);        
        int choice = -1;
        do {       
            System.out.println("");
            System.out.print("=> Enter your choice: ");
            try {
                choice = Integer.parseInt(keyboard.nextLine());
            } 
            catch (NumberFormatException e) {
                System.out.println("<!> Invalid choice selection. Number only please <!>");
                System.out.println("");                
                
            }
            if(choice < 0 || choice > 4){
                System.out.println("<!> Choice outside of range. Please choose again <!>");
                System.out.println("");                
                
            }      
        } while(choice < 0 || choice > 4);       
        return choice;              
    }
    
    private void performAction(int choice) {
            switch(choice){
                case 0: 
                    System.out.println("<*> Thank you for using our app <*>");
                    System.exit(0);
                    break;
                case 1:
            {
                try {
                    createAnAccount();
                } catch (InvalidAccountTypeException ex) {
                    System.out.println("<!> Account was not created successfully <!>");
                    System.out.println("");                                
                }
            }
                    break;
                case 2:
                    makeADeposit();
                    break;
                case 3:    
                    makeWithdrawal(); 
                    break;
                case 4:
                    listBalances();
                    break;
                default: 
                    System.out.println("<!> Unknown Error has occured <!>");
                    System.out.println("");                                   
            }        
        }
        
    private String askQuestion(String question, List<String> answers){
        String response = "";
        Scanner input = new Scanner(System.in);
        boolean firstRun = true;
        //boolean choices = ((answers != null) && !answers.isEmpty());
        boolean choices = ((answers == null) || answers.isEmpty()) ? false : true;
        do{
            if(!firstRun){
                System.out.println("<!> Invalid selection. Please try again <!>");
                System.out.println("");                
            }
            System.out.print(question);
            if(choices){
                System.out.print("(");
                for(int i = 0; i < answers.size() ; i++){
                    System.out.print(answers.get(i) + "/");

                }               
                System.out.print("): ");
            }
            response = input.nextLine();
            firstRun = false;    
            if(!choices){
                break;
            }
        } while(!answers.contains(response));
        return response;
    }
    
    private double getDeposit(String accountType){
        Scanner keyboard = new Scanner(System.in);
        double initialDeposit = 0;        
        boolean valid = false;
        while(!valid){
            System.out.print ("=> Please enter an initial deposit: ");
            try {
                initialDeposit = Double.parseDouble(keyboard.nextLine());
            } 
            catch (NumberFormatException e) {
                System.out.println("<!> Deposit must be a number <!>");
                System.out.println("");                                
            }
            if(accountType.equalsIgnoreCase("checking")){
                if(initialDeposit < 100){
                    System.out.println("<!> Checking account requires a minimum of $100 to open <!>");
                    System.out.println("");                
                } 
                else {
                    valid = true;
                }
            }
            else if(accountType.equalsIgnoreCase("savings")){
                if(initialDeposit < 50){
                    System.out.println("<!> Savings account requires a minimum of $50 to open <!>");
                    System.out.println("");                                    
                } 
                else {
                    valid = true;
                }
            }     
        }
        return initialDeposit;
    }
   
    private void createAnAccount() throws InvalidAccountTypeException {
        displayHeader("Create an Account");
        String accountType = askQuestion("=> Please enter Account Type: ", Arrays.asList("checking", "savings"));             
        String firstName = askQuestion("=> Please enter your first name: ", null);
        String lastName = askQuestion("=> Please enter your last name: ", null);
        String ssn = askQuestion("=> Please enter your ssn: ", null);       
        double initialDeposit = getDeposit(accountType);
        // We can create an Account now
        Account account;
        if(accountType.equalsIgnoreCase("checking")){
            account = new Checking(initialDeposit);
        } 
        else if(accountType.equalsIgnoreCase("savings")) {
            account = new Savings(initialDeposit);            
        }
        else{
            throw new InvalidAccountTypeException();
        }
        // We can create a Customer now
        Customer customer = new Customer(firstName, lastName, ssn, account);
        bank.addCustomer(customer);    
    }
    
    private double getDollarAmount(String question){
        Scanner keyboard = new Scanner(System.in);               
        double amount = 0;
        System.out.print(question);
        try {
            amount = Double.parseDouble(keyboard.nextLine());
        } 
        catch (NumberFormatException e) {
            System.out.println("<!> The desposited amount must be a positive number <!>");
            System.out.println("");                
        
        }        
        return amount;
    }

    private void makeADeposit() {
        displayHeader("Make a Deposit");
        int choice = selectAccount();  // sélection du compte dans lequel on souhaite déposer de l'argent 
        if(choice >= 0){
            double amount = getDollarAmount("How much would you like to deposit ?");
            bank.getCustomer(choice).getAccount().deposit(amount); 
            // sélection du client concerné puis sélection du champ compte client puis dépot d'argent sur ce compte
        }
    }

    private void makeWithdrawal() {
        displayHeader("Make a Withdrawal");
        int choice = selectAccount();
        if(choice >= 0){                    
            double amount = getDollarAmount("How much would you like to withdraw ?"); 
            bank.getCustomer(choice).getAccount().withdraw(amount); 
            // sélection du client concerné puis sélection du champ compte client puis dépot d'argent sur ce compte
        }
    }

    private void listBalances() {
        displayHeader("List Account Details");
        int choice = selectAccount();
        if(choice >= 0){
            displayHeader(bank.getCustomer(choice).getfullName() + " -- Account Details");
            System.out.println(bank.getCustomer(choice).getAccount());
        }
        else {
            System.out.println("<!> Invalid account selection <!>");
        }
        
    }

    private int selectAccount() {
        Scanner keyboard = new Scanner(System.in); 
        ArrayList<Customer> customers = bank.getCustomers();
        if(customers.size() <= 0){
            System.out.println("<!> No customers at your bank <!>");
            System.out.println("");                        
            return -1;
        }
        for(int i=0; i < customers.size(); i++){
            System.out.println("\t" + (i+1) + ") " + customers.get(i).basicInfos());                
        }
//        for(Object customer : customers){
//            System.out.println("\t" + customer);                           
//        }
        int choice = -1;
        try {
            System.out.println("");
            System.out.print("=> Please enter your selection : ");
            choice = Integer.parseInt(keyboard.nextLine()) -1;            
        } catch (NumberFormatException e) {
            System.out.println("<!> Choice invalid. It must be a number <!>");
            System.out.println("");                
           
        }
        if(choice < 0 || choice >= bank.getCustomers().size()){
            System.out.println("<!> The selected account number must be in the list <!>");
            System.out.println("");                           
            return -1;
        }
        return choice;
    }

    private void displayHeader(String message) {
        System.out.println("");
        int width = message.length() + 6;
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for(int i = 0; i < width; i++){
            sb.append("-");            
        }
        sb.append("+");
        System.out.println(sb.toString());
        System.out.println("|   " + message + "   |"); 
        System.out.println(sb.toString());
    }
  
}

//    private String getAccountType(){
//        boolean valid = false;
//        String accountType = "";
//        while(!valid){
//            accountType = askQuestion("Please enter Account Type (checking/savings): ");
//            if(accountType.equalsIgnoreCase("checking") || accountType.equalsIgnoreCase("savings")){
//                valid = true;
//            }
//            else {
//                System.out.println("Invalid account type selected. Please enter checking or savings");                       
//            }            
//        }
//        return accountType;
//    }