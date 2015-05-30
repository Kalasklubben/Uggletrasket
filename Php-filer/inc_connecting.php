<?php
	 /* Inkluderar konfigurationsvariabler */
	require_once("inc_configuring.php"); 
		// 'require_once' istället för 'include':
		// * require: MÅSTE inkluderas (annars skapar PHP ett allvarligt fel)
		// * once: inkluderas INTE om annat skript redan gjort det (och konfig-vars då redan är satta)
	
	/* skapar - och kollar framgång utav - en anslutning till DBMS (databashanterare) */
	if(!mysql_connect($DB_HOST, $DB_USER, $DB_PASS)){
		die('<p class="error">Kunde inte skapa en DBMS-anslutning till "'.$DB_HOST.'", med felet: ' . mysql_error() . '</p>');
	}
	
	/* Sätter - och kollar framgång för - en databas som aktuell/aktiv att arbeta med */
	if(!mysql_select_db($DB_DATABASE)){
		die('<p class="error">Kunde inte välja databasen "'.$DB_DATABASE.'", med felet: ' . mysql_error() . '</p>');
	}
	
?>