<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Lägga till data</title>
</head>

<body>
	<?php 
		
		// Se till att ha en databasanslutning
		include("inc_connecting.php"); // Se källkod för databasanslutning
		
		$Id=$_POST ['Id'];
		$Name=$_POST ['Name'];
		$Password=$_POST ['Password'];

		
		
		// Skapa en SQL-sträng för instruktionen
		// För referens och detaljer se http://www.w3schools.com/sql/sql_insert.asp
		$sql = "INSERT INTO Quiz (Id, Name, Password) VALUES ('{$Id}', '{$Name}', '{$Password}')";
		
		// Skicka in SQL'n till databashanteraren
		$resultat = mysql_query($sql);
		
			
	print mysql_error();
	
		if($resultat > 0){
			print "bra";
		} else {
			print "dåligt";
		}
	
		// Datan är nu tillagd
		
	?>
</body>
</html>