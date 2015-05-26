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
	private double gramPrice; // price per gram in cents
	private double volumePrice; // price per cubic centimeter in cents

	/**
	 * Constructs a new Delivery Price based on the specified gram and
	 * volume prices.
	 *
	 * @param gramPrice
	 * 		-- the price per gram in cents
	 * @param volumePrice
	 * 		-- the price per cubic centimeter in cents
	 */
	public DeliveryPrice(double gramPrice, double volumePrice){
		this.gramPrice = gramPrice;
		this.volumePrice = volumePrice;
	}

	/**
	 * Returns the price per gram for this delivery price.
	 *
	 * @return gram price in cents (per gram)
	 */
	public double getGramPrice(){
		return gramPrice;
	}

	/**
	 * Returns the price per cubic centimeter in cents for this delivery
	 * price.
	 *
	 * @return volume price in cents (per cubic centimeter)
	 */
	public double getVolumePrice(){
		return volumePrice;
	}

	/**
	 * Updates the gram and volume prices in cents to the specified values.
	 *
	 * @param gramPrice
	 * 		-- the new price per gram in cents
	 * @param volumePrice
	 * 		-- the new price per cubic centimeter in cents
	 */
	public void updateDeliveryPrice(double gramPrice, double volumePrice){
		this.gramPrice = gramPrice;
		this.volumePrice = volumePrice;
	}

	/**
	 * Calculate the cost of delivering a package with the specified
	 * weight and volume. Returns the cost of sending the package in
	 * dollars.
	 *
	 * @param weight
	 * 		-- weight of package (in grams)
	 * @param volume
	 * 		-- volume of package (int cubic centimeters)
	 * @return
	 * 		-- cost of delivering package (in dollars)
	 */
	public double calculateDeliveryPrice(int weight, int volume){
		return (((double)weight * gramPrice) + ((double)volume * volumePrice)) / 100; // converts to dollars
	}

	public boolean equals(Object o){
		if(o == this){
			return true;
		}
		if(o == null){
			return false;
		}
		if(o instanceof DeliveryPrice){
			if(((DeliveryPrice)o).getGramPrice() != gramPrice){
				return false;
			}
			if(((DeliveryPrice)o).getVolumePrice() != volumePrice){
				return false;
			}
		}
		else{
			return false;
		}
		return true;
	}

	public String toString(){
		return "Delivery Price: "+gramPrice+" per gram, "+volumePrice+" per cubic cm";
	}
}
