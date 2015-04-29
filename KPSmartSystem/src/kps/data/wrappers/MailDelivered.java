package kps.data.wrappers;

/**
 * Represents a total amount, weight and volume of mail that has been
 * delivered from a particular origin to a destination.
 *
 * @author David
 *
 */
public class MailDelivered {

	// fields
	private int totalAmount;
	private int totalWeight; // in grams
	private int totalVolume; // in cubic centimeters

	/**
	 * Constructs an empty instance of MailDelivered.
	 */
	public MailDelivered(){
		totalAmount = 0;
		totalWeight = 0;
		totalVolume = 0;
	}

	/**
	 * Returns the total amount of mail delivered.
	 *
	 * @return total amount
	 */
	public int getTotalAmount(){
		return totalAmount;
	}

	/**
	 * Returns the total weight in grams of all the mail
	 * that has been delivered.
	 *
	 * @return total weight (in grams)
	 */
	public int getTotalWeight(){
		return totalWeight;
	}

	/**
	 * Returns the total volume in cubic centimeters of all
	 * the mail that has been delivered.
	 *
	 * @return
	 */
	public int getTotalVolume(){
		return totalVolume;
	}

	/**
	 * Updates the totals associated with this object by adding
	 * the weight and volume specified. Also increments the amount
	 * of mail sent.
	 *
	 * @param weight
	 * 		-- weight to be added (in grams)
	 * @param volume
	 * 		-- volume to be added (in cubic centimeters)
	 */
	public void updateMailDelivered(int weight, int volume){
		totalAmount++;
		totalWeight += weight;
		totalVolume += volume;
	}
}
