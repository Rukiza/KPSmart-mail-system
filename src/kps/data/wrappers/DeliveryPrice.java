package kps.data.wrappers;

/**
 * Represents the current delivery price for sending mail from
 * a particular origin to destination.
 * 
 * @author David
 *
 */
public class DeliveryPrice {
	
	// fields
	private double gramPrice; // price per gram
	private double volumePrice; // price per cubic centimeter
	
	/**
	 * Constructs a new Delivery Price based on the specified gram and
	 * volume prices.
	 * 
	 * @param gramPrice
	 * 		-- the price per gram
	 * @param volumePrice
	 * 		-- the price per cubic centimeter
	 */
	public DeliveryPrice(double gramPrice, double volumePrice){
		this.gramPrice = gramPrice;
		this.volumePrice = volumePrice;
	}
	
	/**
	 * Returns the price per gram for this delivery price.
	 * 
	 * @return gram price (per gram)
	 */
	public double getGramPrice(){
		return gramPrice;
	}
	
	/**
	 * Returns the price per cubic centimeter for this delivery
	 * price.
	 * 
	 * @return volume price (per cubic centimeter)
	 */
	public double getVolumePrice(){
		return volumePrice;
	}
	
	/**
	 * Updates the gram and volume prices to the specified values.
	 * 
	 * @param gramPrice
	 * 		-- the new price per gram
	 * @param volumePrice
	 * 		-- the new price per cubic centimeter
	 */
	public void updateDeliveryPrice(double gramPrice, double volumePrice){
		this.gramPrice = gramPrice;
		this.volumePrice = volumePrice;
	}
}
