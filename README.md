# Uebung1

Diese Applikation liest Parlamentsprotokolle in einem XML-Dokument aus und organisiert die Daten in geeigneten Datenstrukturen. Die Daten werden anschließend analysiert um Aussagen über die Parlamentssitzung treffen zu können.

**Bedinung der Applikation:**
- Das Programm beinhaltet zwei Abfrage Masken, die einen Input des User entgegen nehmen:
1. **Eingabe des Dateipfades:** Hier geben Sie bitte den Datei Pfad des Zip Ordners, der die Parlamentsprotokolle beinhaltet, ein. Beispieleingabe:"C:\Users\leander\PRG2\Bundestag_19.zip"
2. **Eingabe der gewünschten Aufgabe:** Hier werden Sie aufgefordert sich eine gewünschte Ausgabe auszusuchen. Dabei steht ihnen eine Auswahl von 8 möglichen Ausgaben zu Verfügung. Durch die Eingabe, der vor der Aufgabe in Klammer stehende Zahl, erhalten Sie die passende Lösung. Beispieleingabe: "1". In folge dessen werden ihnen alle Redner samt Partei ausgegeben.

**Zuordnung der Aufgaben zur den Auswahlmöglichkeiten der Zweiten Maske:**
- "[1]: Auflistung aller Redner" **Aufgabenstellung:** Auflistung aller Redner*innen inkl. Partei- / Fraktionszugehörigkeit
- "[2]: Nach dem Namen bestimmter Redner Filtern" **Aufgabenstellung:** Implementieren Sie einen Filter für die Namen der Abgeordneten
- "[3]: Auflistung aller Redner nach Partei/Fraktion" **Aufgabenstellung:** Auflistung aller Redner pro Partei / Fraktion
- "[4]: Ausgabe des Textes eines bestimmten Tagesordnungspunktes" **Aufgabenstellung:** Ausgabe des Textes eines Tagesordnungspunktes gemäß Sitzungs- und Nummern-Index!
- "[5]: Ausgabe der Top 5 Tagesordnungspunkte mit den meisten Zwischenrufen" **Aufgabenstellung:** Listen Sie die Redebeiträge mit den meisten Zurufen absteigend sortiert auf
- "[6]: Durchschnittliche Redelänge aller Beiträge" **Aufgabenstellung:** Wie lang ist der durchschnittliche Redebeitrag aller Beiträge?
- "[7]: Durchschnittliche Redelänge aller Redner" **Aufgabenstellung:** Wie lang ist der durchschnittliche Redebeitrag je Abgeordnetem?
- "[0]: Exit" Beendet die  Applikation.

**Anmerkungen**
- Bei falscher Eingabe werden Sie darauf hingewiesen das die Eingabe nicht zulässig ist und werden dann aufgefordert erneut eine Eingabe zu tätigen.
- Das Einlesen der Xml Dokumente nimmt etwas Zeit in Anspruch, weshalb sie eine wenig Geduld haben müssen, bevor die zweite Abfrage Maske erscheint.

**Aufgabe 2f)**
1. Zur Aufgabe **Auflistung aller Redner pro Partei / Fraktion**: Diese Ausgabe ist über die Eingabe "3" abrufbar. Auffällig ist, dass BÜNDNIS 90/DIE GRÜNEN mehrmals auftaucht, jedoch in minimal abgeänderter Schreibweise. Das liegt daran das in den Protokollen keine einheitliche Schreibweise dieses Parteinamens erfolgt ist. Da in den Übungen gesagt wurde, dass wir so wenig wie möglich "hart coden" sollen um das ganze so dynamisch wie möglich zu gestalten, hab ich mich dazu entschieden diese unterschiedlichen Schreibweisen auch gesondert auszugeben.
2. Zur Aufgabe **Implementieren Sie einen Filter für die Namen der Abgeordneten.**: Bei dieser Aufgabe werden Sie nach der zweiten Abfrage Maske gefragt nach welchem Suchstring Sie filtern wollen. Hierbei werden dann alle Abgeordneten ausgegeben, die diese Zeichenkette im Namen haben. Zu beachten ist das die Suche Case Sensitiv ist. Sollten Sie also nach "angela" suchen, so werden Sie nicht, wie vielleicht vermutet, Angela Merkel als Ausgabe bekommen, da es kleingeschrieben ist.
3. Zur Aufgabe **Ausgabe des Textes eines Tagesordnungspunktes gemäß Sitzungs- und Nummern-Index!**: Hier werden Sie zusätzlich gebeten zunächst den Sitzungsindex (1-239) einzugeben und anschließend den Nummern-Index (1 - Maximale Nummern-Index). Anschließend wird in der Inhalt dieses Tagesordnungspunktes ausgegeben. Dabei werden alle Inhalt, die mit einem p oder kommentar Tag gekennzeichet sind, ausgegeben.

**Aufgabe 3)**
1) Zur Aufgabe **Wie lang ist der durchschnittliche Redebeitrag je Abgeordnetem?**: Aufgrund von Fehler in der ID Verteilung wird die durchschnittliche Redelänge pro Abgeordneter nicht für alle korrekt ausgegeben. Siehe z.B.: Sitzungsnummer 1: Sowohl Alterspräsident Dr. Hermann Otto Solms als auch Marco Buschmann haben die selbe ID (11002190).
2) Zur Aufgabe **Listen Sie die Redebeiträge mit den meisten Zurufen absteigend sortiert auf**:
   Da Kommentare auch Dinge wie zum Beispiel "Beifall bei der CDU/CSU und der SPD" sein können muss man die Tags mit Kommentar noch einmal Filtern. Da ich mir nicht sicher war, woran genau man ein Zuruf erkennt habe ich nach dem Zeichen "[" gefilter. Denn, wenn in einem Kommentar ein Politiker spricht, immer dessen Partei in Klammern dahinter steht.
Des weiteren war ich unentschlossen darüber, woran man den Titel eines Tagesordnungspunkt erkennt. Ich habe dafür die p Element mit dem Attribut T_NaS und T_fett rausgefiltert.