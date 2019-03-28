package cn.edu.nju.software.iot.iotcore.entity.base;

public enum State {
    ONLINE("ONLINE"),OFFLINE("OFFLINE");
    private String name;

    State(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
