package cn.edu.nju.software.iot.iotcore.entity;

import cn.edu.nju.software.iot.iotcore.entity.Record;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
public class DoubleRecord extends Record {

    @Transient
    private Double value;

    public Double getValue() {
        if(value==null){
            return getDouble(this.getByteValue());
        }
        return value;
    }

    public void setValue(Double value) {
        byte[] b = new byte[8];
        long l = Double.doubleToLongBits(value);

        for (int i = 0; i < 8; i++) {
            b[i] = new Long(l).byteValue();
            l = l >> 8;
        }
        this.value = value;
        this.setByteValue(b);
    }
    public static double getDouble(byte[] b) {
        long m;
        m = b[0];
        m &= 0xff;
        m |= ((long) b[1] << 8);
        m &= 0xffff;
        m |= ((long) b[2] << 16);
        m &= 0xffffff;
        m |= ((long) b[3] << 24);
        m &= 0xffffffffl;
        m |= ((long) b[4] << 32);
        m &= 0xffffffffffl;
        m |= ((long) b[5] << 40);
        m &= 0xffffffffffffl;
        m |= ((long) b[6] << 48);
        m &= 0xffffffffffffffl;
        m |= ((long) b[7] << 56);
        return Double.longBitsToDouble(m);
    }
}
