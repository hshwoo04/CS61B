public class CompoundInterest {
    static final int THIS_YEAR = 2014;

    /** Computes the number of years between targetYear and THIS_YEAR,
        e.g. if THIS_YEAR is 2014 and targetYear is 2015, the result
        should be 1. Throughout the assignment it is OK to assume that
        targetYear is >= THIS_YEAR. */

    static int numYears(int targetYear) {
        return targetYear - THIS_YEAR;
    }

    /** Suppose we have an asset worth presentValue that appreciates
        by rate compounded annually. This method returns the value of that
        asset in the year given by targetYear.  

        The rate is given as a percentage return. For example, if the
        presentValue is 10, the rate is 12, and the targetYear is 2016,
        then the futureValue will be 10*1.12*1.12 = 12.544. */

    static double futureValue(double presentValue, double rate, int targetYear) {
        double tempValue = presentValue;
        for(int num = 1; num <= numYears(targetYear); num++){
          tempValue = tempValue * (1 + (rate/100));
        }
        return tempValue;
    }

    /** Same as futureValue, except that the final amount is converted
        into THIS_YEAR dollars, assuming a simple model where inflation
        compounds annually at a constant rate.

        For example, suppose the presentValue is 10, the rate is 12, the
        targetYear is 2016, and the inflationRate is 3. 

        In this case, the nominal value is 12.544. If we convert this into
        2014 dollars, we get 12.544 * 0.97 * 0.97 = 11.8026496 dollars */

    static double futureValueReal(double presentValue, double rate, int targetYear, double inflationRate) {
      double curValue = futureValue(presentValue, rate, targetYear);
      double infValue = curValue;
      for(int num =1; num<= numYears(targetYear); num++){
        infValue = infValue * (1-(inflationRate/100));
      }
      return infValue;

    }

    /** Suppose you invest perYear dollars at the end of every year until 
        targetYear, with growth compounded annually at rate. This method 
        returns the total value of your savings in targetYear. 

        For example, if perYear is 5000, targetYear is 2016, and rate is 10,
        then the result will be 5000*1.1*1.1 + 5000*1.1 + 5000 = 16550.*/

    static double totalSavings(double perYear, int targetYear, double rate) {
        double finalValue = 0;
        for(int num = numYears(targetYear); num >= 0; num--){
          double compSavings = perYear;
          int compNum = num;
          while (compNum >= 1){
            compSavings = compSavings * (1 + (rate/100));
            compNum--;
          }
          finalValue += compSavings;
        }
        return finalValue;
    }

    /** Same as totalSavingsReal, except given in THIS_YEAR dollars */

    static double totalSavingsReal(double perYear, int targetYear, double rate, double inflationRate) {
      double curValue = totalSavings(perYear, targetYear, rate);
      double infValue = curValue;
      for(int num =1; num <= numYears(targetYear); num++){
        infValue = infValue * (1-(inflationRate/100));
      }
      return infValue;
    }

    /** Prints out the future value of a dollar in a nice format, assuming
      * yearly compounded interest at a rate equal to returnRate. */

    static void printDollarFV(int targetYear, double returnRate, double inflationRate) {

        double nominalDollarValue = 1;
        double realDollarValue = futureValueReal(nominalDollarValue, returnrate, targetYear, inflationRate);
        

        /* Do not change anything in this method below this line */

        String dollarSummary = String.format("Assuming a %.2f%% rate of return," +
                               " a dollar saved today would be worth" +
                               " %.2f dollars in the year %d, or %.2f dollars" +
                               " adjusted for inflation.", returnRate, nominalDollarValue,
                               targetYear, realDollarValue);

        System.out.println(dollarSummary);      
    }

    /** Prints out the future value of your total savings in a nice format, 
      * assuming yearly compounded interest at a rate equal to returnRate,
      * and an investment of perYear dollars each year. */

    static void printSavingsFV(int targetYear, double returnRate, double inflationRate, 
                              double perYear) {

        double nominalSavings = 1;
        double realSavings = nominalSavings + totalSavingsReal(perYear, targetYear, returnrate, inflationrate);

        /* Do not change anything in this method below this line */

        String savingsSummary = String.format("Assuming a %.2f%% rate of return," +
                               " in the year %d, after saving %.2f" + 
                               " dollars per year, you'll have %.2f dollars or" +
                               " %.2f dollars adjusted for inflation.", returnRate, 
                               targetYear, perYear, nominalSavings, realSavings);

        System.out.println(savingsSummary);
    }

    public static void main(String[] args) {
        /* Parameters for the analysis.

         * targetYear is the year of interest.
         * returnRate is the percentage rate that you expect to earn on
              average until targetYear.
         * inflationRate is the average inflation rate until targetYear
         * perYear is how much money you will save per year until targetYear */

        int targetYear = 2054;
        double returnRate = 10; 
        double inflationRate = 3;

        double perYear = 10000;

        /* Do not modify anything below this line in main */

        printDollarFV(targetYear, returnRate, inflationRate);
        System.out.println();
        printSavingsFV(targetYear, returnRate, inflationRate, perYear);
    }
}