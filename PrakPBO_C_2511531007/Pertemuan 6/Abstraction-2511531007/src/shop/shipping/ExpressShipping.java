package shop.shipping;

public class ExpressShipping extends ShippingService{
    public ExpressShipping() {
        super("Expres");
    }
    
    @Override 
    protected long ratePerKg() {
        return 18_000;
    }
}
