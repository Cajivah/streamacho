insert into chat_message(id, author_username, chat_id, send_at, text) values
(1, 'malyjasiak', 2, '2017-06-22 23:48:41', 'Początek traktatu czasu być zelżywemi'),
(2, 'petra', 2, '2017-06-22 23:48:41', 'lub przerwy? Czemuż on jest pobudką do czego kto '),
(3, 'adam', 2, '2017-06-22 23:48:41', 'zadał to miało miejsce, tedyby to stało obarczeniem'),
(4, 'admin', 2, '2017-06-22 23:48:41', 'pisowni mojej znajduje się trafią gdzie uczynki nie ze'),
(5, 'malyjasiak', 2, '2017-06-22 23:48:41', 'względu na siebie samych tylko były przypadłościami innej rzeczy, lecz Istneść najrealniejsza'),
(6, 'malyjasiak', 2, '2017-06-22 23:48:41', 'Istność musi religia jest kolejne następowanie. Dobro był w dziewięćdziesiątym roku 184 wydał mój nie w takiejże biedzie i dobrego czynu, '),
(7, 'Michal', 2, '2017-06-22 23:48:41', 'nie dał mu nic dobrze czyniono'),
(8, 'lordoffire', 2, '2017-06-22 23:48:41', 'Porównawszy kary są właśnie na'),
(9, 'anon', 2, '2017-06-22 23:48:41', 'owo zakończenie '),
(10, 'Słowacki', 5, '2017-06-22 21:48:41', 'Na górze pierniczki, na dole ciasta, Adam Mickiewicz to pederasta'),
(11, 'Mickiewicz', 5, '2017-06-22 22:48:41', 'Na górze wacki, na dole wacki, kto lubi wacki? Juliusz Słowacki'),
(12, 'Słowacki', 5, '2017-06-22 22:50:41', 'Bursztynowy jest świerzop, gryka jak śnieg biała, Panienieńskim jest rumieniec, zaś Mickiewicz pała'),
(13, 'Skłodowska', 5, '2017-06-22 23:48:41', 'Słowacki to pedał, Mickiewicz to dziad, umarłam na raka bo wąchałam rad'),
(14, 'Dumbledore', 9, '2017-06-22 23:48:41', '10 points for Gryffindor!')
;

-- workaround for sequence not being updated
select setval('hibernate_sequence', 1000);
