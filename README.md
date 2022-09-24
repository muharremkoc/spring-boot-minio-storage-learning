# spring-boot-minio-storage-learning


## Goal
Spring Boot File Storage with NinIO and Bucket Notification with PostgreSQL

## Technologies
- Spring Boot
- MINIO
- Lombok
- PostgreSQL
- Spring Boot Web
- OpenAPI
- Docker

## Install Documentation
İlk olarak PostgreSQL ile ilgili gerekli ayarlamaları yaptıktan sonra pgAdmin ekranına bağlanıyoruz.(Spring Boot ayarlarımıza uygun olacak şekilde yapılandırmamızı yapıyoruz)

![image](https://user-images.githubusercontent.com/80245013/192085202-feeb4126-d467-4d5b-bf06-56c7225c6b0d.png)

İkinci Olarak 127.0.0.1:9000 URL'sinden MiniIO Console penceresinde kullanıcı ve şifremizle giriş yapıyoruz.

![image](https://user-images.githubusercontent.com/80245013/192085263-7ef0d79a-f24b-4966-b2a8-ad2398e01113.png)

Üçüncü Olarak Containerimizin içerisine girerek aynı veriler ile bir alias oluşturuyoruz.

![image](https://user-images.githubusercontent.com/80245013/192085281-43223d28-36af-43dd-b0d3-2af7e381ae4c.png)

Dördüncü Olarak Spring Boot Projemizde bir bucket oluşturuyoruz.

![image](https://user-images.githubusercontent.com/80245013/192085292-328e0c9c-45b6-473f-a3a2-f8e7dc7b24d4.png)

Beşinci Olarak,MiniIO Console ekranına gelerek Notifications' alanına gelerek bir Notification Target oluşturuyoruz.(PostgreSQL yaptığımız için PostgreSQL seçiyoruz)

![image](https://user-images.githubusercontent.com/80245013/192085308-8e26a4ac-3ec1-412d-b260-54eb54a7c6d8.png)

Altıncı olarak PostgreSQL konfigürasyonlarımızı buraya gerekli istekleri karşılayacak şekilde kaydediyoruz.

NOT:Burada oluşturalacak Tablo yeni bir tablo meydana getirecektir.

Eğer Minio'nun kaydedeceği verileri biliyorsak kendi tablomuza kaydedebiliriz.)

(Console Penceresinden düzenleme)

![image](https://user-images.githubusercontent.com/80245013/192085316-9f578b29-25ed-45d2-8b91-e04942fe9254.png)

(Container Penceresinden düzenleme)

mc --debug admin config set myminio notify\_postgresql

connection\_string=" **host=postgresql database=miniodb user=root password=password port=5432 sslmode=disable**"

table="files"

format="namespace"


Yedinci kısımın ilk işlemi olaram "mc admin info "ALIAS" –json" komutu ile postgresql alanımızı kontrol ediyoruz.

![image](https://user-images.githubusercontent.com/80245013/192085331-930a720e-c5e5-4c96-a107-881ab92e3105.png)


Yedinci kısmın ikinci maddesi olarak eventleri düzenleyeceğimiz bir ARN yapısı düzenliyoruz.

"$ mc admin info --json myminio | jq .info.sqsARN"

![image](https://user-images.githubusercontent.com/80245013/192085335-7851dc4b-a31c-4271-ac81-8ddc7cab51f9.png)

Sekizinci Kısımda Olayları Ekleme evresine geçiyoruz.

(Spring Boot Projesinde kullanacağımız için İki Evreye ayırıyoruz)

(Container Penceresinden düzenleme)

"mc event add ALIAS/BUCKET arn:minio:sqs::\_:postgresql –event put,delete,get" komutu ile event olaylarını postgresql ile ilişkilendirilmiş alias yapımıza ekliyoruz

- ALIAS/BUCKET:Olaylarını Dinlemek istediğimiz alias ve bucket kısmını belirliyoruz.
- arn:minio:sqs::\_:postgresql-Notification alanından aldığımız jq yapısı
- --event komutu ile hangi olaylar postgresql tarafından dinlensin onu belirliyoruz.

**Dipnot:**** Ben tüm dosyaları dinlemek istediğim için herhangi bir filtreleme belirlemedim.**

**Sadece resim uzantılı dosyaları dinlemek isterseniz:**

"mc event add "ALIAS/BUCKET arn:minio:sqs::\_:postgresql –event put,delete,get --suffix .jpg"

İle Sadece .jp uzantılı dosyaları kaydedebilirsiniz.


(Java IDE Penceresinden düzenleme)

![image](https://user-images.githubusercontent.com/80245013/192085345-5878a36a-5dfd-4c75-8143-f20a9d97ef20.png)

- Events dizisi ile konsol ekranında kaydettiğimiz put,delete,get eventlerini alıyoruz.
- bucket ile dinlemek istediğimiz bucket nesnesini
- events ile dinlenecek olayları
- Ek olarak Görsel'de olmayan .suffix ile dinlemek için konsol da belirttiğimiz filtreyi buraya ekleyebiliriz.

![image](https://user-images.githubusercontent.com/80245013/192085358-dfe82bb6-7d66-4d26-a81d-71a7365ca6f8.png)


![image](https://user-images.githubusercontent.com/80245013/192085365-bf9f33a7-0a6c-4d72-82fd-20a292657b12.png)

Şekilde görüldüğü gibi herhangi bir dosya ekleme vs. işlemini buradan görüntüleyebiliyoruz.


## Owner
[Muharrem Koç](https://github.com/muharremkoc)
