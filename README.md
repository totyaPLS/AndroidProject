# AndroidProject

## Javítási szempontok
> Ez esetleg arra jó lehet, hogy megkönnyítse/ meggyorsítsa a javítást a projektben. A checkboxokat használdd nyugodtan arra, ha szerinted az megfelelt a követelményeknek. A linkek szövegében a pl.: "(#54)" a fájlban lévő sorszámot jelenti.

- [ ] Fordítási hiba nincs 

- [ ] Futtatási hiba nincs

- [ ] Firebase autentikáció meg van valósítva: [Be lehet jelentkezni (#54)](./app/src/main/java/com/app/nutritionalsupplements/activities/LoginActivity.java) és [regisztrálni (#109)](./app/src/main/java/com/app/nutritionalsupplements/activities/SignUpActivity.java)

- [ ] Adatmodell definiálása (class vagy interfész formájában)

- [ ] Legalább 3 különböző activity vagy fragmens használata

- [ ] Beviteli mezők beviteli típusa megfelelő (jelszó kicsillagozva, email-nél megfelelő billentyűzet jelenik meg stb.)

- [ ] [ConstraintLayout](./app/src/main/res/layout/activity_main.xml) és még egy [másik layout](./app/src/main/res/layout/activity_login.xml) típus használata

- [ ] Reszponzív:  
    - különböző kijelző méreteken is jól jelennek meg a GUI elemek (akár tableten is)  
    - elforgatás esetén is igényes marad a layout

- [ ] Legalább [2 különböző animáció (#61, #62)](./app/src/main/java/com/app/nutritionalsupplements/adapters/ProductAdapter.java) használata

- [ ] Intentek használata: navigáció meg van valósítva az activityk/fragmensek között  
(minden activity/fragmens elérhető)

- [ ] Legalább [egy Lifecycle Hook (#64)](./app/src/main/java/com/app/nutritionalsupplements/activities/MainActivity.java) használata a teljes projektben:  
    - onCreate nem számít  
    - az alkalmazás funkcionalitásába értelmes módon beágyazott, azaz pl. nem csak egy logolás

- [ ] Legalább egy olyan [androidos erőforrás használata](./app/src/main/java/com/app/nutritionalsupplements/Device.java), amihez [kell android permission (#5)](./app/src/main/AndroidManifest.xml)

- [ ] Legalább egy [notification](./app/src/main/java/com/app/nutritionalsupplements/NotificationHandler.java) vagy alam manager vagy job scheduler használata

- [ ] [CRUD műveletek mindegyike (#250-től lefele)](./app/src/main/java/com/app/nutritionalsupplements/activities/MainActivity.java) megvalósult és az adatbázis műveletek a konvenciónak  
megfelelően külön szálon történnek

- [ ] [Legalább 2 komplex Firestore lekérdezés (#191, #275)](./app/src/main/java/com/app/nutritionalsupplements/activities/MainActivity.java) megvalósítása, amely indexet igényel (ide tartoznak: where feltétel, rendezés, léptetés, limitálás)

- [ ] Szubjektív pontozás a projekt egészére vonatkozólag:  
ez 5-ről indul és le lehet vonni, ha igénytelen, összecsapott, látszik hogy nem foglalkozott vele, kísértetiesen hasonlít a videóban létrehozotthoz stb.


*DC elérhetőségem ha vmi gond lenne:* **Ákos#2982**