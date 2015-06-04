package kps.data.wrappers;

import java.util.ArrayList;
import java.util.List;

public class Metrics{

    // fields
    private double totalRevenue;
    private double totalExpenditure;
    //private List<Tuple<Double>> mailDelivered;
    private List<Double> allRevenue;
    private List<Double> allExpenditure;

    // business event counters
    private int totalMailDeliveryEvents;
    private int totalPriceUpdateEvents;
    private int totalTransportCostUpdateEvents;
    private int totalTransportDiscontinuedEvents;

    public Metrics(){
        totalRevenue = 0;
        totalExpenditure = 0;
        totalMailDeliveryEvents = 0;
        totalPriceUpdateEvents = 0;
        totalTransportCostUpdateEvents = 0;
        totalTransportDiscontinuedEvents = 0;
        //mailDelivered = new ArrayList<Tuple<Double>>();
        allRevenue = new ArrayList<Double>();
        allExpenditure = new ArrayList<Double>();
    }

    public Metrics(double totalRevenue, double totalExpenditure){
        this.totalRevenue = totalRevenue;
        this.totalExpenditure = totalExpenditure;
        totalMailDeliveryEvents = 0;
        totalPriceUpdateEvents = 0;
        totalTransportCostUpdateEvents = 0;
        totalTransportDiscontinuedEvents = 0;
        //mailDelivered = new ArrayList<Tuple<Double>>();
        allRevenue = new ArrayList<Double>();
        allExpenditure = new ArrayList<Double>();
    }

    public double getTotalRevenue(){
        return totalRevenue;
    }

    public void addRevenue(double revenue){
        totalRevenue += revenue;
    }

    public double getTotalExpenditure(){
        return totalExpenditure;
    }

    public void addExpenditure(double expenditure){
        totalExpenditure += expenditure;
    }

    public int getTotalMailDeliveryEvents(){
        return totalMailDeliveryEvents;
    }

    public void addMailDeliveryEvent(double revenue, double expenditure){
    	totalRevenue += revenue;
    	totalExpenditure += expenditure;
    	//mailDelivered.add(new Tuple<Double>(revenue, expenditure));
    	allRevenue.add(revenue);
    	allExpenditure.add(expenditure);
        totalMailDeliveryEvents++;
    }

    public int getTotalPriceUpdateEvents(){
        return totalPriceUpdateEvents;
    }

    public void addPriceUpdateEvent(){
        totalPriceUpdateEvents++;
    }

    public int getTotalTransportCostUpdateEvents(){
        return totalTransportCostUpdateEvents;
    }

    public void addTransportCostUpdateEvent(){
        totalTransportCostUpdateEvents++;
    }

    public int getTotalTransportDiscontinuedEvents(){
        return totalTransportDiscontinuedEvents;
    }

    public void addTransportDiscontinuedEvent(){
        totalTransportDiscontinuedEvents++;
    }

    public int getTotalBusinessEvents(){
        return totalMailDeliveryEvents + totalPriceUpdateEvents + totalTransportCostUpdateEvents + totalTransportDiscontinuedEvents;
    }

    public List<Double> getAllRevenue(){
    	return allRevenue;
    }

    public List<Double> getAllExpenditure(){
    	return allExpenditure;
    }
    /*
    private class Tuple<E>{

        private E[] elements;

        @SafeVarargs
        public Tuple(E...elements){
            this.elements = elements;
        }

        public int size(){
            return elements.length;
        }

        public E get(int index){
            if(index >= 0 && index < elements.length){
                return elements[index];
            }
            return null;
        }
    }
    */
}