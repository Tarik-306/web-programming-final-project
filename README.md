# Java MVC ve JSTL ile Temel E-Ticaret Portalı

Bu proje Servlet, JSP, JSTL, JDBC ve MySQL kullanılarak hazırlanmış temel e-ticaret portalıdır.

## Kurulum
1. MySQL'de `database/schema.sql` dosyasını çalıştır.
2. `src/main/resources/db.properties` dosyasında kullanıcı adı ve şifreyi kendi MySQL bilgilerine göre değiştir.
3. Maven ile WAR oluştur:
   ```bash
   mvn clean package
   ```
4. `target/ecommerce-mvc-jstl.war` dosyasını Apache Tomcat webapps klasörüne koy.
5. Tarayıcıdan aç:
   `http://localhost:8080/ecommerce-mvc-jstl/home`

## Admin Girişi
- E-posta: `admin@site.com`
- Şifre: `admin`

## Kullanılan Yapı
- Model: User, Product, Category, Order, OrderItem, CartItem
- DAO: UserDAO, ProductDAO, CategoryDAO, OrderDAO
- Controller/Servlet: HomeServlet, ProductServlet, CartServlet, OrderServlet, RegisterServlet, LoginServlet, Admin servletleri
- View: JSP sayfaları

## Türkçe karakter ve görsel düzeltmesi

Bu sürümde Türkçe karakterlerin bozulmaması için şu düzeltmeler yapılmıştır:

- Tüm JSP sayfalarına `pageEncoding="UTF-8"` eklendi.
- `EncodingFilter` ile bütün isteklerde `request.setCharacterEncoding("UTF-8")` ve `response.setCharacterEncoding("UTF-8")` ayarlandı.
- `WEB-INF/web.xml` içine JSP UTF-8 ayarı eklendi.
- MySQL bağlantısına `useUnicode=true`, `characterEncoding=UTF-8` ve `connectionCollation=utf8mb4_unicode_ci` ayarları eklendi.
- Ürün görselleri artık dış bağlantı değil, proje içindeki `src/main/webapp/assets/img/` klasöründen geliyor.

Mevcut veritabanını silmeden düzeltmek için MySQL'de şunu çalıştırabilirsiniz:

```sql
SOURCE database/update_utf8_and_images.sql;
```

Veritabanını baştan kuruyorsanız sadece `database/schema.sql` dosyasını çalıştırmanız yeterlidir.


## Teslim Dosyaları
- Kaynak kodlar: `src/main/java` ve `src/main/webapp`
- Veritabanı SQL dosyası: `database/schema.sql`
- Word raporu: `report/proje-raporu.docx`
- Ürün görselleri: `src/main/webapp/assets/img`

## Türkçe Karakter Notu
Projede JSP sayfaları, Servlet cevapları, Maven kaynak kodlaması ve MySQL veritabanı UTF-8/utf8mb4 uyumlu ayarlanmıştır. Eğer phpMyAdmin üzerinden SQL içe aktarırken karakter problemi olursa dosya kodlamasının UTF-8 olduğundan emin olun.


## Word Raporu
Proje raporu Word biçiminde `report/proje-raporu.docx` ve aynı klasörde `Proje_Raporu_Word.docx` adıyla bulunmaktadır. Rapor Türkçe hazırlanmıştır ve ekran/ürün görselleri eklenmiştir.


## Fotoğraf düzeltmesi
Ürün fotoğrafları `src/main/webapp/assets/img` klasöründedir. Sayfalarda görseller `/product-image?file=...` servleti üzerinden çağrılır. Bu yüzden Tomcat context path farklı olsa bile fotoğraflar görünür. Veritabanında `image_url` alanı `telefon.png`, `laptop.png` gibi dosya adı olarak tutulur.
