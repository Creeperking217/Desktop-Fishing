public class Bait {
    int power;
    int type = 0;
    String name;
    String imageName;
    int amt = 0;

    public Bait(int power, String name, String imageName) {
        this.power = power;
        this.name = name;
        this.imageName = imageName;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public int getPower() {
        return power;
    }
    public int getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public String getImageName() {
        return imageName;
    }

    
}
