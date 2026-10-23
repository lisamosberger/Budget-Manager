# Budget Manager
This projekt is a CLI program written in Java. The purpose of this program is to keep 
track of income and expenses. The user can create categories, add transactions 
and see how much money he has left. It is also possible to save the transactions, which 
will be automatically loaded when starting the program. 

I divided the program into different classes to make the code easier to understand 
and follow. Each class has its own responsibility. 
The CliApp handles the suer interaction, the BudgetCalculator 
calculates the totals and the TransactionFile saves and loads the
transactions

## Functions
The program contains: 
- Add transactions (income or expense)
- Create categories (a transaction can only use an existing category)
- Show all transactions
- Show the balance and the income and expense in total and per category
- Filter transactions by type and sort them by amount
- Remove one single transaction or clear all of them
- Save the transactions to a file and load them at start
- Log invalig input and file problems on several levels

The amount can be typed with "," or ".", it doesn't matter.
Category names are cleaned up (Spaces removed, first letter upper case)
so food turns to Food.

## Project structure 
Transaction - Record with date, category, amount and type. Validates itself in the constructor.
TransactionType - Enum with INCOME and EXPENSE
InvalidTransactionException - My own umchecked exception for invalid transactions
Repository<T> - Genereic class. Stores objects of any type in a list. Used for transactions and for category names.
BudgetCalculator - All calculations: income, expense, balance and sums per category. 
TransactionFile - Saves and loads transactions as text lines
CliApp - The menu. It reads the input, calls the other classes and prints the result.

The menu is hard to test because it reads from the keboard, so the rest of the logic
lives in classes that can be tested directly. 

## Design decisions 
Transaction is a record. A transaction does not change after 
it has been created, so a record fits. The constructor of the 
transaction checks so an invalid transaction can never exist anywhere in the 
program, not only when it comes from the menu. An invalid value 
throws InvalidTransactionException.

Categories cannot contain commas. The file uses commas to seperate the 
fields, s a comma in a category name would break the line. The Transaction 
constructor therefore rejects it direclty.

Optional for the category lookup. findCategory returns an 
Optional <String>. A missing category is a normal result and not an 
error and the caller is forced to handle both cases.

## File format 
Each transaction is one line safed in transaction.txt
example: 
2026-10-22,Food,1000.0,EXPENSE
The fields are date, category, amount and type. If the file does 
not exist the program starts with no transaction. A line that cannot be read
(wrong number of fields or invalid value) will be skipped and a warning to the log 
appears. Categories are not saved separately. They are rebuilt from the 
transactions when the file is loaded

## G Requirements 
- [X] Working application with collections (ArrayList), genereic class and exception class
- [X] Validation of input when adding a transaction 
- [X] Balance and sum per category 
- [X] Filter and sort transactions
- [X] File for reading and writing transactions with readonable error handling 
- [X] Missing or broken file does not crash the program 
- [X] Basic logging for invalid input and file errors
- [X] Junit 5 tests for the central logic 
- [X] Tests for normal cases and edge cases 
- [X] Several meaningful commits, including commits for tests

## VG requirements
- [X] Logging on several levels (DEBUG, INFO, WARN, ERROR)
- [X] Reflection about generics and the Stream API 

## Testing 
I wrote one Junit 5 test class for each central class, including 
the main factors that needed to be testet. Also situation when 
wrong spelling/ wrong input can lead to a crash. 

The tests follow Arrange Act Assert and each test checks one thing 
Because the amounts are double, I compare them with a delta 0.0001.

## Debugging and bug report
I found the bugs below while testing. For each one I got either a failed test 
or a failed program. After this I used the debugger to find the cause, which 
i could then fix and created a test as protections. All the tests now pass.

Bug 1: A broken date in the file crashed the program at start
If transaction.txt contained a line like not-a-date,Food,10.0,EXPENSE,
the program ended with an exception at start instead of skipping the line. 
Lines with a broken amount or an unknown type are skipped now 
correctly. 

I wrote a test that saves a file with one valid line and one 
line with the date not-a-date and expects one loaded transaction. The test 
failed with a DateTimeParseException at first. 

Debugging. I set a breakpoint on LocalDate.parse(parts[0])
in loadTransaction and stepped over the line. The debugger left the try block 
and the exception travveled up through the whole call, so my catch 
did not match. The catch only handled IllegalArgumentException. 

I Fixed it, The catch for the parsing now handles RuntimeException, so 
every parsing problem caught. At the same time I added FileFormatException 
for the lines with the wrong numbers of fields. Both cases write a warning with the 
line number to the log and continue the next line like 
normally

The test is calld "shouldSkipBrokenLines" in TransactionFileTest.

Bug 2: A category typed in different capitalisations got the wrong sum 
I created the category food and added expense to Food. At first you couldn't even add them in the category 
because the program didn't connect these two. 
 I found it while running the program by hand and then I noticed that 
it always gave an error.
This Error was pretty simple to fix, additionally I added an method that 
makes the capitalisation better, the first letter is always a big one while the other 
that follows are set to lowercase.

Smaller mistakes like that I loaded the transactions twice in the beginning or that comma could be created but 
never used were simple to fix but hard to find in my opinion. 


## Logging 
I used a logger with a file handler, the classes CLiApp, Repository and TransactionFile used the logger.
In the end of the project I realised what a big help it could have been, to find the problems. 
But because it was the first time using a logger in a project for me I added it really late to 
the project. I understand now how it can improve the working flow in finding bugs and solving them. 

I used FINE for small problems, WARNING for problems such as invalid transaction data, 
SEVERE only when failing to save or load a file and INFO to log info if it actions were sucessfully.

## Reflection 
Generics: Repository<T> Withour generics I would have needed two nearly 
identical classes, one for transactions and one for category names or one class that stores 
Objects. With Repository<T>  I wrote the logic once and the compiler 
checks the type everywhere. It was really usefull because it was easier to handle and 
save into a list. There wasnt't anything double. findWhere() made it even more reusable. 
The same method is used with every condition from TransactionFilter and the repository 
does not know anything about transactions. 

Stram API 
At first the calculations were written with loops. Every method had the same structure: 
start a total at 0, loop over all transactions, check a condition and add the amount. 

With streams the same calculations says what I want instead of how to count it
and one method now replaces the two methods for income and expense

It is shorter and each condition has its own lin . The sums per category were 
the biggest improvement, because it replaced a loop, a Map and a check for existing keys
and the TreeMap gives the categories in an alphabetical order. 
Sorting also became much shorter. 

Streams are practical to use but have also downsides. A long chain 
is harder to debug than a loop, because I cannot easily 
put a breakpoint on one step and I had to learn to read the chain 
from top to bottom to understand. For two or three steps I think streams are clearer 
than loops but for long chains it can be easier to split them up. 

Most of my first tests tested the normal case. The bugs 
were in edge cases, a broken line in a file or a different speling. Because of this 
I think sometimes its better to write first the tests before the method itself
I also learned also that a failling test makes debugging much faster, 
because you know what where the error happens and i know where to 
put the breakpoint and when I am done. 
For Logging I still need to practice how to use it propperly 
and when. In the end I understood that I is practical to use, but i completly forgot to add 
that function in the begginning when I started the program. Now i know that 
logging is something to set up in the beggining of a project.

I used txt format for saving my files, which I realised in the end can 
create some problems, for example if the user typed in , it would make reading a line not 
possible because it used to split at ",". I now changed it to ";" because in the name value 
can the user still use a comma. But I created a method so it doesn't crash the program when using 
";", instead it gives a warning and sends the user back to the main menu.

## Limitations 
- A new transaction in my program only gets todays date, this is possible to change. 
- The filter in the menu only filters by type -> expense or income.
- A category without any transaction is not saved in file, so the category will be gone after a restart.
- Amounts are double. For real money BigDecimal would be probably safer.
- The menu CliApp is only tested through its helper methods, because it reads from the keyboard.