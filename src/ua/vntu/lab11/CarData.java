package ua.vntu.lab11;

public class CarData {
    private String mark;
    private String model;
    private Float volume;
    private String bodyType;
    private String driveType;


    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public CarData withMark(String mark){
        setMark(mark);
        return this;
    }
    public CarData withModel(String model){
        setModel(model);
        return this;
    }
    public CarData withVolume(Float  volume){
        setVolume(volume);
        return this;
    }
    public CarData withBodyType(String  bodyType){
        setBodyType(bodyType);
        return this;
    }
    public CarData withDriveType(String driveType){
        setDriveType(driveType);
        return this;
    }

    @Override
    public String toString() {
        return "CarData{" +
                "mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", volume=" + volume +
                ", bodyType='" + bodyType + '\'' +
                ", driveType='" + driveType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarData carData = (CarData) o;

        if (!getMark().equals(carData.getMark())) return false;
        return getModel().equals(carData.getModel());

    }

    @Override
    public int hashCode() {
        int result = getMark().hashCode();
        result = 31 * result + getModel().hashCode();
        return result;
    }
}
