package proxies;

import java.util.Objects;

public class TestDto {
    private String name;
    private int number;
    private boolean bool;

    public TestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public TestDto copy() {
        TestDto copy = new TestDto();
        copy.setName(name);
        copy.setNumber(number);
        copy.setBool(bool);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestDto testDto)) return false;
        return getNumber() == testDto.getNumber() && isBool() == testDto.isBool() && Objects.equals(getName(), testDto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNumber(), isBool());
    }
}
