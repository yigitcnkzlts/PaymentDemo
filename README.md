# PaymentDemo

PaymentDemo; kredi kartı, Havale / EFT ve dijital cüzdan ödeme akışlarını konsol üzerinden simüle eden, sade bir Java uygulamasıdır. Her işlem; benzersiz bir UUID, ödeme türü, tutar, durum ve açıklama içeren immutable bir `PaymentResult` ile temsil edilir. Proje, nesne yönelimli programlama yaklaşımını ve yaygın tasarım desenlerini küçük, çalıştırılabilir bir örnek üzerinde gösterir.

## Projenin Amacı

Java OOP ve Design Pattern kavramlarını gerçekçi bir ödeme simülasyonu üzerinde uygulamalı olarak öğrenmek ve Singleton, Strategy ile Factory desenlerinin birlikte kullanımını göstermektir.

## Kullanılan Teknolojiler

- Java 21
- Maven
- JUnit 5
- Object-Oriented Programming
- Singleton Pattern
- Strategy Pattern
- Factory Pattern

## Desteklenen Ödeme Yöntemleri

- Kredi kartı
- Havale / EFT
- Dijital cüzdan

## Kullanılan Tasarım Desenleri

### 1. Singleton Pattern

Singleton, bir sınıftan uygulama boyunca yalnızca tek bir nesne oluşturulmasını sağlar. Projede `PaymentService` sınıfı bu deseni kullanır. İç içe `InstanceHolder` sınıfı sayesinde nesne ihtiyaç duyulduğunda oluşturulur; Java'nın sınıf yükleme garantileriyle ek senkronizasyon gerektirmeden thread-safe biçimde paylaşılır. Böylece tüm ödeme talepleri tek bir servis erişim noktası üzerinden yönetilir.

### 2. Strategy Pattern

Strategy, aynı işi farklı yöntemlerle gerçekleştiren algoritmaları ortak bir sözleşme altında toplar. `PaymentStrategy` arayüzü ödeme davranışını tanımlar ve `pay` metodu bir `PaymentResult` döndürür. `CreditCardPayment`, `BankTransferPayment` ve `DigitalWalletPayment` sınıfları bu davranışı birbirinden bağımsız olarak uygular. Stratejiler konsola yazı yazmaz; yalnızca ödeme sonucunu üretir. Bu tercih, ödeme yöntemine özgü kodun servis ve kullanıcı arayüzü katmanlarından ayrılmasını sağlar.

### 3. Factory Pattern

Factory, kullanılacak somut nesnenin oluşturulmasını merkezi bir noktada toplar. `PaymentFactory`, kendisine verilen `PaymentType` değerine göre uygun `PaymentStrategy` uygulamasını üretir. Böylece `PaymentService`, somut ödeme sınıflarını seçme ayrıntısıyla ilgilenmeden ortak arayüz üzerinden çalışır.

## Proje Mimarisi

```text
PaymentDemo/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    ├── main/java/com/paymentdemo/
    │   ├── Main.java
    │   ├── factory/
    │   │   └── PaymentFactory.java
    │   ├── model/
    │   │   ├── PaymentType.java
    │   │   ├── PaymentStatus.java
    │   │   └── PaymentResult.java
    │   ├── singleton/
    │   │   └── PaymentService.java
    │   └── strategy/
    │       ├── PaymentStrategy.java
    │       ├── CreditCardPayment.java
    │       ├── BankTransferPayment.java
    │       └── DigitalWalletPayment.java
    └── test/java/com/paymentdemo/
        ├── factory/PaymentFactoryTest.java
        ├── singleton/PaymentServiceTest.java
        └── strategy/PaymentStrategyTest.java
```

Ödeme isteği `Main` sınıfından alınır ve Singleton `PaymentService` nesnesine iletilir. Servis tutarı doğruladıktan sonra `PaymentFactory` aracılığıyla seçilen türe uygun stratejiyi oluşturur. Ödeme, ortak `PaymentStrategy` arayüzü üzerinden ilgili somut sınıf tarafından simüle edilir. Stratejinin ürettiği `PaymentResult`, servis üzerinden `Main` sınıfına döner ve yalnızca burada ekrana yazdırılır.

```mermaid
flowchart TD
    Main[Main] --> Service[PaymentService<br/>Singleton]
    Service --> Factory[PaymentFactory<br/>Factory]
    Factory --> Strategy[PaymentStrategy<br/>Interface]
    Strategy --> Credit[CreditCardPayment]
    Strategy --> Bank[BankTransferPayment]
    Strategy --> Wallet[DigitalWalletPayment]
    Credit --> Result[PaymentResult<br/>Immutable Record]
    Bank --> Result
    Wallet --> Result
    Result --> Main
```

## Uygulamanın Çalışma Mantığı

1. `Main`, desteklenen ödeme yöntemlerini konsolda listeler.
2. Kullanıcı bir ödeme yöntemi seçer ve ödeme tutarını girer.
3. Menü seçimi bir `PaymentType` değerine dönüştürülür.
4. `PaymentService`, tutarın boş olmadığını ve sıfırdan büyük olduğunu doğrular.
5. `PaymentFactory`, ödeme türüne uygun `PaymentStrategy` nesnesini oluşturur.
6. Seçilen stratejinin `pay` metodu benzersiz bir UUID işlem kimliğine ve `SUCCESS` durumuna sahip `PaymentResult` üretir.
7. `PaymentService`, yapılandırılmış sonucu `Main` sınıfına döndürür; konsol çıktısını `Main` oluşturur.
8. Geçersiz seçimler, sayısal olmayan tutarlar, sıfır ve negatif tutarlar kullanıcıya açıklayıcı mesajlarla bildirilir.

## Yapılandırılmış Ödeme Sonucu

Java 21 `record` yapısıyla tanımlanan `PaymentResult` değiştirilemez bir sonuç modelidir ve şu alanları içerir:

- `UUID transactionId`: Her başarılı demo ödemesinde yeniden üretilen benzersiz işlem kimliği
- `PaymentType paymentType`: Kullanılan ödeme yöntemi
- `BigDecimal amount`: Hassasiyet kaybı olmadan saklanan ödeme tutarı
- `PaymentStatus status`: İşlemin `SUCCESS` veya ileride kullanılabilecek `FAILED` durumu
- `String message`: Ödeme yöntemine özgü sonuç açıklaması

Mevcut demo akışları gerçek bir ödeme altyapısına bağlanmadığı için geçerli tüm işlemler `SUCCESS` üretir. `FAILED` değeri gelecekteki başarısız ödeme senaryolarını modellemek üzere tanımlanmıştır; uygulama yapay hata senaryosu oluşturmaz.

## Konsol Örneği

```text
===== PAYMENT DEMO =====
1 - Kredi Kartı
2 - Havale / EFT
3 - Dijital Cüzdan
0 - Çıkış
Seçiminiz: 1
Ödeme tutarı (TL): 500

===== ÖDEME SONUCU =====
İşlem ID: 550e8400-e29b-41d4-a716-446655440000
Ödeme Yöntemi: Kredi Kartı
Tutar: 500 TL
Durum: SUCCESS
Mesaj: Kredi kartı ile ödeme başarılı.
=========================
```

## Kurulum ve Çalıştırma

Gereksinimler:

- JDK 21
- Maven 3.9 veya üzeri

Projeyi GitHub üzerinden indirdikten sonra terminalde proje dizinine geçin ve aşağıdaki komutları çalıştırın:

```bash
mvn clean compile
mvn test
java -cp target/classes com.paymentdemo.Main
```

Windows PowerShell veya Komut İstemi'nde de proje kök dizininden aynı komutlar kullanılabilir:

```powershell
mvn clean compile
mvn test
java -cp target\classes com.paymentdemo.Main
```

## Testler

Proje toplam **13 JUnit 5 testi** içerir. Testler:

- Factory'nin üç ödeme türü için doğru Strategy sınıfını oluşturmasını,
- null ödeme türünün reddedilmesini,
- Singleton erişimlerinin aynı `PaymentService` nesnesini döndürmesini,
- pozitif tutarın işlenmesini ve null, sıfır ile negatif tutarların reddedilmesini,
- her ödeme stratejisinin doğru tür, tutar, `SUCCESS` durumu ve mesaj içeren bir `PaymentResult` üretmesini,
- işlem kimliğinin boş olmamasını ve farklı ödemelerde farklı UUID değerleri üretilmesini

kontrol eder. Güncel Maven test çalıştırmasında 13 testin tamamı başarılıdır.

## Plug and Play Mantığı

Yeni bir ödeme yöntemi eklemek için mevcut mimari genişletilebilir. Örneğin QR ödeme desteği için:

1. `PaymentStrategy` arayüzünü uygulayan `QrPayment` sınıfı oluşturulur.
2. `PaymentType` enum'una `QR_PAYMENT` değeri eklenir.
3. `PaymentFactory` içindeki `switch` ifadesine bu türü `QrPayment` nesnesiyle eşleyen bir durum eklenir.
4. Konsoldan seçilebilmesi isteniyorsa `Main` sınıfındaki menü ve `paymentTypeFrom` eşlemesi güncellenir.
5. Factory ve Strategy davranışları için ilgili JUnit testleri eklenir.

Bu proje otomatik eklenti keşfi yapmaz; yeni bir ödeme yöntemi yukarıdaki açık genişletme noktalarına kod eklenerek sisteme dahil edilir.

## Proje Hakkında

PaymentDemo, Java ile nesne yönelimli programlama ve tasarım desenlerini öğrenmek amacıyla geliştirilmiş bir eğitim projesidir. Uygulama yalnızca ödeme sürecini konsolda simüle eder; gerçek para transferi gerçekleştirmez ve herhangi bir ödeme kuruluşuyla bağlantı kurmaz.
