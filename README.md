# Đơn vị hành chính Việt Nam

## Nguồn dữ liệu từ Tổng cục thống kê

- [http://www.gso.gov.vn/dmhc2015/Default.aspx](http://www.gso.gov.vn/dmhc2015/Default.aspx)
- Dữ liệu được cập nhật tới ngày 21/12/2019

## Cấu trúc tệp dữ liệu

- Thư mục:
  - `.../data/excel`: Thư mục chứa các tệp dữ liệu lấy từ Tổng cục thống kê (21/12/2019) dạng `excel`
  - `.../data/json`: Thư mục chứa các tệp dữ liệu được trích xuất sang dạng `json`

- Tệp dữ liệu:
  - `tinh_tp.json`: Tệp dữ liệu chứa thông tin về tỉnh, thành phố
  - `quan-huyen/`: Thư mục chứa các file `json` là thông tin các quận, huyện, thị xã, thành phố trực thuộc của một tỉnh. Tên file là mã của tỉnh. Dùng để truy vấn ở client. Ví dụ: `quan_huyen/01.json` là thông tin các quận, huyện,... của tỉnh có mã `01`, là `Hà Nội`.
  - `xa-phuong/`: Thư mục chứa các file `json` là thông tin các xã, phường, thị trấn của một quận, huyện,... Tên file là mã của quận, huyện, thị xã hoặc thành phố trực thuộc tỉnh. Dùng để truy vấn ở client. Ví dụ: `xa_phuong/001.json` là thông tin các xã, phường,... của quận/huyện có mã `001`, là `quận Ba Đình, Hà Nội`.
  
## Project

- Ngôn ngữ: `Java (JDK 8)`
- Build Tool: `Maven`
- Các thư viện sử dụng:

```xml
<dependencies>
    <!-- Excel 2003 and earlier -->
    <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi</artifactId>
      <version>4.1.1</version>
    </dependency>

    <!-- Excel 2007 and later -->
    <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi-ooxml</artifactId>
      <version>4.1.1</version>
    </dependency>

    <!-- Lombok -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.10</version>
      <optional>true</optional>
    </dependency>

    <!--Apache Commons Lang, a package of Java utility classes for the classes that are in java.lang's hierarchy,
        or are considered to be so standard as to justify existence in java.lang.-->
    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-lang3</artifactId>
      <version>3.9</version>
    </dependency>

    <!--Gson-->
    <dependency>
      <groupId>com.google.code.gson</groupId>
      <artifactId>gson</artifactId>
      <version>2.8.6</version>
      <scope>compile</scope>
    </dependency>
</dependencies>
```
