
public class Result {
    double area;
    double waterAmount;
    boolean areaOk;
    boolean waterOk;

    public Result(double area, double waterAmount, boolean areaOk, boolean waterOk) {
        this.area = area;
        this.waterAmount = waterAmount;
        this.areaOk = areaOk;
        this.waterOk = waterOk;
    }

    public double getArea() {
        return area;
    }

    public double getWaterAmount() {
        return waterAmount;
    }

    public boolean isAreaOk() {
        return areaOk;
    }

    public boolean isWaterOk() {
        return waterOk;
    }

   
  
}