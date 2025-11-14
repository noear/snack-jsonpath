package features.snack4.jsonschema.victools;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用于 JSON Schema 测试的样本实体类
 * 包含 Java 常见的各种数据类型
 */
public class TestSampleEntity {

    // 基本数据类型
    private boolean primitiveBoolean;
    private byte primitiveByte;
    private short primitiveShort;
    private int primitiveInt;
    private long primitiveLong;
    private float primitiveFloat;
    private double primitiveDouble;
    private char primitiveChar;

    // 包装类型
    private Boolean wrapperBoolean;
    private Byte wrapperByte;
    private Short wrapperShort;
    private Integer wrapperInteger;
    private Long wrapperLong;
    private Float wrapperFloat;
    private Double wrapperDouble;
    private Character wrapperCharacter;

    // 字符串和大数字
    private String stringValue;
    private BigInteger bigInteger;
    private BigDecimal bigDecimal;

    // 日期时间类型
    private Date utilDate;
    private LocalDate localDate;
    private LocalDateTime localDateTime;

    // 数组和集合类型
    private int[] intArray;
    private String[] stringArray;
    private List<String> stringList;
    private List<Integer> integerList;
    private Set<String> stringSet;
    private Map<String, Object> map;

    // 枚举类型
    private Status status;

    // 嵌套对象
    private Address address;
    private List<Address> addresses;

    // 静态嵌套类 - 地址信息
    public static class Address {
        private String country;
        private String province;
        private String city;
        private String street;
        private String zipCode;

        // 构造方法
        public Address() {}

        public Address(String country, String province, String city, String street, String zipCode) {
            this.country = country;
            this.province = province;
            this.city = city;
            this.street = street;
            this.zipCode = zipCode;
        }

        // getter 和 setter 方法
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }

        public String getProvince() { return province; }
        public void setProvince(String province) { this.province = province; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }

        public String getZipCode() { return zipCode; }
        public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    }

    // 枚举定义
    public enum Status {
        ACTIVE,
        INACTIVE,
        PENDING,
        DELETED
    }

    // 默认构造方法
    public TestSampleEntity() {}

    // getter 和 setter 方法
    public boolean isPrimitiveBoolean() { return primitiveBoolean; }
    public void setPrimitiveBoolean(boolean primitiveBoolean) { this.primitiveBoolean = primitiveBoolean; }

    public byte getPrimitiveByte() { return primitiveByte; }
    public void setPrimitiveByte(byte primitiveByte) { this.primitiveByte = primitiveByte; }

    public short getPrimitiveShort() { return primitiveShort; }
    public void setPrimitiveShort(short primitiveShort) { this.primitiveShort = primitiveShort; }

    public int getPrimitiveInt() { return primitiveInt; }
    public void setPrimitiveInt(int primitiveInt) { this.primitiveInt = primitiveInt; }

    public long getPrimitiveLong() { return primitiveLong; }
    public void setPrimitiveLong(long primitiveLong) { this.primitiveLong = primitiveLong; }

    public float getPrimitiveFloat() { return primitiveFloat; }
    public void setPrimitiveFloat(float primitiveFloat) { this.primitiveFloat = primitiveFloat; }

    public double getPrimitiveDouble() { return primitiveDouble; }
    public void setPrimitiveDouble(double primitiveDouble) { this.primitiveDouble = primitiveDouble; }

    public char getPrimitiveChar() { return primitiveChar; }
    public void setPrimitiveChar(char primitiveChar) { this.primitiveChar = primitiveChar; }

    public Boolean getWrapperBoolean() { return wrapperBoolean; }
    public void setWrapperBoolean(Boolean wrapperBoolean) { this.wrapperBoolean = wrapperBoolean; }

    public Byte getWrapperByte() { return wrapperByte; }
    public void setWrapperByte(Byte wrapperByte) { this.wrapperByte = wrapperByte; }

    public Short getWrapperShort() { return wrapperShort; }
    public void setWrapperShort(Short wrapperShort) { this.wrapperShort = wrapperShort; }

    public Integer getWrapperInteger() { return wrapperInteger; }
    public void setWrapperInteger(Integer wrapperInteger) { this.wrapperInteger = wrapperInteger; }

    public Long getWrapperLong() { return wrapperLong; }
    public void setWrapperLong(Long wrapperLong) { this.wrapperLong = wrapperLong; }

    public Float getWrapperFloat() { return wrapperFloat; }
    public void setWrapperFloat(Float wrapperFloat) { this.wrapperFloat = wrapperFloat; }

    public Double getWrapperDouble() { return wrapperDouble; }
    public void setWrapperDouble(Double wrapperDouble) { this.wrapperDouble = wrapperDouble; }

    public Character getWrapperCharacter() { return wrapperCharacter; }
    public void setWrapperCharacter(Character wrapperCharacter) { this.wrapperCharacter = wrapperCharacter; }

    public String getStringValue() { return stringValue; }
    public void setStringValue(String stringValue) { this.stringValue = stringValue; }

    public BigInteger getBigInteger() { return bigInteger; }
    public void setBigInteger(BigInteger bigInteger) { this.bigInteger = bigInteger; }

    public BigDecimal getBigDecimal() { return bigDecimal; }
    public void setBigDecimal(BigDecimal bigDecimal) { this.bigDecimal = bigDecimal; }

    public Date getUtilDate() { return utilDate; }
    public void setUtilDate(Date utilDate) { this.utilDate = utilDate; }

    public LocalDate getLocalDate() { return localDate; }
    public void setLocalDate(LocalDate localDate) { this.localDate = localDate; }

    public LocalDateTime getLocalDateTime() { return localDateTime; }
    public void setLocalDateTime(LocalDateTime localDateTime) { this.localDateTime = localDateTime; }

    public int[] getIntArray() { return intArray; }
    public void setIntArray(int[] intArray) { this.intArray = intArray; }

    public String[] getStringArray() { return stringArray; }
    public void setStringArray(String[] stringArray) { this.stringArray = stringArray; }

    public List<String> getStringList() { return stringList; }
    public void setStringList(List<String> stringList) { this.stringList = stringList; }

    public List<Integer> getIntegerList() { return integerList; }
    public void setIntegerList(List<Integer> integerList) { this.integerList = integerList; }

    public Set<String> getStringSet() { return stringSet; }
    public void setStringSet(Set<String> stringSet) { this.stringSet = stringSet; }

    public Map<String, Object> getMap() { return map; }
    public void setMap(Map<String, Object> map) { this.map = map; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }

    @Override
    public String toString() {
        return "TestSampleEntity{" +
                "primitiveBoolean=" + primitiveBoolean +
                ", primitiveInt=" + primitiveInt +
                ", stringValue='" + stringValue + '\'' +
                ", status=" + status +
                '}';
    }
}