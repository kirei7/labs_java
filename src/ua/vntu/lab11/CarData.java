package ua.vntu.lab11;

// клас для зберігання даних машини
public class CarData {
    //марка
    private String mark;
    //модель
    private String model;
    //об'єм двигуна
    private Float volume;
    //тип кузова
    private String bodyType;
    //тип приводу
    private String driveType;

    //гетери-сетери для полів
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        if (mark == null || mark.equals("")) throw new NumberFormatException();
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (model == null || model.equals("")) throw new NumberFormatException();
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
        if (bodyType == null || bodyType.equals("")) throw new NumberFormatException();
        this.bodyType = bodyType;
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        if (driveType == null || driveType.equals("")) throw new NumberFormatException();
        this.driveType = driveType;
    }

    /*сетери, які вертають при присвоєнні об'єкт, якому присвоюється значення
    для чого це потрібно:
    код зі звичайними сетерами
    CarData car = new CarData();
    car.setMark(mark);
    car.setModel(model);

    код, який робить те саме, але замість set-методів використовує with-методи:
    CarData car = new CarData().withMark(mark).withModel(model);
    типу так можна код в одну строку записати
    */
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

    // переписуємо методи
    // перетворення об'єкта у строкове представлення
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
    // порівняння з іншим об'єктом
    // пояснень писати не буду, т.я. цей метод можна згенерувати
    // автоматично в intellij
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarData carData = (CarData) o;
        // при порівнянні двох об'єктів враховуємо лише марку і модель
        if (!getMark().equals(carData.getMark())) return false;
        return getModel().equals(carData.getModel());

    }
    // генерування хеш-коду об'єкта - потрібно, наприклад, щоб об'єкти
    // цього типу можна було класти в колекцію, Map, наприклад
    @Override
    public int hashCode() {
        int result = getMark().hashCode();
        result = 31 * result + getModel().hashCode();
        return result;
    }
}
